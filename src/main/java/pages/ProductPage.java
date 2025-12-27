package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ProductPage class - represents individual product detail page
 */
public class ProductPage extends BasePage {

    // Locators
    private final By productTitle = By.cssSelector(".name");
    private final By productPrice = By.cssSelector(".price-container");
    private final By productDescription = By.id("more-information");
    private final By addToCartButton = By.linkText("Add to cart");
    private final By homeLink = By.linkText("Home");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get product name
     */
    public String getProductName() {
        String name = getText(productTitle);
        System.out.println("Product name: " + name);
        return name;
    }

    /**
     * Get product price
     */
    public String getProductPrice() {
        String price = getText(productPrice);
        System.out.println("Product price: " + price);
        return price;
    }

    /**
     * Get product description
     */
    public String getProductDescription() {
        return getText(productDescription);
    }

    /**
     * Add product to cart
     */
    public void addToCart() {
        click(addToCartButton);
        System.out.println("Add to cart button clicked");
    }

    /**
     * Get alert message after adding to cart
     */
    public String getAlertMessage() {
        customWait(500);
        return getAlertText();
    }

    /**
     * Close alert
     */
    public void closeAlert() {
        acceptAlert();
    }

    /**
     * Complete process of adding product to cart
     */
    public String addProductToCart() {
        addToCart();
        String alertMessage = getAlertMessage();
        closeAlert();
        System.out.println("Product added to cart. Alert: " + alertMessage);
        return alertMessage;
    }

    /**
     * Navigate back to home
     */
    public void goToHome() {
        click(homeLink);
    }

    /**
     * Verify product page is loaded
     */
    public boolean isProductPageLoaded() {
        return isDisplayed(productTitle) && isDisplayed(addToCartButton);
    }

    public void waitForPageLoad() {
    }
}
