package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Popup_Part_II {
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

		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(new FirefoxProfile());
		options.addPreference("dom.webnotifications.enabled", false);

		driver = new FirefoxDriver(options);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Fixed_Not_In_DOM_Tiki() {
		driver.get("https://tiki.vn/");

		By loginPopup = By.cssSelector("div.ReactModal__Content");

		// Verify chưa hiển thị khi chưa click vào button login
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);

		// click button login
		driver.findElement(By.xpath("//span[text()='Tài khoản']")).click();
		sleepInSecond(3);

		// Verify Popup login is display
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		// click login with email link
		driver.findElement(By.xpath("//p[text()='Đăng nhập bằng email']")).click();
		sleepInSecond(3);

		// click button login and empty data input
		driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
		sleepInSecond(3);

		// verify message
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Email không được để trống']")).getText(),
				"Email không được để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Mật khẩu không được để trống']")).getText(),
				"Mật khẩu không được để trống");

		// click button close
		driver.findElement(By.cssSelector("button.btn-close")).click();
		sleepInSecond(3);

		// Verify Popup is undisplay
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);
	}

	@Test
	public void TC_02_Fixed_In_DOM_FB() {
		driver.get("https://www.facebook.com/");

		By signupPopup = By.xpath("//div[text() = \"It's quick and easy.\"]/parent::div/parent::div");

		// Verify Popup is undisplayed
		Assert.assertEquals(driver.findElements(signupPopup).size(), 0);

		// click button 'Create new account'
		driver.findElement(By.xpath("//a[text()='Create new account']")).click();
		sleepInSecond(3);

		// Verify Popup is displayed
		Assert.assertTrue(driver.findElement(signupPopup).isDisplayed());

		// input information login
		driver.findElement(By.name("firstname")).sendKeys("Automaiton");
		driver.findElement(By.name("lastname")).sendKeys("FC");
		driver.findElement(By.name("reg_email__")).sendKeys("0987654321");
		driver.findElement(By.name("reg_passwd__")).sendKeys("ABCabc123!@#");
		new Select(driver.findElement(By.id("day"))).selectByVisibleText("30");
		new Select(driver.findElement(By.id("month"))).selectByVisibleText("Apr");
		new Select(driver.findElement(By.id("year"))).selectByVisibleText("1997");
		driver.findElement(By.xpath("//label[text()='Female']/following-sibling::input")).click();
		
		// Click button close
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div/img")).click();
		sleepInSecond(3);

		// Verify Popup is undisplayed
		Assert.assertEquals(driver.findElements(signupPopup).size(), 0);
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
