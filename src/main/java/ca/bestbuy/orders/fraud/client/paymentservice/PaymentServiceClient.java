package ca.bestbuy.orders.fraud.client.paymentservice;


import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;

public interface PaymentServiceClient {


    PaymentDetails.PayPal.PayPalAdditionalInfo getPayPalAdditionalInfo(String paymentServiceReferenceId) throws PaymentServiceException;


}
