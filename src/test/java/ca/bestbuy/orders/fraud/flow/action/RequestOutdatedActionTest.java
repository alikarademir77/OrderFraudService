/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
public class RequestOutdatedActionTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;
	
	@Mock
	RequestOutdatedAction requestOutdatedActionMock;
	
	@Test
	public void testExecute(){
		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 2l;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);

		FraudRequest foundRequest = new FraudRequest();
		foundRequest.setOrderNumber(new BigDecimal(orderNumber));
		foundRequest.setRequestVersion(foundRequestVersion);
		when(context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT)).thenReturn(Arrays.asList(new FraudRequest[]{foundRequest}));
		
		doCallRealMethod().when(requestOutdatedActionMock).execute(any(StateContext.class));
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 3) {
                	assertTrue(orderNumber.equals(arguments[0]));
                	assertTrue(String.valueOf(requestVersion).equals(arguments[1]));
                	assertTrue(String.valueOf(foundRequestVersion).equals(arguments[2]));                	
                }
                return null;
            }
			
		}).when(requestOutdatedActionMock).logMessage(orderNumber, String.valueOf(requestVersion), String.valueOf(foundRequestVersion));

		
		requestOutdatedActionMock.execute(context);
		verify(requestOutdatedActionMock, times(1)).logMessage(anyString(), anyString(), anyString());
	}
}
