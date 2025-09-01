package Project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class ReadWriteData {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		Properties properties = new Properties();
		
		FileInputStream inputstream = new FileInputStream(".\\src\\test\\resources\\properties\\testData.properties");
		properties.load(inputstream);
		
		String browser = properties.getProperty("browser");
		System.out.println(browser);
		
		String url = properties.getProperty("URL");
		System.out.println(url);
		
		FileOutputStream outputstream = new FileOutputStream(".\\src\\test\\resources\\properties\\testData.properties");
		
		properties.setProperty("Contact", "678349");
		properties.store(outputstream, "This is Mock Data");
		
		
					
	}

}
