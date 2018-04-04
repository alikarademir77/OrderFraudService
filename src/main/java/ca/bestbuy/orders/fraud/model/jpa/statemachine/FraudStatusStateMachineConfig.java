package ca.bestbuy.orders.fraud.model.jpa.statemachine;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;

/**
 * @author akaradem
 *
 */
@Configuration
@Scope(scopeName="prototype")
@EnableStateMachineFactory(name="FraudStatusStateMachine", contextEvents=false)
public class FraudStatusStateMachineConfig
		extends EnumStateMachineConfigurerAdapter<FraudStatusCodes, FraudStatusEvents> {
	
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer)
	 */
	@Override
	public void configure(StateMachineConfigurationConfigurer<FraudStatusCodes, FraudStatusEvents> config)
			throws Exception {
		config.
			withConfiguration().
				autoStartup(true);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineStateConfigurer)
	 */
	@Override
	public void configure(StateMachineStateConfigurer<FraudStatusCodes, FraudStatusEvents> states) throws Exception {
		states.
			withStates()
				.initial(FraudStatusCodes.INITIAL_REQUEST)
				.states(EnumSet.allOf(FraudStatusCodes.class));
	}

	/* (non-Javadoc)
	 * @see org.springframework.statemachine.config.AbstractStateMachineConfigurerAdapter#configure(org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer)
	 */
	@Override
	public void configure(StateMachineTransitionConfigurer<FraudStatusCodes, FraudStatusEvents> transitions) throws Exception {
		transitions
			.withExternal()
				.source(FraudStatusCodes.INITIAL_REQUEST).target(FraudStatusCodes.PENDING_REVIEW)
				.event(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
				.and()
			.withExternal()
				.source(FraudStatusCodes.INITIAL_REQUEST).target(FraudStatusCodes.FINAL_DECISION)
				.event(FraudStatusEvents.FINAL_DECISION_RECEIVED)
				.and()
			.withExternal()
				.source(FraudStatusCodes.INITIAL_REQUEST).target(FraudStatusCodes.CANCELLED)
				.event(FraudStatusEvents.CANCELLATION_RECEIVED)
				.and()
			.withExternal()
				.source(FraudStatusCodes.PENDING_REVIEW).target(FraudStatusCodes.FINAL_DECISION)
				.event(FraudStatusEvents.FINAL_DECISION_RECEIVED);
	}	
}
