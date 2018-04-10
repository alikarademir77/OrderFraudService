/**
 * 
 */
package ca.bestbuy.orders.fraud.flow.action;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.statemachine.StateContext;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.dao.FraudRequestStatusHistoryRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult.FraudResponseStatusCodes;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.service.FraudOutboundMessageProducingService;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessagingEvent;
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;


@RunWith(SpringRunner.class)
public class OutboundReplyActionTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    StateContext<FlowStates, FlowEvents> context;

    @Mock
    FraudRequestStatusHistoryRepository repository;

    @Mock
    FraudOutboundMessageProducingService service;


    @Test
    public void testDoExecute() throws Exception {

        OutboundReplyAction action = new OutboundReplyAction(service, repository);

        when(context.getExtendedState().getVariables().get(KEYS.REQUEST)).thenReturn(createRequestMessage("1234", "1", EventTypes.FraudCheck));

        when(repository.findByFraudRequestOrderNumberAndFraudRequestRequestVersion(any(), anyLong(), any())).thenReturn(createFraudRequestStatusHistories(3));

        OutboundMessagingEvent outboundMessagingEvent = OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "1", FraudResponseStatusCodes.ACCEPTED.name()).accertifyUser("user").recommendationCode("9080").totalFraudScore("1000").build();

        action.doExecute(context);

        ArgumentCaptor<OutboundMessagingEvent> argumentCaptor = ArgumentCaptor.forClass(OutboundMessagingEvent.class);
        verify(service).sendOutboundMessage(argumentCaptor.capture());

        Assert.assertEquals(argumentCaptor.getValue().getType(), EventTypes.FraudCheck);
        Assert.assertEquals(argumentCaptor.getValue().getOrderNumber(), "1234");
        Assert.assertEquals(argumentCaptor.getValue().getResult().getStatus(), FraudResponseStatusCodes.ACCEPTED.name());
        Assert.assertEquals(argumentCaptor.getValue().getResult().getAccertifyUser(), "user");
        Assert.assertEquals(argumentCaptor.getValue().getResult().getTotalFraudScore(), "1000");
        Assert.assertEquals(argumentCaptor.getValue().getResult().getRecommendationCode(), "9080");
        Assert.assertEquals(argumentCaptor.getValue().getResult().getRequestVersion(), "1");
    }


    private MessagingEvent createRequestMessage(String orderNumber, String requestVersion, EventTypes eventType) {
        MessagingEvent messagingEvent = new MessagingEvent(eventType, orderNumber, null, requestVersion, new Date());
        return messagingEvent;
    }


    private List<FraudRequestStatusHistory> createFraudRequestStatusHistories(int numberOfItems) {
        List<FraudRequestStatusHistory> list = new ArrayList<>();

        for(long i = 1; i <= numberOfItems; i++) {
            list.add(createFraudRequestStatusHistory(i));
        }

        return list;
    }


    private FraudRequestStatusHistory createFraudRequestStatusHistory(Long id) {

        FraudRequestStatusHistory statusHistory = new FraudRequestStatusHistory();
        statusHistory.setFraudRequestStatusHistoryId(id);

        FraudRequestStatusHistoryDetail details = new FraudRequestStatusHistoryDetail();
        details.setAccertifyUser("user");
        details.setTotalFraudScore("1000");
        details.setRecommendationCode("9080");
        details.setFraudResponseStatusCode(FraudResponseStatusCodes.ACCEPTED);

        statusHistory.setFraudRequestStatusHistoryDetail(details);

        return statusHistory;
    }



}
