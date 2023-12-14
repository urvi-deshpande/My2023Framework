package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AccountsPageTest extends BaseTest{

	@BeforeClass
	public void accPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	@Description("Test Accounts Page Title")
	@Severity(SeverityLevel.NORMAL)
	public void accountsPageTitleTest() {
		String actTitle = accountsPage.getAccountsPageTitle();
		Assert.assertEquals(actTitle, Constants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	@Description("Test Search field existance")
	@Severity(SeverityLevel.CRITICAL)
	public void isSearchExistTest() {
		Assert.assertTrue(accountsPage.isSearchExists());
	}


	@Test
	@Description("Test whether Accounts page or not")
	@Severity(SeverityLevel.CRITICAL)
	public void isAccountsPageTest() {
		Assert.assertTrue(accountsPage.isAccountsPage());
	}

	@Test
	@Description("Test Accounts page sections")
	@Severity(SeverityLevel.CRITICAL)
	public void accountsPageSectionsListTest() {
		List<String> actAccountsPageSecList = accountsPage.getAccountsPageSectionsList();
		System.out.println("Accounts page section list is : "+ actAccountsPageSecList);
		Assert.assertEquals(actAccountsPageSecList, Constants.ACCOUNTS_SECTIONS_LIST);

	}





	
}
