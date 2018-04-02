/**
 * 
 */
package ca.bestbuy.orders.fraud.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.stereotype.Service;

import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Slf4j
@Service
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{
	
	@Autowired
	@Qualifier("FlowStateMachine")
 	StateMachineFactory<FlowStates, FlowEvents> flowStateMachineFactory;
	
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.messaging.MessageConsumingService#consumeMessage(ca.bestbuy.orders.messaging.MessagingEvent)
	 */
	@Override
	public void consumeMessage(MessagingEvent event) throws Exception {
		validateEvent(event);
		
		if(EventTypes.FraudCheck.equals(event.getType())){
			
			Message<FlowEvents> message = MessageBuilder
					.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
					.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
					.build();
			
			StateMachine<FlowStates, FlowEvents> flowStateMachine = flowStateMachineFactory.getStateMachine("FlowSM_"+Thread.currentThread().getId());

			flowStateMachine.getStateMachineAccessor()
					.doWithRegion(new StateMachineFunction<StateMachineAccess<FlowStates, FlowEvents>>() {
						@Override
						public void apply(StateMachineAccess<FlowStates, FlowEvents> function) {
							function.addStateMachineInterceptor(errorHandler(event));
						}
					});

			flowStateMachine.sendEvent(message);	
			if(flowStateMachine.hasStateMachineError()){
				throw (Exception) flowStateMachine.getExtendedState().getVariables().get("ERROR");
			}
		}
	}
	
	private void validateEvent(MessagingEvent event) throws Exception {
		if(event == null){
			throw new IllegalArgumentException("Input messaging event can not be null!");
		} 
		
		if(!StringUtils.isNumeric(event.getOrderNumber())){
			throw new IllegalArgumentException("Input messaging event order number("+event.getOrderNumber()+") should be a valid order number.");			
		}
		
		try{
			Long.parseLong(event.getRequestVersion());
		}catch( NumberFormatException nfe){
			throw new IllegalArgumentException("Input messaging event request version ("+event.getRequestVersion()+") should be a valid version number.");			
		}
		
		if(event.getType() == null){
			throw new IllegalArgumentException("Input messaging event type should be provided!");
		}
	}

	private StateMachineInterceptor<FlowStates, FlowEvents> errorHandler(final MessagingEvent event) {
		return new StateMachineInterceptorAdapter<FlowStates, FlowEvents>() {
			@Override
			public Exception stateMachineError(StateMachine<FlowStates, FlowEvents> stateMachine, Exception exception) {
				StringBuilder builder = 
						(new StringBuilder())
							.append("Terminating flow SM transitions for input order(")
							.append(event.getOrderNumber())
							.append(") and request version(")
							.append(event.getRequestVersion())
							.append(") as exception happened..\n")
							.append(ExceptionUtils.getStackTrace(exception));

						log.error(builder.toString());
						stateMachine.getExtendedState().getVariables().put("ERROR", exception);
				return exception;
			}
		};
	}
}
