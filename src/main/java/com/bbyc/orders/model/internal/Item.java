package com.bbyc.orders.model.internal;

import java.util.List;

public class Item {

    /**
     * Name of item
     */
    private String name;

    /**
     * Category of item in format: department_class_subclass
     */
    private String category;

    /**
     * Item price
     */
    private float itemPrice;

    /**
     * Tax on item
     */
    private float itemTax;


    /**
     * Item discounts
     */
    private List<Discount> itemDiscounts;


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

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getItemTax() {
        return itemTax;
    }

    public void setItemTax(float itemTax) {
        this.itemTax = itemTax;
    }

    public List<Discount> getItemDiscounts() {
        return itemDiscounts;
    }

    public void setItemDiscounts(List<Discount> itemDiscounts) {
        this.itemDiscounts = itemDiscounts;
    }


    public static class Discount {

        public Boolean isStaffPurchase;

        public float unitValue;

        public int quantity;

    }

}
