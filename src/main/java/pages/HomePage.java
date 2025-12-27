package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * HomePage class - represents the main landing page
 * Contains methods for product display and category navigation
 */
public class HomePage extends BasePage {

    // Locators
    private final By homeLink = By.linkText("Home");
    private final By productsContainer = By.id("tbodyid");
    private final By productCards = By.cssSelector(".card");
    private final By productTitles = By.cssSelector(".card-title a");
    private final By categoryPhones = By.linkText("Phones");
    private final By categoryLaptops = By.linkText("Laptops");
    private final By categoryMonitors = By.linkText("Monitors");
    private final By nextButton = By.id("next2");
    private final By previousButton = By.id("prev2");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to home page
     */
    public void navigateToHomePage() {
        driver.get("https://www.demoblaze.com/");
        System.out.println("Navigated to Demoblaze home page");
    }

    /**
     * Click on Home link in navigation
     */
    public void clickHome() {
        click(homeLink);
    }

    /**
     * Get total number of products displayed on current page
     */
    public int getProductCount() {
        List<WebElement> products = getElements(productCards);
        int count = products.size();
        System.out.println("Total products displayed: " + count);
        return count;
    }

    /**
     * Get all product names displayed on current page
     */
    public List<String> getProductNames() {
        List<WebElement> titles = getElements(productTitles);
        List<String> names = titles.stream()
                .map(WebElement::getText)
                .toList();
        System.out.println("Product names: " + names);
        return names;
    }

    /**
     * Select product category
     */
    public void selectCategory(String category) {
        System.out.println("Selecting category: " + category);
        switch (category.toLowerCase()) {
            case "phones":
                click(categoryPhones);
                break;
            case "laptops":
                click(categoryLaptops);
                break;
            case "monitors":
                click(categoryMonitors);
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
        customWait(1000); // Wait for products to load
    }

    /**
     * Click on specific product by name
     */
    public void clickProduct(String productName) {
        By productLink = By.linkText(productName);
        click(productLink);
        System.out.println("Clicked on product: " + productName);
    }

    /**
     * Check if specific product is displayed
     */
    public boolean isProductDisplayed(String productName) {
        By productLink = By.linkText(productName);
        return isDisplayed(productLink);
    }

    /**
     * Click next page button
     */
    public void clickNext() {
        click(nextButton);
        customWait(1000);
    }

    /**
     * Click previous page button
     */
    public void clickPrevious() {
        click(previousButton);
        customWait(1000);
    }

    /**
     * Verify home page is loaded
     */
    public boolean isHomePageLoaded() {
        return isDisplayed(productsContainer);
    }

    public void waitForPageLoad() {
    }
}

