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
     * Possible statuses:
     * Open: If sorder is not shipped or not cancled
     * Canceled: if Sorder is cancelled
     * Completed: If Sorder is shipped.
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
    public static class ShippingDetails {

        /**
         * Shipping method
         * The format will be {Carrier Code}-{Service Level}
         * e.g. CPCL-GROUND
         */
        public String shippingMethod;


        /**
         * Shipping deadline
         */
        public LocalDateTime deadline;

        /**
         * Shipping address
         */
        public Address shippingAddress;

    }


    /**
     * Chargeback representation
     */
    public static class Chargeback {

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
    public static class OrderLine {

        /**
         * Shipping order line number
         */
        public String lineNumber;

        /**
         * Description
         */
        public String description;


        /**
         * Item Category
         * Format is Department_Class_Subclass
         */
        public String category;


        /**
         * Shipping order line status
         * Possible statuses:
         * Backorder: If line is backorder
         * Open: If line is not yet shipped or backorder or canceled
         * Cancel: If like is cancelled
         * Completed: If line is shipped.
         */
        public String status;


        /**
         * Item price
         * This will be the unit price of the item minus any precapture discounts on the item price
         */

        //todo: (Unit Price – Discount(Except POST Capture Discount))
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

        /**
         * Total of all taxes, which include the product sales tax, ehf tax and shipping charge tax
         */

        //todo: calculate using (Product TAX+EHF Tax+Shipping Charge Tax)
        public double totalTax;

    }


}
