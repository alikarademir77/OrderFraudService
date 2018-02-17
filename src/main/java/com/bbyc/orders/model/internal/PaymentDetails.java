package com.bbyc.orders.model.internal;

import java.util.List;

/**
 * Class for holding all payment details
 */
public class PaymentDetails {

    /**
     * List of credit cards
     */
    private List<CreditCard> creditCards;

    /**
     * List of gift cards
     */
    private List<GiftCard> giftCards;

    /**
     * PayPal details
     */
    private PayPal payPal;


    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<GiftCard> getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(List<GiftCard> giftCards) {
        this.giftCards = giftCards;
    }

    public PayPal getPayPal() {
        return payPal;
    }

    public void setPayPal(PayPal payPal) {
        this.payPal = payPal;
    }


    /**
     * Credit card representation
     */
    public static class CreditCard {

        public Address billingAddress;

        public String creditCardType;

        public String creditCardNumber;

        public double totalAuthorizedAmount;

        public String creditCardExpiryDate;

        public String avsResponse;

        public String cvvResponse;

        public String secureValue3D;

    }


    /**
     * Gift card representation
     */
    public static class GiftCard {

        public String giftCardNumber;

    }


    /**
     * PayPal representation
     */
    public static class PayPal {

        public String requestID;

        public String email;

        public String verifiedStatus;

        public double totalAuthorizedAmount;

    }



}
