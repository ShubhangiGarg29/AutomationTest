package FrameworkBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
	
	public WebDriver driver;
	
	
	//Method to start the Browser
	@BeforeMethod
	 public void setUp(){
		 driver= BrowserFactory.startBrowser(driver, "Chrome");
	 }
	 
	
	//Method to quit the browser after executing test
	 @AfterMethod
	 public void quit(){
		 BrowserFactory.tearDown(driver);
	 }

}
