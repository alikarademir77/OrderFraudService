/**
 * 
 */
package ca.bestbuy.orders.fraud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Service
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{
	
	@Autowired
	StateMachine<FlowStates, FlowEvents> flowStateMachine;
	
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.messaging.MessageConsumingService#consumeMessage(ca.bestbuy.orders.messaging.MessagingEvent)
	 */
	@Override
	public void consumeMessage(MessagingEvent event) {
		
		if(EventTypes.FraudCheck.equals(event.getType())){
			
			Message<FlowEvents> message = MessageBuilder
					.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
					.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
					.build();
			
			flowStateMachine.sendEvent(message);
		}
	}
}
