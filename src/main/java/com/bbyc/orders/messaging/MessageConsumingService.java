/**
 * 
 */
package com.bbyc.orders.messaging;

/**
 * @author akaradem
 *
 */
public interface MessageConsumingService<T> {

	public void consumeMessage(T event);
	
}
