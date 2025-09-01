package Project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;


public class HandleSSLErrors {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		WebDriverManager.chromedriver().setup(); // From WebDriverManager 
		
		//Global Profile
		DesiredCapabilities dc = new DesiredCapabilities();
		/* Method 1 */
		//dc.setAcceptInsecureCerts(true);
		/* Method 2 */
		dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		
		ChromeOptions copt = new ChromeOptions();
		copt.merge(dc);
		
		WebDriver driver = new ChromeDriver(copt);  
	
		
		driver.get("https://expired.badssl.com/");
		driver.manage().window().maximize();
		
		Thread.sleep(2000);
		
		driver.close();
					
	}

}
