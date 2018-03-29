/**
 * 
 */
package ca.bestbuy.orders.fraud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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

import ca.bestbuy.orders.fraud.client.orderdetails.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.orderdetails.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.client.paymentservice.PaymentServiceClient;
import ca.bestbuy.orders.fraud.client.paymentservice.PaymentServiceClientConfig;
import ca.bestbuy.orders.fraud.client.tas.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.tas.FraudServiceTASClientConfig;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudStatusRepositoryTest {
	
	@MockBean
	private OrderDetailsClientConfig orderDetailsClientConfig;
	
	@MockBean
	OrderDetailsClient orderDetailsClient;

	@MockBean
	private FraudServiceTASClientConfig fraudServiceTASClientConfig;

	@MockBean
	private FraudServiceTASClient fraudServiceTASClient;

	@MockBean
	private PaymentServiceClientConfig paymentServiceClientConfig;

	@MockBean
	private PaymentServiceClient paymentServiceClient;
	
	@Autowired
	FraudStatusRepository fraudStatusRepository;
	
	@Test
	@Transactional
	public void testFindAll(){
		Iterable<FraudStatus> it = fraudStatusRepository.findAll();
		List<FraudStatus.FraudStatusCodes> allStatusList  = Arrays.asList(FraudStatus.FraudStatusCodes.values());

		for(FraudStatus status:it){
			assertTrue(allStatusList.contains(status.getFraudStatusCode()));
		}
	}

	@Test
	@Transactional
	public void testFindOne(){
		FraudStatus fraudStatus = fraudStatusRepository.findOne(FraudStatus.FraudStatusCodes.PENDING_REVIEW);
		
		assertEquals(FraudStatus.FraudStatusCodes.PENDING_REVIEW,fraudStatus.getFraudStatusCode());
		
	}
	
}
