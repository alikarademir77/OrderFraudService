/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import java.util.List;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Component
@Slf4j
public class RequestOutdatedAcion implements Action<FlowStates, FlowEvents> {

	/* (non-Javadoc)
	 * @see org.springframework.statemachine.action.Action#execute(org.springframework.statemachine.StateContext)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void execute(StateContext<FlowStates, FlowEvents> context) {
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);

		String orderNumber = messagingEvent.getOrderNumber();
		String requestVersion = messagingEvent.getRequestVersion();
		
		List<FraudRequest> existenceCheckResultList = (List<FraudRequest>)context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT);
		FraudRequest existenceCheckResultObj = existenceCheckResultList.get(0);
		
		logMessage(orderNumber, requestVersion, String.valueOf(existenceCheckResultObj.getRequestVersion()));
		
	}

	/**
	 * Used to help with testing
	 * 
	 * @param orderNumber
	 * @param requestVersion
	 * @param existenceCheckResultObj
	 */
	void logMessage(String orderNumber, String requestVersion, String foundRequestVersion) {
		log.info("Ignoring fraud request with order number={} and requestVersion={}, as a newer version({}) of request for same order exists in DB", orderNumber, requestVersion, foundRequestVersion);
	}


}
