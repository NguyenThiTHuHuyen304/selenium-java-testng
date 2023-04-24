package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Button_Radio_Textbox {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Default_Checkbox_Radio() {
		driver.get("https://automationfc.github.io/multiple-fields/");

		// Click chọn 1 checkbox
		driver.findElement(By.xpath("//label[text()= ' Diabetes ']//preceding-sibling::input")).click();

		// Click chọn 1 radio button
		driver.findElement(By.xpath("//label[contains(string(),\"I don't drink\")]/preceding-sibling::input")).click();

		// Verify
		Assert.assertTrue(
				driver.findElement(By.xpath("//label[text()= ' Diabetes ']//preceding-sibling::input")).isSelected());
		Assert.assertTrue(
				driver.findElement(By.xpath("//label[contains(string(),\"I don't drink\")]/preceding-sibling::input"))
						.isSelected());

		// Bỏ chọn checkbox
		driver.findElement(By.xpath("//label[text()= ' Diabetes ']//preceding-sibling::input")).click();

		// Verify bỏ chọn checkbox
		Assert.assertFalse(
				driver.findElement(By.xpath("//label[text()= ' Diabetes ']//preceding-sibling::input")).isSelected());
	}

//	@Test
	public void TC_02_Default_Checkbox_Multiple() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		List<WebElement> allCheckboxItems = driver.findElements(By.cssSelector("input.form-checkbox"));

		// click chọn button
		for (WebElement webElement : allCheckboxItems) {
			webElement.click();
			sleepInSecond(1);
		}

		// Verify tất cả các checkbox đã được chọn
		for (WebElement checkBox : allCheckboxItems) {
			Assert.assertTrue(checkBox.isEnabled());
		}

		// Nếu như gặp 1 checkbox có tên X thì click chọn
		for (WebElement checkBox : allCheckboxItems) {
			if (checkBox.getAttribute("value").equals("Arthritis")) {
				checkBox.click();
			}
		}

	}

//	@Test
	public void TC_03_Custom_Checkbox_Radio() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

		/*
		 * Case 1: Thẻ input bị che nên ko thao tác được Thẻ input lại dùng để verify
		 * được vì isSelected() chỉ dùng với thẻ input
		 */

		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input"))
						.isSelected());
	}

//	@Test
	public void TC_04_Custom_Checkbox_Radio() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

		/*
		 * Case 2: Dùng thẻ khác input để click(span/div/label...) nhưng ko dùng để
		 * verify
		 */

		driver.findElement(By.xpath(
				"//div[text()='Đăng ký cho người thân']/preceding-sibling::div/div[@class = 'mat-radio-outer-circle']"))
				.click();
		Assert.assertTrue(driver.findElement(By.xpath(
				"//div[text()='Đăng ký cho người thân']/preceding-sibling::div/div[@class = 'mat-radio-outer-circle']"))
				.isSelected());
	}

//	@Test
	public void TC_05_Custom_Checkbox_Radio() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

		/*
		 * Case 3: Dùng thẻ khác input để click(span/div/label...) quay lại thẻ input để
		 * verify verify Note: Phù hợp để demo, nếu apply vào framework thì không nên do
		 * 1 element phải define nhiều locator
		 */

		driver.findElement(By.xpath(
				"//div[text()='Đăng ký cho người thân']/preceding-sibling::div/div[@class = 'mat-radio-outer-circle']"))
				.click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input"))
						.isSelected());
	}

//	@Test
	public void TC_06_Custom_Checkbox_Radio() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

		/*
		 * Case 4: Dùng thẻ input để click. Hàm click() của WebElement nó sẽ không thao
		 * tác vào element bị ẩn được ==> Sẽ sử dụng hàm click() của JavaScript để click
		 * Dùng thẻ input để verify JavaScriptExecutor: là thư viện của selenium dùng để
		 * nhúng các đoạn code JS vào kịch bản test
		 */

		By radioButton = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input");

		jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
		sleepInSecond(3);

		Assert.assertTrue(
				driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input"))
						.isSelected());
	}

	@Test
	public void TC_07_Custom_Checkbox_Radio() {
		driver.get(
				"https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(2);

		By radioButton = By.cssSelector("div[aria-label = 'Hà Nội']");
		By checkbox = By.cssSelector("div[aria-label = 'Quảng Ngãi']");

		jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkbox));
		sleepInSecond(2);

		// Cách số 1
		Assert.assertTrue(
				driver.findElement(By.cssSelector("div[aria-label = 'Hà Nội'][aria-checked = 'true']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label = 'Quảng Ngãi'][aria-checked='true']"))
				.isDisplayed());
		// Cách số 2
		Assert.assertEquals(driver.findElement(radioButton).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(checkbox).getAttribute("aria-checked"), "true");
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
