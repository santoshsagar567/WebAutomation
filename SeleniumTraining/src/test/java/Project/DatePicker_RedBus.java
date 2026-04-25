package Project;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DatePicker_RedBus {
	
	public static void main(String[] args) throws InterruptedException {

		String ExpectedDay = "30";
		String ExpectedMonthYear = "July 2028";
		//String ExpectedYear = "2028";
		// String calendarMonth = "";
		// String calendarYear = "";

		// To launch Browser by using selection option
		Browser_Selection browser = new Browser_Selection();
		browser.Browser_Selection_Func();

		// WebDriver driver = obj.driver;
		WebDriver driver = Browser_Selection.driver; // Accessing in a static way

		driver.get("https://www.redbus.in/");

		driver.manage().window().maximize();
		Thread.sleep(3000);
		WebElement datepicker = driver.findElement(By.xpath("//div[@class='dateInputWrapper___874b87 dateHighlight___4d1fef']"));
		datepicker.click();
		System.out.println("📅 Opened date picker");

		while (true) {
			String calendarMonthYear = driver.findElement(By.className("monthYear___a4142d")).getText();
			//String calendarMonthYear = driver.findElement(By.xpath("//div[contains(@class,'DayPicker-Caption')]")).getText();
			System.out.println(calendarMonthYear);
			
			if (calendarMonthYear.equals(ExpectedMonthYear)) {

				List<WebElement> daysList = driver.findElements(
			            By.xpath(
			                    "//div[contains(@class,'DayPicker-Day') and " +
			                    "not(contains(@class,'DayPicker-Day--disabled')) and " +
			                    "not(contains(@class,'DayPicker-Day--outside'))]"
			                ));
				for (WebElement e : daysList) {
					String calendarDay = e.getText();
					if (calendarDay.equals(ExpectedDay)) {
						e.click();
						break;
					}
				}
				
				break;
			}
			
			else {
				driver.findElement(By.className("icon icon-arrow arrow___9f2561 right___a1e77a ")).click();
			}
			
		}

	}

}
