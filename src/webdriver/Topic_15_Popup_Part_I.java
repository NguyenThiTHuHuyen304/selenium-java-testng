package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Popup_Part_I {
	WebDriver driver;
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Fixed_In_DOM() {
		driver.get("https://ngoaingu24h.vn/");

		By loginPopup = By.cssSelector("div.modal.fade.in div.modal-content");

		// Verify Popup login is undisplayed
//		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

		// Click vào button login
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(3);

		// Verify Popup login is displayed
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		// Input data
		driver.findElement(By.cssSelector("div.modal.fade.in input#account-input")).sendKeys("automationFC");
		driver.findElement(By.cssSelector("div.modal.fade.in input#password-input")).sendKeys("automationFC");

		// Click button
		driver.findElement(By.xpath("//div[@class = 'modal fade in']//button[text() ='Đăng nhập' ]")).click();
		sleepInSecond(3);

		// Verify message
		Assert.assertEquals(
				driver.findElement(By.xpath("//div[@class = 'modal fade in']//div[text() ='Tài khoản không tồn tại!']"))
						.getText(),
				"Tài khoản không tồn tại!");
	}

//	@Test
	public void TC_02_Fixed_In_DOM() {
		driver.get("https://skills.kynaenglish.vn/");

		By loginPopup = By.cssSelector("div#k-popup-account-login");

		// Verify Popup login is undisplayed
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

		// Click button login
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);

		// Verify Popup login is displayed
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		// Input data
		driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(3);

		// Verify message
		Assert.assertEquals(driver.findElement(By.xpath("//div[text()= 'Sai tên đăng nhập hoặc mật khẩu']")).getText(),
				"Sai tên đăng nhập hoặc mật khẩu");

		// Click button close
		driver.findElement(By.cssSelector("div.k-popup-account-mb-content button.k-popup-account-close")).click();

		// Verify Popup is undisplayed
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
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
