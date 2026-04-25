package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Selenium Java script to select a travel date on RedBus.in
 *
 * Prerequisites: - Java 11+ - Maven / Gradle with selenium-java dependency -
 * ChromeDriver matching your Chrome version (or use WebDriverManager:
 * io.github.bonigarcia:webdrivermanager)
 *
 * Maven dependency: <dependency> <groupId>org.seleniumhq.selenium</groupId>
 * <artifactId>selenium-java</artifactId> <version>4.18.1</version>
 * </dependency>
 *
 * Optional (auto-manages ChromeDriver): <dependency>
 * <groupId>io.github.bonigarcia</groupId>
 * <artifactId>webdrivermanager</artifactId> <version>5.8.0</version>
 * </dependency>
 */
public class RedBus {

	private WebDriver driver;
	private WebDriverWait wait;

	// ─── CONFIGURATION ────────────────────────────────────────────────────────
	// Set your desired travel date here (YYYY-MM-DD)
	private static final String TARGET_DATE = "2026-05-15";

	// Source and destination cities
	private static final String SOURCE = "Kolkata";
	private static final String DESTINATION = "Delhi";
	// ──────────────────────────────────────────────────────────────────────────

	public static void main(String[] args) {
		RedBus bot = new RedBus();
		try {
			bot.setUp();
			bot.openRedBus();
			//bot.enterSource(SOURCE);
			//bot.enterDestination(DESTINATION);
			bot.selectDate(TARGET_DATE);
			System.out.println("✅ Date selected successfully: " + TARGET_DATE);
			Thread.sleep(3000); // Pause so you can see the result
		} catch (Exception e) {
			System.err.println("❌ Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			bot.tearDown();
		}
	}

	// ── SETUP ──────────────────────────────────────────────────────────────────

	public void setUp() {
		// If you use WebDriverManager, uncomment the next line instead of setting the
		// path:
		// io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

		// If NOT using WebDriverManager, set the ChromeDriver path:
		// System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		// options.addArguments("--headless"); // Uncomment to run without UI

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// ── NAVIGATION ─────────────────────────────────────────────────────────────

	public void openRedBus() {
		driver.get("https://www.redbus.in/");
		System.out.println("🌐 Opened RedBus.in");
	}

	// ── SOURCE CITY ────────────────────────────────────────────────────────────

	public void enterSource(String city) throws InterruptedException {
		// Click the "From" input
		WebElement fromBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("src")));
		fromBox.clear();
		fromBox.sendKeys(city);
		System.out.println("📍 Typing source: " + city);

		// Wait for autocomplete suggestions and click the first match
		pickFirstSuggestion(city);
	}

	// ── DESTINATION CITY ───────────────────────────────────────────────────────

	public void enterDestination(String city) throws InterruptedException {
		WebElement toBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("dest")));
		toBox.clear();
		toBox.sendKeys(city);
		System.out.println("📍 Typing destination: " + city);

		pickFirstSuggestion(city);
	}

	// ── DATE PICKER ────────────────────────────────────────────────────────────

	/**
	 * Opens the date picker and navigates to the month that contains TARGET_DATE,
	 * then clicks the matching day cell.
	 *
	 * @param dateStr Date in "YYYY-MM-DD" format
	 */
	public void selectDate(String dateStr) throws InterruptedException {
		LocalDate targetDate = LocalDate.parse(dateStr);
		int targetDay = targetDate.getDayOfMonth();
		int targetMonth = targetDate.getMonthValue(); // 1-12
		int targetYear = targetDate.getYear();

		// Click the date field to open the calendar
		WebElement dateField = driver.findElement(By.xpath("//div[@class='dateInputWrapper___874b87 dateHighlight___4d1fef']"));
		
		dateField.click();
		System.out.println("📅 Opened date picker");
		Thread.sleep(1000);

		// Navigate months until the correct month/year is visible
		navigateToMonth(targetMonth, targetYear);

		// Click the target day
		clickDay(targetDay);
	}

	// ── HELPERS ────────────────────────────────────────────────────────────────

	private void pickFirstSuggestion(String city) throws InterruptedException {
		Thread.sleep(1500); // Allow suggestions to load

		// RedBus renders suggestions in a <ul> under the active input
		List<WebElement> suggestions = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("ul.sc-dnqmqq li")));

		if (suggestions.isEmpty()) {
			throw new RuntimeException("No suggestions found for: " + city);
		}

		suggestions.get(0).click();
		System.out.println("✅ Selected suggestion for: " + city);
		Thread.sleep(500);
	}

	private void navigateToMonth(int targetMonth, int targetYear) throws InterruptedException {

		for (int attempt = 0; attempt < 24; attempt++) { // max 24 months ahead

			// Read currently displayed month/year header
			// RedBus calendar header format: "May 2026"
			//WebElement header = wait.until(ExpectedConditions
					//.visibilityOfElementLocated(By.xpath("//div[contains(@class,'DayPicker-Caption')]")));
			
			WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("p[class*=monthYear]")));

			String headerText = header.getText().trim(); // e.g. "May 2026"
			LocalDate displayed = LocalDate.parse("01 " + headerText, DateTimeFormatter.ofPattern("dd MMMM yyyy"));

			if (displayed.getMonthValue() == targetMonth && displayed.getYear() == targetYear) {
				System.out.println("📆 Correct month displayed: " + headerText);
				return;
			}

			// Click the Next (►) arrow
			WebElement nextArrow = driver.findElement(By.xpath("//span[contains(@class,'DayPicker-NavButton--next')]"));
			nextArrow.click();
			Thread.sleep(500);
		}

		throw new RuntimeException("Could not navigate to target month: " + targetMonth + "/" + targetYear);
	}

	private void clickDay(int day) {
		// Find all enabled day cells and click the one matching our day number
		List<WebElement> dayCells = driver.findElements(By.xpath(
				"//div[contains(@class,'DayPicker-Day') and " + "not(contains(@class,'DayPicker-Day--disabled')) and "
						+ "not(contains(@class,'DayPicker-Day--outside'))]"));

		for (WebElement cell : dayCells) {
			if (cell.getText().trim().equals(String.valueOf(day))) {
				cell.click();
				System.out.println("✅ Clicked day: " + day);
				return;
			}
		}

		throw new RuntimeException("Day cell not found for day: " + day);
	}

	// ── TEARDOWN ───────────────────────────────────────────────────────────────

	public void tearDown() {
		if (driver != null) {
			driver.quit();
			System.out.println("🔒 Browser closed");
		}
	}
}
