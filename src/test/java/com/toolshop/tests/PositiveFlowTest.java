package com.toolshop.tests;

import com.toolshop.tests.base.BaseTest;
import com.toolshop.tests.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// T3: the main positive flow — search for a product and view its details
class PositiveFlowTest extends BaseTest {

    @Test
    @DisplayName("T3 - Search for Pliers, verify results, open product detail")
    void searchAndViewProductDetail() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);
        homePage.waitForProductsToLoad();

        // search for pliers
        homePage.searchFor("Pliers");

        // wait for results
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-test='product-name']")));

        List<String> productNames = homePage.getProductNames();
        assertFalse(productNames.isEmpty(), "Should get at least one result for 'Pliers'");

        // check that at least one result actually has "pliers" in the name
        boolean containsPliers = productNames.stream()
                .anyMatch(name -> name.toLowerCase().contains("pliers"));
        assertTrue(containsPliers, "One of the results should contain 'Pliers'. Got: " + productNames);

        // click the first product
        homePage.clickProduct(0);

        // check that the product detail page shows a name and price
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='product-name']")));
        assertFalse(productTitle.getText().isEmpty(), "Product page should show the name");

        WebElement productPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='unit-price']")));
        assertFalse(productPrice.getText().isEmpty(), "Product page should show the price");
    }
}
