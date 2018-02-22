package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
    private ZonedDateTime receiveDate;


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

    public ZonedDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(ZonedDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }
}
