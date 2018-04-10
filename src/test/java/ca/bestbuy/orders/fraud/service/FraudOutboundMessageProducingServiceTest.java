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
import ca.bestbuy.orders.messaging.model.OutboundMessagingEvent;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"dev", "unittest"})
public class FraudOutboundMessageProducingServiceTest {


    @Autowired
    private OrderFraudChannels channels;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private FraudOutboundMessageProducingService service;

    @Test
    public void testProducer_AllFieldsPopulatedInMessage() throws Exception {

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.fraudOutbound());

        // Create message
        OutboundMessagingEvent message = OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "1", "SUCCESS").totalFraudScore("5000").recommendationCode("9080").accertifyUser("user").accertifyUserCreationDate(new Date()).build();

        // Send message
        service.sendOutboundMessage(message);

        Matcher<String> matcher1 = Matchers.containsString("{\"type\":\"FraudCheck\",\"orderNumber\":\"1234\",\"messageCreationDate\":\"");
        Matcher<String> matcher2 = Matchers.containsString("\"result\":{\"status\":\"SUCCESS\",\"requestVersion\":\"1\","
            + "\"totalFraudScore\":\"5000\",\"recommendationCode\":\"9080\",\"accertifyUser\":\"user\",\"accertifyUserCreationDate\":\"");

        Assert.assertThat(messages, MessageQueueMatcher.receivesPayloadThat(Matchers.allOf(matcher1, matcher2)));
    }



    @Test
    public void testProducer_OnlyRequiredFieldsPopulatedInMessage() throws Exception {

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.fraudOutbound());

        // Create message
        OutboundMessagingEvent message = OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "1", "SUCCESS").build();

        // Send message
        service.sendOutboundMessage(message);

        Matcher<String> matcher1 = Matchers.containsString("{\"type\":\"FraudCheck\",\"orderNumber\":\"1234\",\"messageCreationDate\":\"");
        Matcher<String> matcher2 = Matchers.containsString("\"result\":{\"status\":\"SUCCESS\",\"requestVersion\":\"1\"}}");

        Assert.assertThat(messages, MessageQueueMatcher.receivesPayloadThat(Matchers.allOf(matcher1, matcher2)));
    }


}