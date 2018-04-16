package ca.bestbuy.orders.fraud.service;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.cloud.stream.test.matcher.MessageQueueMatcher;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bestbuy.orders.fraud.OrderFraudChannels;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.model.FraudResult;
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"unittest"})
public class OutboundMessageProducerServiceTest {


    @Autowired
    private OrderFraudChannels channels;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private OutboundQueueProducerService service;

    @Test
    public void testProducer_AllFieldsPopulatedInMessage() throws Exception {

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.fraudOutbound());

        // Create message
        FraudResult result = FraudResult.Builder.create("SUCCESS")
            .totalFraudScore("5000")
            .recommendationCode("9080")
            .accertifyUser("user")
            .accertifyUserCreationDate(new Date())
            .build();

        OutboundMessagingEvent message = new OutboundMessagingEvent(EventTypes.FraudCheck, "1", "1234", result);

        // Send message
        service.sendOutboundMessage(message);

        Matcher<String> matcher1 = Matchers.containsString("{\"type\":\"FraudCheck\",\"requestVersion\":\"1\",\"orderNumber\":\"1234\",\"messageCreationDate\":\"");
        Matcher<String> matcher2 = Matchers.containsString("\"result\":{\"status\":\"SUCCESS\","
            + "\"totalFraudScore\":\"5000\",\"recommendationCode\":\"9080\",\"accertifyUser\":\"user\",\"accertifyUserCreationDate\":\"");

        Assert.assertThat(messages, MessageQueueMatcher.receivesPayloadThat(Matchers.allOf(matcher1, matcher2)));
    }



    @Test
    public void testProducer_OnlyRequiredFieldsPopulatedInMessage() throws Exception {

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.fraudOutbound());

        // Create message
        FraudResult result = FraudResult.Builder.create("SUCCESS")
            .build();

        OutboundMessagingEvent message = new OutboundMessagingEvent(EventTypes.FraudCheck, "1", "1234", result);

        // Send message
        service.sendOutboundMessage(message);

        Matcher<String> matcher1 = Matchers.containsString("{\"type\":\"FraudCheck\",\"requestVersion\":\"1\",\"orderNumber\":\"1234\",\"messageCreationDate\":\"");
        Matcher<String> matcher2 = Matchers.containsString("\"result\":{\"status\":\"SUCCESS\"}}");

        Assert.assertThat(messages, MessageQueueMatcher.receivesPayloadThat(Matchers.allOf(matcher1, matcher2)));
    }


}