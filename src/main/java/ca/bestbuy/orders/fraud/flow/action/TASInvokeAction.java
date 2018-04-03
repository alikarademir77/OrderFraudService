package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusEvents;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Component 
public class TASInvokeAction  extends ActionWithException<FlowStates, FlowEvents>  {

	private final String userName;
	
	private final OrderDetailsClient orderDetailsClient;
	private final FraudServiceTASClient  fraudServiceTASClient;
	private final FraudRequestRepository fraudRequestRepository;
	
	@Autowired
	public TASInvokeAction(
			OrderDetailsClient orderDetailsClient,
			FraudServiceTASClient  fraudServiceTASClient,
			FraudRequestRepository fraudRequestRepository,
			@Value("${spring.datasource.username}")
			String userName){
		this.orderDetailsClient = orderDetailsClient;
		this.fraudServiceTASClient = fraudServiceTASClient;
		this.fraudRequestRepository = fraudRequestRepository;
		this.userName = userName;
	}
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.fraud.flow.action.ActionWithException#doExecute(org.springframework.statemachine.StateContext)
	 */
	@Override
	protected void doExecute(StateContext<FlowStates, FlowEvents> context) throws Exception {
		MessagingEvent messagingEvent = (MessagingEvent) context.getMessageHeader(KEYS.MESSAGING_KEY);
		String orderNumber = messagingEvent.getOrderNumber();
		String requestVersion = messagingEvent.getRequestVersion();

		Order orderDetails = orderDetailsClient.getOrderDetails(orderNumber);
		
		// TODO: Call Resource Service to populate item SKU Category details
		// TODO: Call Payment Service to populate paypal details

		//store the requestVersion and the order details in a FraudAssessmentRequest object
		FraudAssessmentRequest fraudAssessmentRequest = new FraudAssessmentRequest(Integer.parseInt(requestVersion), orderDetails);
		FraudAssessmentResult fraudAssessmentResult = fraudServiceTASClient.doFraudCheck(fraudAssessmentRequest);
		//TODO: Validate that response and request has same order number and request version
				
		if(fraudAssessmentResult!=null){
			// Validate that response and request has same order number and request version
			validateAssesmentResult(orderNumber, requestVersion, fraudAssessmentResult);
			processAssesmentResult(orderNumber, requestVersion, fraudAssessmentResult);
			sendFraudCheckEvent(context, messagingEvent);
		}
	}


	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.fraud.flow.action.ActionWithException#getErrorEvent()
	 */
	@Override
	protected FlowEvents getErrorEvent() {
		return FlowEvents.SM_ERROR_EVENT;
	}	

	/**
	 * @param orderNumber
	 * @param requestVersion
	 * @param fraudAssessmentResult
	 */
	private void processAssesmentResult(String orderNumber, String requestVersion,
			FraudAssessmentResult fraudAssessmentResult) {
		if (fraudAssessmentResult != null) {

			Iterable<FraudRequest> fraudRequestIt = fraudRequestRepository
					.findByOrderNumberAndRequestVersion(new BigDecimal(orderNumber), Long.valueOf(requestVersion, 10));

			if ((fraudRequestIt != null) && (fraudRequestIt.iterator().hasNext())) {
				FraudRequest fraudRequest = fraudRequestIt.iterator().next();
				persistInvocationResult(fraudAssessmentResult, fraudRequest);
			} else {
				StringBuilder builder = (new StringBuilder())
						.append("Failed to find fraud request record (order number=").append(orderNumber)
						.append(" and request version=").append(requestVersion)
						.append(") in DB for which TAS call is made!");

				throw new IllegalStateException(builder.toString());
			}
		}
	}

	/**
	 * @param context
	 * @param messagingEvent
	 */
	private void sendFraudCheckEvent(StateContext<FlowStates, FlowEvents> context, MessagingEvent messagingEvent) {
		Message<FlowEvents> message = MessageBuilder
				.withPayload(FlowEvents.RECEIVED_FRAUD_CHECK_MESSAGING_EVENT)
				.setHeader(FlowStateMachineConfig.KEYS.MESSAGING_KEY, messagingEvent)
				.build();
		
		context.getStateMachine().sendEvent(message);
	}

