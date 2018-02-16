/**
 * 
 */
package com.bbyc.orders.fraud.service;

import org.springframework.stereotype.Service;

import com.bbyc.orders.messaging.MessageConsumingService;
import com.bbyc.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */
@Service
public class FraudInboundMessageConsumingService implements MessageConsumingService <MessagingEvent>{

	/* (non-Javadoc)
	 * @see com.bbyc.orders.messaging.MessageConsumingService#consumeMessage(com.bbyc.orders.messaging.MessagingEvent)
	 */
	@Override
	public void consumeMessage(MessagingEvent event) {
		
		System.out.println("Received event:");
		System.out.println("Type:"+event.getType());
		System.out.println("OrderNumber:"+event.getOrderNumer());
		System.out.println("PurchaseOrderNumber:"+event.getPurchaseOrderNumer());
		System.out.println("RequestVersion:"+event.getRequestVersion());
		System.out.println("CreDate:"+event.getMessageCreationDate().getTime());
		
		throw new RuntimeException("failed");

	}

}
