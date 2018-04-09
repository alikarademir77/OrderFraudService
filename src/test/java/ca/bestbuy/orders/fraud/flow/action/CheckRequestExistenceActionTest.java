/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudServiceApplication;
import ca.bestbuy.orders.fraud.dao.FraudRequestRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;
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
public class CheckRequestExistenceActionTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	StateContext<FlowStates, FlowEvents> context;
	
	@MockBean
	FraudRequestRepository fraudRequestRepository;
	
	@Autowired
	CheckRequestExistenceAction checkRequestExistenceAction;
	
	@Autowired
    CreateInitialRequestAction createInitialRequestAction;
	
	@Test
	public void testExecuteForRequestNotFound(){
		String orderNumber = "123456";
		long requestVersion = 1;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);
		
		List<FraudRequest> foundList = Arrays.asList(new FraudRequest[]{null});
		when(fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(new BigDecimal(orderNumber), requestVersion)).thenReturn(foundList);
		
		ArgumentCaptor<List> requestListArgumentCaptor =
				(ArgumentCaptor<List>) ArgumentCaptor.forClass(List.class);

		when(context.getExtendedState().getVariables().put(eq(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT), requestListArgumentCaptor.capture())).thenReturn(null);
		checkRequestExistenceAction.execute(context);
		
		List<FraudRequest> capturedList = requestListArgumentCaptor.getValue();
		assertTrue(capturedList.get(0)==null);
	}

	@Test
	public void testExecuteForNewerRequestVersionFound(){
		String orderNumber = "123456";
		long requestVersion = 1l;
		long foundRequestVersion= 2l;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());

		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);
		
		List<FraudRequest> foundList = Arrays.asList(new FraudRequest[]{(
				new FraudRequest()
					.setOrderNumber(new BigDecimal(orderNumber))
					.setRequestVersion(foundRequestVersion))});
		when(fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(new BigDecimal(orderNumber), requestVersion)).thenReturn(foundList);

		ArgumentCaptor<List> requestListArgumentCaptor =
				(ArgumentCaptor<List>) ArgumentCaptor.forClass(List.class);

		when(context.getExtendedState().getVariables().put(eq(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT), requestListArgumentCaptor.capture())).thenReturn(null);
		checkRequestExistenceAction.execute(context);
		
		List<FraudRequest> capturedList = requestListArgumentCaptor.getValue();
		assertTrue(capturedList.get(0).getRequestVersion()== foundRequestVersion);
	}

	@Test
	public void testExecuteForSameRequestVersionFound(){
		String orderNumber = "123456";
		long requestVersion = 1l;
		MessagingEvent event = new MessagingEvent(EventTypes.FraudCheck, orderNumber, null, String.valueOf(requestVersion), new Date());
		when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(event);
		
		List<FraudRequest> foundList = Arrays.asList(new FraudRequest[]{(
				new FraudRequest()
					.setOrderNumber(new BigDecimal(orderNumber))
					.setRequestVersion(requestVersion))});
		when(fraudRequestRepository.findByOrderNumberAndRequestVersionGTE(new BigDecimal(orderNumber), requestVersion)).thenReturn(foundList);

		ArgumentCaptor<List> requestListArgumentCaptor =
				(ArgumentCaptor<List>) ArgumentCaptor.forClass(List.class);

		when(context.getExtendedState().getVariables().put(eq(KEYS.MAX_VERSION_EXISTENCE_CHECK_RESULT), requestListArgumentCaptor.capture())).thenReturn(null);
		checkRequestExistenceAction.execute(context);
		
		List<FraudRequest> capturedList = requestListArgumentCaptor.getValue();
		assertTrue(capturedList.get(0).getRequestVersion()==requestVersion);
	}
	
}
