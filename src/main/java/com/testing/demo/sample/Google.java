package com.testing.demo.sample;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Google {
	private WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(css = "#lst-ib[title='Search'][type='text']")
	private WebElement seachTextbox;
	
	@FindBy(css = "input[name=btnK]")
	private WebElement seachButton;
	
	@FindBys(@FindBy(xpath = "//cite[@class]"))
	private List<WebElement> searchResults;
	
	@FindBy(xpath = "//cite[@class]")
	private WebElement searchResult;
	
	
	public Google(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 20);
	}
	

	public List<WebElement> getSearchResults() {
	      return searchResults;
	    } 
	
	public void enterValuesInSearchTextbox(String searchText) {
		wait.until(ExpectedConditions.visibilityOf(seachTextbox));
		seachTextbox.sendKeys(searchText);
		//seachTextbox.sendKeys(Keys.ENTER);
	}
	
	public void clickOnGoogleSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(seachButton));
		seachButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(searchResult));
	}
	
	
}
