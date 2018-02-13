/**
 * 
 */
package com.bbyc.orders.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.bbyc.orders.messaging.MessagingEvent;


/**
 * @author akaradem
 *
 */
@Component
@EnableBinding(OrderFraudChannels.class)
public class OrderFraudMessagingClient {
	
	private final OrderFraudChannels channels;
	
	@Autowired
	public OrderFraudMessagingClient(OrderFraudChannels channels){
		this.channels = channels;
	}
	
	@StreamListener(OrderFraudChannels.INPUT)
	public void receiveEvent(MessagingEvent event) {
		System.out.println("Received event:");
		System.out.println("Type:"+event.getType());
		System.out.println("OrderNumber:"+event.getOrderNumer());
		System.out.println("PurchaseOrderNumber:"+event.getPurchaseOrderNumer());
		System.out.println("RequestVersion:"+event.getRequestVersion());
		System.out.println("CreDate:"+event.getMessageCreationDate().getTime());
	}
	
}
