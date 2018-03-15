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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestStatusHistoryRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudRequestStatusHistoryRepositoryTest {

	@MockBean
	private OrderDetailsClientConfig orderDetailsClientConfig;
	
	@MockBean
	OrderDetailsClient orderDetailsClient;
	
	@Autowired
	FraudStatusRepository statusRepository;
	
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	@Autowired
	FraudRequestStatusHistoryRepository fraudRequestStatusHistoryRepository;

	@Autowired
	FraudRequestRepository fraudRequestRepository;


	@Test
	@Transactional
	public void testFraudRequestStatusHistoryRetrieval(){
		String orderNumber = "123456";
		
		List<Long> requestVersions = Arrays.asList(new Long[]{1l,2l,3l,4l,5l});
		
		
		for(Long nextVersion:requestVersions){
			createAndSaveFraudRequest(orderNumber, nextVersion);
		}
		
		Iterable<FraudRequestStatusHistory> requestHistoriesRetrieved = fraudRequestStatusHistoryRepository
				.findByFraudRequestOrderNumber(new BigDecimal(orderNumber), new Sort(Direction.DESC, "createDate"));
		
		long fraudRequestStatusHistoryId = 0;
		for(FraudRequestStatusHistory requestStatusHistory:requestHistoriesRetrieved){
			assertTrue(requestStatusHistory.getFraudRequest().getOrderNumber().toString().equals(orderNumber));
			assertTrue(requestVersions.contains(requestStatusHistory.getFraudRequest().getRequestVersion().longValue()));
			fraudRequestStatusHistoryId =  requestStatusHistory.getFraudRequestStatusHistoryId();
		}

		FraudRequestStatusHistory requestStatusHistoryRetrieved = fraudRequestStatusHistoryRepository
				.findOne(fraudRequestStatusHistoryId);
		assertNotNull(requestStatusHistoryRetrieved);
		assertTrue(requestStatusHistoryRetrieved.getFraudRequestStatusHistoryId() == fraudRequestStatusHistoryId);

		Iterable<FraudRequestStatusHistory> requestsHistoryRetrieved2 = fraudRequestStatusHistoryRepository
				.findByFraudRequestOrderNumberAndFraudRequestRequestVersion(new BigDecimal(orderNumber),
						new BigDecimal(2l), new Sort(Direction.DESC, "createDate"));
		for(FraudRequestStatusHistory requestStatusHistory:requestsHistoryRetrieved2){
			assertTrue(requestStatusHistory.getFraudRequest().getOrderNumber().toString().equals(orderNumber));
			assertTrue(requestStatusHistory.getFraudRequest().getRequestVersion().longValue() == 2l);
			
		}
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
