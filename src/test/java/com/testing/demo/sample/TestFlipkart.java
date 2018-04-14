package com.testing.demo.sample;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestFlipkart {

	
	public static WebDriver driver;
	
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception{
		//Check if parameter passed from TestNG is 'firefox'
		if(browser.equalsIgnoreCase("firefox")){
			String str = System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\geckodriver.exe";
			System.setProperty("webdriver.firefox.marionette", str);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		//Check if parameter passed as 'chrome'
		else if(browser.equalsIgnoreCase("chrome")){
			String str = System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver",str);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		//Check if parameter passed as 'Edge'
				else if(browser.equalsIgnoreCase("Edge")){
					String str = System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\MicrosoftWebDriver.exe";
					System.setProperty("webdriver.edge.driver",str);
					driver = new EdgeDriver();
					driver.manage().window().maximize();
				}
		else{
			//If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
	@Parameters({"username", "password", "mobilename"})
	@Test
	public void testFlipkart(String username, String password, String mobilename) throws InterruptedException{
		
    	Flipkart testPageObjects= PageFactory.initElements(driver, Flipkart.class);  	
    	testPageObjects.getURL("http://flipkart.com/");
    	
    	testPageObjects.login(username, password);
    	testPageObjects.searchMobile(mobilename);
    	
    	testPageObjects.addToCart();
    	Assert.assertTrue(testPageObjects.checkCartItem(mobilename));
    	testPageObjects.logout();
    	
    }

	@AfterTest
	public void tearDown() {
		if(driver!=null) {
			System.out.println("Closing browser");
			driver.quit();
		}
	}

}
