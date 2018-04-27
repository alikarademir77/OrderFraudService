/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"unittest"})
@DirtiesContext
public class FraudRequestTypeRepositoryTest {
	
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	@Test
	@Transactional
	public void testFindAll(){
		Iterable<FraudRequestType> it = typeRepository.findAll();
		List<FraudRequestType.RequestTypeCodes> allTypesList  = Arrays.asList(FraudRequestType.RequestTypeCodes.values());

		for(FraudRequestType type:it){
			assertTrue(allTypesList.contains(type.getRequestTypeCode()));
		}
	}

	@Test
	@Transactional
	public void testFindOne(){
		FraudRequestType fraudRequestType = typeRepository.findOne(FraudRequestType.RequestTypeCodes.ORDER_CANCEL);
		
		assertEquals(FraudRequestType.RequestTypeCodes.ORDER_CANCEL,fraudRequestType.getRequestTypeCode());
		
	}
	
}
