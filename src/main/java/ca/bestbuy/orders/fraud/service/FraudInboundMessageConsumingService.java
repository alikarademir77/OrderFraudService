/**
 * 
 */
package ca.bestbuy.orders.fraud.service;

import org.springframework.stereotype.Service;

import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Service
@Slf4j
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{

	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.messaging.MessageConsumingService#consumeMessage(ca.bestbuy.orders.messaging.MessagingEvent)
	 */
	@Override
	public void consumeMessage(MessagingEvent event) {
		
		log.info("Received event:"+event);
		
		throw new RuntimeException("failed");

	}

}
