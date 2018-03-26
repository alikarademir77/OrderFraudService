/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStates;

/**
 * @author akaradem
 *
 */
@Component
public class OutboundReplyAction implements Action<FlowStates, FlowEvents> {

	/* (non-Javadoc)
	 * @see org.springframework.statemachine.action.Action#execute(org.springframework.statemachine.StateContext)
	 */
	@Override
	public void execute(StateContext<FlowStates, FlowEvents> context) {
		// TODO Auto-generated method stub
		System.out.println("OutboundReplyAction");
	}

}
