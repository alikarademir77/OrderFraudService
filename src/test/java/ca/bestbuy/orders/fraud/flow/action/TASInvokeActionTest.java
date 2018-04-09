/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.client.orderdetails.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.tas.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult.FraudResponseStatusCodes;
import ca.bestbuy.orders.fraud.model.internal.Order;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;
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
public class TASInvokeActionTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;
	
	@MockBean
	OrderDetailsClient orderDetailsClientMock;
	@MockBean
	FraudServiceTASClient  fraudServiceTASClientMock;
	
	@Autowired
	CreateInitialRequestAction createInitialRequestAction;
	
	@Autowired
	TASInvokeAction tasInvokeAction;
	
	@Autowired	
	FraudRequestRepository fraudRequestRepository;
	
	@Test
	@Transactional
	public void testExecuteForFraudResultStatusAccepted(){
		String orderNumber = "123456";
		long requestVersion = 1;
		FraudResponseStatusCodes fraudResponseStatusCode= FraudResponseStatusCodes.ACCEPTED;
		
		executeRecordPhase(orderNumber, requestVersion, fraudResponseStatusCode);
		
		tasInvokeAction.execute(context);
		
		executeVerificationPhase(orderNumber, requestVersion, FraudStatusCodes.FINAL_DECISION);
	}

	@Test
	@Transactional
	public void testExecuteForFraudResultStatusSoftDeclined(){
		String orderNumber = "123456";
		long requestVersion = 1;
		FraudResponseStatusCodes fraudResponseStatusCode= FraudResponseStatusCodes.SOFT_DECLINE;
		
		executeRecordPhase(orderNumber, requestVersion, fraudResponseStatusCode);
		
		tasInvokeAction.execute(context);
		
		executeVerificationPhase(orderNumber, requestVersion, FraudStatusCodes.FINAL_DECISION);
	}

	@Test
	@Transactional
	public void testExecuteForFraudResultStatusHardDeclined(){
		String orderNumber = "123456";
		long requestVersion = 1;
		FraudResponseStatusCodes fraudResponseStatusCode= FraudResponseStatusCodes.HARD_DECLINE;
		
		executeRecordPhase(orderNumber, requestVersion, fraudResponseStatusCode);
		
		tasInvokeAction.execute(context);
		
		executeVerificationPhase(orderNumber, requestVersion, FraudStatusCodes.FINAL_DECISION);
	}

	@Test
	@Transactional
	public void testExecuteForFraudResultStatusPendingReview(){
		String orderNumber = "123456";
		long requestVersion = 1;
		FraudResponseStatusCodes fraudResponseStatusCode= FraudResponseStatusCodes.PENDING_REVIEW;
		
		executeRecordPhase(orderNumber, requestVersion, fraudResponseStatusCode);
		
		tasInvokeAction.execute(context);
		
		executeVerificationPhase(orderNumber, requestVersion, FraudStatusCodes.PENDING_REVIEW);
	}	
	/**
	 * @param orderNumber
	 * @param requestVersion
	 */
	private void executeVerificationPhase(String orderNumber, long requestVersion, FraudStatusCodes finalStatusCode) {
		Iterable<FraudRequest> retrievedRequestsIt = fraudRequestRepository.findByOrderNumberAndRequestVersion(new BigDecimal(orderNumber), requestVersion);
		assertNotNull(retrievedRequestsIt);
		List<FraudRequest> foundFraudRequestList = new ArrayList<>();
		retrievedRequestsIt.forEach(foundFraudRequestList::add);
		
		assertTrue(foundFraudRequestList.size()==1);
		FraudRequest foundRequest = foundFraudRequestList.get(0);
		assertTrue(foundRequest.getFraudStatusStateMachine().getState().getId() == finalStatusCode);
		assertTrue(foundRequest.getFraudRequestStatusHistory().size()==2);		
		List<FraudRequestStatusHistory> statusHistoryList = foundRequest.getFraudRequestStatusHistory();
		Collections.sort(statusHistoryList, new Comparator<FraudRequestStatusHistory>(){

			@Override
			public int compare(FraudRequestStatusHistory o1, FraudRequestStatusHistory o2) {
				if(o1.getFraudRequestStatusHistoryId()<o2.getFraudRequestStatusHistoryId()){
					return 1;
				}else if(o1.getFraudRequestStatusHistoryId()==o2.getFraudRequestStatusHistoryId()){
					return 0;
				}else {
					return -1;
				}	
			}
		});
		assertTrue(statusHistoryList.get(0).getFraudStatusStateMachine().getState().getId()==finalStatusCode);
		assertTrue(statusHistoryList.get(1).getFraudStatusStateMachine().getState().getId()==FraudStatusCodes.INITIAL_REQUEST);
	}
	/**
	 * @param orderNumber
	 * @param requestVersion
	 * @param fraudResponseStatusCode
	 */
	private void executeRecordPhase(String orderNumber, long requestVersion,
			FraudResponseStatusCodes fraudResponseStatusCode) {
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);
		
		createInitialRequestAction.execute(context);
		
		when(orderDetailsClientMock.getOrderDetails(orderNumber)).thenReturn(new Order());		
		when(fraudServiceTASClientMock.doFraudCheck(any(FraudAssessmentRequest.class))).thenReturn(
				(new FraudAssessmentResult())
					.setOrderNumber(orderNumber)
					.setRequestVersion(requestVersion)
					.setRecommendationCode("recommendationCode")
					.setTotalFraudScore("1975")
					.setTasRequest("TASRequest")
					.setTasResponse("TASResponse")
					.setAccertifyUser("abc@accertify.com")
					.setAccertifyUserActionTime(new Date())
					.setFraudResponseStatus(fraudResponseStatusCode));
	}	
}
