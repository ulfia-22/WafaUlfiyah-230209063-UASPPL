package com.praktikum.otomation.test.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.*;

import java.time.Duration;

/**
 * BaseTest class - contains setup and teardown methods
 * All test classes extend this class
 */
public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected SignUp.SignUpPage signUpPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected String baseUrl = "https://www.demoblaze.com/";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        initializePages();
        driver.get(baseUrl);
    }

    private void initializePages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signUpPage = new SignUp.SignUpPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}