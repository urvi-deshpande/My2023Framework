package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

//import io.qameta.allure.Step;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By SearchHeader = By.cssSelector("div#content h1");
	private By SearchBreadcrumb = By.linkText("Search");
	private By products = By.xpath("//div[@class='caption']//a");
	private By MacBook = By.cssSelector("img[title='MacBook']");

	public SearchResultsPage(WebDriver driver){
		this.driver= driver;
		eleUtil = new ElementUtil(driver);
	}

	//@Step("Search page existance")
	public boolean isSearchPage() {
		return eleUtil.isElementDisplayed(SearchBreadcrumb);
	}

	//@Step("Get search results page header")
	public String getSearchResultsPageHeader() {
		if(eleUtil.isElementDisplayed(SearchHeader)) {
			return eleUtil.getText(SearchHeader);
		}
		return null;
	}

	//@Step("Get product search count")
	public int getProductsSearchCount() {
		return eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT).size();
	}

	//@Step("Get product results list")
	public List<String> getProductResultsList() {
		List<WebElement> productsList = eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
		List<String> actProductsList = new ArrayList<>();
		for(WebElement e: productsList) {
			String text = e.getText();
			actProductsList.add(text);
		}
		return actProductsList;
	}

	//@Step("Select product using ProductName {0}")
	public ProductInfoPage selectProduct(String mainProductName) {
		List<WebElement> productList = eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
		for(WebElement e: productList) {
			String text = e.getText();
			if(text.equals(mainProductName)) {	
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}


}
