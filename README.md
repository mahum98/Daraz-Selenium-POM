**Project Overview**

This project automates the testing of Daraz.pk functionalities using Selenium WebDriver and TestNG, following the Page Object Model (POM) design pattern.
It validates search, brand and price filters, product count, and free shipping availability.

**Requirements**

Java 17+ | Maven | Selenium WebDriver 4.23.0 | TestNG | ChromeDriver | Google Chrome Browser

**Test Flow**

1.	Navigate to https://www.daraz.pk/
2.	Search for a product (e.g., electronics)
3.	Apply a brand filter
4.	Apply a price filter (Min: 500, Max: 5000)
5.	Verify the count of loaded products (>0)
6.	Click on the first product in results
7.	Validate if free shipping is available

**Features Implemented**

•	Page Object Model structure for maintainability
•	Automated search functionality
•	Brand filter with dynamic scrolling
•	Price filter automation
•	Product count verification with assertions
•	Free shipping validation
•	Console logging for each test action (navigated to homepage, searched for electronics, applied filter, etc.)

**Project Structure**

src/test/java/tests/      → TestNG test classes
src/main/java/pages/      → Page Objects (HomePage, SearchResultsPage, ProductPage)
src/main/java/utils/      → BaseTest utilities
pom.xml                   → Maven dependencies

**▶How to Run**

1.	Clone the repository
2.	Install dependencies using Maven
3.	Ensure ChromeDriver matches your Chrome version
4.	Run tests using:
      mvn clean test
5.	View console logs for detailed execution steps

**Conclusion**

All assignment requirements have been implemented successfully.
The tests provide detailed logs and ensure reliable validation of Daraz functionalities. 








# Daraz-Selenium-POM
