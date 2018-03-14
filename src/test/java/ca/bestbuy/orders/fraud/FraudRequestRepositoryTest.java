/**
 * 
 */
package ca.bestbuy.orders.fraud;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestHistoryDetail;
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
	@Transactional(readOnly=true)
	public void testInitialRequestCreation(){
		
		FraudRequest request = new FraudRequest();
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		request.setFraudRequestType(fraudCheckType);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatuses.INITIAL_REQUEST);
		request.setFraudStatus(status);
		
		String userName = "order_fraud_test";
		Date now = new Date();
		request.setCreateDate(now);
		request.setCreateUser(userName);
		request.setUpdateDate(now);
		request.setUpdateUser(userName);
		request.setOrderNumber(new BigDecimal("123456"));
		request.setRequestVersion(new BigDecimal(1));
		
		FraudRequestHistory history = new FraudRequestHistory();
		history.setFraudRequest(request);
		history.setFraudRequestType(fraudCheckType);
		history.setFraudStatus(status);
		history.setOrderNumber(new BigDecimal("123456"));
		history.setRequestVersion(new BigDecimal(1));
		history.setCreateDate(now);
		history.setCreateUser(userName);
		history.setUpdateDate(now);
		history.setUpdateUser(userName);
		List<FraudRequestHistory> historyList = new ArrayList<>();
		historyList.add(history);
		
		request.setFraudRequestHistory(historyList);
		
		fraudRequestRepository.save(request);
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assert(result.getFraudRequestType().getRequestTypeCode()==FraudRequestType.RequestTypes.FRAUD_CHECK);
		assert(result.getFraudRequestHistory().size()>0);
	}

	@Test
	@Transactional(readOnly=true)
	public void testStatusUpdate(){
		FraudRequest request = new FraudRequest();
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		request.setFraudRequestType(fraudCheckType);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatuses.DECISION_MADE);
		request.setFraudStatus(status);
		
		String userName = "order_fraud_test";
		Date now = new Date();
		request.setCreateDate(now);
		request.setCreateUser(userName);
		request.setUpdateDate(now);
		request.setUpdateUser(userName);
		request.setOrderNumber(new BigDecimal("123456"));
		request.setRequestVersion(new BigDecimal(1));

		List<FraudRequestHistory> historyList = new ArrayList<>();
		FraudRequestHistory history = new FraudRequestHistory();
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

		request.setFraudRequestHistory(historyList);
		fraudRequestRepository.save(request);

		FraudRequestHistoryDetail historyDetail = new FraudRequestHistoryDetail();
		historyDetail.setFraudRequestHistoryId(history.getFraudRequestHistoryId());
		historyDetail.setFraudRequestHistory(history);
		historyDetail.setTasRequest("Test TAS Request");
		historyDetail.setTasResponse("Test TAS Response");
		historyDetail.setCreateDate(now);
		historyDetail.setCreateUser(userName);
		historyDetail.setUpdateDate(now);
		historyDetail.setUpdateUser(userName);
		history.setFraudRequestHistoryDetail(historyDetail);

		fraudRequestRepository.save(request);
		
		FraudRequest result = fraudRequestRepository.findOne(request.getFraudRequestId());
		assert(result.getFraudStatus().getFraudStatusCode()==FraudStatus.FraudStatuses.DECISION_MADE);
		assert(result.getFraudRequestHistory().size()==1);
		assert(result.getFraudRequestHistory().get(0).getFraudRequestHistoryDetail().getFraudRequestHistoryId()==result.getFraudRequestHistory().get(0).getFraudRequestHistoryId());
	}

	@Test
	public void testRetrieveFraudRequests(){
		
	}

	@Test
	public void testRetrieveStatusHistory(){
		
	}
	
}
