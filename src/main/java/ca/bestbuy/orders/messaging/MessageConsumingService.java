/**
 * 
 */
package ca.bestbuy.orders.messaging;

/**
 * @author akaradem
 *
 */
public interface MessageConsumingService<T> {

	/**
	 * @param event
	 */
	public void consumeMessage(T event) throws Exception ;
	
}
