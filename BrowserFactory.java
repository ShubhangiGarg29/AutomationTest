package FrameworkBase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {
	
	//Method to start the browser based on browserName
	public static WebDriver startBrowser(WebDriver driver, String browserName)
	{
		
		
		if(browserName.equals("Chrome"))
		{
			driver=new ChromeDriver();
			
		}
		
		else if(browserName.equals("Firefox"))
		{
			driver=new FirefoxDriver();
		}
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.get("https://ndtv.com");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
		return driver;
	}
	
	//Method to quit the browser
	public static void tearDown(WebDriver driver)
	{
		driver.quit();
	}    

}
