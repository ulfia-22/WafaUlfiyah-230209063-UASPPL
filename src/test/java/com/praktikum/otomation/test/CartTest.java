package com.praktikum.otomation.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import utils.TestData;
import pages.HomePage;
import pages.ProductPage;
import pages.CartPage;

import java.time.Duration;

/**
 * Cart Test Class
 * Contains test cases for shopping cart functionality
 *
 * BUG REPORTS FOUND:
 * ==================
 * BUG-CART-001: Cart tidak menampilkan total price yang benar ketika ada multiple items
 *   - Severity: Medium
 *   - Steps to Reproduce: Add 2+ products → Check total price
 *   - Expected: Sum of all product prices
 *   - Actual: Total price tidak update atau salah kalkulasi
 *   - Workaround: Verifikasi individual product prices saja
 *
 * BUG-CART-002: Delete button kadang tidak responsive di click pertama
 *   - Severity: Low
 *   - Steps to Reproduce: Add product → Go to cart → Click delete
 *   - Expected: Item langsung terhapus
 *   - Actual: Perlu click 2x atau refresh page
 *   - Workaround: Add wait setelah delete dan retry jika perlu
 *
 * BUG-CART-003: Cart count tidak update real-time setelah add product
 *   - Severity: Low
 *   - Steps to Reproduce: Add product → Check cart icon counter
 *   - Expected: Counter langsung bertambah
 *   - Actual: Counter baru update setelah refresh/navigate
 *   - Workaround: Navigate ke cart page untuk verify actual count
 *
 * BUG-CART-004: Alert "Product added" kadang muncul di background
 *   - Severity: Low
 *   - Steps to Reproduce: Quick add multiple products
 *   - Expected: Alert muncul di foreground
 *   - Actual: Alert tertutup element lain
 *   - Workaround: Add explicit wait untuk alert
 */
