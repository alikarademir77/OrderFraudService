/**
 * 
 */
package com.bbyc.orders.fraud;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.bbyc.orders.messaging.MessagingEvent;


/**
 * @author akaradem
 *
 */
@Component
@EnableBinding(OrderFraudChannels.class)
public class OrderFraudMessagingClient {
	
	@Value("${messaging.errorRetryCount}")
	private Long errorRetryCount; 
	
	private final OrderFraudChannels channels;
	
	
	@Autowired
	public OrderFraudMessagingClient(OrderFraudChannels channels){
		this.channels = channels;
	}

	
	@StreamListener(OrderFraudChannels.INPUT)
	public void receiveEvent(MessagingEvent event, @Header(name = "x-death", required = false) Map<?,?> death) {
		if (death != null && death.get("count").equals(errorRetryCount)) {
            // giving up - don't send to DLX
			System.out.println("Reached retry limit for event (" + event + ")..");
			return;
        }

		System.out.println("Received event:");
		System.out.println("Type:"+event.getType());
		System.out.println("OrderNumber:"+event.getOrderNumer());
		System.out.println("PurchaseOrderNumber:"+event.getPurchaseOrderNumer());
		System.out.println("RequestVersion:"+event.getRequestVersion());
		System.out.println("CreDate:"+event.getMessageCreationDate().getTime());
		
		throw new RuntimeException("failed");
		
	}


}