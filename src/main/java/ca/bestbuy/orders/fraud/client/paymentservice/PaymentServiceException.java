package ca.bestbuy.orders.fraud.client.paymentservice;

public class PaymentServiceException extends Exception {

    private String errorCode;
    private String errorSubCode;
    private String errorDescription;

    public PaymentServiceException(String message) {
        super(message);
    }

    public PaymentServiceException(String message, String errorCode, String errorSubCode, String errorDescription) {
        super(message);
        this.errorCode = errorCode;
        this.errorSubCode = errorSubCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorSubCode() {
        return errorSubCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

}
