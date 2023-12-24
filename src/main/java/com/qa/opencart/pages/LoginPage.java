package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	//1. private By locators
	private By emailID = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPswd = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By logOutLink;
	private By loginPageErrorMesg = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	//2. public page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	//3. Public page actions
	@Step("Get Login page title")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleToBe(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_TITLE);
		//return driver.getTitle();
	}

	@Step("Get Login page URL")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrl(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_FRACTION_URL);
		//return driver.getCurrentUrl();
	}

	@Step("To check existance of Forgot Password Link")
	public boolean isForgotPswdLinkExists() {
		return eleUtil.isElementDisplayed(forgotPswd);
	}

	@Step("Check existance of Register Link")
	public boolean isRegisterLinkExists() {
		return eleUtil.isElementDisplayed(registerLink);
	}

	@Step("Login to the application using correct username {0} and correct password {1}")
	public AccountsPage doLogin(String un, String pwd) {

		eleUtil.waitForElementToBeVisible(emailID, Constants.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("Login to the application using incorrect credentials")
	public String doInvalidLogin(String un, String pwd) {

		WebElement ele_EmailID = eleUtil.waitForElementToBeVisible(emailID, Constants.DEFAULT_TIME_OUT);
		ele_EmailID.clear();
		ele_EmailID.sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return eleUtil.getText(loginPageErrorMesg);
	}

	public boolean isRegisterLinkExist() {
		return eleUtil.waitForElementToBeVisible(registerLink, Constants.DEFAULT_TIME_OUT).isDisplayed();
	}

	@Step("Navigate to Registration page")
	public RegistrationPage navigateToRegisterPage() {
		if(isRegisterLinkExist()) {
			eleUtil.doClick(registerLink);
			return new RegistrationPage(driver);
		}
		return null;
	}
	
	
}