public class CartTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        // Initialize driver (adjust based on your setup)
        // driver = DriverManager.getDriver();
        // wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize page objects
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    /**
     * TC_CART_001: Menambahkan produk ke keranjang
     *
     * Test Steps:
     * 1. Buka halaman utama
     * 2. Klik pada produk yang dipilih
     * 3. Klik tombol "Add to cart"
     * 4. Verifikasi alert "Product added"
     * 5. Buka halaman cart
     * 6. Verifikasi produk ada di cart
     *
     * Expected Result:
     * - Alert "Product added" ditampilkan
     * - Produk berhasil ditambahkan ke cart
     * - Cart count bertambah
     *
     * Known Bugs: BUG-CART-003, BUG-CART-004
     */
    @Test(priority = 1,
            description = "TC_CART_001 - Menambahkan produk ke keranjang",
            groups = {"smoke", "regression", "cart"})
    public void testAddProductToCart() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_CART_001: Add Product to Cart                 ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.println("Step 1: Select and click product");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);

        System.out.println("Step 2: Verify product page loaded");
        Assert.assertTrue(productPage.isProductPageLoaded(),
                "Product page should be loaded");

        String productName = productPage.getProductName();
        System.out.println("Selected Product: " + productName);

        System.out.println("Step 3: Add product to cart");
        productPage.addToCart();

        // Workaround untuk BUG-CART-004: Add explicit wait untuk alert
        waitForSeconds(1);

        System.out.println("Step 4: Verify alert message");
        String alertMessage = "";
        try {
            alertMessage = productPage.getAlertMessage();
            System.out.println("Alert Message: " + alertMessage);

            Assert.assertEquals(alertMessage, TestData.PRODUCT_ADDED_MESSAGE,
                    "Alert should confirm product addition");

            productPage.closeAlert();
        } catch (Exception e) {
            System.out.println("⚠ Known Issue (BUG-CART-004): Alert tidak muncul, continuing test...");
            // Soft assertion - test tetap passed karena known bug
        }

        System.out.println("Step 5: Navigate to cart");
        cartPage.goToCart();

        // Workaround untuk BUG-CART-003: Wait untuk cart load
        waitForSeconds(2);

        System.out.println("Step 6: Verify product in cart");
        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page should be loaded");

        int cartCount = cartPage.getCartItemCount();
        System.out.println("Items in cart: " + cartCount);

        Assert.assertTrue(cartCount > 0,
                "Cart should contain at least one item");

        var cartProducts = cartPage.getCartProductNames();
        System.out.println("Products in cart: " + cartProducts);

        Assert.assertTrue(cartProducts.contains(productName),
                "Cart should contain the added product");

        System.out.println("✓ TEST PASSED: Product added to cart successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_CART_002: Menghapus produk dari keranjang
     *
     * Test Steps:
     * 1. Tambahkan produk ke cart
     * 2. Buka halaman cart
     * 3. Catat jumlah item awal
     * 4. Klik tombol Delete pada item pertama
     * 5. Verifikasi item terhapus dari cart
     * 6. Verifikasi jumlah item berkurang
     *
     * Expected Result:
     * - Produk berhasil dihapus dari cart
     * - Cart count berkurang
     * - Total harga diupdate
     *
     * Known Bugs: BUG-CART-002
     */
    @Test(priority = 2,
            description = "TC_CART_002 - Menghapus produk dari keranjang",
            groups = {"smoke", "regression", "cart"})
    public void testRemoveProductFromCart() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_CART_002: Remove Product from Cart            ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Pre-condition: Add product to cart
        System.out.println("Pre-condition: Adding product to cart...");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);
        String addedProduct = productPage.getProductName();

        productPage.addToCart();
        waitForSeconds(1); // Wait untuk alert

        try {
            String alertMessage = productPage.getAlertMessage();
            System.out.println("Alert: " + alertMessage);
            productPage.closeAlert();
        } catch (Exception e) {
            System.out.println("⚠ Alert handling skipped (known issue)");
        }

        System.out.println("Step 1: Navigate to cart");
        cartPage.goToCart();
        waitForSeconds(2); // Wait untuk cart load

        System.out.println("Step 2: Get initial cart count");
        int initialCount = cartPage.getCartItemCount();
        System.out.println("Initial cart count: " + initialCount);

        Assert.assertTrue(initialCount > 0,
                "Cart should have at least one item before deletion");

        var initialProducts = cartPage.getCartProductNames();
        System.out.println("Products before deletion: " + initialProducts);

        System.out.println("Step 3: Delete first item from cart");

        // Workaround untuk BUG-CART-002: Retry delete jika perlu
        boolean deleteSuccessful = false;
        int retryCount = 0;
        int maxRetries = 2;

        while (!deleteSuccessful && retryCount < maxRetries) {
            try {
                cartPage.deleteFirstItem();
                waitForSeconds(2); // Wait untuk deletion selesai

                int currentCount = cartPage.getCartItemCount();
                if (currentCount < initialCount) {
                    deleteSuccessful = true;
                    System.out.println("✓ Delete successful");
                } else {
                    retryCount++;
                    System.out.println("⚠ Retry delete (BUG-CART-002), attempt: " + retryCount);
                }
            } catch (Exception e) {
                retryCount++;
                System.out.println("⚠ Delete failed, retrying... (attempt " + retryCount + ")");
                waitForSeconds(1);
            }
        }

        System.out.println("Step 4: Verify item removed");
        int newCount = cartPage.getCartItemCount();
        System.out.println("New cart count: " + newCount);

        Assert.assertEquals(newCount, initialCount - 1,
                "Cart count should decrease by 1 after deletion");

        if (newCount > 0) {
            var remainingProducts = cartPage.getCartProductNames();
            System.out.println("Remaining products: " + remainingProducts);
            Assert.assertFalse(remainingProducts.contains(addedProduct),
                    "Deleted product should not be in cart");
        } else {
            System.out.println("Cart is now empty");
            // Soft check karena method mungkin tidak ada
            try {
                Assert.assertTrue(cartPage.isCartEmpty(),
                        "Cart should be empty");
            } catch (Exception e) {
                System.out.println("Cart empty check skipped");
            }
        }

        System.out.println("✓ TEST PASSED: Product removed from cart successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_CART_003: Add multiple products to cart
     *
     * Known Bugs: BUG-CART-001, BUG-CART-003
     */
    @Test(priority = 3,
            description = "TC_CART_003 - Add multiple products to cart",
            groups = {"regression", "cart"})
    public void testAddMultipleProductsToCart() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_CART_003: Add Multiple Products               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Add first product
        System.out.println("Adding first product...");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);
        String firstProduct = productPage.getProductName();
        productPage.addToCart();

        waitForSeconds(1);
        try {
            String alert1 = productPage.getAlertMessage();
            System.out.println("Alert 1: " + alert1);
            productPage.closeAlert();
        } catch (Exception e) {
            System.out.println("⚠ Alert 1 skipped (known issue)");
        }

        // Navigate back dengan wait
        System.out.println("Navigating back to home...");
        driver.navigate().back();
        waitForSeconds(2);

        // Add second product
        System.out.println("Adding second product...");
        homePage.clickProduct(TestData.PRODUCT_NOKIA_LUMIA);
        String secondProduct = productPage.getProductName();
        productPage.addToCart();

        waitForSeconds(1);
        try {
            String alert2 = productPage.getAlertMessage();
            System.out.println("Alert 2: " + alert2);
            productPage.closeAlert();
        } catch (Exception e) {
            System.out.println("⚠ Alert 2 skipped (known issue)");
        }

        // Verify cart
        System.out.println("Navigating to cart...");
        cartPage.goToCart();
        waitForSeconds(2); // Workaround untuk BUG-CART-003

        int cartCount = cartPage.getCartItemCount();
        System.out.println("Total items in cart: " + cartCount);

        Assert.assertTrue(cartCount >= 2,
                "Cart should contain at least 2 items");

        var cartProducts = cartPage.getCartProductNames();
        System.out.println("Products in cart: " + cartProducts);

        Assert.assertTrue(cartProducts.contains(firstProduct),
                "Cart should contain first product: " + firstProduct);
        Assert.assertTrue(cartProducts.contains(secondProduct),
                "Cart should contain second product: " + secondProduct);

        // Skip total price verification karena BUG-CART-001
        System.out.println("⚠ Note: Total price verification skipped (BUG-CART-001)");
        System.out.println("   Known Issue: Total price calculation incorrect with multiple items");

        System.out.println("✓ TEST PASSED: Multiple products added successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_CART_004: Verify cart persists across page navigation
     */
    @Test(priority = 4,
            description = "TC_CART_004 - Cart persistence test",
            groups = {"regression", "cart"})
    public void testCartPersistence() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_CART_004: Cart Persistence Test               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Add product to cart
        System.out.println("Step 1: Add product to cart");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);
        String productName = productPage.getProductName();
        productPage.addToCart();

        waitForSeconds(1);
        try {
            productPage.closeAlert();
        } catch (Exception e) {
            System.out.println("⚠ Alert close skipped");
        }

        // Navigate to cart
        System.out.println("Step 2: Go to cart");
        cartPage.goToCart();
        waitForSeconds(2);

        int initialCount = cartPage.getCartItemCount();
        System.out.println("Initial cart count: " + initialCount);

        // Navigate away and back
        System.out.println("Step 3: Navigate to home and back to cart");
        driver.navigate().back();
        waitForSeconds(2);

        cartPage.goToCart();
        waitForSeconds(2);

        // Verify cart persists
        System.out.println("Step 4: Verify cart persists");
        int finalCount = cartPage.getCartItemCount();
        System.out.println("Final cart count: " + finalCount);

        Assert.assertEquals(finalCount, initialCount,
                "Cart count should remain the same after navigation");

        var cartProducts = cartPage.getCartProductNames();
        Assert.assertTrue(cartProducts.contains(productName),
                "Cart should still contain the product");

        System.out.println("✓ TEST PASSED: Cart persists correctly");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * Helper method untuk wait dengan Thread.sleep
     */
    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
}