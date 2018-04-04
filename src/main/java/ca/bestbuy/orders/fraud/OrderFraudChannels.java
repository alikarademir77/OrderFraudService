/**
 * 
 */
package ca.bestbuy.orders.fraud;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author akaradem
 *
 */
public interface OrderFraudChannels {

	String INPUT = "fraudInbound";
	String OUTPUT = "fraudOutbound";
	
	/**
	 * @return
	 */
	@Input(OrderFraudChannels.INPUT)
	SubscribableChannel fraudInbound();

	/**
	 * @return
	 */
	@Output(OrderFraudChannels.OUTPUT)
	MessageChannel fraudOutbound();
	
}
