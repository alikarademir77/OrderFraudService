package com.bbyc.orders.model.internal;

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
    private float shippingCharge;

    /**
     * Tax on shipping charge
     */
    private float shippingTax;

    /**
     * Discount on shipping charge
     */
    private float shippingDiscount;

    /**
     * Environment handling fee on shipping order line
     */
    private float ehf;

    /**
     * Tax on environment handling fee
     */
    private float ehfTax;


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

    public float getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(float shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public float getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(float shippingTax) {
        this.shippingTax = shippingTax;
    }

    public float getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(float shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public float getEhf() {
        return ehf;
    }

    public void setEhf(float ehf) {
        this.ehf = ehf;
    }

    public float getEhfTax() {
        return ehfTax;
    }

    public void setEhfTax(float ehfTax) {
        this.ehfTax = ehfTax;
    }
}
