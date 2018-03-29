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
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudRequestTypeRepositoryTest {

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
	FraudRequestTypeRepository typeRepository;
	
	@Test
	@Transactional
	public void testFindAll(){
		Iterable<FraudRequestType> it = typeRepository.findAll();
		List<FraudRequestType.RequestTypes> allTypesList  = Arrays.asList(FraudRequestType.RequestTypes.values());

		for(FraudRequestType type:it){
			assertTrue(allTypesList.contains(type.getRequestTypeCode()));
		}
	}

	@Test
	@Transactional
	public void testFindOne(){
		FraudRequestType fraudRequestType = typeRepository.findOne(FraudRequestType.RequestTypes.ORDER_CANCEL);
		
		assertEquals(FraudRequestType.RequestTypes.ORDER_CANCEL,fraudRequestType.getRequestTypeCode());
		
	}
	
}
