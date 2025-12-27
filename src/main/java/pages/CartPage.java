package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * CartPage class - handles shopping cart and checkout functionality
 */
public class CartPage extends BasePage {

    // Locators
    private final By cartLink = By.id("cartur");
    private final By cartTable = By.cssSelector("#tbodyid");
    private final By cartItems = By.cssSelector("#tbodyid tr");
    private final By deleteButtons = By.linkText("Delete");
    private final By totalPrice = By.id("totalp");
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");

    // Order form locators
    private final By orderModal = By.id("orderModal");
    private final By nameField = By.id("name");
    private final By countryField = By.id("country");
    private final By cityField = By.id("city");
    private final By cardField = By.id("card");
    private final By monthField = By.id("month");
    private final By yearField = By.id("year");
    private final By purchaseButton = By.xpath("//button[text()='Purchase']");
    private final By closeButton = By.xpath("//div[@id='orderModal']//button[text()='Close']");

    // Confirmation locators
    private final By confirmationModal = By.cssSelector(".sweet-alert");
    private final By confirmationTitle = By.cssSelector(".sweet-alert h2");
    private final By confirmationMessage = By.cssSelector(".sweet-alert .lead");
    private final By okButton = By.cssSelector(".confirm");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to cart page
     */
    public void goToCart() {
        click(cartLink);
        customWait(1000);
        System.out.println("Navigated to cart page");
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        try {
            List<WebElement> items = getElements(cartItems);
            int count = items.size();
            System.out.println("Cart contains " + count + " items");
            return count;
        } catch (Exception e) {
            System.out.println("Cart is empty");
            return 0;
        }
    }

    /**
     * Get names of all products in cart
     */
    public List<String> getCartProductNames() {
        By productNames = By.cssSelector("#tbodyid tr td:nth-child(2)");
        List<WebElement> names = getElements(productNames);
        return names.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Get total cart price
     */
    public String getTotalPrice() {
        String total = getText(totalPrice);
        System.out.println("Total cart price: " + total);
        return total;
    }

    /**
     * Delete first item from cart
     */
    public void deleteFirstItem() {
        click(deleteButtons);
        customWait(1500); // Wait for deletion to complete
        System.out.println("Deleted first item from cart");
    }

    /**
     * Delete specific item by index (0-based)
     */
    public void deleteItemByIndex(int index) {
        List<WebElement> deleteLinks = getElements(deleteButtons);
        if (index >= 0 && index < deleteLinks.size()) {
            deleteLinks.get(index).click();
            customWait(1500);
            System.out.println("Deleted item at index: " + index);
        }
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    /**
     * Click Place Order button
     */
    public void clickPlaceOrder() {
        click(placeOrderButton);
        waitForVisibility(orderModal);
        System.out.println("Place Order button clicked");
    }

    /**
     * Fill order form
     */
    public void fillOrderForm(String name, String country, String city,
                              String card, String month, String year) {
        type(nameField, name);
        type(countryField, country);
        type(cityField, city);
        type(cardField, card);
        type(monthField, month);
        type(yearField, year);
        System.out.println("Order form filled - Name: " + name + ", Card: " + card);
    }

    /**
     * Click Purchase button
     */
    public void clickPurchase() {
        click(purchaseButton);
        waitForVisibility(confirmationModal);
        System.out.println("Purchase button clicked");
        customWait(1000);
    }

    /**
     * Get confirmation title
     */
    public String getConfirmationTitle() {
        String title = getText(confirmationTitle);
        System.out.println("Confirmation title: " + title);
        return title;
    }

    /**
     * Get confirmation message
     */
    public String getConfirmationMessage() {
        String message = getText(confirmationMessage);
        System.out.println("Confirmation message: " + message);
        return message;
    }

    /**
     * Close confirmation modal
     */
    public void closeConfirmation() {
        click(okButton);
        System.out.println("Confirmation modal closed");
        customWait(1000);
    }

    /**
     * Complete checkout process
     */
    public String completeCheckout(String name, String country, String city,
                                   String card, String month, String year) {
        clickPlaceOrder();
        fillOrderForm(name, country, city, card, month, year);
        clickPurchase();
        String confirmation = getConfirmationMessage();
        closeConfirmation();
        System.out.println("Checkout completed");
        return confirmation;
    }

    /**
     * Verify cart page is loaded
     */
    public boolean isCartPageLoaded() {
        return isDisplayed(cartTable);
    }
}



