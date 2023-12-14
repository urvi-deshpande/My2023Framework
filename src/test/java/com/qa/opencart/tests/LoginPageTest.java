package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 102 - Design Login page for Open cart application")
@Story("US 103 - Design Login page features")
public class LoginPageTest extends BaseTest{

		@Test
		@Description("Test Login Page Title")
		@Severity(SeverityLevel.NORMAL)
		public void loginPageTitleTest() {
			String actTitle = loginPage.getLoginPageTitle();
			System.out.println("Login page title is : "+actTitle);
			Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);
		}

		@Test
		@Description("Test Login Page URL")
		@Severity(SeverityLevel.NORMAL)
		public void loginPageUrlTest() {
			String url = loginPage.getLoginPageUrl();
			Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_FRACTION_URL));
		}

		@Test
		@Description("Test Register Link existance")
		@Severity(SeverityLevel.CRITICAL)
		public void isRegisterLinkExist() {
			Assert.assertTrue(loginPage.isRegisterLinkExists());
		}

		@Test
		@Description("Test Login Functionality")
		@Severity(SeverityLevel.BLOCKER)
		public void loginTest() {
			accountsPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
			Assert.assertTrue(accountsPage.isAccountsPage());
		}

	
}
