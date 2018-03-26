/**
 * 
 */
package ca.bestbuy.orders.fraud;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.TimeZone;

import ca.bestbuy.orders.fraud.client.FraudServiceTASClient;
import ca.bestbuy.orders.fraud.client.FraudServiceTASClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.bestbuy.orders.fraud.client.OrderDetailsClient;
import ca.bestbuy.orders.fraud.client.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.client.ResourceApiClientConfig;
import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderFraudServiceApplication.class)
@TestPropertySource(properties = {
	    "messaging.errorRetryCount=3",
	})
@DirtiesContext
public class OrderFraudServiceMessageConsumptionTest {

	@Autowired 
	private OrderFraudChannels channels;
	
	@MockBean
	private MessageConsumingService<MessagingEvent> messageConsumingService;

	@MockBean
	private ResourceApiClientConfig resourceApiClientConfig;

	@MockBean
	private OrderDetailsClientConfig orderDetailsClientConfig;
	
	@MockBean
	OrderDetailsClient orderDetailsClient;

	@MockBean
	private FraudServiceTASClientConfig fraudServiceTASClientConfig;

	@MockBean
	private FraudServiceTASClient fraudServiceTASClient;
	    
    @Test
    public void contextLoadsAndWiring() {
        assertNotNull(this.channels.fraudInbound());
        assertNotNull(this.channels.fraudOutbound());
    }
	
	
	@Test
    public void whenSendMessagingEventThenConsumingServiceShouldReceiveSameEvent() {
		Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		MessagingEvent event = new MessagingEvent(
				EventTypes.FraudCheck,"FSOrder1234", null, "1", currentTime.getTime());
		
		this.channels.fraudInbound()
          .send(MessageBuilder.withPayload(event)
          .build());

		ArgumentCaptor<MessagingEvent> messageArgumentCaptor =
				(ArgumentCaptor<MessagingEvent>) ArgumentCaptor.forClass(MessagingEvent.class);
		
		verify(this.messageConsumingService, times(1)).consumeMessage(messageArgumentCaptor.capture());
		
		MessagingEvent capturedEvent = messageArgumentCaptor.getValue();
		
		assertTrue(event.equals(capturedEvent));
    }
}
