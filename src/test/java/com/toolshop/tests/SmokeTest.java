package com.toolshop.tests;

import com.toolshop.tests.base.BaseTest;
import com.toolshop.tests.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// T1: smoke test — just checks that the page actually loads
class SmokeTest extends BaseTest {

    @Test
    @DisplayName("T1 - Homepage loads with correct title and visible products")
    void homepageLoadsSuccessfully() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);

        // check the title
        String title = homePage.getPageTitle();
        assertTrue(title.contains("Practice Software Testing"),
                "Page title should contain 'Practice Software Testing', but was: " + title);

        // make sure products are showing
        assertTrue(homePage.isLoaded(), "Product cards should be visible on the homepage");
        assertTrue(homePage.getProductCount() > 0, "There should be at least one product");
    }
}
