package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.messaging.MessagingEvent;

@Component
public class CreateInitialRequestAcion implements Action<FlowStates, FlowEvents> {

	//TODO Consider getting value from configuration
	private String userName = "order_fraud_user";
	
	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	@Autowired
	FraudStatusRepository statusRepository;
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void execute(StateContext<FlowStates, FlowEvents> context) {
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);
		createNewFraudRequest(messagingEvent);
	}

	//private
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
