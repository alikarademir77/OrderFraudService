package ca.bestbuy.orders.fraud.client.paymentservice;


public class MissingInformationException extends RuntimeException{


    public MissingInformationException() {
    }

    public MissingInformationException(String message) {
        super(message);
    }

    public MissingInformationException(String message, Throwable cause) {
        super(message, cause);
    }


}
