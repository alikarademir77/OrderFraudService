package com.bbyc.orders.model.internal;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    /**
     * Customer order number
     */
    private String webOrderNumber;


    /**
     * OMS FS order ID
     */
    private String fsOrderNumber;


    /**
     * CSR sales rep ID
     */
    private String csrSalesRepID;

    /**
     * IP address of customer submitting order
     */
    private String ipAddress;

    /**
     * Time order was created on the website
     */
    private LocalDateTime orderCreationTime;

    /**
     * Total amount billed to customer
     */
    private double totalAmount;

    /**
     * Reward zone ID
     */
    private String rewardZoneID;

    /**
     * Shipping orders
     */
    private List<ShippingOrder> shippingOrders;

    /**
     * List of historical shipping addresses
     */
    private List<ShippingAddress> shippingAddresses;

    /**
     * Payment details
     */
    private PaymentDetails paymentDetails;


    public String getWebOrderNumber() {
        return webOrderNumber;
    }

    public void setWebOrderNumber(String webOrderNumber) {
        this.webOrderNumber = webOrderNumber;
    }

    public String getFsOrderNumber() {
        return fsOrderNumber;
    }

    public void setFsOrderNumber(String fsOrderNumber) {
        this.fsOrderNumber = fsOrderNumber;
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

    public LocalDateTime getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(LocalDateTime orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRewardZoneID() {
        return rewardZoneID;
    }

    public void setRewardZoneID(String rewardZoneID) {
        this.rewardZoneID = rewardZoneID;
    }

    public List<ShippingOrder> getShippingOrders() {
        return shippingOrders;
    }

    public void setShippingOrders(List<ShippingOrder> shippingOrders) {
        this.shippingOrders = shippingOrders;
    }

    public List<ShippingAddress> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
