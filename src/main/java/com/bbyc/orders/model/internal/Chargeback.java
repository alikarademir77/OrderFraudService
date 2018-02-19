package com.bbyc.orders.model.internal;

import java.time.LocalDateTime;

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
    private LocalDateTime receiveDate;


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

    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }
}
