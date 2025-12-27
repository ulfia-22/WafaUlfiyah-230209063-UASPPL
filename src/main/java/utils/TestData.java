package utils;

import java.util.Random;

/**
 * TestData class - contains all test data used in test cases
 */
public class TestData {

    // User credentials
    public static final String TEST_USERNAME = "shop_admin" + System.currentTimeMillis();
    public static final String TEST_PASSWORD = "ulfivelvet12";
    public static final String VALID_USERNAME = "shop_admin";
    public static final String VALID_PASSWORD = "ulfivelvet12";

    // Customer information
    public static final String CUSTOMER_NAME = "Laura Margareth";
    public static final String CUSTOMER_COUNTRY = "USA";
    public static final String CUSTOMER_CITY = "New York";
    public static final String CREDIT_CARD = "1344-6278-9876-5432";
    public static final String CARD_MONTH = "12";
    public static final String CARD_YEAR = "2025";

    // Alternative customer data
    public static final String CUSTOMER_NAME_2 = "Laura Margareth";
    public static final String CUSTOMER_COUNTRY_2 = "USA";
    public static final String CUSTOMER_CITY_2 = "New York";

    // Product names
    public static final String PRODUCT_SAMSUNG_S6 = "Samsung galaxy s6";
    public static final String PRODUCT_NOKIA_LUMIA = "Nokia lumia 1520";
    public static final String PRODUCT_NEXUS_6 = "Nexus 6";
    public static final String PRODUCT_SONY_VAIO = "Sony vaio i5";
    public static final String PRODUCT_MACBOOK_AIR = "MacBook air";
    public static final String PRODUCT_APPLE_MONITOR = "Apple monitor 24";

    // Categories
    public static final String CATEGORY_PHONES = "Phones";
    public static final String CATEGORY_LAPTOPS = "Laptops";
    public static final String CATEGORY_MONITORS = "Monitors";

    // Expected messages
    public static final String SUCCESS_SIGNUP_MESSAGE = "Sign up successful";
    public static final String PRODUCT_ADDED_MESSAGE = "Product added";
    public static final String PURCHASE_SUCCESS_MESSAGE = "Thank you for your purchase!";

    // ============================================
    // URL TEST DATA
    // ============================================

    public static final String BASE_URL = "https://www.demoblaze.com/";
    public static final String HOME_URL = "https://www.demoblaze.com/index.html";
    public static final String CART_URL = "https://www.demoblaze.com/cart.html";

    /**
     * Generate unique username
     */
    public static String generateUsername() {
        return "user_" + System.currentTimeMillis() + "_" + new Random().nextInt(1000);
    }

    /**
     * Generate random credit card number
     */
    public static String generateCreditCard() {
        Random random = new Random();
        StringBuilder card = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            card.append(random.nextInt(10));
        }
        return card.toString();
    }
}
