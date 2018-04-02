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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
