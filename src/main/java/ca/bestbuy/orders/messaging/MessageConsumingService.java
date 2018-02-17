/**
 * 
 */
package ca.bestbuy.orders.messaging;

/**
 * @author akaradem
 *
 */
public interface MessageConsumingService<T> {

	public void consumeMessage(T event);
	
}
