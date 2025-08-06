package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

    public class BaseTest {
        protected WebDriver driver;

        @BeforeClass
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://www.daraz.pk/");
        }

        @AfterClass
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }

