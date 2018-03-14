package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;

public class Item {

    /**
     * FS order line ID associated with item
     */
    private String fsoLineID;

    /**
     * Name of item
     */
    private String name;

    /**
     * Category of item in format: department_class_subclass
     */
    private String category;

    /**
     * Item unit price
     */
    private BigDecimal itemUnitPrice;

    /**
     * Item quantity ordered
     */
    private int itemQuantity;

    /**
     * Tax on item
     */
    private BigDecimal itemTax;

    /**
     * Total Item discounts including Staff Purchases
     */
    private BigDecimal itemTotalDiscount;

    /**
     * Staff Purchase Discount
     */
    private BigDecimal staffDiscount;

    /**
     * Item Status
     */
    private String itemStatus;

    /**
     * Item Sku
     */
    private String itemSkuNumber;

    /**
     * Item Sku Description
     */
    private String itemSkuDescription;


    /**
     * Post-Capture Discount Total
     */
    private BigDecimal postCaptureDiscount;


    public String getFsoLineID() {
        return fsoLineID;
    }

    public void setFsoLineID(String fsoLineID) {
        this.fsoLineID = fsoLineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(BigDecimal itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getItemTax() {
        return itemTax;
    }

    public void setItemTax(BigDecimal itemTax) {
        this.itemTax = itemTax;
    }

    public BigDecimal getItemTotalDiscount() {
        return itemTotalDiscount;
    }

    public void setItemTotalDiscount(BigDecimal itemTotalDiscount) {
        this.itemTotalDiscount = itemTotalDiscount;
    }

    public BigDecimal getStaffDiscount() {
        return staffDiscount;
    }

    public void setStaffDiscount(BigDecimal staffDiscount) {
        this.staffDiscount = staffDiscount;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemSkuNumber() {
        return itemSkuNumber;
    }

    public void setItemSkuNumber(String itemSkuNumber) {
        this.itemSkuNumber = itemSkuNumber;
    }

    public String getItemSkuDescription() {
        return itemSkuDescription;
    }

    public void setItemSkuDescription(String itemSkuDescription) {
        this.itemSkuDescription = itemSkuDescription;
    }

    public BigDecimal getPostCaptureDiscount() {
        return postCaptureDiscount;
    }

    public void setPostCaptureDiscount(BigDecimal postCaptureDiscount) {
        this.postCaptureDiscount = postCaptureDiscount;
    }
}
