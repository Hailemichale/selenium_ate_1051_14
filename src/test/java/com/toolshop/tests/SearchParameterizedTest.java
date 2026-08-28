package com.toolshop.tests;

import com.toolshop.tests.base.BaseTest;
import com.toolshop.tests.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// T6: parameterized test using equivalence partitioning
// I split possible search inputs into groups:
//   P1-P3: valid product names that should return results
//   P4: random nonsense that should return nothing
//   P5: special characters to check the site handles them ok
class SearchParameterizedTest extends BaseTest {

    @ParameterizedTest(name = "Search for \"{0}\" — expect hasResults={1}")
    @CsvSource({
            "Pliers,          true",
            "Hammer,          true",
            "Bolt,            true",
            "xyznonexistent123, false",
            "<script>alert(1),  false"
    })
    @DisplayName("T6 - Parameterized search with equivalence partitioning")
    void searchWithEquivalencePartitions(String query, boolean expectResults) {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);
        homePage.waitForProductsToLoad();

        homePage.searchFor(query);

        if (expectResults) {
            List<String> results = homePage.getProductNames();
            assertFalse(results.isEmpty(),
                    "Search for '" + query + "' should return results but got none");

            boolean hasMatch = results.stream()
                    .anyMatch(name -> name.toLowerCase().contains(query.toLowerCase()));
            assertTrue(hasMatch,
                    "At least one result should contain '" + query + "'. Found: " + results);
        } else {
            assertTrue(homePage.isNoResultsDisplayed(),
                    "Search for '" + query + "' should return no results");
        }
    }
}
