package com.toolshop.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Page Object for the login page at /auth/login
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // css selector locators
    private final By emailInput = By.cssSelector("[data-test='email']");
    private final By passwordInput = By.cssSelector("[data-test='password']");
    private final By loginButton = By.cssSelector("[data-test='login-submit']");
    private final By errorMessage = By.cssSelector("[data-test='login-error']");

    // id-based locator (to show different strategy for T2)
    private final By emailInputById = By.id("email");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public LoginPage open(String baseUrl) {
        driver.get(baseUrl + "/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        return this;
    }

    public LoginPage login(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(passwordInput);
        passwordField.clear();
        passwordField.sendKeys(password);

        driver.findElement(loginButton).click();
        return this;
    }

    public String getErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return error.getText();
    }

    public boolean isErrorDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // these two methods return the same element but found with different locators
    // used in LocatorStrategyTest to show By.id vs By.cssSelector
    public WebElement getEmailInputById() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputById));
    }

    public WebElement getEmailInputByCss() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // returns true if we got redirected to /account (meaning login worked)
    public boolean isLoginSuccessful() {
        try {
            WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            loginWait.until(ExpectedConditions.urlContains("/account"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
