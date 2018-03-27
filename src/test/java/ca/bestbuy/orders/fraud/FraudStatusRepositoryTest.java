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
import org.springframework.statemachine.StateMachine;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.FraudServiceTASClientConfig;
import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;

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
	
	@Autowired
	FraudStatusRepository fraudStatusRepository;
	
	@Autowired
	StateMachine<FlowStates, FlowEvents> flowStateMachine;
	
	@Test
	@Transactional
	public void testFindAll(){
		Iterable<FraudStatus> it = fraudStatusRepository.findAll();
		List<FraudStatusCodes> allStatusList  = Arrays.asList(FraudStatusCodes.values());

		for(FraudStatus status:it){
			assertTrue(allStatusList.contains(status.getFraudStatusCode()));
		}
	}

	@Test
	@Transactional
	public void testFindOne(){
		FraudStatus fraudStatus = fraudStatusRepository.findOne(FraudStatusCodes.PENDING_REVIEW);
		
		assertEquals(FraudStatusCodes.PENDING_REVIEW,fraudStatus.getFraudStatusCode());
		
	}

//	@Test
//	public void testStateMachine(){
//	
//		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck,
//				"123456", null, "1", new Date());
//		
//		Message<FlowEvents> message = MessageBuilder
//				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
//				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
//				.build();
//		
//		flowStateMachine.sendEvent(message);
//		State<FlowStates, FlowEvents> currentState = flowStateMachine.getState();
//		System.out.println(currentState.toString());
//	}
	
}
