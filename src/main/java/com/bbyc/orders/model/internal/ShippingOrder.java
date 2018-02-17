package com.bbyc.orders.model.internal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Shipping order representation
 */
public class ShippingOrder {

    /**
     * Shipping order ID
     */
    private String shippingOrderID;

    /**
     * Global contract ID
     */
    private String globalContractID;

    /**
     * Shipping order status
     * Possible statuses:
     * Open: If sorder is not shipped or not cancelled
     * Canceled: if Sorder is cancelled
     * Completed: If Sorder is shipped.
     */
    private String status;


    /**
     * Shipping charge
     */
    private double shippingCharge;

    /**
     * Fulfillment partner
     */
    private String fulfillmentPartner;


    /**
     * Shipping method
     * The format will be {Carrier Code}-{Service Level}
     * e.g. CPCL-GROUND
     */
    private String shippingMethod;


    /**
     * Shipping deliveryDate
     */
    private LocalDateTime deliveryDate;


    /**
     * Shipping address
     */
    private Address shippingAddress;


    /**
     * List of shipping order lines
     */
    private List<ShippingOrderLine> shippingOrderLines;


    /**
     * List of chargebacks
     */
    private List<Chargeback> chargebacks;



    public String getShippingOrderID() {
        return shippingOrderID;
    }

    public void setShippingOrderID(String shippingOrderID) {
        this.shippingOrderID = shippingOrderID;
    }

    public String getGlobalContractID() {
        return globalContractID;
    }

    public void setGlobalContractID(String globalContractID) {
        this.globalContractID = globalContractID;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getFulfillmentPartner() {
        return fulfillmentPartner;
    }

    public void setFulfillmentPartner(String fulfillmentPartner) {
        this.fulfillmentPartner = fulfillmentPartner;
    }


    public List<ShippingOrderLine> getShippingOrderLines() {
        return shippingOrderLines;
    }

    public void setShippingOrderLines(List<ShippingOrderLine> shippingOrderLines) {
        this.shippingOrderLines = shippingOrderLines;
    }

    public List<Chargeback> getChargebacks() {
        return chargebacks;
    }

    public void setChargebacks(List<Chargeback> chargebacks) {
        this.chargebacks = chargebacks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }



}
