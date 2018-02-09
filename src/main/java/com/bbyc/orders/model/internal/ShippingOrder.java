package com.bbyc.orders.model.internal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Shipping order representation
 */
public class ShippingOrder {

    /**
     * Purchase order ID (PO #) associated with this shipping order
     */
    private String purchaseOrderID;

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
     */
    private String shippingOrderStatus;

    /**
     * Purchase order status
     */
    private String purchaseOrderStatus;

    /**
     * Shipping charge
     */
    private double shippingCharge;

    /**
     * Shipping charge tax
     */
    private double shippingChargeTax;

    /**
     * Fulfillment partner
     */
    private String fulfillmentPartner;


    /**
     * Shipping details
     */
    private ShippingDetails shippingDetails;


    /**
     * List of shipping order lines
     */
    private List<OrderLine> shippingOrderLines;


    /**
     * List of chargebacks
     */
    private List<Chargeback> chargebacks;


    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

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

    public String getShippingOrderStatus() {
        return shippingOrderStatus;
    }

    public void setShippingOrderStatus(String shippingOrderStatus) {
        this.shippingOrderStatus = shippingOrderStatus;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public double getShippingChargeTax() {
        return shippingChargeTax;
    }

    public void setShippingChargeTax(double shippingChargeTax) {
        this.shippingChargeTax = shippingChargeTax;
    }

    public String getFulfillmentPartner() {
        return fulfillmentPartner;
    }

    public void setFulfillmentPartner(String fulfillmentPartner) {
        this.fulfillmentPartner = fulfillmentPartner;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public List<OrderLine> getShippingOrderLines() {
        return shippingOrderLines;
    }

    public void setShippingOrderLines(List<OrderLine> shippingOrderLines) {
        this.shippingOrderLines = shippingOrderLines;
    }

    public List<Chargeback> getChargebacks() {
        return chargebacks;
    }

    public void setChargebacks(List<Chargeback> chargebacks) {
        this.chargebacks = chargebacks;
    }


    /**
     * Shipping detail representation
     */
    static class ShippingDetails {

        /**
         * Shipping method
         */
        public String shippingMethod;

        /**
         * Carrier code
         */
        public String carrierCode;

        /**
         * Carrier service level
         */
        public String serviceLevel;

        /**
         * Shipping deadline
         */
        public LocalDateTime deadline;

        /**
         * Shipping address
         */
        public ShippingAddress shippingAddress;

    }


    /**
     * Chargeback representation
     */
    static class Chargeback {

        /**
         * Chargeback amount
         */
        public double amount;

        /**
         * Chargeback reason code
         */
        public String reasonCode;

        /**
         * Chargeback receive date
         */
        public LocalDateTime receiveDate;

    }


    /**
     * Shipping order line representation
     */
    static class OrderLine {

        /**
         * Shipping order line number
         */
        public String lineNumber;

        /**
         * Description
         */
        public String description;


        /**
         * Shipping order line status
         */
        public String status;


        /**
         * Item price
         */
        public double price;

        /**
         * Item quantity
         */
        public int quantity;

        // ? - no use
        public double staffDiscount;

        /**
         * Post capture discount
         */
        public double postCaptureDiscount;


        // all taxes in detail level
        public double salesTax;


        // ? - no use
        public double itemTax;


        /**
         * Tax on the product
         */
        public double productSalesTax;

        /**
         * Shipping charge tax
         */
        public double shippingChargeTax;

        /**
         * Environment handling fee (EHF) tax
         */
        public double environmentHandlingFeeTax;

    }


}
