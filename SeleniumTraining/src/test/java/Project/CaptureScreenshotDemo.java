package Project;

import java.io.File;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshotDemo {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
		//To launch Browser by using selection option
		Browser_Selection obj = new Browser_Selection();
		obj.Browser_Selection_Func();
		//WebDriver driver = obj.driver; 
		WebDriver driver = Browser_Selection.driver; //Accessing in a static way
				
		driver.get("https://adactinhotelapp.com/index.php");
		driver.manage().window().maximize();
		
		Date currentdate = new Date();
		//System.out.println(currentdate);
		
		String ScreenshotFileName = currentdate.toString().replace(" ", "_").replace(":", "_");
		//System.out.println(ScreenshotFileName);
		
		File ScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ScreenshotFile, new File(".//Screenshot//" + ScreenshotFileName + ".png"));
		
		driver.close(); 
					
	}

}
