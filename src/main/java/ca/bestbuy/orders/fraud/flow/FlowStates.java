package ca.bestbuy.orders.fraud.flow;

/**
 * @author akaradem
 *
 */
public enum FlowStates {
	READY, 
	REQUEST_EXISTENCE_CHECK,
	REQUEST_NOTFOUND,
	REQUEST_OUTDATED,
	INITIAL_REQUEST,
	READY_FOR_REPLY
}
