/**
 * 
 */
package ca.bestbuy.orders.fraud.model.jpa.statemachine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FraudStatusStateMachineTest {

	@Test
	public void testFraudStatusStateMachinePendingReviwSuccess() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.FINAL_DECISION)
					.and()
				.build();
				
		plan.test();		
	}

	@Test(expected=Throwable.class)
	public void testFraudStatusStateMachinePendingReviwFailure() throws Exception{
		
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.CANCELLATION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.CANCELLED)
					.and()
				.build();
		plan.test();		
	}
	
	@Test
	public void testFraudStatusStateMachineFinalDecisionSuccess() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.FINAL_DECISION)
					.and()
				.build();
				
		plan.test();		
	}

	@Test(expected=Throwable.class)
	public void testFraudStatusStateMachineFinalDecisionFailure() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.FINAL_DECISION)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.build();
				
		plan.test();		
	}
	
	@Test
	public void testFraudStatusStateMachineCancellationSuccess() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.CANCELLATION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.CANCELLED)
					.and()
				.build();
				
		plan.test();		
	}

	@Test(expected=Throwable.class)
	public void testFraudStatusStateMachineCancellationFailure() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.CANCELLATION_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.CANCELLED)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.build();
				
		plan.test();		
	}

	@Test(expected=Throwable.class)
	public void testFraudStatusStateMachineInvalidCancellationEvent() throws Exception{
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = buildMachine();
		
		StateMachineTestPlan<FraudStatusCodes, FraudStatusEvents> plan =
				StateMachineTestPlanBuilder.<FraudStatusCodes, FraudStatusEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(statusStateMachine)
				.step()
					.expectStates(FraudStatusCodes.INITIAL_REQUEST)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.step()
					.sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED)
					.expectStateChanged(1)
					.expectStates(FraudStatusCodes.PENDING_REVIEW)
					.and()
				.build();
				
		plan.test();		
	}
	
	@SuppressWarnings("unchecked")
	private StateMachine<FraudStatusCodes, FraudStatusEvents> buildMachine() {
		
		StateMachine<FraudStatusCodes, FraudStatusEvents> statusStateMachine = null;
		AnnotationConfigApplicationContext context = null;
		try{
			context = new AnnotationConfigApplicationContext(FraudStatusStateMachineConfig.class);
			StateMachineFactory<FraudStatusCodes, FraudStatusEvents> factory = context.getBean("FraudStatusStateMachine", StateMachineFactory.class);
			statusStateMachine = factory.getStateMachine("FraudStatusSM");
		}finally {
			if(context!=null){
				context.close();
			}
		}
		return statusStateMachine;
	}
}
