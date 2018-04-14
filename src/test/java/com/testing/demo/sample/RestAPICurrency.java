package com.testing.demo.sample;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAPICurrency {

	@Test
	public void testGetFindwithId() {
		String respName, respCapital, respRegion;
		int respPop;
		List<String> respCurr = new ArrayList<String>();
		
		List<String> name = new ArrayList<String>(Arrays.asList("Bhutan","India","Zimbabwe"));
		List<String> capital = new ArrayList<String>(Arrays.asList("Thimphu","New Delhi","Harare"));
		List<String> region = new ArrayList<String>(Arrays.asList("Asia","Asia","Africa"));
		List<String> currencies = new ArrayList<String>(Arrays.asList("Bhutanese ngultrum","Indian rupee","South African rand"));
		List<String> population = new ArrayList<String>(Arrays.asList("775620","1295210000","14240168"));
		
		RestAssured.baseURI = "http://restcountries.eu/rest/v2/currency";
		 
		Response response = given()
		.accept(ContentType.JSON)
		.when()
		.get("/inr")
		.thenReturn();
		
		Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
		JSONArray jsonResponse =new JSONArray(response.asString());
		
		// Response has 3 countries so checking information for all 
		for(int i=0;i<jsonResponse.length();i++) {
			respName = jsonResponse.getJSONObject(i).getString("name");
			Assert.assertEquals(respName, name.get(i));
			
			respCapital = jsonResponse.getJSONObject(i).getString("capital");
			Assert.assertEquals(respCapital, capital.get(i));
			
			respRegion = jsonResponse.getJSONObject(i).getString("region");
			Assert.assertEquals(respRegion, region.get(i));
			
			respPop = jsonResponse.getJSONObject(i).getInt("population");
			Assert.assertEquals(String.valueOf(respPop), population.get(i));
			
			JSONArray curr= jsonResponse.getJSONObject(i).getJSONArray("currencies");
			respCurr.clear();
			for(int j=0;j<curr.length();j++) {
				if(!(curr.getJSONObject(j).isNull("name"))) {
					respCurr.add(curr.getJSONObject(j).getString("name"));
				}
				
			}
			Assert.assertTrue(respCurr.contains(currencies.get(i)));
		}
		
		
		
	}
	
	
}
