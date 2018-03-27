package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestStatusHistoryRepository;
import ca.bestbuy.orders.fraud.dao.FraudRequestTypeRepository;
import ca.bestbuy.orders.fraud.dao.FraudStatusRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult;
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
public class TASInvokeAction implements Action<FlowStates, FlowEvents> {

	@Value("${spring.datasource.username}")
	private String userName;
	
	@Autowired
	OrderDetailsClient orderDetailsClient;
	@Autowired
	FraudServiceTASClient  fraudServiceTASClient;

	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	@Autowired	
	FraudRequestStatusHistoryRepository StatusHistoryRepository;
	
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
		//TODO: Validate that response and request has same order number and request version
				
		if(fraudAssesmentResult!=null){

			Iterable<FraudRequest> fraudRequestIt = 
					fraudRequestRepository.findByOrderNumberAndRequestVersion(new BigDecimal(orderNumber), Long.valueOf(requestVersion, 10));
			
			if((fraudRequestIt!=null) && (fraudRequestIt.iterator().hasNext())){
				FraudRequest fraudRequest =  fraudRequestIt.iterator().next();
				persistInvocationResult(fraudAssesmentResult, fraudRequest);
			}
		
		}
	}

	private void persistInvocationResult(FraudAssesmentResult fraudAssesmentResult, FraudRequest fraudRequest) {
		FraudAssesmentResult.FraudResponseStatusCodes fraudResponseStatusCode = fraudAssesmentResult.getFraudResponseStatus();

		Date now = new Date();
		//Set Status History
		FraudRequestStatusHistory statusHistory = new FraudRequestStatusHistory();
		statusHistory.setCreateDate(now);
		statusHistory.setCreateUser(userName);
		statusHistory.setUpdateDate(now);
		statusHistory.setUpdateUser(userName);
		statusHistory.setFraudRequest(fraudRequest);
		
		if(fraudResponseStatusCode == FraudAssesmentResult.FraudResponseStatusCodes.PENDING_REVIEW){
			statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
			fraudRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.PENDING_REVIEW_RECEIVED);
		}else if (decisionMadeResponseStatusCodes().contains(fraudResponseStatusCode)){
			statusHistory.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
			fraudRequest.getFraudStatusStateMachine().sendEvent(FraudStatusEvents.FINAL_DECISION_RECEIVED);
		}else {
			//TODO: Throw Exception
		}
		fraudRequest.getFraudRequestStatusHistory().add(statusHistory);
		
		//Set FraudRequestStatusHistoryDetail
		FraudRequestStatusHistoryDetail statusHistoryDetail = new FraudRequestStatusHistoryDetail();
		statusHistoryDetail.setCreateDate(now);
		statusHistoryDetail.setCreateUser(userName);
		statusHistoryDetail.setFraudRequestStatusHistory(statusHistory);
		statusHistoryDetail.setFraudResponseStatusCode(fraudResponseStatusCode);
		statusHistoryDetail.setTotalFraudScore(fraudAssesmentResult.getTotalFraudScore());
		statusHistoryDetail.setRecommendationCode(fraudAssesmentResult.getRecommendationCode());
		statusHistoryDetail.setAccertifyUser(fraudAssesmentResult.getAccertifyUser());
		statusHistoryDetail.setAccertifyUserActionTime(fraudAssesmentResult.getAccertifyUserActionTime());
		statusHistoryDetail.setTasRequest(fraudAssesmentResult.getTasRequest());
		statusHistoryDetail.setTasResponse(fraudAssesmentResult.getTasResponse());
		statusHistoryDetail.setUpdateDate(now);
		statusHistoryDetail.setUpdateUser(userName);
		
		statusHistory.setFraudRequestStatusHistoryDetail(statusHistoryDetail);
		fraudRequestRepository.save(fraudRequest);
	}
	
	private List<FraudAssesmentResult.FraudResponseStatusCodes> decisionMadeResponseStatusCodes(){
		return Arrays.asList(new FraudAssesmentResult.FraudResponseStatusCodes[]{
				FraudAssesmentResult.FraudResponseStatusCodes.ACCEPTED,
				FraudAssesmentResult.FraudResponseStatusCodes.HARD_DECLINE,
				FraudAssesmentResult.FraudResponseStatusCodes.SOFT_DECLINE
		});
	}

	//TODO: Remove
//	private Order createTestOrder(){
//
//	    Order fraudOrder = new Order();
//
//
//	    List<ShippingOrder> shippingOrders = new ArrayList<>();
//	    ShippingOrder shippingOrder = new ShippingOrder();
//	    shippingOrder.setTotalAuthorizedAmount(new BigDecimal(1500));
//	    shippingOrders.add(shippingOrder);
//	    fraudOrder.setShippingOrders(shippingOrders);
//	    
//	    List<Item> itemList = new ArrayList<>();
//
//	    Item item= new Item();
//	    item.setFsoLineID("fsolineid");
//	    item.setName("name");
//	    item.setCategory("category");
//	    item.setItemUnitPrice(new BigDecimal("9000"));
//	    item.setItemQuantity(1);
//	    item.setItemTax(new BigDecimal("0"));
//	    item.setItemTotalDiscount(new BigDecimal("0"));
//	    item.setStaffDiscount(new BigDecimal("0"));
//	    item.setItemStatus("OPEN");
//	    item.setItemSkuNumber("sku");
//	    item.setItemSkuDescription("desc");
//	    item.setPostCaptureDiscount(new BigDecimal("0"));
//
//	    itemList.add(item);
//	    fraudOrder.setItems(itemList);
//
//	    return fraudOrder;
//
//	}
}
