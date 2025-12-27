package com.praktikum.otomation.test.base;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestData;

import java.util.List;

/**
 * Product Test Class
 * Contains test cases for product display and navigation
 */
public class ProductTest extends BaseTest {

    /**
     * TC_PROD_001: Menampilkan semua produk di halaman utama
     *
     * Test Steps:
     * 1. Buka halaman utama Demoblaze
     * 2. Verifikasi produk ditampilkan di halaman
     * 3. Hitung jumlah produk yang tampil
     * 4. Verifikasi minimal 9 produk ditampilkan
     *
     * Expected Result:
     * - Halaman utama berhasil dimuat
     * - Produk-produk ditampilkan dalam bentuk card
     * - Minimal 9 produk terlihat di halaman pertama
     */
    @Test(priority = 1,
            description = "TC_PROD_001 - Menampilkan semua produk di halaman utama",
            groups = {"smoke", "regression", "product"})
    public void testDisplayAllProductsOnHomePage() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PROD_001: Display All Products on Home        ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.println("Step 1: Verify home page is loaded");
        Assert.assertTrue(homePage.isHomePageLoaded(),
                "Home page should be loaded successfully");

        System.out.println("Step 2: Get product count");
        int productCount = homePage.getProductCount();
        System.out.println("Products displayed: " + productCount);

        System.out.println("Step 3: Get product names");
        List<String> productNames = homePage.getProductNames();
        System.out.println("Product list:");
        productNames.forEach(name -> System.out.println("  - " + name));

        // Assertions
        Assert.assertTrue(productCount > 0,
                "At least one product should be displayed");
        Assert.assertTrue(productCount >= 9,
                "At least 9 products should be visible on first page");
        Assert.assertFalse(productNames.isEmpty(),
                "Product names list should not be empty");

        System.out.println("✓ TEST PASSED: Products displayed successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_PROD_002: Navigasi antar kategori produk
     *
     * Test Steps:
     * 1. Buka halaman utama
     * 2. Klik kategori "Phones"
     * 3. Verifikasi produk phone ditampilkan
     * 4. Klik kategori "Laptops"
     * 5. Verifikasi produk laptop ditampilkan
     * 6. Klik kategori "Monitors"
     * 7. Verifikasi produk monitor ditampilkan
     *
     * Expected Result:
     * - Setiap kategori dapat diakses
     * - Produk yang ditampilkan sesuai dengan kategori
     * - Filter kategori berfungsi dengan baik
     */
    @Test(priority = 2,
            description = "TC_PROD_002 - Navigasi antar kategori produk",
            groups = {"smoke", "regression", "product"})
    public void testNavigateBetweenProductCategories() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PROD_002: Navigate Between Categories         ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Test Phones Category
        System.out.println("\n--- Testing PHONES Category ---");
        homePage.selectCategory(TestData.CATEGORY_PHONES);

        System.out.println("Verifying phone products...");
        Assert.assertTrue(homePage.isProductDisplayed(TestData.PRODUCT_SAMSUNG_S6),
                "Samsung Galaxy S6 should be in Phones category");
        Assert.assertTrue(homePage.isProductDisplayed(TestData.PRODUCT_NOKIA_LUMIA),
                "Nokia Lumia 1520 should be in Phones category");

        int phonesCount = homePage.getProductCount();
        System.out.println("Phones displayed: " + phonesCount);
        Assert.assertTrue(phonesCount > 0, "Phones category should have products");

        // Test Laptops Category
        System.out.println("\n--- Testing LAPTOPS Category ---");
        homePage.selectCategory(TestData.CATEGORY_LAPTOPS);

        System.out.println("Verifying laptop products...");
        Assert.assertTrue(homePage.isProductDisplayed(TestData.PRODUCT_SONY_VAIO),
                "Sony Vaio i5 should be in Laptops category");
        Assert.assertTrue(homePage.isProductDisplayed(TestData.PRODUCT_MACBOOK_AIR),
                "MacBook Air should be in Laptops category");

        int laptopsCount = homePage.getProductCount();
        System.out.println("Laptops displayed: " + laptopsCount);
        Assert.assertTrue(laptopsCount > 0, "Laptops category should have products");

        // Test Monitors Category
        System.out.println("\n--- Testing MONITORS Category ---");
        homePage.selectCategory(TestData.CATEGORY_MONITORS);

        System.out.println("Verifying monitor products...");
        Assert.assertTrue(homePage.isProductDisplayed(TestData.PRODUCT_APPLE_MONITOR),
                "Apple Monitor 24 should be in Monitors category");

        int monitorsCount = homePage.getProductCount();
        System.out.println("Monitors displayed: " + monitorsCount);
        Assert.assertTrue(monitorsCount > 0, "Monitors category should have products");

        System.out.println("\n✓ TEST PASSED: All categories navigated successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_PROD_003: View product details (Bonus Test)
     */
    @Test(priority = 3,
            description = "TC_PROD_003 - View product details",
            groups = {"regression", "product"})
    public void testViewProductDetails() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_PROD_003: View Product Details                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.println("Step 1: Click on product");
        homePage.clickProduct(TestData.PRODUCT_SAMSUNG_S6);

        System.out.println("Step 2: Verify product page loaded");
        Assert.assertTrue(productPage.isProductPageLoaded(),
                "Product page should be loaded");

        System.out.println("Step 3: Get product details");
        String productName = productPage.getProductName();
        String productPrice = productPage.getProductPrice();

        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);

        Assert.assertEquals(productName, TestData.PRODUCT_SAMSUNG_S6,
                "Product name should match");
        Assert.assertFalse(productPrice.isEmpty(),
                "Product price should be displayed");

        System.out.println("✓ TEST PASSED: Product details displayed correctly");
        System.out.println("════════════════════════════════════════════════════\n");
    }
}