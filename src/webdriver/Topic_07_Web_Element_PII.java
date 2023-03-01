package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Web_Element_PII {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	By myAcc = By.xpath("//div[@class='footer']//a[text()= 'My Account']");
	By emailTextBox = By.id("email");
	By passwordTextBox = By.id("pass");
	By loginButton = By.id("send2");
	By messEmail = By.xpath("//input[@id='email']/parent::div[@class='input-box']//div[@class='validation-advice']");
	By messPass = By.xpath("//input[@id='pass']/parent::div[@class='input-box']//div[@class='validation-advice']");

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

	@Test
	public void TC_01_empty() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(myAcc).click();
		driver.findElement(emailTextBox).clear();
		driver.findElement(passwordTextBox).clear();
		driver.findElement(loginButton).click();

		// Verify
		Assert.assertEquals(driver.findElement(messEmail).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(messPass).getText(), "This is a required field.");
	}

	@Test
	public void TC_02_invalidEmail() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(myAcc).click();
		driver.findElement(emailTextBox).sendKeys("123434234@12312.123123");
		sleepInSecond(2);
		driver.findElement(passwordTextBox).sendKeys("123456");
		sleepInSecond(2);
		driver.findElement(loginButton).click();

		// Verify
		Assert.assertEquals(driver.findElement(messEmail).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_invalidPassword() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(myAcc).click();
		driver.findElement(emailTextBox).sendKeys("automation@gmail.com");
		sleepInSecond(2);
		driver.findElement(passwordTextBox).sendKeys("123");
		sleepInSecond(2);
		driver.findElement(loginButton).click();

		// Verify
		Assert.assertEquals(driver.findElement(messPass).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_incorrect() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(myAcc).click();
		driver.findElement(emailTextBox).sendKeys("automation@gmail.com");
		sleepInSecond(2);
		driver.findElement(passwordTextBox).sendKeys("123123123");
		sleepInSecond(2);
		driver.findElement(loginButton).click();

		// Verify
		Assert.assertEquals(driver.findElement(By.xpath("//ul[@class='messages']")).getText(),
				"Invalid login or password.");
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
