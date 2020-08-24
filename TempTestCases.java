package testCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import FrameworkBase.BaseClass;
import pageobjects.LogicalMethods;
import Utility.RESTAPIUtils;

public class TempTestCases extends BaseClass{
	
	RESTAPIUtils util= new RESTAPIUtils();
	
	/* Testcase for phase 1
	 * Steps:
	 * 1. Go to NDTV and click Weather
	 * 2. In the dropdown, enter city and select
	 * 3. Store the temp info from map
	 * 4. Verify Alert should come once the city is clicked
	 */
	@Test(priority=1 , description="Store the weather for the city")
	public void getWeatherDetailsFromUI() throws Exception {
		LogicalMethods nd= PageFactory.initElements(driver,LogicalMethods.class);
		nd.clickMenu();
		nd.captureWeather("Ahmedabad");
		nd.setTemp("Ahmedabad");
		nd.verifyAlert("Ahmedabad");
	}
	
	/* Testcase for phase 2
	 * Steps:
	 * 1. Go to API and provide city and app id
	 * 2. Store the temp
	 */
	@Test(priority=2 , description="GET the weather details from API")
	public void getWeatherInfoFromAPI() throws Exception {
		util.checkWeather("Bangalore","7fe67bf08c80ded756e598d6f8fedaea");
		
	}
	
	/* Testcase for phase 3
	 * Steps:
	 * 1. Collect weather info for a city from NDTV.com UI
	 * 2. Collect info for a city from API
	 * 3. Compare the difference in both temp , if within range (values given in config.properties) , assert result
	 */
	
	@Test(priority=3, description="Compare the temperature stored from UI and API")
	public void compareTempFromUIAndAPI() throws Exception{
		LogicalMethods nd= PageFactory.initElements(driver,LogicalMethods.class);
		nd.clickMenu();
		nd.captureWeather("Ahmedabad");
		nd.varianceLogic("Ahmedabad","Bangalore","7fe67bf08c80ded756e598d6f8fedaea");
	}
	
	
	/* Negative testcase for passing incorrect city
	 * Steps:
	 * 1. Go to API and provide city and app id
	 * 2. Fetch the error message
	 */
	@Test(priority=4, description="Incorrect city name should not return the result from API")
	public void wrongCityInfoInAPI() throws Exception{
		util.verifyStatusCode("Ahmed","7fe67bf08c80ded756e598d6f8fedaea");
	}
	
}
