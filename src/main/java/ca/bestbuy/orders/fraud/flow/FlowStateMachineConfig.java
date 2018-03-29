/**
 * 
 */
package ca.bestbuy.orders.fraud.flow;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.action.CheckRequestExistenceAction;
import ca.bestbuy.orders.fraud.flow.action.CreateInitialRequestAcion;
import ca.bestbuy.orders.fraud.flow.action.OutboundReplyAction;
import ca.bestbuy.orders.fraud.flow.action.RequestOutdatedAcion;
import ca.bestbuy.orders.fraud.flow.action.TASInvokeAction;
import ca.bestbuy.orders.fraud.flow.guard.RequestFoundAsInitialGuard;
import ca.bestbuy.orders.fraud.flow.guard.RequestFoundAsReadyForReplyGuard;
import ca.bestbuy.orders.fraud.flow.guard.RequestOutdatedGuard;

/**
 * @author akaradem
 *
 */
@Configuration
@EnableStateMachine(name="FlowStateMachine")
public class FlowStateMachineConfig
		extends EnumStateMachineConfigurerAdapter<FlowStates, FlowEvents> {

	@Autowired
	FraudStatusRepository statusRepository;
	@Autowired
	FraudRequestTypeRepository typeRepository;

	@Autowired	
	CheckRequestExistenceAction checkRequestExistenceAction;
	@Autowired	
	TASInvokeAction tasInvokeAction;
	@Autowired
	RequestOutdatedAcion requestOutdatedAcion;
	@Autowired	
	OutboundReplyAction outboundReplyAction;
	@Autowired
	CreateInitialRequestAcion createInitialRequestAcion;
	@Autowired
	RequestOutdatedGuard requestOutdatedGuard;
	@Autowired
	RequestFoundAsInitialGuard requestFoundAsInitialGuard;
	@Autowired
	RequestFoundAsReadyForReplyGuard requestFoundAsReadyForReplyGuard;
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<FlowStates, FlowEvents> config)
			throws Exception {
		config.
			withConfiguration().
				autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<FlowStates, FlowEvents> states)
			throws Exception {
		states
			.withStates()
				.initial(FlowStates.READY)
				.choice(FlowStates.REQUEST_EXISTENCE_CHECK)
				.state(FlowStates.REQUEST_NOTFOUND, createInitialRequestAcion,null)
				.state(FlowStates.REQUEST_OUTDATED, requestOutdatedAcion,null)
				.state(FlowStates.INITIAL_REQUEST, tasInvokeAction,null)
				.state(FlowStates.READY_FOR_REPLY, outboundReplyAction,null)
				.states(EnumSet.allOf(FlowStates.class));
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<FlowStates, FlowEvents> transitions) throws Exception {
		transitions
			.withExternal()
				.source(FlowStates.READY).target(FlowStates.REQUEST_EXISTENCE_CHECK)
				.event(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.action(checkRequestExistenceAction)
				.and()
			.withChoice()
				.source(FlowStates.REQUEST_EXISTENCE_CHECK)
				.first(FlowStates.REQUEST_OUTDATED, requestOutdatedGuard)
				.then(FlowStates.INITIAL_REQUEST, requestFoundAsInitialGuard)
				.then(FlowStates.READY_FOR_REPLY, requestFoundAsReadyForReplyGuard)
				.last(FlowStates.REQUEST_NOTFOUND)
				.and()
			.withExternal()
				.source(FlowStates.REQUEST_NOTFOUND).target(FlowStates.INITIAL_REQUEST)
				.and()
			.withExternal()
				.source(FlowStates.INITIAL_REQUEST).target(FlowStates.READY_FOR_REPLY)
				.and()
			.withExternal()
				.source(FlowStates.READY_FOR_REPLY).target(FlowStates.READY)
				.and()
			.withExternal()
				.source(FlowStates.REQUEST_OUTDATED).target(FlowStates.READY);
	}
	
	public interface KEYS {
		public static final String MESSAGING_KEY = "MESSAGING_KEY"; 
		public static final String MAX_VERSION_EXISTENCE_CHECK_RESULT = "MAX_VERSION_EXISTENCE_CHECK_RESULT"; 
	}
		
}
