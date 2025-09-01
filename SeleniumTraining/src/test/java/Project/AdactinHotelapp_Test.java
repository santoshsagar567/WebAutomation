package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AdactinHotelapp_Test {
	
	
	//Variables Declaration
			//Hotel Details page Objects
			public static WebElement o_Location;
			public static WebElement o_Hotels ;
			public static WebElement o_Room_Type;
			public static WebElement o_Number_of_Rooms;
			public static WebElement o_Check_In_Date;
			public static WebElement o_Check_Out_Date;
			public static WebElement o_Adults_per_Room;
			public static WebElement o_Children_per_Room;
			public static WebElement o_First_Name;
			public static WebElement o_Last_Name;
			public static WebElement o_Billing_Address;
			public static WebElement o_Credit_Card_No;
			public static WebElement o_Credit_Card_Type;
			public static WebElement o_Expiry_Date_month;
			public static WebElement o_Expiry_Date_year;
			public static WebElement o_CVV_Number;
			public static WebElement o_Order_No;
			
			//Input Values in String
			public static String s_Location;
			public static String s_Hotels;
			public static String s_Room_Type;
			public static String s_Number_of_Rooms;
			public static String s_Check_In_Date;
			public static String s_Check_Out_Date;
			public static String s_Adults_per_Room;
			public static String s_Children_per_Room;	
			public static String s_First_Name;
			public static String s_Last_Name;
			public static String s_Billing_Address;
			public static String s_Credit_Card_No;
			public static String s_Credit_Card_Type;
			public static String s_Expiry_Date_month;
			public static String s_Expiry_Date_year;
			public static String s_CVV_Number;
			public static String s_Order_No;
	
	
	public static void captureScreenshot(WebDriver driver) throws IOException {
		
		Date currentdate = new Date();
		String ScreenshotFileName = "AdactinHotelapp_Test_"+currentdate.toString().replace(" ", "_").replace(":", "_");
		
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
		//Path for Config File - AdactinHotelapp.properties
		FileInputStream inputstream = new FileInputStream(".\\src\\test\\resources\\properties\\AdactinHotelapp.properties");
		properties.load(inputstream);
			
		String testURL = properties.getProperty("testURL");
		driver.get(testURL);
		driver.manage().window().maximize();
		
		/* Hardcoded username & password 
		String username = "santoshsagar";
		String password = "Q67R5C"; */
		String username = properties.getProperty("username");
		String password = properties.getProperty("password"); //
		
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		
		
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		driver.findElement(By.id("login")).click();
		
		String Actual_PageTitle = driver.getTitle();
		String Login_PageTitle = "Adactin.com - Search Hotel";
		
		if(Actual_PageTitle.equals(Login_PageTitle)==true) {
			System.out.println("Logged in Succesfully");
			//captureScreenshot(driver);
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
		Select o_Location =  new Select(driver.findElement(By.name("location")));
		Select o_Hotels =  new Select(driver.findElement(By.name("hotels")));
		Select o_Room_Type = new Select(driver.findElement(By.name("room_type")));
		Select o_Number_of_Rooms = new Select(driver.findElement(By.name("room_nos")));
		o_Check_In_Date = driver.findElement(By.xpath("//input[@id='datepick_in']"));
		o_Check_Out_Date = driver.findElement(By.xpath("//input[@id='datepick_out']"));
		Select o_Adults_per_Room = new Select(driver.findElement(By.name("adult_room")));
		Select o_Children_per_Room = new Select(driver.findElement(By.name("child_room")));
		
		s_Location = properties.getProperty("Location");
		s_Hotels = properties.getProperty("Hotels");
		s_Room_Type = properties.getProperty("Room_Type");
		s_Number_of_Rooms = properties.getProperty("Number_of_Rooms");
		s_Check_In_Date = properties.getProperty("Check_In_Date");
		s_Check_Out_Date = properties.getProperty("Check_Out_Date");
		s_Adults_per_Room = properties.getProperty("Adults_per_Room");
		s_Children_per_Room = properties.getProperty("Children_per_Room");
		
		
		// **************Perform operation based on the values mentioned
		// above*************************************
		o_Location.selectByValue(s_Location);
		o_Hotels.selectByValue(s_Hotels);
		o_Room_Type.selectByValue(s_Room_Type);
		o_Number_of_Rooms.selectByValue(s_Number_of_Rooms);
		o_Check_In_Date.clear();
		o_Check_In_Date.sendKeys(s_Check_In_Date);
		o_Check_Out_Date.clear();
		o_Check_Out_Date.sendKeys(s_Check_Out_Date);
		o_Adults_per_Room.selectByValue(s_Adults_per_Room);
		o_Children_per_Room.selectByValue(s_Children_per_Room);

		captureScreenshot(driver);
		//Clicking on Search button using XPath 
		driver.findElement(By.xpath("//input[@id='Submit']")).click();
		System.out.println("Searched for Hotel");
		
		//Wait for Next page to appear
		Thread.sleep(2000);
		//Clicking on Radio button in Service Class type using XPath 
		driver.findElement(By.xpath("//input[@name='radiobutton_0']")).click();
		captureScreenshot(driver);
		fetchPageDetails(driver);
		//Clicking on continue button using XPath 
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		System.out.println("Hotel Selected");
		
		// Wait for Next page to appear
		Thread.sleep(1000);
		captureScreenshot(driver);
		fetchPageDetails(driver);
		
		
		o_First_Name = driver.findElement(By.xpath("//input[@id='first_name']"));
		o_Last_Name = driver.findElement(By.xpath("//input[@id='last_name']"));
		o_Billing_Address = driver.findElement(By.xpath("//textarea[@id='address']")); 
		o_Credit_Card_No = driver.findElement(By.xpath("//input[@id='cc_num']"));
		Select o_Credit_Card_Type = new Select(driver.findElement(By.name("cc_type")));
		Select o_Expiry_Date_month = new Select(driver.findElement(By.name("cc_exp_month")));
		Select o_Expiry_Date_year = new Select(driver.findElement(By.name("cc_exp_year")));
		o_CVV_Number = driver.findElement(By.xpath("//input[@id='cc_cvv']"));
		
		s_First_Name = properties.getProperty("First_Name");
		s_Last_Name = properties.getProperty("Last_Name");
		s_Billing_Address = properties.getProperty("Billing_Address");
		s_Credit_Card_No = properties.getProperty("Credit_Card_No");
		s_Credit_Card_Type = properties.getProperty("Credit_Card_Type");
		s_Expiry_Date_month = properties.getProperty("Expiry_Date_month");
		s_Expiry_Date_year = properties.getProperty("Expiry_Date_year");
		s_CVV_Number = properties.getProperty("CVV_Number");
		
		o_First_Name.sendKeys(s_First_Name);
		o_Last_Name.sendKeys(s_Last_Name);
		o_Billing_Address.sendKeys(s_Billing_Address);
		o_Credit_Card_No.sendKeys(s_Credit_Card_No);
		o_Credit_Card_Type.selectByValue(s_Credit_Card_Type);
		o_Expiry_Date_month.selectByValue(s_Expiry_Date_month);
		o_Expiry_Date_year.selectByValue(s_Expiry_Date_year);
		o_CVV_Number.sendKeys(s_CVV_Number);
		
		System.out.println("User Name, Address & Card Details Entered");
		//fetchPageDetails(driver);
		//Clicking on Book Now button using XPath 
		driver.findElement(By.xpath("//input[@id='book_now']")).click();
		captureScreenshot(driver);
		System.out.println("Booking In Progress.......");
		
		Thread.sleep(5000);
		//To perform Scroll on WebPage using Selenium
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");
		
		captureScreenshot(driver);
		String Actual_PageTitle2 = driver.getTitle();
		String BookConfirmation_PageTitle = "Adactin.com - Hotel Booking Confirmation";
		if(Actual_PageTitle2.equals(BookConfirmation_PageTitle)==true) {
			System.out.println("Booked Succesfully!!");
			
			o_Order_No = driver.findElement(By.xpath("//input[@id='order_no']")); //To Locate the *Order No* Web Element
			s_Order_No = o_Order_No.getAttribute("value"); //To get the data of the Order No from its value attribute
			System.out.println("Order No : " +s_Order_No); //To Print the Order No, once Booking completes Succesfully
			
			
			fetchPageDetails(driver);
			
		}else {
			System.out.println("ERROR: Booking Failed!");
			
			fetchPageDetails(driver);
			driver.close();
		}
		
		System.out.println("### Test Completed ###");
		System.out.println("Please Find the Screenshots for the executed test scenario from Path: target/Screenshots/ ");
		driver.close(); 
					
	}

}
