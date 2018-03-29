/**
 * 
 */
package ca.bestbuy.orders.fraud.flow;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.action.CheckRequestExistenceAction;
import ca.bestbuy.orders.fraud.flow.action.CreateInitialRequestAcion;
import ca.bestbuy.orders.fraud.flow.action.OutboundReplyAction;
import ca.bestbuy.orders.fraud.flow.action.RequestOutdatedAcion;
import ca.bestbuy.orders.fraud.flow.action.TASInvokeAction;
import ca.bestbuy.orders.fraud.flow.guard.RequestFoundAsInitialGuard;
import ca.bestbuy.orders.fraud.flow.guard.RequestFoundAsReadyForReplyGuard;
import ca.bestbuy.orders.fraud.flow.guard.RequestOutdatedGuard;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusEvents;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class FlowStateMachineTest {

	@MockBean
	CheckRequestExistenceAction checkRequestExistenceAction;
	
	@MockBean	
	RequestOutdatedAcion requestOutdatedAcion;

	@MockBean	
	CreateInitialRequestAcion createInitialRequestAcion;

	@MockBean	
	TASInvokeAction tasInvokeAction;
	
	@MockBean	
	OutboundReplyAction outboundReplyAction;

	@SpyBean
	RequestOutdatedGuard requestOutdatedGuardSpy;

	@SpyBean
	RequestFoundAsInitialGuard requestFoundAsInitialGuardSpy;
	
	@SpyBean
	RequestFoundAsReadyForReplyGuard requestFoundAsReadyForReplyGuardSpy;

	
	@Autowired 
	StateMachine<FlowStates, FlowEvents> flowStateMachine;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFraudCheckFlowForRequestOutdated() throws Exception{

		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 2L;
		
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                	StateContext<FlowStates, FlowEvents> stateContext = (StateContext<FlowStates, FlowEvents>) arguments[0];
                	FraudRequest request = new FraudRequest();
                	request.setOrderNumber(new BigDecimal(orderNumber));
                	request.setRequestVersion(foundRequestVersion);
                	
                	stateContext.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{request}));
                }
                return null;
            }
        }).when(checkRequestExistenceAction).execute(any(StateContext.class));
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
				.build();
		
		StateMachineTestPlan<FlowStates, FlowEvents> plan =
				StateMachineTestPlanBuilder.<FlowStates, FlowEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(flowStateMachine)
				.step()
					.expectStates(FlowStates.READY)
					.and()
				.step()
					.sendEvent(message)
					.expectTransition(2)
					.expectStateEntered(2)
					.expectStates(FlowStates.READY)
					.and()
				.build();
				
		plan.test();		
		
		verify(checkRequestExistenceAction, times(1)).execute(any(StateContext.class));
		verify(requestOutdatedGuardSpy, times(1)).evaluate(any(StateContext.class));
		verify(requestOutdatedAcion, times(1)).execute(any(StateContext.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFraudCheckFlowForRequestNotFound() throws Exception{

		String orderNumber = "123456";
		long requestVersion = 1;
		
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                	StateContext<FlowStates, FlowEvents> stateContext = (StateContext<FlowStates, FlowEvents>) arguments[0];
                	
                	stateContext.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{null}));
                }
                return null;
            }
        }).when(checkRequestExistenceAction).execute(any(StateContext.class));
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
				.build();
		
		StateMachineTestPlan<FlowStates, FlowEvents> plan =
				StateMachineTestPlanBuilder.<FlowStates, FlowEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(flowStateMachine)
				.step()
					.expectStates(FlowStates.READY)
					.and()
				.step()
					.sendEvent(message)
					.expectTransition(4)
					.expectStateEntered(4)
					.expectStates(FlowStates.READY)
					.and()
				.build();
				
		plan.test();		
		
		verify(checkRequestExistenceAction, times(1)).execute(any(StateContext.class));
		verify(requestOutdatedAcion, times(0)).execute(any(StateContext.class));
		verify(createInitialRequestAcion, times(1)).execute(any(StateContext.class));
		verify(tasInvokeAction, times(1)).execute(any(StateContext.class));
		verify(outboundReplyAction, times(1)).execute(any(StateContext.class));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFraudCheckFlowForRequestFoundAsInitialRequest() throws Exception{

		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                	StateContext<FlowStates, FlowEvents> stateContext = (StateContext<FlowStates, FlowEvents>) arguments[0];
                	FraudRequest request = new FraudRequest();
                	request.setOrderNumber(new BigDecimal(orderNumber));
                	request.setRequestVersion(foundRequestVersion);
                	
                	stateContext.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{request}));
                }
                return null;
            }
        }).when(checkRequestExistenceAction).execute(any(StateContext.class));
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
				.build();
		
		StateMachineTestPlan<FlowStates, FlowEvents> plan =
				StateMachineTestPlanBuilder.<FlowStates, FlowEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(flowStateMachine)
				.step()
					.expectStates(FlowStates.READY)
					.and()
				.step()
					.sendEvent(message)
					.expectTransition(3)
					.expectStateEntered(3)
					.expectStates(FlowStates.READY)
					.and()
				.build();
				
		plan.test();		
		
		verify(checkRequestExistenceAction, times(1)).execute(any(StateContext.class));
		verify(createInitialRequestAcion, times(0)).execute(any(StateContext.class));
		verify(requestFoundAsInitialGuardSpy, times(1)).evaluate(any(StateContext.class));
		verify(tasInvokeAction, times(1)).execute(any(StateContext.class));
		verify(outboundReplyAction, times(1)).execute(any(StateContext.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFraudCheckFlowForRequestFoundAsPendingReview() throws Exception{

		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                	StateContext<FlowStates, FlowEvents> stateContext = (StateContext<FlowStates, FlowEvents>) arguments[0];
                	FraudRequest request = new FraudRequest();
                	request.setOrderNumber(new BigDecimal(orderNumber));
                	request.setRequestVersion(foundRequestVersion);
                	request.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
                	
                	stateContext.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{request}));
                }
                return null;
            }
        }).when(checkRequestExistenceAction).execute(any(StateContext.class));
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
				.build();
		
		StateMachineTestPlan<FlowStates, FlowEvents> plan =
				StateMachineTestPlanBuilder.<FlowStates, FlowEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(flowStateMachine)
				.step()
					.expectStates(FlowStates.READY)
					.and()
				.step()
					.sendEvent(message)
					.expectTransition(2)
					.expectStateEntered(2)
					.expectStates(FlowStates.READY)
					.and()
				.build();
				
		plan.test();		
		
		verify(checkRequestExistenceAction, times(1)).execute(any(StateContext.class));
		verify(requestFoundAsReadyForReplyGuardSpy, times(1)).evaluate(any(StateContext.class));
		verify(createInitialRequestAcion, times(0)).execute(any(StateContext.class));
		verify(tasInvokeAction, times(0)).execute(any(StateContext.class));
		verify(outboundReplyAction, times(1)).execute(any(StateContext.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFraudCheckFlowForRequestFoundAsFinalDecisionMade() throws Exception{

		String orderNumber = "123456";
		long requestVersion = 1;
		long foundRequestVersion = 1L;
		
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                	StateContext<FlowStates, FlowEvents> stateContext = (StateContext<FlowStates, FlowEvents>) arguments[0];
                	FraudRequest request = new FraudRequest();
                	request.setOrderNumber(new BigDecimal(orderNumber));
                	request.setRequestVersion(foundRequestVersion);
                	request.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
                	
                	stateContext.getExtendedState().getVariables().put(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT, Arrays.asList(new FraudRequest[]{request}));
                }
                return null;
            }
        }).when(checkRequestExistenceAction).execute(any(StateContext.class));
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, event)
				.build();
		
		StateMachineTestPlan<FlowStates, FlowEvents> plan =
				StateMachineTestPlanBuilder.<FlowStates, FlowEvents>builder()
				.defaultAwaitTime(2)
				.stateMachine(flowStateMachine)
				.step()
					.expectStates(FlowStates.READY)
					.and()
				.step()
					.sendEvent(message)
					.expectTransition(2)
					.expectStateEntered(2)
					.expectStates(FlowStates.READY)
					.and()
				.build();
				
		plan.test();		
		
		verify(checkRequestExistenceAction, times(1)).execute(any(StateContext.class));
		verify(requestFoundAsReadyForReplyGuardSpy, times(1)).evaluate(any(StateContext.class));
		verify(createInitialRequestAcion, times(0)).execute(any(StateContext.class));
		verify(tasInvokeAction, times(0)).execute(any(StateContext.class));
		verify(outboundReplyAction, times(1)).execute(any(StateContext.class));
		
	}

}
