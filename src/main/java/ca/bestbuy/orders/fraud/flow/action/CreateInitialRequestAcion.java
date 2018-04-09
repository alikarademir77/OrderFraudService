package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.messaging.MessagingEvent;

@Component
public class CreateInitialRequestAcion extends ActionWithException<FlowStates, FlowEvents> {

	private final String userName;
	
	private final FraudRequestRepository fraudRequestRepository;
	private final FraudRequestTypeRepository typeRepository;
	
	@Autowired
	public CreateInitialRequestAcion(
			FraudRequestRepository fraudRequestRepository,
			FraudRequestTypeRepository typeRepository,
			@Value("${spring.datasource.username}") 
			String userName
			){
		super();
		this.fraudRequestRepository = fraudRequestRepository;
		this.typeRepository = typeRepository;
		this.userName = userName; 
	}
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.fraud.flow.action.ActionWithException#doExecute(org.springframework.statemachine.StateContext)
	 */
	@Override
	protected void doExecute(StateContext<FlowStates, FlowEvents> context) throws Exception {

		MessagingEvent messagingEvent = (MessagingEvent) context.getExtendedState().getVariables().get(KEYS.REQUEST);

		createNewFraudRequest(messagingEvent);

		context.getStateMachine().sendEvent(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT);

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
