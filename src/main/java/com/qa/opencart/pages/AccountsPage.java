package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

//import io.qameta.allure.Step;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	//1. private By locators
	private By accountBreadcrumb = By.linkText("Account");
	private By search = By.name("search");
	private By searchBtn = By.cssSelector("div#search i");
	private By header = By.cssSelector("div#logo a");
	private By accSecList = By.cssSelector("div#content h2");

	//2. Public constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	//3. Public functions
	@Step("To get Accounts page title")
	public String getAccountsPageTitle() {
		return eleUtil.waitForTitleToBe(Constants.DEFAULT_TIME_OUT, Constants.ACCOUNTS_PAGE_TITLE);
	}

	@Step("To get Accounts page URL")
	public String getAccountsPageUrl() {
		return eleUtil.waitForUrl(Constants.DEFAULT_TIME_OUT, Constants.ACCOUNTS_PAGE_FRACTION_URL);
	}

	@Step("To check Search field exists or not")
	public boolean isSearchExists() {
		return eleUtil.isElementDisplayed(search);
	}

	@Step("To check the page is Accounts page or not")
	public boolean isAccountsPage() {
		return eleUtil.isElementDisplayed(accountBreadcrumb);
	}

	@Step("To get Accounts page sections list")
	public List<String> getAccountsPageSectionsList() {
		List<WebElement> secValueList = eleUtil.getElements(accSecList);
		List<String> actSecValueList = new ArrayList<>();

		for(WebElement e: secValueList) {
			String text = e.getText();
			actSecValueList.add(text);
		}
		return actSecValueList;
	}

	@Step("To do search")
	public SearchResultsPage doSearch(String productName) {
		if(isSearchExists()) {
			eleUtil.doSendKeys(search, productName);
			eleUtil.doClick(searchBtn);
		}
		return new SearchResultsPage(driver);
	}

	
}
