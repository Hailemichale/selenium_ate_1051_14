# Selenium End-to-End Testing — ate_1051_14

**Name:** Hailemichale Lijalem  
**Student ID:** ate_1051_14  
**Course:** Automated Testing  

## About

This is my individual homework for the Automated Testing course. I wrote end-to-end tests using Selenium WebDriver against the [Practice Software Testing Toolshop](https://practicesoftwaretesting.com).

I chose this site because it has a login page, product search, and a product catalog that loads dynamically — which gave me enough to cover all the required test types without dealing with CAPTCHAs or bot blockers.

## What's in the project

- **T1 — Smoke test:** Opens the homepage and checks the title and product grid
- **T2 — Locator strategies:** Uses `By.cssSelector`, `By.className`, and `By.id`
- **T3 — Positive flow:** Searches for "Pliers", checks results, opens a product detail page
- **T4 — Negative path:** Tries logging in with wrong credentials, checks error message
- **T5 — Explicit wait:** Uses `WebDriverWait` for dynamically loaded Angular content
- **T6 — Parameterized test:** 5 search queries using equivalence partitioning
- **T7 — Page Objects:** `HomePage` and `LoginPage` classes
- **T8 — Lifecycle:** Fresh browser per test with `@BeforeEach` / `@AfterEach`

## How to run

Make sure you have Java 21 and Maven installed, then:

```bash
mvn test
```

All 14 tests should pass.

## Tech stack

- Java 21
- Maven
- Selenium 4.27
- JUnit 5
- WebDriverManager
- Chrome (headless)
