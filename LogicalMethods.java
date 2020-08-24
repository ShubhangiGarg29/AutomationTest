package pageobjects;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import FrameworkBase.BaseClass;
import Utility.RESTAPIUtils;

public class LogicalMethods extends BaseClass{
	
	WebDriver driver;
	public LogicalMethods(WebDriver ldriver) {
	    this.driver = ldriver;
	}
	public WebDriverWait wait = null;
	public final Long IMPLICITWAIT_INSECONDS = (long) 20; 
	SoftAssert softassert= new SoftAssert();
	
	RESTAPIUtils util=new RESTAPIUtils();
	
	@FindBy(xpath="//a[@id='h_sub_menu']")
	 WebElement click_Link;
	
	@FindBy(xpath="//a[contains(text(),'WEATHER')]")
	 WebElement Weather_xpath;
	
	@FindBy(xpath="//input[@id='searchBox']")
	 WebElement searchBox;
	
	@FindBy(xpath="//div[contains(text(),'Bengaluru')]")
	 WebElement cityOnMap_xpath;
	
	@FindBy(xpath="//*[@id='map_canvas']/div[1]/div[4]/div[12]/div/div[1]/span[1]")
	 WebElement CelsiusWeather_xpath;
	
	@FindBy(xpath="//div[11]//div[1]//div[1]//span[2]")
	 WebElement FarenheitWeather_xpath;
	
	@FindBy(xpath="//input[@type='checkbox']")
	List<WebElement> checkbox;
	
	@FindBy(xpath="//div[@class='cityText']")
	List<WebElement> city_xpath;
	
	@FindBy(xpath="//b[starts-with(text(),'Condition : ')]")
	WebElement popup_xpath;
	
	 String Celsiusweather="";
	 
//Method to get the Temp for the particular city	
	public double setTemp(String city)
	{ double UItemp=0.0;
		for(int i=0;i<city_xpath.size();i++)
		{
			boolean flag=false;
			WebElement select_city=city_xpath.get(i);
			String value=select_city.getText();
			if(value.equalsIgnoreCase(city))
			{
				flag=true;
				Celsiusweather=CelsiusWeather_xpath.getText();
				System.out.println(Celsiusweather);
				
				//Storing the temp after removing ℃ for comparison
				Celsiusweather = Celsiusweather.replaceAll("[^\\d.]", "");
				UItemp = Double.parseDouble(Celsiusweather);
				softassert.assertEquals(flag,true);
			}
			}
		softassert.assertAll();
		return UItemp;
	}
	
//Method to verify that the alert should come once clicked on the city	
	public void verifyAlert(String city) throws InterruptedException
	{
		for(int i=0;i<city_xpath.size();i++)
		{
			boolean flag=false;
			WebElement select_city=city_xpath.get(i);
			String value=select_city.getText();
			if(value.equalsIgnoreCase(city)){
				select_city.click();
				Thread.sleep(1000);
				if(isElementDisplayed(popup_xpath))
				{
					flag=true;
					softassert.assertEquals(flag,true);
				}
				softassert.assertAll();
			}
		}
	}
//Method to select the city passed for which temp needs to be captured
	public void selectCity(String city)
	{
	for(int i=0;i<checkbox.size();i++)
	{
		WebElement select_city=checkbox.get(i);
		String value=select_city.getAttribute("id");
		if(value.equalsIgnoreCase(city))
		{
			select_city.click();
		}
		
	}
	}
	 
//Wrapper method which returns boolean value for verifying the element displayed	 
	  boolean isElementDisplayed(WebElement webElement){

         try{
             webElement.isDisplayed();
         }
         catch(NoSuchElementException e)
         {
             return false;
         }

         return true;
     }
	  
//Method to go inside the map URL from ndtv.com	
	  public void clickMenu()
	  {			
	 click_Link.click();
	 Weather_xpath.click();
	  }

//Method to add the city name in dropdown and select 
	  public void captureWeather(String city) throws InterruptedException
	  	{
	Thread.sleep(1000);
	searchBox.sendKeys(city);
	Thread.sleep(1000);
	selectCity(city);
	driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  	}


//Method to get Temp from both UI and API ,compute the difference and if its in range (range is coming from
// properties file) , the test case pases
	  public void varianceLogic(String city1,String city2,String appId) throws Exception
	  {
		  double weather1 = 0.0 , weather2 = 0.0, diff = 0.0;
		  boolean flag=false;
		  //Loading the properties file 
		  Properties prop=new Properties();
		  File file= new File(System.getProperty("user.dir")+"/config.properties");
		  FileReader reader = new FileReader(file);
		  prop.load(reader);
		  //Fetching data from properties file
		  String r1=prop.getProperty("Range1");
		  String r2=prop.getProperty("Range2");
		  //Parsing the string data to int
		  
		  double range1=Double.parseDouble(r1);
		  double range2=Double.parseDouble(r2);
		  
		  weather1=setTemp(city1);
		  weather2=util.checkWeather(city2,appId);
		  //Printing the weather values on console
		  System.out.println("weather 1:"+weather1);	  
		  System.out.println("weather 2:"+weather2);
		  
		  diff=weather1-weather2;
		  System.out.println("Difference:"+diff);
		  	if(diff>range1 && diff<range2)
		  	{
			flag=true;
			softassert.assertEquals(flag,true,"Assertion passed");
			System.out.println("Variance is inside the range");
		  	}
		  	else
		  	{
			flag=false;
			softassert.assertEquals(flag,true,"Assertion failed");
			System.out.println("Variance is not inside the range");
		  	}
		  softassert.assertAll();
	  		}	
		}	
