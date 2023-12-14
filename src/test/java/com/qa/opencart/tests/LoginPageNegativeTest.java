package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 102 - Design Login page for Open cart application")
@Story("US 103 - Design Login page features")
public class LoginPageNegativeTest extends BaseTest{

	@DataProvider
	public Object[][] getLoginNegativeData() {
		return new Object[][] {
			{"ajinkyapatil@gmail.com", "Srjnvkslbnfk1234"},
			{"   ", "FKefndvkd1234$"},
			{"aadgrm@gmail.com", "     "},
			{"Fgugbvdj@gmail.com.com","abscd1234$"}
		};
	}
	
	@Test(dataProvider = "getLoginNegativeData")
	@Description("Test Login Functionality using Invalid data")
	@Severity(SeverityLevel.NORMAL)
	public void loginNegativeTest(String username, String password) {
		String actLoginPageErrorMesg = loginPage.doInvalidLogin(username, password);
		Assert.assertEquals(actLoginPageErrorMesg, Errors.LOGIN_PAGE_ERROR_MESG, Errors.LOGIN_PAGE_ERROR_MESG_NOT_CORRECT);	
	}
	
	
//	public void test1() {
//		int a=10;
//		int b=20;
//		int sum = a+b;
//		//In below line, if sum is actual and expected are not matching, the provided message will be printed on console and report
//		Assert.assertEquals(sum, 40, "Sum is incorrect");
//	}
	
}
