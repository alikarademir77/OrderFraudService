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

}
