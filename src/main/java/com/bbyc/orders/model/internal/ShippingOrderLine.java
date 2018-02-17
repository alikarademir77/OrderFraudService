package com.bbyc.orders.model.internal;

public class ShippingOrderLine {

    /**
     * Shipping order line ID
     */
    private String shippingOrderLineID;

    /**
     * Shipping order line status
     */
    private String status;


    /**
     * Quantity ordered
     */
    private Integer quantity;


    /**
     * Shipping charge for shipping order line
     */
    private double shippingCharge;

    /**
     * Tax on shipping charge
     */
    private double shippingTax;

    /**
     * Discount on shipping charge
     */
    private double shippingDiscount;

    /**
     * Environment handling fee on shipping order line
     */
    private double ehf;

    /**
     * Tax on environment handling fee
     */
    private double ehfTax;


    public String getShippingOrderLineID() {
        return shippingOrderLineID;
    }

    public void setShippingOrderLineID(String shippingOrderLineID) {
        this.shippingOrderLineID = shippingOrderLineID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public double getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(double shippingTax) {
        this.shippingTax = shippingTax;
    }

    public double getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(double shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public double getEhf() {
        return ehf;
    }

    public void setEhf(double ehf) {
        this.ehf = ehf;
    }

    public double getEhfTax() {
        return ehfTax;
    }

    public void setEhfTax(double ehfTax) {
        this.ehfTax = ehfTax;
    }
}
