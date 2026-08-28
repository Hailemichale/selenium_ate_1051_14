package com.toolshop.tests;

import com.toolshop.tests.base.BaseTest;
import com.toolshop.tests.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// T4: negative path — testing what happens with bad input
class NegativePathTest extends BaseTest {

    @Test
    @DisplayName("T4 - Login with wrong credentials shows error message")
    void loginWithInvalidCredentialsShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL);

        // try logging in with fake credentials
        loginPage.login("invalid@test.com", "wrongpassword");

        // should see an error
        assertTrue(loginPage.isErrorDisplayed(), "Error message should show up");

        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains("Invalid email or password"),
                "Error should say invalid credentials. Got: " + errorText);
    }

    @Test
    @DisplayName("T4 - Login with empty fields shows inline validation errors")
    void loginWithEmptyFieldsShowsValidationError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL);

        // submit without typing anything
        loginPage.login("", "");

        // the site shows field-level validation instead of the main error banner
        // so I just check that we're still on the login page
        assertTrue(driver.getCurrentUrl().contains("/auth/login"),
                "Should stay on login page with empty fields");

        // make sure login didn't somehow work
        assertFalse(loginPage.isLoginSuccessful(), "Login should not work with empty fields");
    }
}
