package ca.bestbuy.orders.fraud.flow;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author akaradem
 *
 */
@Component
public class TASInvokeAction implements Action<FlowStates, FlowEvents> {

	@Override
	public void execute(StateContext<FlowStates, FlowEvents> context) {
		// TODO Auto-generated method stub
		System.out.println("TASInvokeAction");
	}

}
