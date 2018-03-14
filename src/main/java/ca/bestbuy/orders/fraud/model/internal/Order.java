package ca.bestbuy.orders.fraud.model.internal;

import java.util.List;

import org.joda.time.DateTime;

public class Order {

    /**
     * Web order reference ID
     */
    private String webOrderRefID;


    /**
     * Date-time order was created on the website
     */
    private DateTime webOrderCreationDate;


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


    /**
     * Enterprise Customer Id
     */
    private String enterpriseCustomerId;

    /**
     *  Sales Channel
     */
    private String salesChannel;

    /**
     * Order Message
     */

    private String OrderMessage;


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

    public DateTime getWebOrderCreationDate() {
        return webOrderCreationDate;
    }

    public void setWebOrderCreationDate(DateTime webOrderCreationDate) {
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

    public String getEnterpriseCustomerId() {
        return enterpriseCustomerId;
    }

    public void setEnterpriseCustomerId(String enterpriseCustomerId) {
        this.enterpriseCustomerId = enterpriseCustomerId;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getOrderMessage() {
        return OrderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        OrderMessage = orderMessage;
    }
}
