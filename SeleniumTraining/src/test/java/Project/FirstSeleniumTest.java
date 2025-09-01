package Project;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirstSeleniumTest {
	
	WebDriver driver;
	
	public void launch_ChromeBrowser() {
		
		/* Two ways of using WebDrivers: 
		 1. Downloaded Webdriver - Provide the path of webdriver in local.
		 2. Using WebDriverManager - via Maven
		 */
		
		//System.setProperty("webdriver.chrome.driver", "<Path of chromedriver.exe>");
		
		WebDriverManager.chromedriver().setup(); /* From WebDriverManager */
		
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		
	}
	
	public void fetchPageDetails() {
		System.out.println("Page Title: " + driver.getTitle());
		System.out.println("Page URL: " +driver.getCurrentUrl());
	}
	
	public void closeBrowser(){
		System.out.println("Closing...");
		driver.close();
	}
	public void navigate1(){
		driver.navigate().to("https://www.flipkart.com/");
		System.out.println("Navigating...");
		
	}
	
	public void navigate2(){
		//To navigate to previous page
		System.out.println("Navigating to previous page...");
		driver.navigate().back();
	}
	
	public void navigate3(){
			//To navigate to next page
			System.out.println("Navigating to next page...");
			driver.navigate().forward();
	}
	
	public void refresh(){
		//Refresh current web page
		driver.navigate().refresh();
	}

	public static void main(String[] args) throws InterruptedException {
		FirstSeleniumTest obj = new FirstSeleniumTest();
		obj.launch_ChromeBrowser();
		obj.fetchPageDetails();
		Thread.sleep(2000);
		obj.navigate1();
		obj.fetchPageDetails();
		Thread.sleep(2000);
		obj.navigate2();
		obj.fetchPageDetails();
		Thread.sleep(2000);
		obj.navigate3();
		obj.fetchPageDetails();
		Thread.sleep(2000);
		obj.refresh();
		obj.closeBrowser();

	}

}
