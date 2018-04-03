/**
 * 
 */
package ca.bestbuy.orders.fraud;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.bestbuy.orders.messaging.EventTypes;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderFraudServiceApplication.class)
@ActiveProfiles({"dev","unittest"})
@DirtiesContext
public class OrderFraudServiceMessageConsumptionTest {

	@Autowired 
	private OrderFraudChannels channels;
	
	@MockBean
	private MessageConsumingService<MessagingEvent> messageConsumingService;

    @Test
    public void contextLoadsAndWiring() {
        assertNotNull(this.channels.fraudInbound());
        assertNotNull(this.channels.fraudOutbound());
    }
	
	
	@Test
    public void whenSendMessagingEventThenConsumingServiceShouldReceiveSameEvent() throws Exception {
		MessagingEvent event = new MessagingEvent(
				EventTypes.FraudCheck,"FSOrder1234", null, "1", new Date());
		ObjectMapper mapper = new ObjectMapper();
		String msg = mapper.writeValueAsString(event);
		
		Message<String> message = MessageBuilder.withPayload(msg).build();
		this.channels.fraudInbound()
          .send(message);

		ArgumentCaptor<MessagingEvent> messageArgumentCaptor =
				(ArgumentCaptor<MessagingEvent>) ArgumentCaptor.forClass(MessagingEvent.class);
		
		verify(this.messageConsumingService, times(1)).consumeMessage(messageArgumentCaptor.capture());
		
		MessagingEvent capturedEvent = messageArgumentCaptor.getValue();
		String capturedMsg =  mapper.writeValueAsString(capturedEvent);
		assertTrue(msg.equals(capturedMsg));
    }
}
