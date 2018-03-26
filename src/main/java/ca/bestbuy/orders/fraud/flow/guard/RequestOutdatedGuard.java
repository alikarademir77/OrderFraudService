package ca.bestbuy.orders.fraud.flow.guard;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.messaging.MessagingEvent;

@Component
public class RequestOutdatedGuard implements Guard<FlowStates, FlowEvents> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(StateContext<FlowStates, FlowEvents> context) {
		
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);

		BigDecimal orderNumber = new BigDecimal(Long.valueOf(messagingEvent.getOrderNumber(), 10));
		Long requestVersion = Long.valueOf(messagingEvent.getRequestVersion(), 10);
		
		List<FraudRequest> existenceCheckResult = (List<FraudRequest>)context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT);
		FraudRequest existenceCheckResultObj = null;
		if(!CollectionUtils.isEmpty(existenceCheckResult)){
			existenceCheckResultObj = existenceCheckResult.get(0);
		}
		if ((existenceCheckResultObj != null)
				&& (existenceCheckResultObj.getOrderNumber().equals(orderNumber))
				&& (existenceCheckResultObj.getRequestVersion().longValue() > requestVersion.longValue())) {
			
			return true;
		}
		return false;
	}
}