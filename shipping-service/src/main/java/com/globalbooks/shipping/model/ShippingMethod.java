package com.globalbooks.shipping.model;

public enum ShippingMethod {
    STANDARD("Standard Shipping"),
    EXPRESS("Express Shipping"),
    OVERNIGHT("Overnight Shipping"),
    SAME_DAY("Same Day Delivery"),
    ECONOMY("Economy Shipping"),
    INTERNATIONAL("International Shipping"),
    PICKUP("Store Pickup");

    private final String displayName;

    ShippingMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
