package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.messaging.MessagingEvent;

@Component
public class CreateInitialRequestAcion extends ActionWithException<FlowStates, FlowEvents> {

	@Value("${spring.datasource.username}")
	private String userName;
	
	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	@Autowired
	FraudStatusRepository statusRepository;
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.fraud.flow.action.ActionWithException#doExecute(org.springframework.statemachine.StateContext)
	 */
	@Override
	protected void doExecute(StateContext<FlowStates, FlowEvents> context) throws Exception {
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);
		createNewFraudRequest(messagingEvent);
		
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, messagingEvent)
				.build();
		
		context.getStateMachine().sendEvent(message);
	}

	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.fraud.flow.action.ActionWithException#getErrorEvent()
	 */
	@Override
	protected FlowEvents getErrorEvent() {
		return FlowEvents.SM_ERROR_EVENT;
	}

	//private
	/**
	 * @param event
	 */
	private void createNewFraudRequest(MessagingEvent event){

		FraudRequestType.RequestTypeCodes requestTypeCode = FraudRequestType.RequestTypeCodes.findByEventType(event.getType());
		FraudRequestType fraudRequestType = typeRepository.findOne(requestTypeCode);

		FraudRequest request = new FraudRequest();

		Date now = new Date();
		request.setFraudRequestType(fraudRequestType)
				.setEventDate(event.getMessageCreationDate())
				.setOrderNumber(new BigDecimal(event.getOrderNumber()))
				.setRequestVersion(Long.valueOf(event.getRequestVersion()))
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);

		FraudRequestStatusHistory history = new FraudRequestStatusHistory();
		
		history.setFraudRequest(request)
				.setCreateDate(now)
				.setCreateUser(userName)
				.setUpdateDate(now)
				.setUpdateUser(userName);

		List<FraudRequestStatusHistory> historyList = new ArrayList<>();
		historyList.add(history);
		request.setFraudRequestStatusHistory(historyList);
		
		fraudRequestRepository.save(request);
	}


}
