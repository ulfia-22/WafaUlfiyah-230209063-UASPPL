package com.praktikum.otomation.test.base;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestData;

/**
 * Authentication Test Class
 * Contains test cases for user registration and login
 */

public class AuthenticationTest extends BaseTest {

    @Test(priority = 1,
            description = "TC_AUTH_001 - Register akun baru dengan data valid",
            groups = {"smoke", "regression", "authentication"})
    public void testRegisterNewAccountWithValidData() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_AUTH_001: Register with Valid Data            ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // Generate unique username
        String username = TestData.generateUsername();
        String password = TestData.TEST_PASSWORD;

        System.out.println("Step 1: Open sign up modal");
        signUpPage.openSignUpModal();
        Assert.assertTrue(signUpPage.isSignUpModalDisplayed(),
                "Sign up modal should be displayed");

        System.out.println("Step 2: Fill registration form");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        signUpPage.fillSignUpForm(username, password);

        System.out.println("Step 3: Submit registration");
        signUpPage.clickSignUpButton();

        System.out.println("Step 4: Verify success message");
        String alertMessage = signUpPage.getAlertMessage();
        System.out.println("Alert Message: " + alertMessage);

        // Assertions
        Assert.assertTrue(
                alertMessage.contains("Sign up successful") ||
                        alertMessage.contains("successful"),
                "Expected success message but got: " + alertMessage
        );

        signUpPage.closeAlert();

        System.out.println("✓ TEST PASSED: User registered successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    @Test(priority = 2,
            description = "TC_AUTH_002 - Login dengan kredensial valid",
            groups = {"smoke", "regression", "authentication"})
    public void testLoginWithValidCredentials() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_AUTH_002: Login with Valid Credentials        ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        String username = TestData.VALID_USERNAME;
        String password = TestData.VALID_PASSWORD;

        System.out.println("Step 1: Open login modal");
        loginPage.openLoginModal();
        Assert.assertTrue(loginPage.isLoginModalDisplayed(),
                "Login modal should be displayed");

        System.out.println("Step 2: Enter credentials");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        loginPage.fillLoginForm(username, password);

        System.out.println("Step 3: Click login button");
        loginPage.clickLoginButton();

        System.out.println("Step 4: Verify login success");
        Assert.assertTrue(loginPage.isLoggedIn(),
                "User should be logged in successfully");

        String welcomeMessage = loginPage.getWelcomeMessage();
        System.out.println("Welcome Message: " + welcomeMessage);

        // Assertions
        Assert.assertTrue(welcomeMessage.contains("Welcome"),
                "Welcome message should contain 'Welcome'");
        Assert.assertTrue(welcomeMessage.contains(username),
                "Welcome message should contain username: " + username);

        String loggedInUser = loginPage.getLoggedInUsername();
        Assert.assertEquals(loggedInUser, username,
                "Logged in username should match");

        System.out.println("✓ TEST PASSED: User logged in successfully");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    /**
     * TC_AUTH_003: Register dengan username yang sudah ada (Bonus Test)
     */
    @Test(priority = 3,
            description = "TC_AUTH_003 - Register dengan username yang sudah ada",
            groups = {"regression", "authentication", "negative"})
    public void testRegisterWithExistingUsername() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║  TC_AUTH_003: Register with Existing Username     ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        String existingUsername = TestData.VALID_USERNAME;
        String password = TestData.TEST_PASSWORD;

        String alertMessage = signUpPage.registerNewUser(existingUsername, password);

        Assert.assertTrue(
                alertMessage.contains("This user already exist") ||
                        alertMessage.contains("already exists"),
                "Should show error for existing username"
        );

        System.out.println("✓ TEST PASSED: Duplicate username detected");
        System.out.println("════════════════════════════════════════════════════\n");
    }
}
