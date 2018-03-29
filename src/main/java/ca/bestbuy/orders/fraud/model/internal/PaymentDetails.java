package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;
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
    private List<PayPal> payPals;


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

    public List<PayPal> getPayPals() {
        return payPals;
    }

    public void setPayPals(List<PayPal> payPals) {
        this.payPals = payPals;
    }


    /**
     * Credit card representation
     */
    public static class CreditCard {

        public Address billingAddress;

        public String creditCardType;

        public String creditCardNumber;

        public String creditCardExpiryDate;

        public String creditCardAvsResponse;

        public String creditCardCvvResponse;

        public String creditCard3dSecureValue;

        public BigDecimal totalAuthorizedAmount;

        /**
         * ACTIVE or DEACTIVE
         */
        public String status;

    }


    /**
     * Gift card representation
     */
    public static class GiftCard {

        public String giftCardNumber;

        public BigDecimal totalAuthorizedAmount;

        /**
         * ACTIVE or DEACTIVE
         */
        public String status;

    }


    /**
     * PayPal representation
     */
    public static class PayPal {

        public PayPalAdditionalInfo payPalAdditionalInfo;

        public BigDecimal totalAuthorizedAmount;

        public String paymentServiceInternalRefId;

        /**
         * ACTIVE or DEACTIVE
         */
        public String status;



        public static class PayPalAdditionalInfo{

            public String payPalOrderId;

            public String email;

            public String verifiedStatus;
        }





    }



}
