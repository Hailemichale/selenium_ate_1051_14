package com.toolshop.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

// Page Object for the homepage — handles search and product browsing
public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // locators
    private final By searchInput = By.cssSelector("[data-test='search-query']");
    private final By searchButton = By.cssSelector("[data-test='search-submit']");
    private final By productCards = By.cssSelector(".card");
    private final By productNames = By.cssSelector("[data-test='product-name']");
    private final By navLinks = By.className("nav-link");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    // checks if the page loaded by looking for product cards
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // waits until products show up on the page
    public void waitForProductsToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
    }

    // types a query into the search bar and clicks search
    public HomePage searchFor(String query) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(query);

        WebElement button = driver.findElement(searchButton);
        button.click();

        // wait a bit for results to load
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productCards));
        } catch (Exception e) {
            // might be no results — thats fine
        }

        return this;
    }

    // gets all the product names currently showing
    public List<String> getProductNames() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productNames));
            List<WebElement> elements = driver.findElements(productNames);
            return elements.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    // counts how many products are on the page
    public int getProductCount() {
        try {
            List<WebElement> elements = driver.findElements(productNames);
            return (int) elements.stream()
                    .filter(e -> !e.getText().isEmpty())
                    .count();
        } catch (Exception e) {
            return 0;
        }
    }

    // clicks on a product by its position in the list
    public void clickProduct(int index) {
        wait.until(ExpectedConditions.presenceOfElementLocated(productNames));
        List<WebElement> products = driver.findElements(productNames);
        if (index < products.size()) {
            products.get(index).click();
        }
    }

    public WebElement getSearchInputElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
    }

    public List<WebElement> getNavLinkElements() {
        return driver.findElements(navLinks);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // checks if no products are showing (after a search with no matches)
    public boolean isNoResultsDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("[data-test='search_completed']")));
        } catch (Exception e) {
            // timeout is ok here
        }
        List<WebElement> products = driver.findElements(productNames);
        return products.isEmpty() || products.stream().allMatch(e -> e.getText().isEmpty());
    }
}
