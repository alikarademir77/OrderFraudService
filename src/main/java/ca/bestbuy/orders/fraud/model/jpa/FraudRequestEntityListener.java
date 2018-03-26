/**
 * 
 */
package ca.bestbuy.orders.fraud.model.jpa;

import java.util.List;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.support.DefaultStateMachineContext;

/**
 * @author akaradem
 *
 */
public class FraudRequestEntityListener {
	@PostLoad
	public void postLoad(FraudRequest entity) {
		StateMachine<FraudStatusCodes, FraudStatusEvents> stateMachine = entity.getFraudStatusStateMachine();
		handleState(entity.fraudStatusCode, stateMachine);
	}
	
	 @PrePersist 
	 public void onPrePersist(FraudRequest entity) {
		 StateMachine<FraudStatusCodes, FraudStatusEvents> stateMachine = entity.getFraudStatusStateMachine();
		 entity.fraudStatusCode = stateMachine.getState().getId();
	 };
	 
	private void handleState(FraudStatusCodes state, StateMachine<FraudStatusCodes, FraudStatusEvents> stateMachine) {
		stateMachine.stop();
		List<StateMachineAccess<FraudStatusCodes, FraudStatusEvents>> withAllRegions = stateMachine.getStateMachineAccessor().withAllRegions();
		for (StateMachineAccess<FraudStatusCodes, FraudStatusEvents> a : withAllRegions) {
			a.resetStateMachine(new DefaultStateMachineContext<FraudStatusCodes, FraudStatusEvents>(state, null, null, null));
		}
		stateMachine.start();
	}
}	
