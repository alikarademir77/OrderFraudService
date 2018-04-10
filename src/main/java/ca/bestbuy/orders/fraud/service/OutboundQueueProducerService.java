package ca.bestbuy.orders.fraud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ca.bestbuy.orders.fraud.OrderFraudChannels;
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;

@Service
public class OutboundQueueProducerService {

    private OrderFraudChannels channels;

    private Long timeout;

    @Value("${messaging.outbound.timeout}")
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Autowired
    public OutboundQueueProducerService(OrderFraudChannels channels) {
        Assert.notNull(channels, "Input channels cannot be null");
        this.channels = channels;
    }


    public void sendOutboundMessage(OutboundMessagingEvent outboundMessage) {

        Assert.notNull(outboundMessage, "Input message cannot be null");

        Message<OutboundMessagingEvent> messageToSend = MessageBuilder.withPayload(outboundMessage).build();

        // TODO: Will need to handle timeout exceptions
        boolean messageSent;
        if(timeout != null) {
            messageSent = channels.fraudOutbound().send(messageToSend, timeout);
        } else {
            messageSent = channels.fraudOutbound().send(messageToSend);
        }

        if(!messageSent) {
            // TODO: Retry or throw an exception
        }

    }

}
