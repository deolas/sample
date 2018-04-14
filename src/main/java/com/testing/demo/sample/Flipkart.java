package com.testing.demo.sample;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Flipkart {
	static WebDriver driver;
	Actions action;
	WebDriverWait wait;

	public Flipkart(WebDriver driver) {
		this.driver = driver;
		action=new Actions(driver);
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}
	
	//@FindBy(xpath = "//span[text()='Enter Email/Mobile number']")
	@FindBy(css = "input._2zrpKA")
	private WebElement userName;
	
	//@FindBy(xpath = "//span[text()='Enter Password']")
	@FindBy(css = "input[type='Password']")
	private WebElement password;
	
	@FindBy(xpath = "//button/span[text()='Login']")
	private WebElement loginBtn;
	
	@FindBy(xpath = "//input[@type='text'][contains(@title,'Search for products')]")
	private WebElement searchField;
	
	@FindBy(xpath = "//div/div[@class='_3wU53n']")
	private WebElement searchResult;
	
	@FindBy(xpath = "//button[text()='ADD TO CART']")
	private WebElement btnAddToCart;
	
	@FindBy(xpath = "//button//span[text()='Continue shopping']")
	private WebElement btnContinueShopping;
	
	@FindBy(xpath = "//span[text()='Cart']")
	private WebElement cartLink;
	
	@FindBy(xpath = "//div[@class='_1Ox9a7']")
	private WebElement cartItem;
	
	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountLink;
	
	@FindBy(xpath = "//div[text()='My Profile']")
	private WebElement myProfile;
	
	@FindBy(xpath = "//*[text()='Logout']")
	private WebElement logoutLink;
	
	@FindBy(xpath = "//span[text()='Remove']")
	private WebElement removeAddedItem;
	
	@FindBy(css = "button.vh79eN")
	private WebElement searchButton;
	
	
	
	public void getURL(String url) {
		driver.get(url);
	}
	
	public void login(String UserName, String Password) {
		wait.until(ExpectedConditions.visibilityOf(userName));
		sendKey(userName, UserName);
		sendKey(password, Password);
		click(loginBtn);
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
	}
	
	public void searchMobile(String mobileName) {
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
		sendKey(searchField, mobileName);
		//searchField.sendKeys(Keys.ENTER);
		click(searchButton);
		wait.until(ExpectedConditions.visibilityOf(searchResult));
	}
	
	public void addToCart() {
		click(searchResult);
		 String winHandleBefore = driver.getWindowHandle();
		switchToChildWindow();
		wait.until(ExpectedConditions.visibilityOf(btnAddToCart));
		click(btnAddToCart);
		wait.until(ExpectedConditions.visibilityOf(btnContinueShopping));
		click(btnContinueShopping);
		driver.switchTo().window(winHandleBefore);
		
	}
	
	public boolean checkCartItem(String itemToCheck) {
		boolean flag=false;
		wait.until(ExpectedConditions.visibilityOf(cartLink));
		click(cartLink);
		wait.until(ExpectedConditions.visibilityOf(btnContinueShopping));
		if(cartItem.getText().trim().contains(itemToCheck)) {
			flag=true;
			
		} else {
			flag=false;
		}		
		
		click(removeAddedItem);
		return flag;
	}
	
	public void logout() {
		click(myAccountLink);
		wait.until(ExpectedConditions.visibilityOf(myProfile));
		click(myProfile);
		wait.until(ExpectedConditions.visibilityOf(logoutLink));
		click(logoutLink);
		 
		
	}
	
	public void switchToChildWindow( ) {
		String mainWindowHandle = driver.getWindowHandle();
		for (String childWindowHandle : driver.getWindowHandles()) {
			  if(!childWindowHandle.equals(mainWindowHandle)){
			  driver.switchTo().window(childWindowHandle);
			  }
			} 
	}
	
	public void sendKey(WebElement locator, String text) {
		locator.sendKeys(text);
		//searchField.sendKeys(Keys.ENTER);
	}
	
	public void click(WebElement locator) {
		locator.click();
		//searchField.sendKeys(Keys.ENTER);
	}


}
