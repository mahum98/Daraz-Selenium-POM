package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductPage;
import utils.BaseTest;

public class ProductTest extends BaseTest {

    @Test
    public void testFreeShipping() {
        System.out.println("Verifying free shipping on product page..");
        ProductPage product = new ProductPage(driver);
        boolean freeShipping = product.isFreeShippingAvailable();
        System.out.println(freeShipping ? "Free shipping is available." : "Free shipping is NOT available.");
        Assert.assertTrue(freeShipping, "Free shipping should be available.");
    }
}
