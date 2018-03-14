/**
 * 
 */
package ca.bestbuy.orders.fraud.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.dao.FraudRequestHistoryRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Service
@Slf4j
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{
	
	@Autowired
	FraudRequestRepository fraudRequestRepository;
	
	@Autowired
	FraudRequestHistoryRepository historyRepository;

	
	@Autowired
	FraudStatusRepository statusRepository;

	
	@Autowired
	FraudRequestTypeRepository typeRepository;
	
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.messaging.MessageConsumingService#consumeMessage(ca.bestbuy.orders.messaging.MessagingEvent)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void consumeMessage(MessagingEvent event) {
		
		//TODO Should replace below code with actual implementation 
		//Below is a mock implementation just to test Oracle DB Access is successful
		if(EventTypes.FraudCheck.equals(event.getType())){
			long fsOrderId = Long.parseLong(event.getOrderNumer(), 10);
			FraudRequest foundRequest = fraudRequestRepository.findOne(fsOrderId);
			if(foundRequest == null){
				createNewFraudRequest(event);
			}
			
		}
	}

	
	//private
	private void createNewFraudRequest(MessagingEvent event){
		FraudRequest request = new FraudRequest();
		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		request.setFraudRequestType(fraudCheckType);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatuses.INITIAL_REQUEST);
		request.setFraudStatus(status);
		
		String userName = "order_fraud_test";
		Date now = new Date();
		request.setCreateDate(now);
		request.setCreateUser(userName);
		request.setUpdateDate(now);
		request.setUpdateUser(userName);
		request.setOrderNumber(new BigDecimal(event.getOrderNumer()));
		request.setRequestVersion(new BigDecimal(event.getRequestVersion()));
		
		FraudRequestHistory history = new FraudRequestHistory();
		history.setFraudRequest(request);
		history.setFraudRequestType(fraudCheckType);
		history.setFraudStatus(status);
		history.setOrderNumber(new BigDecimal(event.getOrderNumer()));
		history.setRequestVersion(new BigDecimal(event.getRequestVersion()));
		history.setCreateDate(now);
		history.setCreateUser(userName);
		history.setUpdateDate(now);
		history.setUpdateUser(userName);
		List<FraudRequestHistory> historyList = new ArrayList<>();
		historyList.add(history);
		
		request.setFraudRequestHistory(historyList);
		fraudRequestRepository.save(request);
	}



}
