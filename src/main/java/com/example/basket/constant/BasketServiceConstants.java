package com.example.basket.constant;

public class BasketServiceConstants {

    private BasketServiceConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REQUEST_ACCOUNT_BASE_URL = "/basket/";
    public static final String STOCK_CHANGE = "STOCK_CHANGE";
    public static final String STOCK_DEPLETED = "DEPLETED";
    public static final String PRICE_CHANGE = "PRICE_CHANGE";

}
