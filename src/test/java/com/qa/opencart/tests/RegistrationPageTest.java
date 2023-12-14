package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void registerPageSetup() {
		registrationPage = loginPage.navigateToRegisterPage();
	}

	public String generateRandonEmail() {
		Random random = new Random();
		String email = "TestUserName" + random.nextInt(1000) + "@gmail.com";
		return email;
	}

//	@DataProvider
//	public Object[][] getUserData() {
//		return new Object[][] {
//			{"Radha","Kamthe","7567476856","Test@12345","Yes"},
//			{"Gagan","Tyagi","7567476857","Test@12348","No"},
//			{"Adhiraj","Patil","7567476858","Test@12347","Yes"}
//		};
//	}

	@DataProvider
	public Object[][] getUserData(){
		Object data[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;

	}

	@Test(dataProvider = "getUserData")
	@Description("Test new User registration functionality")
	@Severity(SeverityLevel.NORMAL)
	public void newUserRegister(String firstName, String lastName, String phoneNumber, String password, String subscribe) {
		String actSuccessMessage = registrationPage.fillRegistrationForm(firstName, lastName, generateRandonEmail(), phoneNumber, password, subscribe);
		Assert.assertEquals(actSuccessMessage, Constants.NEW_USER_REGISTER_SUCCESS_MESSAGE);
	}

}
