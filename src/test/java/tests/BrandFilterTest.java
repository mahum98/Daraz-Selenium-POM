package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.BaseTest;

public class BrandFilterTest extends BaseTest {

    @Test
    public void testBrandFilter() {
        System.out.println("Performing search for 'electronics'...");
        HomePage home = new HomePage(driver);
        home.searchItem("electronics");

        System.out.println("Applying first brand filter...");
        SearchResultsPage results = new SearchResultsPage(driver);
        results.selectFirstBrand();

        int count = results.getProductCount();
        System.out.println("Products after brand filter: " + count);
        Assert.assertTrue(count > 0, "Brand filter returned no products!");
        System.out.println("Brand filter applied successfully.");
    }
}
