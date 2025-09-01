package Project;

import java.util.Scanner;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;


import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser_Selection {
	
	public static WebDriver driver = null;

	public void Browser_Selection_Func() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Browser Selection: Enter 1 for Firefox, 2 for Chrome, 3 for Edge.");
		System.out.println("Enter your Option:");
		String browser = sc.nextLine();

		if(browser.equals("1")) { 	
			//Firefox
			//System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");  (OR)
			//WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			System.out.println("Firefox Browser is Not available!! Please select Chrome or Edge Browser. Exiting...");
		}
		else if(browser.equals("2")) { 
			//Chrome
			//System.setProperty("webdriver.chrome.driver", "<Path of chromedriver.exe>");
			WebDriverManager.chromedriver().setup(); /* From WebDriverManager */
			driver = new ChromeDriver();
			System.out.println("Chrome Browser Selected.");
		}
		else if(browser.equals("3")) {
			//Edge
			//System.setProperty("webdriver.edge.driver", "./drivers/edgedriver.exe");
			WebDriverManager.edgedriver().setup(); /* From WebDriverManager */
			driver = new EdgeDriver();
			System.out.println("Edge Browser Selected.");
		}
		else {
			System.out.println("ERROR!! Invalid Option");
		}
		
		sc.close();
		
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		
		Browser_Selection obj = new Browser_Selection();
		obj.Browser_Selection_Func();
		Thread.sleep(3000);
		driver.close();
		
	}

}
