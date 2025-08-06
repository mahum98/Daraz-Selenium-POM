package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;

public class SearchTest {

    WebDriver driver;
    HomePage homePage;
    SearchResultsPage searchResultsPage;
    ProductPage productPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.daraz.pk/");
        System.out.println("Navigated to Daraz homepage");
        homePage = new HomePage(driver);
    }

    @Test
    public void searchElectronics() {
        System.out.println("Searching for 'electronics'...");
        searchResultsPage = homePage.searchItem("electronics");
        Assert.assertTrue(searchResultsPage.isResultsLoaded(), "Search results did not load");
        System.out.println("Search results successfully loaded");
    }

    @Test(dependsOnMethods = "searchElectronics")
    public void applyBrandFilter() {
        System.out.println("Applying brand filter for 'Remington'...");
        searchResultsPage.selectBrand("Remington");
        Assert.assertTrue(searchResultsPage.isResultsLoaded(), "Brand filter not applied correctly");
        System.out.println("Brand filter applied and results loaded");
    }

    @Test(dependsOnMethods = "applyBrandFilter")
    public void applyPriceFilter() {
        System.out.println("🔹 Applying price filter: Min 1000, Max 5000...");
        searchResultsPage.applyPriceFilter(1000, 5000);
        Assert.assertTrue(searchResultsPage.isResultsLoaded(), "Price filter not applied!");
        System.out.println("Price filter applied and results reloaded");
    }

    @Test(dependsOnMethods = "applyPriceFilter")
    public void verifyProductCount() {
        System.out.println("Counting filtered products...");
        int count = searchResultsPage.getProductCount();
        System.out.println("Total products found after applying filters: " + count);
        Assert.assertTrue(count > 0, "No products found in results");
        System.out.println("Product count verification passed");
    }

    @Test(dependsOnMethods = "verifyProductCount")
    public void checkFreeShipping() {
        System.out.println("Opening first product to verify free shipping...");
        productPage = searchResultsPage.clickFirstProduct();
        boolean freeShipping = productPage.isFreeShippingAvailable();
        System.out.println(freeShipping ? "Free shipping is available" : "Free shipping is NOT available");
        Assert.assertTrue(freeShipping, "Free shipping is NOT available");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed. Test session ended.");
        }
    }
}
