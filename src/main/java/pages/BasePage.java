package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * BasePage class containing common methods used across all page objects
 * Implements POM design pattern
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_WAIT_TIME = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
    }

    /**
     * Click on element with explicit wait
     */
    protected void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("Clicked on element: " + locator);
        } catch (TimeoutException e) {
            System.err.println("Element not clickable: " + locator);
            throw e;
        }
    }

    /**
     * Type text into input field
     */
    protected void type(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            System.out.println("Typed '" + text + "' into: " + locator);
        } catch (TimeoutException e) {
            System.err.println("Element not visible: " + locator);
            throw e;
        }
    }

    /**
     * Get text from element
     */
    protected String getText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            System.out.println("Got text '" + text + "' from: " + locator);
            return text;
        } catch (TimeoutException e) {
            System.err.println("Element not visible: " + locator);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean displayed = element.isDisplayed();
            System.out.println("Element " + locator + " is displayed: " + displayed);
            return displayed;
        } catch (TimeoutException e) {
            System.out.println("Element " + locator + " is not displayed");
            return false;
        }
    }

    /**
     * Get list of elements
     */
    protected List<WebElement> getElements(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            return driver.findElements(locator);
        } catch (TimeoutException e) {
            System.err.println("Elements not found: " + locator);
            return List.of();
        }
    }

    /**
     * Wait for alert and accept it
     */
    protected void acceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            System.out.println("Alert accepted");
        } catch (TimeoutException e) {
            System.err.println("Alert not present");
            throw e;
        }
    }

    /**
     * Get alert text
     */
    protected String getAlertText() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText);
            return alertText;
        } catch (TimeoutException e) {
            System.err.println("Alert not present");
            throw e;
        }
    }

    /**
     * Wait for element to be visible
     */
    protected void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be invisible
     */
    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Custom wait with specified duration
     */
    public void customWait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

