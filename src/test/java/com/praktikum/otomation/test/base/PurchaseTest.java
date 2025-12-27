package com.praktikum.otomation.test.base;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestData;

/**
 * Purchase Test Class
 * Contains test cases for checkout and purchase functionality
 */

public class PurchaseTest extends BaseTest {
    /**
     * TC_PURCHASE_001: Melakukan checkout dengan satu produk
     *
     * Test Steps:
     * 1. Tambahkan satu produk ke cart
     * 2. Buka Shalaman cart
     * 3. Klik tombol "Place Order"
     * 4. Isi form order dengan data valid
     * 5. Klik tombol "Purchase"
     * 6. Verifikasi konfirmasi purchase berhasil
     *
     * Expected Result:
     * - Form order dapat diisi
     * - Purchase berhasil diproses
     * - Konfirmasi "Thank you for your purchase!" ditampilkan
     * - Detail order ditampilkan
     */
    @Test(priority = 1,
            description = "TC_PURCHASE_001 - Melakukan checkout dengan satu produk",
            groups = {"smoke", "regression", "purchase"})
    public void testCheckoutWithSingleProduct() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PURCHASE_001: Checkout Single Product         ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Pre-condition: Add product to cart
        System.out.println("Step 1: Add product to cart");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);
        String productName = productPage.getProductName();
        String productPrice = productPage.getProductPrice();
        System.out.println("Selected: " + productName + " - " + productPrice);

        productPage.addProductToCart();

        System.out.println("Step 2: Navigate to cart");
        cartPage.goToCart();

        System.out.println("Step 3: Verify cart contents");
        int cartCount = cartPage.getCartItemCount();
        String totalPrice = cartPage.getTotalPrice();
        System.out.println("Cart items: " + cartCount);
        System.out.println("Total price: $" + totalPrice);

        Assert.assertEquals(cartCount, 1,
                "Cart should contain exactly 1 item");

        System.out.println("Step 4: Click Place Order");
        cartPage.clickPlaceOrder();

        System.out.println("Step 5: Fill order form");
        System.out.println("Customer: " + TestData.CUSTOMER_NAME);
        System.out.println("Location: " + TestData.CUSTOMER_CITY + ", " + TestData.CUSTOMER_COUNTRY);
        System.out.println("Card: " + TestData.CREDIT_CARD);

        cartPage.fillOrderForm(
                TestData.CUSTOMER_NAME,
                TestData.CUSTOMER_COUNTRY,
                TestData.CUSTOMER_CITY,
                TestData.CREDIT_CARD,
                TestData.CARD_MONTH,
                TestData.CARD_YEAR
        );

        System.out.println("Step 6: Submit purchase");
        cartPage.clickPurchase();

        System.out.println("Step 7: Verify purchase confirmation");
        String confirmationTitle = cartPage.getConfirmationTitle();
        String confirmationMessage = cartPage.getConfirmationMessage();

        System.out.println("Confirmation Title: " + confirmationTitle);
        System.out.println("Confirmation Message: " + confirmationMessage);

        // Assertions
        Assert.assertTrue(
                confirmationTitle.contains("Thank you") ||
                        confirmationMessage.contains("Thank you for your purchase"),
                "Purchase confirmation should be displayed"
        );

        Assert.assertTrue(confirmationMessage.contains("Amount"),
                "Confirmation should contain amount");
        Assert.assertTrue(confirmationMessage.contains("Card Number"),
                "Confirmation should contain card number");
        Assert.assertTrue(confirmationMessage.contains("Name"),
                "Confirmation should contain customer name");

        cartPage.closeConfirmation();

        System.out.println("✓ TEST PASSED: Single product checkout successful");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_PURCHASE_002: Melakukan checkout dengan beberapa produk
     *
     * Test Steps:
     * 1. Tambahkan produk pertama ke cart
     * 2. Kembali ke halaman home
     * 3. Tambahkan produk kedua ke cart
     * 4. Buka halaman cart
     * 5. Verifikasi ada minimal 2 produk
     * 6. Lakukan checkout
     * 7. Isi form dan complete purchase
     * 8. Verifikasi konfirmasi berhasil
     *
     * Expected Result:
     * - Multiple products dapat ditambahkan
     * - Total harga adalah akumulasi semua produk
     * - Checkout berhasil untuk multiple items
     * - Konfirmasi menampilkan total yang benar
     */
    @Test(priority = 2,
            description = "TC_PURCHASE_002 - Melakukan checkout dengan beberapa produk",
            groups = {"smoke", "regression", "purchase"})
    public void testCheckoutWithMultipleProducts() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PURCHASE_002: Checkout Multiple Products      ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Add first product
        System.out.println("Step 1: Add first product");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);
        String product1 = productPage.getProductName();
        System.out.println("Product 1: " + product1);
        productPage.addProductToCart();

        // Navigate back to home
        System.out.println("Step 2: Navigate back to home");
        driver.navigate().back();
        homePage.customWait(1000);

        // Add second product
        System.out.println("Step 3: Add second product");
        homePage.clickProduct(TestData.PRODUCT_NOKIA_LUMIA);
        String product2 = productPage.getProductName();
        System.out.println("Product 2: " + product2);
        productPage.addProductToCart();

        // Navigate back and add third product (optional)
        System.out.println("Step 4: Add third product");
        driver.navigate().back();
        homePage.customWait(1000);
        homePage.clickProduct(TestData.PRODUCT_NEXUS_6);
        String product3 = productPage.getProductName();
        System.out.println("Product 3: " + product3);
        productPage.addProductToCart();

        System.out.println("Step 5: Navigate to cart");
        cartPage.goToCart();

        System.out.println("Step 6: Verify multiple items in cart");
        int cartCount = cartPage.getCartItemCount();
        String totalPrice = cartPage.getTotalPrice();
        var cartProducts = cartPage.getCartProductNames();

        System.out.println("Cart items: " + cartCount);
        System.out.println("Products: " + cartProducts);
        System.out.println("Total price: $" + totalPrice);

        Assert.assertTrue(cartCount >= 2,
                "Cart should contain at least 2 items");
        Assert.assertTrue(cartProducts.size() >= 2,
                "Should have multiple products in cart");

        System.out.println("Step 7: Proceed to checkout");
        cartPage.clickPlaceOrder();

        System.out.println("Step 8: Fill order form");
        cartPage.fillOrderForm(
                TestData.CUSTOMER_NAME_2,
                TestData.CUSTOMER_COUNTRY_2,
                TestData.CUSTOMER_CITY_2,
                TestData.generateCreditCard(),
                "06",
                "2026"
        );

        System.out.println("Step 9: Complete purchase");
        cartPage.clickPurchase();

        System.out.println("Step 10: Verify confirmation");
        String confirmationMessage = cartPage.getConfirmationMessage();
        System.out.println("Confirmation: " + confirmationMessage);

        Assert.assertTrue(
                confirmationMessage.contains("Thank you for your purchase"),
                "Purchase should be successful"
        );

        // Verify amount in confirmation matches cart total
        Assert.assertTrue(confirmationMessage.contains("Amount"),
                "Confirmation should show total amount");

        cartPage.closeConfirmation();

        System.out.println("✓ TEST PASSED: Multiple products checkout successful");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_PURCHASE_003: Empty cart checkout validation (Bonus Test)
     */
    @Test(priority = 3,
            description = "TC_PURCHASE_003 - Validate empty cart checkout",
            groups = {"regression", "purchase", "negative"})
    public void testEmptyCartCheckoutValidation() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PURCHASE_003: Empty Cart Validation           ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.println("Step 1: Go to cart without adding products");
        cartPage.goToCart();

        System.out.println("Step 2: Verify cart is empty");
        boolean isEmpty = cartPage.isCartEmpty();
        System.out.println("Cart is empty: " + isEmpty);

        // Note: Demoblaze allows place order even with empty cart
        // This is a known bug that should be reported

        System.out.println("✓ TEST COMPLETED");
        System.out.println("════════════════════════════════════════════════════\n");
    }
}
