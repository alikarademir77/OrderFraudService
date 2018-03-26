package ca.bestbuy.orders.fraud.flow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusEvents;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Component
public class TASInvokeAction implements Action<FlowStates, FlowEvents> {


	//TODO Consider getting value from configuration
	private String userName = "order_fraud_user";
	
	@Autowired
	OrderDetailsClient orderDetailsClient;
	@Autowired
	FraudServiceTASClient  fraudServiceTASClient;

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

		String orderNumber = messagingEvent.getOrderNumber();
		String requestVersion = messagingEvent.getRequestVersion();
		
		
		//TODO: Add Error Handling
		Order orderDetails = orderDetailsClient.getOrderDetails(orderNumber);
		//TODO: Call Resource Service to populate item SKU Category details
		
		FraudAssesmentResult fraudAssesmentResult = fraudServiceTASClient.doFraudCheck(orderDetails);
		
		Iterable<FraudRequest> fraudRequestIt = 
				fraudRequestRepository.findByOrderNumberAndRequestVersion(new BigDecimal(orderNumber), Long.valueOf(requestVersion, 10));
				
				//ByOrderNumberAndRequestVersion();
		
		if((fraudAssesmentResult!=null) && (fraudRequestIt!=null) && (fraudRequestIt.iterator().hasNext())){
			FraudRequest fraudRequest =  fraudRequestIt.iterator().next();
			FraudAssesmentResult.FraudResponseStatusCodes fraudResponseStatusCode = fraudAssesmentResult.getFraudResponseStatus();

			Date now = new Date();
			//Set Status History
			FraudRequestStatusHistory statusHistory = new FraudRequestStatusHistory();
			statusHistory.setCreateDate(now);
			statusHistory.setCreateUser(userName);
			statusHistory.setUpdateDate(now);
			statusHistory.setUpdateUser(userName);
				
			FraudStatus fraudStatus = null;
			if(fraudResponseStatusCode == FraudAssesmentResult.FraudResponseStatusCodes.PENDING_REVIEW){
				statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
			}else if (decisionMadeResponseStatusCodes().contains(fraudResponseStatusCode)){
				statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
			}else {
				//TODO: Throw Exception
			}
			fraudRequest.getFraudRequestStatusHistory().add(statusHistory);
			fraudRequestRepository.save(fraudRequest);
			
			//Set FraudRequestStatusHistoryDetail
			FraudRequestStatusHistoryDetail statusHistoryDetail = new FraudRequestStatusHistoryDetail();
			statusHistoryDetail.setCreateDate(now);
			statusHistoryDetail.setCreateUser(userName);
			statusHistoryDetail.setFraudRequestStatusHistory(statusHistory);
			statusHistoryDetail.setFraudRequestStatusHistoryId(statusHistory.getFraudRequestStatusHistoryId());
			statusHistoryDetail.setTasRequest(fraudAssesmentResult.getTasRequest());
			statusHistoryDetail.setTasResponse(fraudAssesmentResult.getTasResponse());
			statusHistoryDetail.setUpdateDate(now);
			statusHistoryDetail.setUpdateUser(userName);
			
			statusHistory.setFraudRequestStatusHistoryDetail(statusHistoryDetail);
			fraudRequestRepository.save(fraudRequest);
		}
	}
	
	private List<FraudAssesmentResult.FraudResponseStatusCodes> decisionMadeResponseStatusCodes(){
		return Arrays.asList(new FraudAssesmentResult.FraudResponseStatusCodes[]{
				FraudAssesmentResult.FraudResponseStatusCodes.ACCEPTED,
				FraudAssesmentResult.FraudResponseStatusCodes.HARD_DECLINE,
				FraudAssesmentResult.FraudResponseStatusCodes.SOFT_DECLINE
		});
	}

}
