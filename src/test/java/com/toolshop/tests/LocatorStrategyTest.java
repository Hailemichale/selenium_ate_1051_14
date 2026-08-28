package com.toolshop.tests;

import com.toolshop.tests.base.BaseTest;
import com.toolshop.tests.pages.HomePage;
import com.toolshop.tests.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// T2: showing that I can use different locator strategies
class LocatorStrategyTest extends BaseTest {

    @Test
    @DisplayName("T2 - Locate search input using By.cssSelector")
    void locateElementsByCssSelector() {
        driver.get(BASE_URL);

        HomePage homePage = new HomePage(driver);
        homePage.waitForProductsToLoad();

        // using cssSelector with data-test attribute
        WebElement searchInput = homePage.getSearchInputElement();
        assertTrue(searchInput.isDisplayed(), "Search input should be found via cssSelector");
    }

    @Test
    @DisplayName("T2 - Locate nav links using By.className")
    void locateElementsByClassName() {
        driver.get(BASE_URL);

        HomePage homePage = new HomePage(driver);
        homePage.waitForProductsToLoad();

        // using className
        List<WebElement> navLinks = homePage.getNavLinkElements();
        assertFalse(navLinks.isEmpty(), "Should find nav links via className");
    }

    @Test
    @DisplayName("T2 - Locate email input using By.id on login page")
    void locateElementsById() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL);

        // using By.id
        WebElement emailById = loginPage.getEmailInputById();
        assertTrue(emailById.isDisplayed(), "Email input should be found via By.id");

        // same element but found with cssSelector — both work
        WebElement emailByCss = loginPage.getEmailInputByCss();
        assertTrue(emailByCss.isDisplayed(), "Email input also found via cssSelector");
    }
}
