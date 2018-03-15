/**
 * 
 */
package ca.bestbuy.orders.fraud;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudRequestRepositoryTest {

	@MockBean
	private OrderDetailsClientConfig orderDetailsClientConfig;
	
	@MockBean
	OrderDetailsClient orderDetailsClient;
	
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
		assert(result.getFraudRequestType().getRequestTypeCode()==FraudRequestType.RequestTypes.FRAUD_CHECK);
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

		Iterable<FraudRequest> requestsRetrieved2 =fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(new BigDecimal(orderNumber), new BigDecimal(3l));
		List<Long> requestVersionsExpected = Arrays.asList(new Long[]{3l,4l,5l});
		for(FraudRequest request:requestsRetrieved2){
			assertTrue(request.getOrderNumber().toString().equals(orderNumber));
			assertTrue(requestVersionsExpected.contains(request.getRequestVersion().longValue()));
			
		}
	}

	
	@Test
	@Transactional(readOnly=true)
	public void testStatusUpdate(){
		FraudRequest request = new FraudRequest();
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		request.setFraudRequestType(fraudCheckType);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatusCodes.DECISION_MADE);
		request.setFraudStatus(status);
		
		String userName = "order_fraud_test";
		Date now = new Date();
		request.setCreateDate(now);
		request.setCreateUser(userName);
		request.setUpdateDate(now);
		request.setUpdateUser(userName);
		request.setOrderNumber(new BigDecimal("123456"));
		request.setRequestVersion(new BigDecimal(1));

		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		FraudRequestStatusHistory history = new FraudRequestStatusHistory();
		historyList.add(history);
		history.setFraudRequest(request);
		history.setFraudRequestType(fraudCheckType);
		history.setFraudStatus(status);
		history.setOrderNumber(new BigDecimal("123456"));
		history.setRequestVersion(new BigDecimal(1));
		history.setCreateDate(now);
		history.setCreateUser(userName);
		history.setUpdateDate(now);
		history.setUpdateUser(userName);

		request.setFraudRequestStatusHistory(historyList);
		request = fraudRequestRepository.save(request);

		FraudRequestStatusHistoryDetail historyDetail = new FraudRequestStatusHistoryDetail();
		historyDetail.setFraudRequestStatusHistoryId(history.getFraudRequestStatusHistoryId());
		historyDetail.setFraudRequestStatusHistory(history);
		historyDetail.setTasRequest("Test TAS Request");
		historyDetail.setTasResponse("Test TAS Response");
		historyDetail.setCreateDate(now);
		historyDetail.setCreateUser(userName);
		historyDetail.setUpdateDate(now);
		historyDetail.setUpdateUser(userName);
		history.setFraudRequestStatusHistoryDetail(historyDetail);

		request = fraudRequestRepository.save(request);
		
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assert(result.getFraudStatus().getFraudStatusCode()==FraudStatus.FraudStatusCodes.DECISION_MADE);
		assert(result.getFraudRequestStatusHistory().size()==1);
		assert(result.getFraudRequestStatusHistory().get(0).getFraudRequestStatusHistoryDetail().getFraudRequestStatusHistoryId()==result.getFraudRequestStatusHistory().get(0).getFraudRequestStatusHistoryId());
	}

	//private
	private FraudRequest createAndSaveFraudRequest(String orderNumber, long requestVersion) {
		FraudRequest request = new FraudRequest();
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		request.setFraudRequestType(fraudCheckType);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatusCodes.INITIAL_REQUEST);
		request.setFraudStatus(status);
		
		String userName = "order_fraud_test";
		Date now = new Date();
		request.setCreateDate(now);
		request.setCreateUser(userName);
		request.setUpdateDate(now);
		request.setUpdateUser(userName);
		request.setOrderNumber(new BigDecimal(orderNumber));
		request.setRequestVersion(new BigDecimal(requestVersion));
		
		FraudRequestStatusHistory history = new FraudRequestStatusHistory();
		history.setFraudRequest(request);
		history.setFraudRequestType(fraudCheckType);
		history.setFraudStatus(status);
		history.setOrderNumber(new BigDecimal(orderNumber));
		history.setRequestVersion(new BigDecimal(requestVersion));
		history.setCreateDate(now);
		history.setCreateUser(userName);
		history.setUpdateDate(now);
		history.setUpdateUser(userName);
		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		historyList.add(history);
		
		request.setFraudRequestStatusHistory(historyList);
		
		request = fraudRequestRepository.save(request);
		return request;
	}

	
}
