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


public class SearchResultsPageTest extends BaseTest{

	@BeforeClass
	public void searchResultsPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	@Description("Test Search header")
	@Severity(SeverityLevel.NORMAL)
	public void searchHeaderTest() {
		searchResultsPage = accountsPage.doSearch("Macbook");
		String searchResultsHeader = searchResultsPage.getSearchResultsPageHeader();
		Assert.assertTrue(searchResultsHeader.contains("Macbook"));
	}

	@Test
	@Description("Test search product count")
	@Severity(SeverityLevel.NORMAL)
	public void searchProductCountTest() {
		searchResultsPage = accountsPage.doSearch("iMac");
		int actProductsSearchCount = searchResultsPage.getProductsSearchCount();
		Assert.assertEquals(actProductsSearchCount, Constants.iMACBOOK_PRODUCT_COUNT);
	}

	@Test
	@Description("Test search product list")
	@Severity(SeverityLevel.NORMAL)
	public void getSearchProductListTest() {
		searchResultsPage = accountsPage.doSearch("iMac");
		List<String> actProductList = searchResultsPage.getProductResultsList();
		System.out.println(actProductList);
	}
	
}
