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

import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestStatusHistoryRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
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
	FraudRequestStatusHistoryRepository historyRepository;

	
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
			long fsOrderId = Long.parseLong(event.getOrderNumber(), 10);
			long requestVersion = Long.parseLong(event.getRequestVersion(), 10);
			Iterable<FraudRequest> foundRequestIt = fraudRequestRepository
					.findByOrderNumberAndRequestVersionGTE(new BigDecimal(fsOrderId),
							new BigDecimal(requestVersion));
			
			if((foundRequestIt == null)||(foundRequestIt.iterator().hasNext()==false)){
				createNewFraudRequest(event);
			}else if(foundRequestIt != null) {
				FraudRequest request = foundRequestIt.iterator().next();
				request.setCreateUser("Updated");
				fraudRequestRepository.save(request);
				System.out.println(request.getCreateUser());
			}
		}
	}

	
	//private
	private void createNewFraudRequest(MessagingEvent event){

		FraudRequestType fraudCheckType = typeRepository.findOne(FraudRequestType.RequestTypes.FRAUD_CHECK);
		FraudStatus status = statusRepository.findOne(FraudStatus.FraudStatusCodes.INITIAL_REQUEST);

		FraudRequest request = new FraudRequest();

		try {
			String userName = "order_fraud_test";
			Date now = new Date();

			request.setFraudRequestType(fraudCheckType)
					.setFraudStatus(status)
					.setEventDate(event.getMessageCreationDate())
					.setOrderNumber(new BigDecimal(event.getOrderNumber()))
					.setRequestVersion(new BigDecimal(event.getRequestVersion()))
					.setCreateDate(now)
					.setCreateUser(userName)
					.setUpdateDate(now)
					.setUpdateUser(userName);

			FraudRequestStatusHistory history = new FraudRequestStatusHistory();
			
			history.setFraudRequest(request)
					.setFraudStatus(status)
					.setCreateDate(now)
					.setCreateUser(userName)
					.setUpdateDate(now)
					.setUpdateUser(userName);

			List<FraudRequestStatusHistory> historyList = new ArrayList<>();
			historyList.add(history);
			request.setFraudRequestStatusHistory(historyList);
		} catch (Exception ex) {
			log.error("Failed to parse MessageEvent: " + event, ex);
			return;
		}

		try {
			fraudRequestRepository.save(request);
		} catch (Exception ex) {
			log.error("Failed to save to DB MessageEvent: " + event, ex);
		}
	}
}
