/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.guard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusEvents;
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
public class RequestFoundAsReadyForReplyGuardTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;
	
	@Test
	public void testEvaluateForRequestFoundAsPendingReview(){
		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		FraudRequest foundRequest = new FraudRequest();
		foundRequest.setOrderNumber(new BigDecimal(orderNumber));
		foundRequest.setRequestVersion(foundRequestVersion);
		foundRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{foundRequest}));
		
		RequestFoundAsReadyForReplyGuard guardObj = new RequestFoundAsReadyForReplyGuard();
		assertTrue(guardObj.evaluate(context));
	}

	@Test
	public void testEvaluateForRequestFoundAsFinalDecision(){
		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		FraudRequest foundRequest = new FraudRequest();
		foundRequest.setOrderNumber(new BigDecimal(orderNumber));
		foundRequest.setRequestVersion(foundRequestVersion);
		foundRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{foundRequest}));
		
		RequestFoundAsReadyForReplyGuard guardObj = new RequestFoundAsReadyForReplyGuard();
		assertTrue(guardObj.evaluate(context));
	}

	@Test
	public void testEvaluateForRequestFoundAsInitialRequest(){
		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		FraudRequest foundRequest = new FraudRequest();
		foundRequest.setOrderNumber(new BigDecimal(orderNumber));
		foundRequest.setRequestVersion(foundRequestVersion);
		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{foundRequest}));
		
		RequestFoundAsReadyForReplyGuard guardObj = new RequestFoundAsReadyForReplyGuard();
		assertFalse(guardObj.evaluate(context));
	}

	@Test
	public void testEvaluateForRequestFoundAsOutdated(){
		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 2L;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		FraudRequest foundRequest = new FraudRequest();
		foundRequest.setOrderNumber(new BigDecimal(orderNumber));
		foundRequest.setRequestVersion(foundRequestVersion);
		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{foundRequest}));
		
		RequestFoundAsReadyForReplyGuard guardObj = new RequestFoundAsReadyForReplyGuard();
		assertFalse(guardObj.evaluate(context));
	}

	@Test
	public void testEvaluateForRequestNotFound(){
		String orderNumber = "123456";
		long requestVersion = 1;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{null}));
		
		RequestFoundAsReadyForReplyGuard guardObj = new RequestFoundAsReadyForReplyGuard();
		assertFalse(guardObj.evaluate(context));
	}
	
}
