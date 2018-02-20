package com.bbyc.orders.model.internal;

public class PurchaseOrder {

    /**
     * Purchase order ID
     */
    private String purchaseOrderID;

    /**
     * Purchase order status
     */
    private String purchaseOrderStatus;

    /**
     * Associated shipping order ID
     */
    private String shippingOrderRefID;


    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public String getShippingOrderRefID() {
        return shippingOrderRefID;
    }

    public void setShippingOrderRefID(String shippingOrderRefID) {
        this.shippingOrderRefID = shippingOrderRefID;
    }
}
