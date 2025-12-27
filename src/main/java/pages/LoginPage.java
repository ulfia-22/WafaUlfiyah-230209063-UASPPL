package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage class - handles user login functionality
 */
public class LoginPage extends BasePage {

        // Locators
        private final By loginLink = By.id("login2");
        private final By loginModal = By.id("logInModal");
        private final By usernameField = By.id("loginusername");
        private final By passwordField = By.id("loginpassword");
        private final By loginButton = By.xpath("//button[text()='Log in']");
        private final By closeButton = By.xpath("//div[@id='logInModal']//button[text()='Close']");
        private final By welcomeMessage = By.id("nameofuser");
        private final By logoutLink = By.id("logout2");

        public LoginPage(WebDriver driver) {
            super(driver);
        }

        /**
         * Open login modal
         */
        public void openLoginModal() {
            click(loginLink);
            waitForVisibility(loginModal);
            System.out.println("Login modal opened");
        }

        /**
         * Enter username in login form
         */
        public void enterUsername(String username) {
            type(usernameField, username);
        }

        /**
         * Enter password in login form
         */
        public void enterPassword(String password) {
            type(passwordField, password);
        }

        /**
         * Fill complete login form
         */
        public void fillLoginForm(String username, String password) {
            enterUsername(username);
            enterPassword(password);
            System.out.println("Login form filled with username: " + username);
        }

        /**
         * Click login button
         */
        public void clickLoginButton() {
            click(loginButton);
            System.out.println("Login button clicked");
            customWait(1500); // Wait for login to complete
        }

        /**
         * Close login modal
         */
        public void closeModal() {
            click(closeButton);
        }

        /**
         * Check if user is logged in
         */
        public boolean isLoggedIn() {
            return isDisplayed(welcomeMessage);
        }

        /**
         * Get welcome message text
         */
        public String getWelcomeMessage() {
            return getText(welcomeMessage);
        }

        /**
         * Get username from welcome message
         */
        public String getLoggedInUsername() {
            String welcomeText = getWelcomeMessage();
            // Format: "Welcome username"
            return welcomeText.replace("Welcome ", "").trim();
        }

        /**
         * Click logout link
         */
        public void logout() {
            click(logoutLink);
            System.out.println("User logged out");
            customWait(1000);
        }

        /**
         * Complete login process
         */
        public void login(String username, String password) {
            openLoginModal();
            fillLoginForm(username, password);
            clickLoginButton();
            System.out.println("Login completed for user: " + username);
        }

        /**
         * Verify login modal is displayed
         */
        public boolean isLoginModalDisplayed() {
            return isDisplayed(loginModal);
        }
    }

