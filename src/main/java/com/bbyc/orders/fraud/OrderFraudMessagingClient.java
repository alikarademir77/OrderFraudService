/**
 * 
 */
package com.bbyc.orders.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author akaradem
 *
 */
@Component
public class OrderFraudMessagingClient {
	
	private final OrderFraudChannels channels;

	@Autowired
	public OrderFraudMessagingClient(OrderFraudChannels channels){
		this.channels = channels;
	}
	
	@StreamListener(OrderFraudChannels.INPUT)
	public void receiveFromChannel(String event){
		System.out.println("Received event:"+event);
	}
	
	public void publishToChannel(String messageText){
		channels.fraudOutbound().send(MessageBuilder.withPayload(messageText).build());
	}
	
}
