package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class Chargeback {

    /**
     * Chargeback amount
     */
    private BigDecimal amount;

    /**
     * Chargeback reason code
     */
    private String reasonCode;

    /**
     * Chargeback receive date
     */
    private DateTime receiveDate;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public DateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(DateTime receiveDate) {
        this.receiveDate = receiveDate;
    }
}
