package ca.bestbuy.orders.fraud.model.internal;

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
    private float itemUnitPrice;

    /**
     * Item quantity ordered
     */
    private int itemQuantity;

    /**
     * Tax on item
     */
    private float itemTax;


    /**
     * Total Item discounts including Staff Purchases
     */
    private float itemTotalDiscount;

    /**
     * Staff Purchase Discount
     *
     */
    private float staffDiscount;



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

    public float getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(float itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public float getItemTax() {
        return itemTax;
    }

    public void setItemTax(float itemTax) {
        this.itemTax = itemTax;
    }

    public float getItemTotalDiscount() {
        return itemTotalDiscount;
    }

    public void setItemTotalDiscount(float itemTotalDiscount) {
        this.itemTotalDiscount = itemTotalDiscount;
    }

    public float getStaffDiscount() {
        return staffDiscount;
    }

    public void setStaffDiscount(float staffDiscount) {
        this.staffDiscount = staffDiscount;
    }

    public static class Discount {

        public Boolean isStaffPurchase;

        public float unitValue;

        public int quantity;

    }

}
