package ca.bestbuy.orders.fraud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ca.bestbuy.orders.fraud.OrderFraudChannels;
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OutboundQueueProducerService {

    private OrderFraudChannels channels;

    private Long timeout;
    @Value("${messaging.outbound.timeout}")
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    private Integer retryAttempts;
    @Value("${messaging.outbound.retry-attempts}")
    public void setRetryAttempts(Integer retryAttempts) {
        if(retryAttempts == null || retryAttempts < 0) {
            this.retryAttempts = 0;
        } else {
            this.retryAttempts = retryAttempts;
        }
    }

    @Autowired
    public OutboundQueueProducerService(OrderFraudChannels channels) {
        Assert.notNull(channels, "Input channels cannot be null");
        this.channels = channels;
    }


    public void sendOutboundMessage(OutboundMessagingEvent outboundMessage) {

        Assert.notNull(outboundMessage, "Input message cannot be null");

        Message<OutboundMessagingEvent> messageToSend = MessageBuilder.withPayload(outboundMessage).build();

        // Will retry in the event of failure to write to the outbound queue if retry attempts are configured
        int retries = 0;
        boolean messageSent = false;
        do {

            if(timeout != null) {
                messageSent = channels.fraudOutbound().send(messageToSend, timeout);
            } else {
                messageSent = channels.fraudOutbound().send(messageToSend);
            }

            retries++;

        } while (retries <= retryAttempts && !messageSent);


        if(!messageSent) {
            // TODO - Will probably want to handle this once we are actually sending back data to OMS (phase 2)
            log.warn("Outbound message was not delivered. Attempted " + retryAttempts + " retries.");
        } else {
            log.info("Outbound message was delivered to the outbound queue successfully");
        }

    }

}
