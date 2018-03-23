/**
 * 
 */
package ca.bestbuy.orders.fraud.flow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.util.CollectionUtils;

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Configuration
@EnableStateMachine
@Slf4j
public class FlowStateMachineConfig
		extends EnumStateMachineConfigurerAdapter<FlowStates, FlowEvents> {

	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	@Autowired
	FraudStatusRepository statusRepository;
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	@Autowired	
	TASInvokeAction tasInvokeAction;
	@Autowired	
	OutboundReplyAction outboundReplyAction;
	@Autowired
	CreateInitialRequestAcion createInitialRequestAcion;
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<FlowStates, FlowEvents> config)
			throws Exception {
		config.withConfiguration().autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<FlowStates, FlowEvents> states)
			throws Exception {
		states
			.withStates()
				.initial(FlowStates.READY)
				.choice(FlowStates.REQUEST_EXISTENCE_CHECK)
				.state(FlowStates.REQUEST_NOTFOUND, createInitialRequestAcion,null)
				.state(FlowStates.REQUEST_OUTDATED, requestOutdatedAcion(),null)
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
				.action(checkRequestExistenceAction())
				.and()
			.withChoice()
				.source(FlowStates.REQUEST_EXISTENCE_CHECK)
				.first(FlowStates.REQUEST_OUTDATED, requestOutdatedGuard())
				.then(FlowStates.INITIAL_REQUEST, requestFoundAsInitialGuard())
				.then(FlowStates.READY_FOR_REPLY, requestFoundAsReadyForReplyGuard())
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
	
	public Guard<FlowStates, FlowEvents> requestOutdatedGuard() {
		return new Guard<FlowStates, FlowEvents>() {
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
		};
	}

	public Guard<FlowStates, FlowEvents> requestFoundAsInitialGuard() {
		return new Guard<FlowStates, FlowEvents>() {
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
						&& (existenceCheckResultObj.getRequestVersion().longValue() == requestVersion.longValue())
						&& (existenceCheckResultObj.getFraudStatus().getFraudStatusCode()==FraudStatus.FraudStatusCodes.INITIAL_REQUEST)) {
					
					return true;
				}
				return false;
			}
		};
	}
	
	public Guard<FlowStates, FlowEvents> requestFoundAsReadyForReplyGuard() {
		return new Guard<FlowStates, FlowEvents>() {
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
						&& (existenceCheckResultObj.getRequestVersion().longValue() == requestVersion.longValue())
						&& readyForReplyStatusCodes().contains(existenceCheckResultObj.getFraudStatus().getFraudStatusCode())) {
					
					return true;
				}
				return false;
			}
		};
	}	
	
	@Bean
	public Action<FlowStates, FlowEvents> checkRequestExistenceAction() {
		return new Action<FlowStates, FlowEvents>() {
			@Override
			public void execute(StateContext<FlowStates, FlowEvents> context) {
				MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);

				BigDecimal orderNumber = new BigDecimal(Long.valueOf(messagingEvent.getOrderNumber(), 10));
				Long requestVersion = Long.valueOf(messagingEvent.getRequestVersion(), 10);
				
				Iterable<FraudRequest> fraudRequestIt = fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(orderNumber, requestVersion);
				
				List<FraudRequest> fraudRequestList = new ArrayList<>();
				fraudRequestIt.forEach(fraudRequestList::add);
				
				FraudRequest key = new FraudRequest();
				key.setOrderNumber(orderNumber);
				key.setRequestVersion(requestVersion);
				
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
		};
	}


	@Bean
	public Action<FlowStates, FlowEvents> requestOutdatedAcion() {
		return new Action<FlowStates, FlowEvents>() {
			@SuppressWarnings({ "unchecked" })
			@Override
			public void execute(StateContext<FlowStates, FlowEvents> context) {
				MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);

				String orderNumber = messagingEvent.getOrderNumber();
				String requestVersion = messagingEvent.getRequestVersion();
				
				List<FraudRequest> existenceCheckResultList = (List<FraudRequest>)context.getExtendedState().getVariables().get(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT);
				FraudRequest existenceCheckResultObj = existenceCheckResultList.get(0);
				
				log.info("Ignoring fraud request with order number={} and requestVersion={}, as a newer version({}) of request for same order exists in DB", orderNumber, requestVersion, String.valueOf(existenceCheckResultObj.getRequestVersion()));
				
			}
		};
	}
	
	public interface KEYS {
		public static final String MESSAGING_KEY = "MESSAGING_KEY"; 
		public static final String MAX_VERSION_EXISTENCE_CHECK_RESULT = "MAX_VERSION_EXISTENCE_CHECK_RESULT"; 
	}
		
	
	private List<FraudStatus.FraudStatusCodes> readyForReplyStatusCodes() {
		return Arrays.asList(new FraudStatus.FraudStatusCodes[]{
				FraudStatus.FraudStatusCodes.DECISION_MADE,
				FraudStatus.FraudStatusCodes.PENDING_REVIEW
		});
	}

}
