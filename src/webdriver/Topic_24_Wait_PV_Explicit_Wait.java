package webdriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_24_Wait_PV_Explicit_Wait {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
	}

//	@Test
	public void TC_01_Wait_For_Attribute_Contain_ToBe_Value() {
		driver.get("http://live.techpanda.org/");
		explicitWait = new WebDriverWait(driver, 10);

		// Wait cho search textbox chứa giá trị là 1 đoạn placeholder text
		// nếu lấy text chứa tương đối dùng attributeContains
		explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#search"), "placeholder",
				"Search entire store here"));

		// Nếu lấy text chứa tuyệt đối dùng tobe
		explicitWait.until(ExpectedConditions.attributeToBe(By.cssSelector("input#search"), "placeholder",
				"Search entire store here..."));

		Assert.assertEquals(driver.findElement(By.cssSelector("input#search")).getAttribute("placeholder"),
				"Search entire store here...");
	}

//	@Test
	public void TC_02_Wait_For_Element_Clickable() {

		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 10);

		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#start button")));
		driver.findElement(By.cssSelector("div#start button")).click();

		driver.get("https://login.mailchimp.com/signup/");
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#create-account-enabled")));
		driver.findElement(By.cssSelector("button#create-account-enabled")).click();

		driver.get("https://www.fahasa.com/customer/account/create");
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.fhs-btn-register")));
		driver.findElement(By.cssSelector("button.fhs-btn-register")).click();
	}

//	@Test
	public void TC_03_Wait_For_Element_Selected() {
		explicitWait = new WebDriverWait(driver, 10);

		driver.get("https://automationfc.github.io/multiple-fields/");
		explicitWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input.form-checkbox"), 29));
		List<WebElement> allCheckboxItems = driver.findElements(By.cssSelector("input.form-checkbox"));

		// click chọn button
		for (WebElement webElement : allCheckboxItems) {
			webElement.click();
		}

		// Verify tất cả các checkbox đã được chọn
		for (WebElement checkBox : allCheckboxItems) {
			explicitWait.until(ExpectedConditions.elementToBeSelected(checkBox));
			Assert.assertTrue(checkBox.isEnabled());
		}
	}

//	@Test
	public void TC_04_Wait_For_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		explicitWait = new WebDriverWait(driver, 10);

		explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("login_page")));
		driver.findElement(By.cssSelector("input.form-control")).sendKeys("huyenmin");
		driver.findElement(By.cssSelector("a.login-btn")).click();

		// swich to default content
		driver.switchTo().defaultContent();
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#keyboard")));
		Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
	}

//	@Test
	public void TC_05_Wait_For_GetText() {
		driver.get("http://live.techpanda.org/");
		explicitWait = new WebDriverWait(driver, 10);

		explicitWait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='footer']//a[text()= 'My Account']"),
				"My Account".toUpperCase()));
		explicitWait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='footer']//a[text()= 'My Account']")));
		driver.findElement(By.xpath("//div[@class='footer']//a[text()= 'My Account']")).click();

		explicitWait.until(ExpectedConditions.elementToBeClickable(By.id("send2")));
		driver.findElement(By.id("send2")).click();

		// Verify
		explicitWait.until(ExpectedConditions.textToBe(
				By.xpath("//input[@id='email']/parent::div[@class='input-box']//div[@class='validation-advice']"),
				"This is a required field."));
		Assert.assertEquals(driver
				.findElement(By
						.xpath("//input[@id='email']/parent::div[@class='input-box']//div[@class='validation-advice']"))
				.getText(), "This is a required field.");

		explicitWait.until(ExpectedConditions.textToBe(
				By.xpath("//input[@id='pass']/parent::div[@class='input-box']//div[@class='validation-advice']"),
				"This is a required field."));
		Assert.assertEquals(driver
				.findElement(By
						.xpath("//input[@id='pass']/parent::div[@class='input-box']//div[@class='validation-advice']"))
				.getText(), "This is a required field.");
	}

//	@Test
	public void TC_06_Url_Title() {
		// Truy cap vao trang
		driver.get("http://live.techpanda.org/");
		explicitWait = new WebDriverWait(driver, 10);

		// Click MyAccount
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		// verify url login page
		explicitWait.until(ExpectedConditions.urlContains("/customer/account/login/"));
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

		// Click Create An Account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

		// Verify url Register Page
		explicitWait.until(ExpectedConditions.urlContains("customer/account/create/"));
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

		// Truy cap trang
		driver.get("http://live.techpanda.org/");

		// click MyAccount on footer
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		// Verify title Login Page
		explicitWait.until(ExpectedConditions.titleContains("Customer Login"));
		Assert.assertEquals(driver.getTitle(), "Customer Login");

		// Click Create an account button
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

		// Verify title Register page
		explicitWait.until(ExpectedConditions.titleContains("Create New Customer Account"));
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}

	@Test
	public void TC_07_Timeout_Less_Than_5_Seconds() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 3);

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();

		// Chờ cho step trước biến mất (loading)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

		// chờ cho step sau được xuất hiện ( Hello word!)
		explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("div#finish h4"), "Hello World!"));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div#finish h4")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_08_Timeout_equals_5_Seconds() {

		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 5);

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();
		// Chờ cho step trước biến mất (loading)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

		explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("div#finish h4"), "Hello World!"));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div#finish h4")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_09_Timeout_More_Than_5_Seconds() {

		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 100);

		// Chờ cho step trước biến mất (loading)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();
		explicitWait.until(ExpectedConditions.textToBe(By.cssSelector("div#finish h4"), "Hello World!"));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div#finish h4")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");

	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
