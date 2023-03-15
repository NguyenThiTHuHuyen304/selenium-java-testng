package webdriver;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Button_Radio_Textbox_Excersice {
	WebDriver driver;
	Alert alert;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String authenFirefox = projectPath + "\\autoIT\\authen_firefox.exe";
	String authenChrome = projectPath + "\\autoIT\\authen_chrome.exe";
	String username = "admin";
	String password = "admin";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Button() {
		// Truy cập vào trang
		driver.get("https://www.fahasa.com/customer/account/create");

		// Navigate qua tab đăng nhập
		driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();

		By loginButton = By.cssSelector("button.fhs-btn-login");

		// Verify button Login is Disable
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());

		String loginButtonBackground = driver.findElement(loginButton).getCssValue("background-image");
		System.out.println(loginButtonBackground);

		// Verify button login is background màu xám
		Assert.assertTrue(loginButtonBackground.contains("rgb(224, 224, 224)"));

		// Input data in email/password textbox
		driver.findElement(By.id("login_username")).sendKeys("AutomationFC@gmail.com");
		driver.findElement(By.id("login_password")).sendKeys("ABCabc123!@#");

		// Verify button loggin is Enable
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());

		// Verify background color button loggin is red
		loginButtonBackground = driver.findElement(loginButton).getCssValue("background-color");
		// convert qua hex
		Color loginButtonBackgroundColor = Color.fromString(loginButtonBackground);
		Assert.assertEquals(loginButtonBackgroundColor.asHex().toUpperCase(), "#C92127");

	}

//	@Test
	public void TC_02_Default_Checkbox_Radio() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

		// Chọn
		checkToCheckbox(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		Assert.assertTrue(
				driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"))
						.isSelected());

		// bỏ chọn
		uncheckToCheckbox(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));

		Assert.assertFalse(
				driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"))
						.isSelected());

		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		checkToCheckbox(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"))
				.isSelected());

	}

//	@Test
	public void TC_03_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(10);

		// - 1: Có thể switch và tương tác luôn
//		alert = driver.switchTo().alert();

		// 2- Cần wait trước khi tương tác
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());

		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		sleepInSecond(2);
		assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}

//	@Test
	public void TC_04_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(10);

		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}

//	@Test
	public void TC_05_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(10);

		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		assertEquals(alert.getText(), "I am a JS prompt");

		String courseName = "Fullstack Selenium Java";
		alert.sendKeys(courseName);
		sleepInSecond(2);
		alert.accept();
		assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + courseName);
	}

//	@Test
	public void TC_06_Authentication_Alert_I() {
		// Truyền trực tiếp Username/password vào trong Url này --> Tự động SignIn luôn
		// http:// +Username : Password @the-internet.herokuapp.com/basic_auth

		driver.get("https://the-internet.herokuapp.com/");
		String urlBasicAuth = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");

		driver.get(passUserAndPassToUrl(urlBasicAuth, "admin", "admin"));
		sleepInSecond(1);
		Assert.assertTrue(driver
				.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]"))
				.isDisplayed());
	}

	@Test
	public void TC_06_Authentiaction_Alert_II() throws IOException {
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { authenFirefox, username, password });
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] { authenChrome, username, password });
		}

		driver.get("http://the-internet.herokuapp.com/basic_auth");
	}

	public String passUserAndPassToUrl(String url, String username, String password) {
		String[] arrayUrl = url.split("//");
		return arrayUrl[0] + "//" + username + ":" + password + "@" + arrayUrl[1];
	}

	public void checkToCheckbox(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}

	public void uncheckToCheckbox(By by) {
		if (driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
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
