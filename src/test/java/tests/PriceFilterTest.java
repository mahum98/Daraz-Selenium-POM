package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.BaseTest;

public class PriceFilterTest extends BaseTest {

    @Test(dependsOnMethods = "tests.SearchTest.searchElectronics")
    public void testPriceFilter() {
        System.out.println("Searching for 'electronics'..");
        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.searchItem("electronics");

        System.out.println("Applying price filter (500 - 5000)");
        results.applyPriceFilter(500, 5000);
        int count = results.getProductCount();
        System.out.println("Products found after price filter: " + count);

        Assert.assertTrue(count > 0, "No products found after price filter");
        System.out.println("Price filter applied successfully");
    }
}
