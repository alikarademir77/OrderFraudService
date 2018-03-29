package ca.bestbuy.orders.fraud.client.paymentservice;


public class NoActivePaypalException extends RuntimeException{


    public NoActivePaypalException() {
    }

    public NoActivePaypalException(String message) {
        super(message);
    }

    public NoActivePaypalException(String message, Throwable cause) {
        super(message, cause);
    }


}
