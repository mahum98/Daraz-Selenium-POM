package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productCards = By.xpath("//div[contains(@data-qa-locator,'product-item') or contains(@class,'gridItem')]");
    private By firstProduct = By.xpath("(//div[contains(@data-qa-locator,'product-item') or contains(@class,'gridItem')]//a)[1]");
    private By brandHeading = By.xpath("//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'brand')]");
    private By minPrice = By.xpath("//input[@placeholder='Min' or contains(@aria-label,'Min')]");
    private By maxPrice = By.xpath("//input[@placeholder='Max' or contains(@aria-label,'Max')]");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ✅ Scroll to element with retries
    private WebElement scrollToElement(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 10; i++) {
            try {
                WebElement el = driver.findElement(locator);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                wait.until(ExpectedConditions.visibilityOf(el));
                System.out.println("✅ Scrolled to element: " + locator.toString());
                return el;
            } catch (NoSuchElementException | TimeoutException e) {
                js.executeScript("window.scrollBy(0,300);");
                try { Thread.sleep(400); } catch (InterruptedException ignored) {}
            }
        }
        throw new NoSuchElementException("❌ Could not find element after scrolling: " + locator);
    }

    // ✅ Wait for products grid to reload
    private void waitForProductsReload() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
        System.out.println("✅ Products grid loaded successfully");
    }

    // ✅ Check if results loaded
    public boolean isResultsLoaded() {
        try {
            waitForProductsReload();
            boolean loaded = driver.findElements(productCards).size() > 0;
            System.out.println(loaded ? "✅ Search results loaded" : "❌ No products found");
            return loaded;
        } catch (TimeoutException e) {
            System.out.println("❌ Timeout waiting for products to load");
            return false;
        }
    }

    // ✅ Apply brand filter
    public void applyBrandFilter() {
        System.out.println("🔹 Applying brand filter...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Expand if brand filter is collapsed
        try {
            List<WebElement> expand = driver.findElements(By.xpath("//button[contains(.,'Brand') or contains(@aria-label,'Brand')]"));
            if (!expand.isEmpty()) {
                js.executeScript("arguments[0].click();", expand.get(0));
                Thread.sleep(800);
                System.out.println("✅ Expanded brand filter section");
            }
        } catch (Exception ignored) {}

        // Scroll to brand section
        WebElement brandSection = scrollToElement(brandHeading);

        // Find brand checkboxes
        List<WebElement> checkboxes = driver.findElements(By.xpath("//label[contains(@class,'checkbox')]"));
        if (checkboxes.isEmpty()) {
            throw new NoSuchElementException("❌ No brand checkboxes found!");
        }

        // Click the first checkbox
        js.executeScript("arguments[0].click();", checkboxes.get(0));
        System.out.println("✅ Brand filter applied successfully");
        waitForProductsReload();
    }

    // ✅ Apply price filter
    public void applyPriceFilter(int min, int max) {
        System.out.println("🔹 Applying price filter: Min = " + min + " | Max = " + max);
        WebElement minInput = scrollToElement(minPrice);
        WebElement maxInput = scrollToElement(maxPrice);
        minInput.clear();
        minInput.sendKeys(String.valueOf(min));
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(max));
        maxInput.sendKeys(Keys.ENTER);
        waitForProductsReload();
        System.out.println("✅ Price filter applied successfully");
    }

    // ✅ Click first product & open ProductPage
    public ProductPage openFirstProduct() {
        System.out.println("🔹 Clicking on the first product...");
        WebElement first = scrollToElement(firstProduct);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", first);
        System.out.println("✅ Navigated to product details page");
        return new ProductPage(driver);
    }

    // ✅ Count loaded products
    public int getProductCount() {
        waitForProductsReload();
        int count = driver.findElements(productCards).size();
        System.out.println("📌 Total products counted: " + count);
        return count;
    }

    // ✅ Select first brand
    public void selectFirstBrand() { applyBrandFilter(); }

    public ProductPage clickFirstProduct() { return openFirstProduct(); }

    // ✅ Select a specific brand dynamically
    public void selectBrand(String brandName) {
        System.out.println("🔹 Selecting brand: " + brandName);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to brand section
        WebElement section = scrollToElement(brandHeading);

        // Dynamic locator for brand
        By dynamicLocator = By.xpath("//label[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" +
                brandName.toLowerCase() + "')]");
        List<WebElement> brandList = driver.findElements(dynamicLocator);

        if (brandList.isEmpty()) {
            throw new NoSuchElementException("❌ Brand '" + brandName + "' not found!");
        }

        js.executeScript("arguments[0].click();", brandList.get(0));
        waitForProductsReload();
        System.out.println("✅ Brand '" + brandName + "' selected successfully");
    }
}
