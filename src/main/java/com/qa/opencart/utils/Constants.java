package com.qa.opencart.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String ACCOUNTS_PAGE_HEADER = "Opencart";
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";

	public static final List<String> ACCOUNTS_SECTIONS_LIST = Arrays.asList("My Account",
																				"My Orders",
																				"My Affiliate Account",
																				"Newsletter");
	public static final int DEFAULT_TIME_OUT = 5;
	public static final String ACCOUNTS_PAGE_FRACTION_URL = "route=account/account";
	public static final Object MACBOOK_PRODUCT_COUNT = 3;
	public static final Object iMACBOOK_PRODUCT_COUNT = 1;
	public static final int MACBOOK_IMAGES_COUNT = 5;
	public static final String ADD_TO_CART_SUCCESS_MESSAGE = "Success: You have added MacBook to your shopping cart!";
	public static final String NEW_USER_REGISTER_SUCCESS_MESSAGE = "Your Account Has Been Created!";
	public static final String REGISTER_SHEET_NAME = "Register";
	public static final String PRODUCT_SHEET_NAME = "Product";
	public static final String PRODUCT_IMAGES_SHEET_NAME = "ProductImages";
	
	public static final String TEST_DATA_SHEET_PATH = "./src/test/resources/TestData/OpenCartTestData.xlsx";

}
