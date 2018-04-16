package ca.bestbuy.orders.fraud.exception;

/**
 * Created by kundsing on 2018-04-13.
 */

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
