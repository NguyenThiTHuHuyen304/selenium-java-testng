package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Action_Part_I {
	WebDriver driver;
	Actions action;
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
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Tooltip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip")).getText(),
				"We ask for your age only for statistical purposes.");
	}

//	@Test
	public void TC_02_Myntra() {
		driver.get("https://www.myntra.com");
		action.moveToElement(driver.findElement(By.xpath("//a[@class='desktop-main' and text() = 'Kids']"))).perform();

		driver.findElement(By.xpath("//a[@class='desktop-categoryName' and text() = 'Home & Bath']")).click();

		// verify
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Kids Home Bath']")).getText(),
				"Kids Home Bath");
	}

	@Test
	public void TC_03_Fahasa() {
		driver.get("https://www.fahasa.com/");
		action.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
		action.moveToElement(driver.findElement(By.xpath("//a/span[text()='Sách Trong Nước']"))).perform();
		driver.findElement(By.xpath("//div[@class = 'fhs_column_stretch']//a[text()='Quản Trị - Lãnh Đạo']")).click();
		sleepInSecond(3);

		// verify
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class ='breadcrumb']//strong")).isDisplayed());
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
