/**
 * 
 */
package ca.bestbuy.orders.fraud;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.FraudServiceTASClientConfig;
import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusEvents;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
//@DirtiesContext
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
	
	//@Test
	@Transactional
	public void testInitialRequestCreation(){
		
		String orderNumber = "123456";
		long requestVersion = 1; 
		
		FraudRequest request = createAndSaveFraudRequest(orderNumber, requestVersion);
		
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assert(result.getFraudRequestType().getRequestTypeCode()==FraudRequestType.RequestTypeCodes.FRAUD_CHECK);
		assert(result.getFraudRequestStatusHistory().size()>0);
	}
	
	//@Test
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

//	@Test
	public void testVersionUpdate() {

		testCreateForUpdate();
		testUpdate();
	}
	
	@Transactional
	public void testCreateForUpdate() {
		String orderNumber = "123456";

		List<Long> requestVersions = Arrays.asList(new Long[] { 1l });
		FraudRequest request = null;
		for (Long nextVersion : requestVersions) {
			request = createAndSaveFraudRequest(orderNumber, nextVersion);
		}
	}
	
	@Transactional
	public void testUpdate() {
		String orderNumber = "123456";

		long requestVersions = 1L;
		Iterable<FraudRequest> it = fraudRequestRepository.findByOrderNumber(new BigDecimal(orderNumber));
		FraudRequest request = it.iterator().next();
		request.setCreateDate(new Date());

		request = fraudRequestRepository.save(request);
	}
	
	
	@Test
	@Transactional
	public void testStatusUpdate(){
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypeCodes.FRAUD_CHECK);
		FraudStatus status = statusRepository.findOne(FraudStatusCodes.FINAL_DECISION);

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
				//.setFraudStatus(status)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);

		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		historyList.add(history);
		request.setFraudRequestStatusHistory(historyList);
		request = fraudRequestRepository.save(request);

		request = fraudRequestRepository.findOne(request.getFraudRequestId());
		
		FraudRequestStatusHistoryDetail historyDetail = new FraudRequestStatusHistoryDetail();
		historyDetail.setFraudRequestStatusHistoryId(history.getFraudRequestStatusHistoryId())
					 .setFraudRequestStatusHistory(history)
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
		assertEquals(result.getFraudRequestStatusHistory().get(0).getFraudRequestStatusHistoryDetail().getFraudRequestStatusHistoryId(),result.getFraudRequestStatusHistory().get(0).getFraudRequestStatusHistoryId());
	}

	//private
	private FraudRequest createAndSaveFraudRequest(String orderNumber, long requestVersion) {
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypeCodes.FRAUD_CHECK);
		FraudStatus status = statusRepository.findOne(FraudStatusCodes.INITIAL_REQUEST);

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
				//.setFraudStatus(status)
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
