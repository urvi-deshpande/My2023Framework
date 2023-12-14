package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	public static final Logger log = Logger.getLogger(DriverFactory.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public By getBy(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;

		case "name":
			locator = By.name(locatorValue);
			break;

		case "classname":
			locator = By.className(locatorValue);
			break;

		case "xpath":
			locator = By.xpath(locatorValue);
			break;

		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;

		case "linktext":
			locator = By.linkText(locatorValue);
			break;

		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;

		case "tagname":
			locator = By.tagName(locatorValue);
			break;

		default:
			break;
		}
		return locator;

	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doSendKeys(By locator, int value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(String.valueOf(value));
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String getText(By locator) {
		return getElement(locator).getText();
	}

	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean isElementEnabled(By locator) {
		return getElement(locator).isEnabled();
	}

	public List<String> getLinksTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleListText = new ArrayList<>();

		for(WebElement e: eleList) {
			String eleText = e.getText();
			if(!eleList.isEmpty()) {
				//System.out.println(eleText);
				eleListText.add(eleText);
			}
		}
		return eleListText;
	}

	public List<String> getElementAttribute(By locator, String AttributeName) {
		List<WebElement> eleList = driver.findElements(locator);
		List<String> eleAttributeValuesList = new ArrayList<>();

		for(WebElement e: eleList) {
			String AttributeValue = e.getAttribute(AttributeName);
			System.out.println(AttributeValue);
			eleAttributeValuesList.add(AttributeValue);
		}
		return eleAttributeValuesList;
	}

	//*******************Drop Down Utilities***************************//

	/*
	 * This function is used to select the value from Drop Down by using it's visible text
	 */
	public void doSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	/*
	 * This function is used to select the value from Drop Down by using it's Index
	 */
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	/*
	 * This function is used to select the value from Drop Down by using it's value
	 */
	public void doSelectByValue(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByValue(text);
	}

	/*
	 * This method returns the list of options from Drop Down
	 * e.g. to get the list of all countries/states from the Drop Down list
	 */
	public List<String> getDropDownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<>();
		for(WebElement e : optionsList) {
			String OptionText = e.getText();
			optionsValueList.add(OptionText);
		}
		return optionsValueList;
	}

	/*
	 * This function is used to select the value from Drop Down without using selectByIndex,
	 * selectByValue and selectByVisibleText methods
	 */
	public void doSelectDropDownOptions(By locator, String value) {
		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for(WebElement e: optionsList) {
			String text= e.getText();
				if(text.equals(value)) {
					e.click();
					break;
				}
		}
	}

	/*
	 * This function is used to select the value from Drop Down without using
	 * Select class and methods
	 * If Select tag is not there in DOM, follow below practice to select from Drop Down
	 */
	public void doSelectValueFromDropDown(By locator, String value) {
		List<WebElement> optionsList = driver.findElements(locator);

		for(WebElement e: optionsList) {
			String text = e.getText();
			if(text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/*
	 * This function is used to enter the value in Search Field
	 */

	public void enterValueInSearchField(By locator, String inputText) {
		driver.findElement(locator).sendKeys(inputText);
	}

	/*
	 * This function is used to select any one value from suggestion list
	 */
	public void doSelectFromSuggestionList(By locator, String value) {
		List<WebElement> suggestionList = driver.findElements(locator);

		for(WebElement e: suggestionList) {
			String text = e.getText();
			//System.out.println(text);
			if(text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	/*
	 * This function returns the list of suggestions
	 */
	public List<String> getSuggestionList(By locator) {
		List<WebElement> suggestionList = driver.findElements(locator);
		List<String> suggestionListValues = new ArrayList<>();

		for(WebElement e: suggestionList) {
			String text = e.getText();
			//System.out.println(text);
			suggestionListValues.add(text);
		}
		return suggestionListValues;
	}

	public void selectSubMenuLevel(By parentMenu, By childMenu) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).build().perform();
		Thread.sleep(2000);
		getElement(childMenu).click();
		}

	public void selectSubMenuLevel(By parentMenu, By childMenuLevel1, By chileMenuLevel2) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(childMenuLevel1)).build().perform();
		Thread.sleep(2000);
		getElement(chileMenuLevel2).click();
		}

	public void selectSubMenuLevel(By parentMenu, By childMenuLevel1, By chileMenuLevel2, By chileMenuLevel3) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(childMenuLevel1)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(chileMenuLevel2)).build().perform();
		Thread.sleep(2000);
		getElement(chileMenuLevel3).click();
		}

	public void doContextClick(By locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).perform();
	}

	public int getRightClickOptionsCount(By rightClickElement, By rightClickOptions) {
		return getRightClickOptionsList(rightClickElement, rightClickOptions).size();
	}

	public  List<String> getRightClickOptionsList(By rightClickElement, By rightClickOptions) {

		doContextClick(rightClickElement);
		List<WebElement> itemsList = getElements(rightClickOptions);
		List<String> rightClickOptionsList = new ArrayList<>();
		System.out.println(itemsList.size());

		for(WebElement e: itemsList) {
			String text = e.getText();
			rightClickOptionsList.add(text);
			//System.out.println(text);
		}
		return rightClickOptionsList;

	}

	public void selectRightClickMenu(By rightClickElement, By rightClickOptions, String value) {

		doContextClick(rightClickElement);
		List<WebElement> itemsList = getElements(rightClickOptions);

		System.out.println(itemsList.size());
		for(WebElement e: itemsList) {
			String text = e.getText();
			//System.out.println(text);
			if(text.contains(value)) {
				e.click();
				break;
			}
		}
	}
	/**
	 * Equivalent to calling Actions.click(element).sendKeys(KeysToSend)
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator),value).perform();
	}

	/**
	 * Clicks in the middle of the element and then perform click.
	 * Equivalent to Actions.moveToElement(onElement).click()
	 * @param locator
	 */
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	//************************Wait Utils********************//

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width
	 * that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresent(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementToBeVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}


	public Alert waitForAlert(int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}


	public Alert waitForAlert(int timeOut, int pollingTime) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * @param timeOut
	 * @param urlFraction
	 * @return
	 */
	public String waitForUrl(int timeOut, String urlFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		}
		return null;
	}

	/**
	 * An expectation for the URL of the current page to be a specific url.
	 * @param timeOut
	 * @param urlVal
	 * @return
	 */
	public String waitForUrlToBe(int timeOut, String urlVal) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlToBe(urlVal))) {
			return driver.getCurrentUrl();
		}
		return null;
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive substring
	 * @param timeOut
	 * @param titleFraction
	 * @return
	 */
	public String waitForTitleContains(int timeOut, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		return null;
	}

	/**
	 * An expectation for checking the title of a page.
	 * @param timeOut
	 * @param title
	 * @return
	 */
	public String waitForTitleToBe(int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		return null;
	}

	/**
	 * An expectation for checking whether the given frame is available to switch to.
	 * If the frame is available it switches the given driver to the specified frameIndex.
	 * @param timeOut
	 * @param frameName
	 * @return
	 */
	public WebDriver waitForFrameUsingName(int timeOut, String frameName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}

	/**
	 * An expectation for checking whether the given frame is available to switch to.
	 * If the frame is available it switches the given driver to the specified frameIndex.
	 * @param timeOut
	 * @param frameIndex
	 * @return
	 */
	public WebDriver waitForFrameUsingIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	/**
	 * An expectation for checking whether the given frame is available to switch to.
	 * If the frame is available it switches the given driver to the specified frame.
	 * @param timeOut
	 * @param frameLocator
	 * @return
	 */
	public WebDriver waitForFrameUsingLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 */
	public void clickWhenReady(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}


	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible.
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param timeOut
	 * @param locator
	 * @return
	 */
	public List<WebElement> waitForElemets(int timeOut, By locator ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<String> getElementsTextListWithWait(int timeOut, By locator) {
		List<WebElement> list = waitForElemets(timeOut, locator);
		List<String> elementList = new ArrayList<>();
		for(WebElement e: list) {
			String text = e.getText();
			elementList.add(text);
		}
		return elementList;
	}

	public void printAllElements(int timeOut, By locator) {
		List<WebElement> list = waitForElemets(timeOut, locator);
		for(WebElement e: list) {
			System.out.println(e.getText());
		}
	}

	/**
	 * This function is for custom wait
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;
		int attempts = 0;
		while(attempts < timeOut) {
			try {
			element = getElement(locator);
			break;
			} catch(NoSuchElementException e) {
				System.out.println("element is not found in attempt :" + attempts);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if(element == null) {
			try {
				throw new Exception("NOSUCHELEMENTEXCEPTION");
			} catch (Exception e) {
				System.out.println("Element is not found exception... tried for " + timeOut + "seconds with interval of " + 500 + "ms");
			}
		}
		return element;
	}

	public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
		WebElement element = null;
		int attempts = 0;
		while(attempts < timeOut) {
			try {
			element = getElement(locator);
			break;
			} catch(NoSuchElementException e) {
				System.out.println("element is not found in attempt :" + attempts);
				try {
					Thread.sleep(intervalTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if(element == null) {
			try {
				throw new Exception("NOSUCHELEMENTEXCEPTION");
			} catch (Exception e) {
				System.out.println("Element is not found exception... tried for " + timeOut + "seconds with interval of " + intervalTime + "ms");
			}
		}
		return element;
	}

	public void waitForPageLoad(int timeOut) {
		int attempts = 0;

		while(attempts < timeOut) {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			String state = js.executeScript("return document.readyState").toString();
			System.out.println(state);
			if(state.equals("complete")) {
				break;
			}
		}

	}

	public WebElement waitForElementToBePresentWithFluentClass(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
									.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
									.ignoring(NoSuchElementException.class, ElementNotInteractableException.class);

		//maximum 2 exceptions can be provided here

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementToBeVisibleWithFluentClass(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
									.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
									.ignoring(NoSuchElementException.class, ElementNotInteractableException.class);

		//maximum 2 exceptions can be provided here

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public Alert waitForAlertWithFluentClass(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
									.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
									.ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public WebDriver waitForFrameWithFluentClass(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
									.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(pollingTime))
									.ignoring(NoSuchFrameException.class);

		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

}

