package com.testing.demo.sample;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

	public class TestGoogle {

		WebDriver driver;
		List<WebElement> searchResults;
		
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
		
		
		@Parameters({"searchtext"})
		@Test
		public void testParameterWithXML(String searchtext) throws InterruptedException{
			driver.get("http://google.co.in/");
			int counter=1;
			Google google = PageFactory.initElements(driver, Google.class);
			google.enterValuesInSearchTextbox(searchtext);
			google.clickOnGoogleSearchButton();
			
			//Will search www.payjo.co till 10 google results pages
			outerloop:
			for(int i=1;i<=10;i++) {
			counter=1;	
			searchResults = google.getSearchResults();
			for(WebElement wb: searchResults) {
				if((wb.getText().replace("https://","").replace("/","").equalsIgnoreCase(searchtext)) && (counter==1)) {
					System.out.println(searchtext +"appears FIRST in Google search.");
					break outerloop;
				} else if((wb.getText().replace("https://","").replace("/","").equalsIgnoreCase(searchtext))){
					System.out.println(searchtext +" found on "+counter+"in search list on "+i+ " search page");
					break outerloop;
				}
				counter++;
				
			}
			
		}
	}

		
		@AfterTest
		public void tearDown() {
			if(driver!=null) {
				System.out.println("Closing browser");
				driver.quit();
			}
		}
}