package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for the search input
    private By searchInput = By.xpath("//input[@placeholder='Search in Daraz']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Search for an item and return SearchResultsPage
    public SearchResultsPage searchItem(String item) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            input.clear();
            input.sendKeys(item);
            input.sendKeys(Keys.ENTER);

            System.out.println("Search request sent. Navigating to results page...");
            return new SearchResultsPage(driver);
        } catch (Exception e) {
            System.out.println("Failed to perform search due to: " + e.getMessage());
            throw e;
        }
    }
}
