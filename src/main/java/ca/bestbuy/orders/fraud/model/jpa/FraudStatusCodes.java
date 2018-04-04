package ca.bestbuy.orders.fraud.model.jpa;

/**
 * @author akaradem
 *
 */
public enum FraudStatusCodes{
	INITIAL_REQUEST, FINAL_DECISION, PENDING_REVIEW, CANCELLED;
}