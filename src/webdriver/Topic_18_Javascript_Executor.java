package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Javascript_Executor {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailName = "huyen" + getRandomNumber() + "@gmail.com";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	}

//	@Test
	public void TC_01_TechPanda() {

		// truy cập URL
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(5);

		// Lấy ra domain
		String homePageDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(homePageDomain, "live.techpanda.org");

		// Kiểm tra URL được lấy ra có đúng không
		Assert.assertEquals(executeForBrowser("return document.URL"), "http://live.techpanda.org/");

		// Open Mobile Page
		hightlightElement("//a[text() = 'Mobile']");
		clickToElementByJS("//a[text() = 'Mobile']");
		sleepInSecond(3);

		// Click button add to card
		hightlightElement("//a[text() = 'Samsung Galaxy']/parent::h2/following-sibling::div/button");
		clickToElementByJS("//a[text() = 'Samsung Galaxy']/parent::h2/following-sibling::div/button");
		sleepInSecond(5);

		// Verify message
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));

		// Open customer service page
		hightlightElement("//a[text() = 'Customer Service']");
		clickToElementByJS("//a[text() = 'Customer Service']");
		sleepInSecond(10);
		Assert.assertEquals(executeForBrowser("return document.title"), "Customer Service");

		// Scroll to Newsletter
		scrollToElementOnDown("//input[@id = 'newsletter']");

		// Input field email
		hightlightElement("//input[@id = 'newsletter']");
		sendkeyToElementByJS("//input[@id = 'newsletter']", emailName);

		// click subscribe button
		hightlightElement("//button[@class = 'button']");
		clickToElementByJS("//button[@class = 'button']");
		sleepInSecond(5);
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));

		// Navigate tới domain
		navigateToUrlByJS("https://demo.guru99.com/v4/");
		sleepInSecond(5);
		Assert.assertEquals(executeForBrowser("return document.domain"), "demo.guru99.com");

	}

	@Test
	public void TC_02_Rode() {
		driver.get("https://warranty.rode.com/");

		By registerButton = By.xpath("//button[contains(text(), ' Register')]");

		driver.findElement(registerButton).click();
		sleepInSecond(3);

		Assert.assertEquals(getElementValidationMessage("//input[@id = 'firstname']"), "Please fill out this field.");
		getElement("//input[@id = 'firstname']").sendKeys("Automation");

		driver.findElement(registerButton).click();
		sleepInSecond(3);

		Assert.assertEquals(getElementValidationMessage("//input[@id = 'surname']"), "Please fill out this field.");
		getElement("//input[@id = 'surname']").sendKeys("FC");

		driver.findElement(registerButton).click();
		sleepInSecond(3);

		Assert.assertEquals(
				getElementValidationMessage("//div[contains(text(),'Register')]/parent::div//input[@id='email']"),
				"Please fill out this field.");
		getElement("//div[contains(text(),'Register')]/parent::div//input[@id='email']").sendKeys(emailName);

		driver.findElement(registerButton).click();
		sleepInSecond(3);

		String password = "abcABC123!@#$";
		Assert.assertEquals(
				getElementValidationMessage("//div[contains(text(),'Register')]/parent::div//input[@id='password']"),
				"Please fill out this field.");
		getElement("//div[contains(text(),'Register')]/parent::div//input[@id='password']").sendKeys(password);

		driver.findElement(registerButton).click();
		sleepInSecond(3);

		Assert.assertEquals(getElementValidationMessage("//input[@id = 'password-confirm']"),
				"Please fill out this field.");
		getElement("//input[@id = 'password-confirm']").sendKeys(password);

	}

	@Test
	public void TC_03() {

	}

	public int getRandomNumber() {
		return new Random().nextInt(9999);
	}

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 4px solid red; border-style: dashed;");
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public void sleepInSecond(long timeInSleep) {
		try {
			Thread.sleep(timeInSleep * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
