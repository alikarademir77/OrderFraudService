package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;

public class ShippingOrderLine {

    /**
     * Shipping order line ID
     */
    private String shippingOrderLineID;


    /**
     * FS order line reference ID
     */
    private String fsoLineRefID;


    /**
     * Shipping order line status
     */
    private String shippingOrderLineStatus;


    /**
     * Quantity ordered
     */
    private Integer quantity;


    /**
     * Shipping charge for shipping order line
     */
    private BigDecimal shippingCharge;

    /**
     * Tax on shipping charge
     */
    private BigDecimal shippingTax;

    /**
     * Discount on shipping charge
     */
    private BigDecimal shippingDiscount;

    /**
     * Environment handling fee on shipping order line
     */
    private BigDecimal ehf;

    /**
     * Tax on environment handling fee
     */
    private BigDecimal ehfTax;


    public String getShippingOrderLineID() {
        return shippingOrderLineID;
    }

    public void setShippingOrderLineID(String shippingOrderLineID) {
        this.shippingOrderLineID = shippingOrderLineID;
    }

    public String getFsoLineRefID() {
        return fsoLineRefID;
    }

    public void setFsoLineRefID(String fsoLineRefID) {
        this.fsoLineRefID = fsoLineRefID;
    }

    public String getShippingOrderLineStatus() {
        return shippingOrderLineStatus;
    }

    public void setShippingOrderLineStatus(String shippingOrderLineStatus) {
        this.shippingOrderLineStatus = shippingOrderLineStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(BigDecimal shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public BigDecimal getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(BigDecimal shippingTax) {
        this.shippingTax = shippingTax;
    }

    public BigDecimal getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(BigDecimal shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public BigDecimal getEhf() {
        return ehf;
    }

    public void setEhf(BigDecimal ehf) {
        this.ehf = ehf;
    }

    public BigDecimal getEhfTax() {
        return ehfTax;
    }

    public void setEhfTax(BigDecimal ehfTax) {
        this.ehfTax = ehfTax;
    }
}
