package com.bbyc.orders.model.internal;

/**
 * Shipping address representation
 */
public class ShippingAddress extends Address {

    /**
     * Indicator if shipping address is active or not
     */
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