	/**
	 * @param fraudAssessmentResult
	 * @param fraudRequest
	 */
	private void persistInvocationResult(FraudAssessmentResult fraudAssessmentResult, FraudRequest fraudRequest) {
		FraudAssessmentResult.FraudResponseStatusCodes fraudResponseStatusCode = fraudAssessmentResult.getFraudResponseStatus();

		Date now = new Date();
		//Set Status History
		FraudRequestStatusHistory statusHistory = new FraudRequestStatusHistory();
		statusHistory.setCreateDate(now);
		statusHistory.setCreateUser(userName);
		statusHistory.setUpdateDate(now);
		statusHistory.setUpdateUser(userName);
		statusHistory.setFraudRequest(fraudRequest);
		
		if(fraudResponseStatusCode == FraudAssessmentResult.FraudResponseStatusCodes.PENDING_REVIEW){
			statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
			fraudRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
		}else if (decisionMadeResponseStatusCodes().contains(fraudResponseStatusCode)){
			statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
			fraudRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
		}else {
			StringBuilder builder = 
			(new StringBuilder())
				.append("Fraud response status(")
				.append(fraudResponseStatusCode.name())
				.append(") received from TAS is not recognized!");

			throw new RuntimeException(builder.toString());
		}
		fraudRequest.getFraudRequestStatusHistory().add(statusHistory);
		
		//Set FraudRequestStatusHistoryDetail
		FraudRequestStatusHistoryDetail statusHistoryDetail = new FraudRequestStatusHistoryDetail();
		statusHistoryDetail.setCreateDate(now);
		statusHistoryDetail.setCreateUser(userName);
		statusHistoryDetail.setFraudRequestStatusHistory(statusHistory);
		statusHistoryDetail.setFraudResponseStatusCode(fraudResponseStatusCode);
		statusHistoryDetail.setTotalFraudScore(fraudAssessmentResult.getTotalFraudScore());
		statusHistoryDetail.setRecommendationCode(fraudAssessmentResult.getRecommendationCode());
		statusHistoryDetail.setAccertifyUser(fraudAssessmentResult.getAccertifyUser());
		statusHistoryDetail.setAccertifyUserActionTime(fraudAssessmentResult.getAccertifyUserActionTime());
		statusHistoryDetail.setTasRequest(fraudAssessmentResult.getTasRequest());
		statusHistoryDetail.setTasResponse(fraudAssessmentResult.getTasResponse());
		statusHistoryDetail.setUpdateDate(now);
		statusHistoryDetail.setUpdateUser(userName);
		
		statusHistory.setFraudRequestStatusHistoryDetail(statusHistoryDetail);
		fraudRequestRepository.save(fraudRequest);
	}
	
	private List<FraudAssessmentResult.FraudResponseStatusCodes> decisionMadeResponseStatusCodes(){
		return Arrays.asList(new FraudAssessmentResult.FraudResponseStatusCodes[]{
				FraudAssessmentResult.FraudResponseStatusCodes.ACCEPTED,
				FraudAssessmentResult.FraudResponseStatusCodes.HARD_DECLINE,
				FraudAssessmentResult.FraudResponseStatusCodes.SOFT_DECLINE
		});
	}

	private void validateAssesmentResult(String orderNumber, String requestVersion,
			FraudAssessmentResult fraudAssessmentResult) {
		if(fraudAssessmentResult == null){
			StringBuilder builder = 
			(new StringBuilder())
				.append("Response received from TAS client for order number=")
				.append(orderNumber)
				.append(" and request version=")
				.append(requestVersion)
				.append("is null!");
			
			throw new RuntimeException(builder.toString());
		}

		if(!orderNumber.equalsIgnoreCase(fraudAssessmentResult.getOrderNumber()) || 
				Long.valueOf(requestVersion).longValue() != (fraudAssessmentResult.getRequestVersion())){
			StringBuilder builder = 
			(new StringBuilder())
				.append("Wrong response received from TAS client. Request order number=")
				.append(orderNumber)
				.append(" and request version=")
				.append(requestVersion)
				.append(". Response order number=")
				.append(fraudAssessmentResult.getOrderNumber())
				.append(" and request version=")
				.append(fraudAssessmentResult.getRequestVersion());
			
			throw new RuntimeException(builder.toString());
		}
		
	}

}
