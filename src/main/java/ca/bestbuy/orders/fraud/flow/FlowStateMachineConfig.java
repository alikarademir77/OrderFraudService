/**
 * 
 */
package ca.bestbuy.orders.fraud.flow;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import ca.bestbuy.orders.fraud.flow.action.CheckRequestExistenceAction;
import ca.bestbuy.orders.fraud.flow.action.CreateInitialRequestAcion;
import ca.bestbuy.orders.fraud.flow.action.InitializeContextAction;
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
@EnableStateMachineFactory(name="FlowStateMachine", contextEvents=false)
public class FlowStateMachineConfig
		extends EnumStateMachineConfigurerAdapter<FlowStates, FlowEvents> {

	@Autowired
	InitializeContextAction initializeContextAction;
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
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer)
	 */
	@Override
	public void configure(StateMachineConfigurationConfigurer<FlowStates, FlowEvents> config)
			throws Exception {
		config.
			withConfiguration()
				.autoStartup(true);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineStateConfigurer)
	 */
	@Override
	public void configure(StateMachineStateConfigurer<FlowStates, FlowEvents> states)
			throws Exception {
		states
			.withStates()
				.initial(FlowStates.READY, null)
				.choice(FlowStates.REQUEST_EXISTENCE_CHECK)
				.stateEntry(FlowStates.REQUEST_NOTFOUND, createInitialRequestAcion, null)
				.stateEntry(FlowStates.REQUEST_OUTDATED, requestOutdatedAcion,null)
				.stateEntry(FlowStates.INITIAL_REQUEST, tasInvokeAction,null)
				.stateEntry(FlowStates.READY_FOR_REPLY, outboundReplyAction,null)
				.states(EnumSet.allOf(FlowStates.class));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer)
	 */
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
				.event(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.and()
			.withExternal()
				.source(FlowStates.REQUEST_NOTFOUND).target(FlowStates.READY)
				.event(FlowEvents.SM_ERROR_EVENT)
				.and()
			.withExternal()
				.source(FlowStates.INITIAL_REQUEST).target(FlowStates.READY_FOR_REPLY)
				.event(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)				
				.and()
			.withExternal()
				.source(FlowStates.INITIAL_REQUEST).target(FlowStates.READY)
				.event(FlowEvents.SM_ERROR_EVENT)				
				.and()
			.withExternal()
				.source(FlowStates.READY_FOR_REPLY).target(FlowStates.READY)
				.and()
			.withExternal()
				.source(FlowStates.REQUEST_OUTDATED).target(FlowStates.READY);
				

	}

	/**
	 * @author akaradem
	 *
	 */
	public interface KEYS {
		public static final String MESSAGING_KEY = "MESSAGING_KEY"; 
		public static final String MAX_VERSION_EXISTENCE_CHECK_RESULT = "MAX_VERSION_EXISTENCE_CHECK_RESULT"; 
	}
		
}
