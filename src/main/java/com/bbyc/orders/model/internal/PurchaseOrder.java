package com.bbyc.orders.model.internal;

public class PurchaseOrder {

    /**
     * Purchase order ID
     */
    private String purchaseOrderID;

    /**
     * Purchase order status
     */
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingOrderRefID() {
        return shippingOrderRefID;
    }

    public void setShippingOrderRefID(String shippingOrderRefID) {
        this.shippingOrderRefID = shippingOrderRefID;
    }
}
