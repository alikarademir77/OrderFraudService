/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.statemachine.StateContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStates;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"dev","unittest"})
public class InitializeContextActionTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	HashMap<Object, Object> mockedMap;
	
	@Test
	public void testExecute(){
		
		InitializeContextAction action = new InitializeContextAction();
		when(context.getExtendedState().getVariables()).thenReturn(mockedMap);

		action.execute(context);
		verify(mockedMap, times(1)).clear();
	}
}
