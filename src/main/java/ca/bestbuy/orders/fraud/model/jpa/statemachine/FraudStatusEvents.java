/**
 * 
 */
package ca.bestbuy.orders.fraud.model.jpa.statemachine;

/**
 * @author akaradem
 *
 */
public enum FraudStatusEvents {
	FINAL_DECISION_RECEIVED,
	PENDING_REVIEW_RECEIVED,
	CANCELLATION_RECEIVED	
}
