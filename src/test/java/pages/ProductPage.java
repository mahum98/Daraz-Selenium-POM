package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Check if free shipping is available
    public boolean isFreeShippingAvailable() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(1000);

            // Expand delivery section if collapsible
            List<WebElement> expandBtns = driver.findElements(By.xpath("//button[contains(.,'Delivery') or contains(.,'Shipping')]"));
            if (!expandBtns.isEmpty()) {
                System.out.println("Expanding delivery/shipping section...");
                js.executeScript("arguments[0].click();", expandBtns.get(0));
                Thread.sleep(500);
            }

            System.out.println("Waiting for shipping details");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'delivery') or " +
                            "contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'shipping')]")
            ));

            // Scan for free shipping text
            List<WebElement> shippingElements = driver.findElements(By.xpath("//*[contains(text(),'Delivery') or contains(text(),'Shipping')]"));
            for (WebElement el : shippingElements) {
                String text = el.getText().toLowerCase();
                if (text.contains("free") || text.contains("standard delivery") || text.contains("delivery available")) {
                    System.out.println("Free shipping detected: " + text);
                    return true;
                }
            }

            System.out.println("No free shipping info found on product page");
            return false;
        } catch (Exception e) {
            System.out.println("Error while checking free shipping: " + e.getMessage());
            return false;
        }
    }
}
