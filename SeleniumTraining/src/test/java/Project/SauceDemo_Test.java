package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemo_Test {
	
	
	//Variables Declaration
			//Hotel Details page Objects
			public static WebElement o_Product;
			public static WebElement o_Add_to_Cart ;
			public static WebElement o_Shopping_Cart;
			public static WebElement o_Checkout;
			public static WebElement o_First_Name;
			public static WebElement o_Last_Name;
			public static WebElement o_Postal_Code;
			public static WebElement o_Continue;
			public static WebElement o_Finish;
			public static WebElement o_Order_No;
			
			//Input Values in String
			public static String s_Product;
			public static String s_Add_to_Cart;
			public static String s_Shopping_Cart;	
			public static String s_First_Name;
			public static String s_Last_Name;
			public static String s_Postal_Code;
			public static String s_Order_No;
	
	
	public static void captureScreenshot(WebDriver driver) throws IOException {
		
		Date currentdate = new Date();
		String ScreenshotFileName = "SauceDemo_Test_"+currentdate.toString().replace(" ", "_").replace(":", "_");
		
		File ScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ScreenshotFile, new File(".//target/Screenshots//" + ScreenshotFileName + ".png"));
	}
	
	public static void fetchPageDetails(WebDriver driver) {
		System.out.println("Page Title: " + driver.getTitle());
		System.out.println("Page URL: " +driver.getCurrentUrl());
	}
	

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
		//To launch Browser by using selection option
		Browser_Selection obj = new Browser_Selection();
		obj.Browser_Selection_Func();
		//WebDriver driver = obj.driver; 
		WebDriver driver = Browser_Selection.driver; //Accessing in a static way
		
		/* Read testURL, username & password from Properties File */
		Properties properties = new Properties();
		//Path for Config File - SauceDemo.properties
		FileInputStream inputstream = new FileInputStream(".\\src\\test\\resources\\properties\\SauceDemo.properties");
		properties.load(inputstream);
			
		String testURL = properties.getProperty("testURL");
		driver.get(testURL);
		driver.manage().window().maximize();
		
		String username = properties.getProperty("username");
		String password = properties.getProperty("password"); //
		
		driver.findElement(By.id("user-name")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		
		
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		
		String Actual_PageTitle = driver.getTitle();
		String Login_PageTitle = "Swag Labs";
		
		if(Actual_PageTitle.equals(Login_PageTitle)==true) {
			System.out.println("Logged in Succesfully");
			captureScreenshot(driver);
			fetchPageDetails(driver);
			
		}else {
			System.out.println("ERROR: Login Failed!");
			captureScreenshot(driver);
			fetchPageDetails(driver);
			driver.close();
		}
		
		//Switch to Search Hotel Page
		String currentWindowHandle = driver.getWindowHandle();
		driver.switchTo().window(currentWindowHandle);
		
		//Correct way of Selecting values in Select. Gives you more control
		s_Product = properties.getProperty("Product");
		System.out.println("Searching for Required Product");
		
		
		//captureScreenshot(driver);
		fetchPageDetails(driver);
		
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Thread.sleep(3000);
		//driver.findElement(By.linkText("Sauce Labs Fleece Jacket")).click();
		o_Product = driver.findElement(By.linkText(s_Product));
		o_Product.click();
		captureScreenshot(driver);
		
		o_Add_to_Cart = driver.findElement(By.xpath("//button[@id='add-to-cart']"));
		o_Add_to_Cart.click();
		
		System.out.println("Product Added to Cart");
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		o_Shopping_Cart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		o_Shopping_Cart.click();
		System.out.println("Opening Shopping Cart");
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		o_Checkout = driver.findElement(By.xpath("//button[@id='checkout']"));
		o_Checkout.click();
		System.out.println("Proceed to Checkout");
		//captureScreenshot(driver);
		fetchPageDetails(driver);
		
		o_First_Name = driver.findElement(By.xpath("//input[@id='first-name']"));
		s_First_Name = properties.getProperty("First_Name");
		o_First_Name.sendKeys(s_First_Name);
		o_Last_Name = driver.findElement(By.xpath("//input[@id='last-name']"));
		s_Last_Name = properties.getProperty("Last_Name");
		o_Last_Name.sendKeys(s_Last_Name);
		o_Postal_Code = driver.findElement(By.xpath("//input[@id='postal-code']"));
		s_Postal_Code = properties.getProperty("Postal_Code");
		o_Postal_Code.sendKeys(s_Postal_Code);
		
		o_Continue = driver.findElement(By.xpath("//input[@id='continue']"));
		o_Continue.click();
		System.out.println("Proceed to Continue");
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		Thread.sleep(1000);
		o_Finish = driver.findElement(By.xpath("//button[@id='finish']"));
		o_Finish.click();
		
		String Actual_OrderConfirmationPageURL = driver.getCurrentUrl();
		String Expected_OrderConfirmationPageURL = "https://www.saucedemo.com/checkout-complete.html";
		
		if(Actual_OrderConfirmationPageURL.equals(Expected_OrderConfirmationPageURL)==true) {
			System.out.println("Order Placed Succesfully");
			
			
		}else {
			System.out.println("ERROR: Couldn't Place Order!");
			
		}
		
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		System.out.println("### Test Completed ###");
		System.out.println("Please Find the Screenshots for the executed test scenario from Path: target/Screenshots/ ");
		
		driver.close(); 
					
	}

}
