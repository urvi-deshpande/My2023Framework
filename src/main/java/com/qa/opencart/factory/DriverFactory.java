package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.utils.Browsers;
import com.qa.opencart.utils.Errors;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static final Logger log = Logger.getLogger(DriverFactory.class);
	
	/**
	 * This method is used to initialize the webdriver on the basis of given browser
	 * name. This method will take care of local and remote execution
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);

		highlight = prop.getProperty("highlight").trim();
		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase(Browsers.CHROME_BROWSER_VALUE)) {
			log.info("Running tests on Chrome browser");
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		} else if (browserName.equalsIgnoreCase(Browsers.FIREFOX_BROWSER_VALUE)) {
			log.info("Running tests on Firefox browser");
				//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		} else if (browserName.equalsIgnoreCase(Browsers.EDGE_BROWSER_VALUE)) {
			log.info("Running tests on Edge browser");
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		} else {
			System.out.println(Errors.BROWSER_NOT_FOUND_ERROR_MESSAGE + browserName);
			log.info(Errors.BROWSER_NOT_FOUND_ERROR_MESSAGE + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		log.info(prop.getProperty("url") + "url has been launched");
		
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this will return the thread local copy of the webdriver(driver)
	 * 
	 * @return
	 */
//	public static WebDriver getDriver() {
//		return tlDriver.get();
//	}

	/**
	 * This method is used to initialize the properties on the basis of given
	 * environment: QA/DEV/Stage/PROD
	 * @return this returns prop
	 */
	public Properties init_prop() {
		
		prop = new Properties();
		FileInputStream ip = null;
		
		//mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("Running tests on :" + envName);
		log.info("Running tests on :" + envName);
		
		if(envName == null) {
			System.out.println("No environment is provided.. Hence running the test cases on QA environment.");
			log.info("No environment is provided.. Hence running the test cases on QA environment.");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
			switch (envName.toLowerCase()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;
			default:
				System.out.println("Please pass the right environment " + envName);
				log.error("Please pass the right environment " + envName);
				break;
			}
			}
			catch(Exception e) {	
			}
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;

//		prop = new Properties();
//		try {
//			ip = new FileInputStream("./src/test/resources/config/config.properties");
//			try {
//				prop.load(ip);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
