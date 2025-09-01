package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MercuryTours_Test {
	
	//Variables Declaration
	//Flight Details page Objects
	public static WebElement o_num_pass_list;
	public static WebElement o_departing_from_list;
	public static WebElement o_departing_date_month_list;
	public static WebElement o_departing_date_date_list;
	public static WebElement o_arriving_in_list;
	public static WebElement o_returning_list;
	public static WebElement o_airline_list;
	
	//Input Values in String
	public static String s_pass_count;
	public static String s_departing_from_list;
	public static String s_returning_date_date_list;
	public static String s_departing_date_date_list;
	public static String s_departing_date_month_list;
	public static String s_arriving_in_list;
	public static String s_returning_date_month_list;
	public static String s_airline_list;
	
	public static void captureScreenshot(WebDriver driver) throws IOException {

		Date currentdate = new Date();
		String ScreenshotFileName = "MercuryTours_Test_"+ currentdate.toString().replace(" ", "_").replace(":", "_");

		File ScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ScreenshotFile, new File(".//target/Screenshots//" + ScreenshotFileName + ".png"));
	}
	
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		//Launching Browser by using selection option
		Browser_Selection obj = new Browser_Selection();
		obj.Browser_Selection_Func();
		//WebDriver driver = obj.driver; 
		WebDriver driver = Browser_Selection.driver; //Accessing in a static way
		
		
		/* Read username & password from Properties File */
		Properties properties = new Properties();
		//Path for Config File - MercuryTours.properties
		FileInputStream inputstream = new FileInputStream(".\\src\\test\\resources\\properties\\MercuryTours.properties");
		properties.load(inputstream);
		
		String testURL = properties.getProperty("testURL");
		driver.get(testURL);
		driver.manage().window().maximize();
	
		System.out.println("Page Title: " + driver.getTitle()); /* Get Current Page Title */
		System.out.println("Page URL: " +driver.getCurrentUrl()); /* Get Current Page URL */
		
		String username = properties.getProperty("username");
		String password = properties.getProperty("password"); //
		
		driver.findElement(By.name("userName")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		captureScreenshot(driver);
		driver.findElement(By.name("submit")).click();
		
		Thread.sleep(2000);
		
		String Actual_PageTitle = driver.getTitle(); 
		String Login_PageTitle = "Login: Mercury Tours";
		
		if(Actual_PageTitle.equals(Login_PageTitle)==true) {
			System.out.println("Logged in Succesfully");
			captureScreenshot(driver);
			System.out.println("Page Title: " + Actual_PageTitle);
			System.out.println("Page URL: " +driver.getCurrentUrl());
			
		}else {
			System.out.println("ERROR: Login Failed!");
			captureScreenshot(driver);
			System.out.println("Page Title: " + Actual_PageTitle);
			System.out.println("Page URL: " +driver.getCurrentUrl());
			driver.close();
		}
		
		driver.findElement(By.linkText("Flights")).click();
		
		Thread.sleep(300);
		
		//Clicking on Radio button in Flight type using XPath 
		driver.findElement(By.xpath("//input[@name='tripType' and @value='oneway']")).click();
		//Correct way of Selecting values in Select. Gives you more control
		Select o_num_pass_list =  new Select(driver.findElement(By.name("passCount")));
		Select o_departing_from_list =  new Select(driver.findElement(By.name("fromPort")));
		Select o_departing_date_month_list = new Select(driver.findElement(By.name("fromMonth")));
		Select o_departing_date_date_list = new Select(driver.findElement(By.name("fromDay")));
		Select o_arriving_in_list = new Select(driver.findElement(By.name("toPort")));
		Select o_returning_date_month_list = new Select(driver.findElement(By.name("toMonth")));
		Select o_returning_date_date_list = new Select(driver.findElement(By.name("toDay")));
		//Clicking on Radio button in Service Class type using XPath 
		driver.findElement(By.xpath("//input[@name='servClass' and @value='Business']")).click();
		Select o_airline_list = new Select(driver.findElement(By.name("airline")));
		
		//<------------------ Set Values to be entered in the webelements -------------------->
		s_pass_count = "2";
		s_departing_from_list = "London";
		s_departing_date_month_list = "5";  // representing May as value of May is 5 in html
		s_departing_date_date_list = "7";
		s_arriving_in_list = "Sydney";
		s_returning_date_month_list = "10"; // representing October as value of October is 10 in html
		s_returning_date_date_list = "21";
		s_airline_list = "Unified Airlines";
		
		//**************Perform operation based on the values mentioned above*************************************
		o_num_pass_list.selectByValue(s_pass_count);
		o_departing_from_list.selectByValue(s_departing_from_list);
		o_departing_date_month_list.selectByValue(s_departing_date_month_list);
		o_departing_date_date_list.selectByValue(s_departing_date_date_list);
		o_arriving_in_list.selectByValue(s_arriving_in_list);
		o_returning_date_month_list.selectByValue(s_returning_date_month_list);
		o_returning_date_date_list.selectByValue(s_returning_date_date_list);
		o_airline_list.selectByVisibleText(s_airline_list);
				
		captureScreenshot(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement findFlights = wait.until(ExpectedConditions.elementToBeClickable(By.name("findFlights")));
		findFlights.click();
		
		
		//Wait for Next page to appear
		Thread.sleep(300);
		System.out.println("Searching for Flights...");
		System.out.println("Page Title: " + driver.getTitle()); /* Get Current Page Title */
		System.out.println("Page URL: " +driver.getCurrentUrl()); /* Get Current Page URL */
		
		captureScreenshot(driver);
		String result_text = driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td/p/font/b/font[1]")).getText();
		System.out.println("Search Result: "+result_text);
		
		System.out.println("### Test Completed ###");
		System.out.println("Please Find the Screenshots for the executed test scenario from Path: target/Screenshots/ ");
		
		driver.close();
		
				
	}

}
