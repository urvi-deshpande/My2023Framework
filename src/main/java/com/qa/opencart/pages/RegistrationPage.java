package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

//import io.qameta.allure.Step;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By agreeCheckbox = By.name("agree");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '0']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '0']");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	private By successMessg = By.cssSelector("div#content h1");


	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("Fill registration form")
	public String fillRegistrationForm(String firstName, String lastName, String email,
									String telephone, String password,String subscribe ) {
		eleUtil.waitForElementToBeVisible(this.firstName, Constants.DEFAULT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if(subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheckbox);
		eleUtil.doClick(continueBtn);

		String registerSuccessMsg = eleUtil.waitForElementToBeVisible(successMessg, Constants.DEFAULT_TIME_OUT).getText();
		if(registerSuccessMsg.equalsIgnoreCase("Your Account Has Been Created!")) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return registerSuccessMsg;
		}
		return null;

	}



	
}
