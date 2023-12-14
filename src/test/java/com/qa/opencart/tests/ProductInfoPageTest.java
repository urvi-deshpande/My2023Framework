package com.qa.opencart.tests;

import java.util.Map;

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


public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void productInfoPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

//	@DataProvider
//	public Object[][] getProductData(){
//		return new Object[][] {
//			{"macbook","MacBook"},
//			{"macbook","MacBook Air"},
//			{"macbook","MacBook Pro"},
//			{"apple","Apple Cinema 30\""}
//		};
//	}

	@DataProvider
	public Object[][] getProductData(){
		Object data[][] = ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
		return data;
	}

	@Test(dataProvider = "getProductData")
	@Description("Test product info header")
	@Severity(SeverityLevel.NORMAL)
	public void productHeaderTest(String productName, String mainProductName) {
		searchResultsPage = accountsPage.doSearch(productName);
		productInfoPage = searchResultsPage.selectProduct(mainProductName);
		String actualProductHeader = productInfoPage.getProductHeaderText();
		Assert.assertEquals(actualProductHeader, mainProductName);
	}

//	@DataProvider
//	public Object[][] getProductImagesData(){
//		return new Object[][] {
//			{"macbook","MacBook",5},
//			{"macbook","MacBook Air",4},
//			{"macbook","MacBook Pro",4}
//		};
//	}

	@DataProvider
	public Object[][] getProductImagesData(){
		Object data[][] = ExcelUtil.getTestData(Constants.PRODUCT_IMAGES_SHEET_NAME);
		return data;
	}

	@Test(dataProvider = "getProductImagesData")
	@Description("Test product images")
	@Severity(SeverityLevel.NORMAL)
	public void productImagesCountTest(String productName, String mainProductName, int imagesCount) {
		searchResultsPage = accountsPage.doSearch(productName);
		productInfoPage = searchResultsPage.selectProduct(mainProductName);
		Assert.assertTrue(productInfoPage.getProductImagesCount()== imagesCount);
	}

	@DataProvider
	public Object[][] getProductInfoData(){
		return new Object[][] {
			{"macbook","MacBook",5, "Apple", "Product 16","600","In Stock", "$602.00","$500.00"},
			{"macbook","MacBook Air",4, "Apple", "Product 17","700","In Stock", "$1,202.00","$1,000.00"},
			{"macbook","MacBook Pro",4, "Apple", "Product 18","800","In Stock", "$2,000.00","$2,000.00"},
		};
	}

	@Test(dataProvider = "getProductInfoData")
	@Description("Test product information")
	@Severity(SeverityLevel.NORMAL)
	public void productInfoTest(String productName, String mainProductName, int imagesCount,
								String brand, String productCode, String rewardPoints, String availability, String price, String extaxprice) {
		searchResultsPage = accountsPage.doSearch(productName);
		productInfoPage = searchResultsPage.selectProduct(mainProductName);
		Map<String,String> actProductInfo = productInfoPage.getProductInfo();
		actProductInfo.forEach((k, v) -> System.out.println(k + ":" + v));

		softAssert.assertEquals(actProductInfo.get("productname"), mainProductName);
		softAssert.assertEquals(actProductInfo.get("productimagescount"), imagesCount);
		softAssert.assertEquals(actProductInfo.get("Brand"), brand);
		softAssert.assertEquals(actProductInfo.get("Product Code"), productCode);
		softAssert.assertEquals(actProductInfo.get("Reward Points"), rewardPoints);
		softAssert.assertEquals(actProductInfo.get("Availability"), availability);
		softAssert.assertEquals(actProductInfo.get("price"), price);
		softAssert.assertEquals(actProductInfo.get("extaxPrice"), extaxprice);
		softAssert.assertAll();
	}

	@Test
	@Description("Test add product to cart functionality")
	@Severity(SeverityLevel.NORMAL)
	public void addProductTocart() {
		searchResultsPage = accountsPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook");
		productInfoPage.enterQuantityToAddToCart(2);
		String actAlertMessage = productInfoPage.addProductToCart();
//		Assert.assertEquals(actAlertMessage, Constants.ADD_TO_CART_SUCCESS_MESSAGE);
		Assert.assertTrue(actAlertMessage.contains(Constants.ADD_TO_CART_SUCCESS_MESSAGE));
	}
	
}
