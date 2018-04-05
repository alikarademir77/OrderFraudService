/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.client.orderdetails.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.orderdetails.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.client.tas.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.tas.FraudServiceTASClientConfig;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;
import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusEvents;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudRequestRepositoryTest {

	@MockBean
	private OrderDetailsClientConfig orderDetailsClientConfig;
	
	@MockBean
	OrderDetailsClient orderDetailsClient;

	@MockBean
	private FraudServiceTASClientConfig fraudServiceTASClientConfig;

	@MockBean
	private FraudServiceTASClient fraudServiceTASClient;
	
	@Autowired
	FraudStatusRepository statusRepository;
	
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	@Autowired
	FraudRequestRepository fraudRequestRepository;
	
	@Test
	@Transactional
	public void testInitialRequestCreation(){
		
		String orderNumber = "123456";
		long requestVersion = 1; 
		
		FraudRequest request = createAndSaveFraudRequest(orderNumber, requestVersion);
		
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assert(result.getFraudRequestType().getRequestTypeCode()==FraudRequestType.RequestTypeCodes.FRAUD_CHECK);
		assert(result.getFraudRequestStatusHistory().size()>0);
	}
	
	@Test
	@Transactional
	public void testFraudRequestRetrieval(){
		String orderNumber = "123456";
		
		List<Long> requestVersions = Arrays.asList(new Long[]{1l,2l,3l,4l,5l});
		
		
		for(Long nextVersion:requestVersions){
			createAndSaveFraudRequest(orderNumber, nextVersion);
		}
		
		Iterable<FraudRequest> requestsRetrieved = fraudRequestRepository
				.findByOrderNumber(new BigDecimal(orderNumber));
		
		
		long fraudRequestId = 0;
		for(FraudRequest request:requestsRetrieved){
			assertTrue(request.getOrderNumber().toString().equals(orderNumber));
			assertTrue(requestVersions.contains(request.getRequestVersion().longValue()));
			fraudRequestId =  request.getFraudRequestId();
		}

		FraudRequest requestRetrieved = fraudRequestRepository
				.findOne(fraudRequestId);
		assertNotNull(requestRetrieved);
		assertTrue(requestRetrieved.getFraudRequestId() == fraudRequestId);

		Iterable<FraudRequest> requestsRetrieved2 =fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(new BigDecimal(orderNumber), 3l);
		List<Long> requestVersionsExpected = Arrays.asList(new Long[]{3l,4l,5l});
		for(FraudRequest request:requestsRetrieved2){
			assertTrue(request.getOrderNumber().toString().equals(orderNumber));
			assertTrue(requestVersionsExpected.contains(request.getRequestVersion().longValue()));
			
		}
	}

	@Test
	@Transactional
	public void testStatusUpdate(){
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypeCodes.FRAUD_CHECK);

		String userName = "order_fraud_test";
		Date now = new Date();

		FraudRequest request = new FraudRequest();
		request.setFraudRequestType(fraudCheckType)
				.setOrderNumber(new BigDecimal("123456"))
				.setRequestVersion(1L)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);

		request.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
		
		FraudRequestStatusHistory history = new FraudRequestStatusHistory();
		
		history.setFraudRequest(request)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);

		history.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);

		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		historyList.add(history);
		request.setFraudRequestStatusHistory(historyList);
		
		FraudRequestStatusHistoryDetail historyDetail = new FraudRequestStatusHistoryDetail();
		historyDetail.setFraudRequestStatusHistory(history)
					 .setTasRequest("Test TAS Request")
					 .setTasResponse("Test TAS Response")
					 .setCreateDate(now)
					 .setCreateUser(userName)
					 .setUpdateDate(now)
					 .setUpdateUser(userName);

		history.setFraudRequestStatusHistoryDetail(historyDetail);

		request = fraudRequestRepository.save(request);
		
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assertEquals(result.getFraudStatusStateMachine().getState().getId(),FraudStatusCodes.FINAL_DECISION);

		assertEquals(result.getFraudRequestStatusHistory().size(),1);
		FraudRequestStatusHistory historyRetrieved =  result.getFraudRequestStatusHistory().get(0);
		assertEquals(historyRetrieved.getFraudStatusStateMachine().getState().getId(),FraudStatusCodes.FINAL_DECISION);
		assertNotNull(historyRetrieved.getFraudRequestStatusHistoryDetail());
	}

	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testFraudRequestConcurrentUpdate() {
		String orderNumber = "123456";
		Long requestVersion = 1l;

		FraudRequest fraudRequest = createAndSaveFraudRequest(orderNumber, requestVersion);

		Thread thread = new Thread(new Runnable() {
			@Transactional
			@Override
			public void run() {
				Iterable<FraudRequest> it = fraudRequestRepository.findByOrderNumber(new BigDecimal(orderNumber));
				FraudRequest request = it.iterator().next();
				request.setRequestVersion(request.getRequestVersion() + 1L);
				request = fraudRequestRepository.save(request);
			}
		});

		try {
			thread.start();
			thread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		fraudRequest.setRequestVersion(fraudRequest.getRequestVersion().longValue() + 1L);
		
		// this should throw the ObjectOptimisticLockingFailureException
		fraudRequestRepository.save(fraudRequest);
	}	
	
	private FraudRequest createAndSaveFraudRequest(String orderNumber, long requestVersion) {
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypeCodes.FRAUD_CHECK);

		String userName = "order_fraud_test";
		Date now = new Date();

		FraudRequest request = new FraudRequest();
		request.setFraudRequestType(fraudCheckType)
				.setEventDate(now)
				.setOrderNumber(new BigDecimal(orderNumber))
				.setRequestVersion(requestVersion)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);
		
		FraudRequestStatusHistory history = new FraudRequestStatusHistory();
		history.setFraudRequest(request)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);
		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		historyList.add(history);
		
		request.setFraudRequestStatusHistory(historyList);
		
		request = fraudRequestRepository.save(request);
		return request;
	}

	
}
