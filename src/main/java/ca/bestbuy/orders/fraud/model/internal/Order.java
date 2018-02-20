package ca.bestbuy.orders.fraud.model.internal;

import java.time.ZonedDateTime;
import java.util.List;

public class Order {

    /**
     * Web order reference ID
     */
    private String webOrderRefID;


    /**
     * Date-time order was created on the website
     */
    private ZonedDateTime webOrderCreationDate;


    /**
     * OMS FS order ID
     */
    private String fsOrderID;


    /**
     * CSR sales rep ID
     */
    private String csrSalesRepID;


    /**
     * IP address of customer submitting order
     */
    private String ipAddress;


    /**
     * Reward zone ID
     */
    private String rewardZoneID;


    /**
     * List of items that are part of the order
     */
    private List<Item> items;


    /**
     * List of purchase orders
     */
    private List<PurchaseOrder> purchaseOrders;


    /**
     * List of shipping orders
     */
    private List<ShippingOrder> shippingOrders;


    /**
     * Payment details
     */
    private PaymentDetails paymentDetails;


    public String getWebOrderRefID() {
        return webOrderRefID;
    }

    public void setWebOrderRefID(String webOrderRefID) {
        this.webOrderRefID = webOrderRefID;
    }

    public String getFsOrderID() {
        return fsOrderID;
    }

    public void setFsOrderID(String fsOrderID) {
        this.fsOrderID = fsOrderID;
    }

    public String getCsrSalesRepID() {
        return csrSalesRepID;
    }

    public void setCsrSalesRepID(String csrSalesRepID) {
        this.csrSalesRepID = csrSalesRepID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ZonedDateTime getWebOrderCreationDate() {
        return webOrderCreationDate;
    }

    public void setWebOrderCreationDate(ZonedDateTime webOrderCreationDate) {
        this.webOrderCreationDate = webOrderCreationDate;
    }

    public String getRewardZoneID() {
        return rewardZoneID;
    }

    public void setRewardZoneID(String rewardZoneID) {
        this.rewardZoneID = rewardZoneID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public List<ShippingOrder> getShippingOrders() {
        return shippingOrders;
    }

    public void setShippingOrders(List<ShippingOrder> shippingOrders) {
        this.shippingOrders = shippingOrders;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
