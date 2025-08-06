package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;
import utils.BaseTest;

public class FreeShippingTest extends BaseTest {

    @Test
    public void testFreeShipping() {
        HomePage home = new HomePage(driver);
        home.searchItem("electronics");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.clickFirstProduct();

        // Handle tab switch if product opens in a new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        ProductPage product = new ProductPage(driver);
        boolean freeShipping = product.isFreeShippingAvailable();

        if (freeShipping) {
            System.out.println("Free shipping is available.");
        } else {
            System.out.println("Free shipping is NOT available.");
        }

    }
}