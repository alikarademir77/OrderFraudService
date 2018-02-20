package ca.bestbuy.orders.fraud.model.internal;

import java.time.ZonedDateTime;

public class Chargeback {

    /**
     * Chargeback amount
     */
    private float amount;

    /**
     * Chargeback reason code
     */
    private String reasonCode;

    /**
     * Chargeback receive date
     */
    private ZonedDateTime receiveDate;


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public ZonedDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(ZonedDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }
}
