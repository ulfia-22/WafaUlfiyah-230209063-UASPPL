package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SignUpPage class - handles user registration functionality
 */
public class SignUp {
    public static class SignUpPage extends BasePage {

        // Locators
        private final By signUpLink = By.id("signin2");
        private final By signUpModal = By.id("signInModal");
        private final By usernameField = By.id("sign-username");
        private final By passwordField = By.id("sign-password");
        private final By signUpButton = By.xpath("//button[text()='Sign up']");
        private final By closeButton = By.xpath("//div[@id='signInModal']//button[text()='Close']");

        public SignUpPage(WebDriver driver) {
            super(driver);
        }

        /**
         * Open sign up modal
         */
        public void openSignUpModal() {
            click(signUpLink);
            waitForVisibility(signUpModal);
            System.out.println("Sign up modal opened");
        }

        /**
         * Enter username in sign up form
         */
        public void enterUsername(String username) {
            type(usernameField, username);
        }

        /**
         * Enter password in sign up form
         */
        public void enterPassword(String password) {
            type(passwordField, password);
        }

        /**
         * Fill complete sign up form
         */
        public void fillSignUpForm(String username, String password) {
            enterUsername(username);
            enterPassword(password);
            System.out.println("Sign up form filled with username: " + username);
        }

        /**
         * Click sign up button
         */
        public void clickSignUpButton() {
            click(signUpButton);
            System.out.println("Sign up button clicked");
        }

        /**
         * Get alert message after sign up attempt
         */
        public String getAlertMessage() {
            customWait(500);
            return getAlertText();
        }

        /**
         * Close alert message
         */
        public void closeAlert() {
            acceptAlert();
        }

        /**
         * Close sign up modal
         */
        public void closeModal() {
            click(closeButton);
        }

        /**
         * Complete registration process
         */
        public String registerNewUser(String username, String password) {
            openSignUpModal();
            fillSignUpForm(username, password);
            clickSignUpButton();
            String alertMessage = getAlertMessage();
            closeAlert();
            System.out.println("Registration completed. Alert: " + alertMessage);
            return alertMessage;
        }

        /**
         * Verify sign up modal is displayed
         */
        public boolean isSignUpModalDisplayed() {
            return isDisplayed(signUpModal);
        }
    }

}
