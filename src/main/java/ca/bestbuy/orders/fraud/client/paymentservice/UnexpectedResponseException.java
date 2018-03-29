package ca.bestbuy.orders.fraud.client.paymentservice;

public class UnexpectedResponseException extends RuntimeException {

    public UnexpectedResponseException(String message) {
        super(message);
    }

    public UnexpectedResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
