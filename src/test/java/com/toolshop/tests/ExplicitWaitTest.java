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

// T5: using explicit waits instead of Thread.sleep
// the site is an Angular app so elements load dynamically
class ExplicitWaitTest extends BaseTest {

    @Test
    @DisplayName("T5 - Wait for product cards to appear on homepage")
    void waitForProductCardsToLoad() {
        driver.get(BASE_URL);

        // products are loaded by Angular after the page renders,
        // so we need to wait for them explicitly
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement firstProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='product-name']")));

        assertNotNull(firstProduct, "First product should show up after waiting");
        assertFalse(firstProduct.getText().isEmpty(), "Product name should not be empty");
    }

    @Test
    @DisplayName("T5 - Wait for search results to refresh after searching")
    void waitForSearchResultsToRefresh() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);
        homePage.waitForProductsToLoad();

        homePage.searchFor("Hammer");

        // wait for the results to load after the search
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        List<WebElement> results = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector("[data-test='product-name']")));

        assertFalse(results.isEmpty(), "Should see results after searching");

        boolean hasHammer = results.stream()
                .anyMatch(el -> el.getText().toLowerCase().contains("hammer"));
        assertTrue(hasHammer, "At least one result should have 'Hammer' in the name");
    }
}
