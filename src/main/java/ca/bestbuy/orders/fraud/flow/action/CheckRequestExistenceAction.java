/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Component
public class CheckRequestExistenceAction implements Action<FlowStates, FlowEvents> {

	private final FraudRequestRepository fraudRequestRepository;
	
	@Autowired
	public CheckRequestExistenceAction(FraudRequestRepository fraudRequestRepository){
		this.fraudRequestRepository = fraudRequestRepository;
	}
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.action.Action#execute(org.springframework.statemachine.StateContext)
	 */
	@Override
	public void execute(StateContext<FlowStates, FlowEvents> context) {
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);

		BigDecimal orderNumber = new BigDecimal(Long.valueOf(messagingEvent.getOrderNumber(), 10));
		Long  requestVersion = Long.valueOf(messagingEvent.getRequestVersion(), 10);
		
		Iterable<FraudRequest> fraudRequestIt = fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(orderNumber, requestVersion);
		
		List<FraudRequest> fraudRequestList = new ArrayList<>();
		fraudRequestIt.forEach(fraudRequestList::add);
		
		FraudRequest fraudRequestWithMaxVersion =  null;
		if(!CollectionUtils.isEmpty(fraudRequestList)){
			fraudRequestWithMaxVersion = Collections.max(fraudRequestList, new Comparator<FraudRequest>() {
				@Override
				public int compare(FraudRequest o1, FraudRequest o2) {
					if (o1.getRequestVersion() < o2.getRequestVersion()) {
						return -1;
					} else if (o1.getRequestVersion() == o2.getRequestVersion()) {
						return 0;
					} else {
						return 1;
					}
				}
			});

		} 
		context.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{fraudRequestWithMaxVersion}));
	}
}
