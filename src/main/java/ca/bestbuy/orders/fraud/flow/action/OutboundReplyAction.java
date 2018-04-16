package ca.bestbuy.orders.fraud.flow.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import ca.bestbuy.orders.fraud.dao.FraudRequestStatusHistoryRepository;
import ca.bestbuy.orders.fraud.flow.FlowEvents;
import ca.bestbuy.orders.fraud.flow.FlowStateMachineConfig.KEYS;
import ca.bestbuy.orders.fraud.flow.FlowStates;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistoryDetail;
import ca.bestbuy.orders.fraud.service.OutboundQueueProducerService;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessagingEvent;
import ca.bestbuy.orders.messaging.model.FraudResult;
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;

@Component
public class OutboundReplyAction extends ActionWithException<FlowStates, FlowEvents> {

    private final OutboundQueueProducerService outboundMessageService;
    private final FraudRequestStatusHistoryRepository fraudRequestStatusHistoryRepository;


    @Autowired
    public OutboundReplyAction(OutboundQueueProducerService outboundMessageService, FraudRequestStatusHistoryRepository fraudRequestStatusHistoryRepository) {

        Assert.notNull(outboundMessageService, "OutboundMessageProducerService must not be null");
        Assert.notNull(fraudRequestStatusHistoryRepository, "FraudRequestStatusHistoryRepository must not be null");

        this.outboundMessageService = outboundMessageService;
        this.fraudRequestStatusHistoryRepository = fraudRequestStatusHistoryRepository;
    }


    @Override
    protected void doExecute(StateContext<FlowStates, FlowEvents> context) throws Exception {

        // NOTE: Here we will return the same fraud request version that is in the request message even if there is a more up to date version in the DB

        // Get request from state machine's extended state
        MessagingEvent messagingEvent = (MessagingEvent) context.getExtendedState().getVariables().get(KEYS.REQUEST);

        // Get fraud request status history
        FraudRequestStatusHistory fraudRequestStatusHistory = getFraudRequestDetails(messagingEvent.getOrderNumber(), messagingEvent.getRequestVersion());

        // Create outbound message
        OutboundMessagingEvent outboundMessage = createOutboundMessage(messagingEvent, fraudRequestStatusHistory);

        // Send outbound message
        outboundMessageService.sendOutboundMessage(outboundMessage);
    }


    @Override
    protected FlowEvents getErrorEvent() {
        return FlowEvents.SM_ERROR_EVENT;
    }


    private FraudRequestStatusHistory getFraudRequestDetails(String orderNumber, String requestVersion) {

        // Convert order number and request version to their expected number type, to be used for querying the database
        BigDecimal orderNumberAsNumber = new BigDecimal(Long.valueOf(orderNumber, 10));
        Long requestVersionAsNumber = Long.valueOf(requestVersion);

        // Get the fraud request status history for order number and request version
        Iterable<FraudRequestStatusHistory> fraudRequestStatusHistoryIterable = fraudRequestStatusHistoryRepository.findByFraudRequestOrderNumberAndFraudRequestRequestVersion(
            orderNumberAsNumber, requestVersionAsNumber, new Sort(Direction.DESC, "fraudRequestStatusHistoryId"));

        // Add results to a list (will be sorted by ID)
        List<FraudRequestStatusHistory> fraudRequestStatuses = new ArrayList<>();
        fraudRequestStatusHistoryIterable.forEach(fraudRequestStatuses::add);

        if (fraudRequestStatuses.size() == 0) {
            throw new IllegalStateException("Query to get status details for fraud request returned no results, but results are expected at this point");
        }

        // Get the first fraud request status history in the list which will be the most recent status
        return fraudRequestStatuses.get(0);
    }


    private OutboundMessagingEvent createOutboundMessage(MessagingEvent requestMessage, FraudRequestStatusHistory fraudRequestStatusHistory) {

        // Extract order number, request version, and event type from message
        String orderNumber = requestMessage.getOrderNumber();
        String requestVersion = requestMessage.getRequestVersion();
        EventTypes eventType = requestMessage.getType();

        // Get the details of the fraud request status
        FraudRequestStatusHistoryDetail details = fraudRequestStatusHistory.getFraudRequestStatusHistoryDetail();

        // Create outbound message
        FraudResult result = FraudResult.Builder.create(details.getFraudResponseStatusCode().name())
            .totalFraudScore(details.getTotalFraudScore())
            .recommendationCode(details.getRecommendationCode())
            .accertifyUser(details.getAccertifyUser())
            .accertifyUserCreationDate(details.getAccertifyUserActionTime())
            .build();

        OutboundMessagingEvent outboundMessage = new OutboundMessagingEvent(eventType, orderNumber, requestVersion, result);

        return outboundMessage;
    }


}
