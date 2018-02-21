/**
 * 
 */
package ca.bestbuy.orders.fraud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Service
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see ca.bestbuy.orders.messaging.MessageConsumingService#consumeMessage(ca.bestbuy.orders.messaging.MessagingEvent)
	 */
	@Override
	public void consumeMessage(MessagingEvent event) {
		
		log.info("Received event:"+event);
		throw new RuntimeException("failed");

	}





}
