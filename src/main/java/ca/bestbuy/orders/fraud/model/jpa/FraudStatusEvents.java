/**
 * 
 */
package ca.bestbuy.orders.fraud.model.jpa;

/**
 * @author akaradem
 *
 */
public enum FraudStatusEvents {
	INITIAL_REQUEST_RECEIVED,
	FINAL_DECISION_RECEIVED,
	PENDING_REVIEW_RECEIVED,
	CANCELLATION_RECEIVED	
}
