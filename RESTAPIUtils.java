package Utility;


import org.testng.asserts.SoftAssert;

import FrameworkBase.BaseClass;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


public class RESTAPIUtils extends BaseClass{

	public double checkWeather(String city, String key) throws Exception {  
	
	Response response = given()
             			.when()
             			.get("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key)
             			.thenReturn();
             				
		//Storing temp from API Call
	String temp = response.jsonPath().getString("main.temp");
		System.out.println(temp);
		//Convert the temp from Kelvin to Float and return the value
		double currenttemp = Double.parseDouble(temp);  
		double celsius = currenttemp - 273.15;
    return celsius;
	}

	public void verifyStatusCode(String city, String key) throws Exception {  
		boolean flag=false;
		SoftAssert softassert= new SoftAssert();
		
		Response response = given()
	             			.when()
	             			.get("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key)
	             			.thenReturn();
	             			
	    if(response.statusCode()==200)
	    {	
			//Negative test to check if the status code is 200
			flag=false;
			softassert.assertEquals(flag,true);	
			}
	    else
	    	{
	    	flag=true;
	    	softassert.assertEquals(flag,true);	
	    	System.out.println("Error received:"+ response.jsonPath().getString("message") );
	    	}
	    softassert.assertAll();
		}

	
}
