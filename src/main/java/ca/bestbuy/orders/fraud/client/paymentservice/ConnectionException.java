package ca.bestbuy.orders.fraud.client.paymentservice;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
