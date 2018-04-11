/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"unittest"})
@DirtiesContext
public class CreateInitialRequestActionTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;

	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	
	@Autowired
	CreateInitialRequestAction action;
	
	@Test
	@Transactional
	public void testExecuteForNewRecordCreated(){
		String orderNumber = "123456";
		long requestVersion = 1;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);
		
		action.execute(context);
		
		Iterable<FraudRequest> retrievedRequestsIt = fraudRequestRepository.findByOrderNumberAndRequestVersion(new BigDecimal(orderNumber), requestVersion);
		assertNotNull(retrievedRequestsIt);
		List<FraudRequest> foundFraudRequestList = new ArrayList<>();
		retrievedRequestsIt.forEach(foundFraudRequestList::add);
		assertTrue(foundFraudRequestList.size()==1);
		FraudRequest foundRequest = foundFraudRequestList.get(0);
		assertTrue(foundRequest.getFraudRequestStatusHistory().size()==1);
	}

}
