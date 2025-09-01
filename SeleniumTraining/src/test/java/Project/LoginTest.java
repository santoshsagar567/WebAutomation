package Project;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		/*To launch Chrome Browser by Default
		System.setProperty("webdriver.chrome.driver", "<Path of chromedriver.exe>"); ( or )
		WebDriverManager.chromedriver().setup(); // From WebDriverManager 
		WebDriver driver = new ChromeDriver(); */
		
		//To launch Browser by using selection option
		Browser_Selection obj = new Browser_Selection();
		obj.Browser_Selection_Func();
		//WebDriver driver = obj.driver; 
		WebDriver driver = Browser_Selection.driver; //Accessing in a static way
		
		driver.get("https://demo.guru99.com/test/newtours/");
		driver.manage().window().maximize();
		
		System.out.println("Page Title: " + driver.getTitle()); /* Get Current Page Title */
		System.out.println("Page URL: " +driver.getCurrentUrl()); /* Get Current Page URL */
		
		driver.findElement(By.name("userName")).sendKeys("IMTEST");
		driver.findElement(By.name("password")).sendKeys("Test@12345");
		driver.findElement(By.name("submit")).click();
		
		Thread.sleep(2000);
		
		String Actual_PageTitle = driver.getTitle(); 
		String Expected_PageTitle = "Login: Mercury Tours";
		
		if(Expected_PageTitle.equals(Actual_PageTitle)==true) {
			System.out.println("Test is Passed.");
		}else {
			System.out.println("Test is Failed.");
		}
		
		System.out.println("Page Title: " + Actual_PageTitle);
		System.out.println("Page URL: " +driver.getCurrentUrl());
		
		driver.close();
					
	}

}
