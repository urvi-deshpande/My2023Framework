package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

//import io.qameta.allure.Step;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private String[] meta;

	//private By productInfoHeader = By.cssSelector("div#content h1");
	private By productInfoHeader = By.xpath("//*[@id=\"content\"]/div/div[2]/h1");
	private By productImages = By.cssSelector("div#content img");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1)");
	private By productPricingData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2)");
	private By inputQty = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By successmsg = By.cssSelector("div.alert.alert-success.alert-dismissible");

	private Map<String,String> ProductInfoMap = new LinkedHashMap<>();

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("Get product images count")
	public int getProductImagesCount() {
		return eleUtil.waitForElementsToBeVisible(productImages, Constants.DEFAULT_TIME_OUT).size();
	}

	//@Step("Get product header text")
	public String getProductHeaderText() {
		return eleUtil.waitForElementPresent(productInfoHeader, Constants.DEFAULT_TIME_OUT).getText();
	}

	@Step("Get product info")
	public Map<String, String> getProductInfo(){
		ProductInfoMap = new LinkedHashMap<>();
		ProductInfoMap.put("productname", getProductHeaderText());
		ProductInfoMap.put("productimagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		return ProductInfoMap;
	}

	@Step("Get product meta data")
	private void getProductMetaData() {
		List<WebElement> MetaDataList = eleUtil.waitForElementsToBeVisible(productMetaData, Constants.DEFAULT_TIME_OUT);
		for(WebElement e: MetaDataList) {
			String text = e.getText();
			String[] meta = text.split(":");
			String key = meta[0].trim();
			String value = meta[1].trim();
			ProductInfoMap.put(key, value);
		}
	}

	@Step("Get product price data")
	private void getProductPriceData() {
		List<WebElement> MetaPriceList = eleUtil.waitForElementsToBeVisible(productPricingData, Constants.DEFAULT_TIME_OUT);

			String price = MetaPriceList.get(0).getText().trim();
			String exprice = MetaPriceList.get(1).getText().trim();
			ProductInfoMap.put("price", price);
			ProductInfoMap.put("extaxPrice", exprice);

	}

	@Step("Add to cart using Quantity {0}")
	public void enterQuantityToAddToCart(int productQuantity) {
		eleUtil.doSendKeys(inputQty, productQuantity);
	}

	@Step("Add to cart")
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		return eleUtil.waitForElementPresent(successmsg, Constants.DEFAULT_TIME_OUT).getText().trim();
	}



	
}
