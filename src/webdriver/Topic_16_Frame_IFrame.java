package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Frame_IFrame {
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
	public void TC_01_IFrame() {
		driver.get("https://skills.kynaenglish.vn/");
		sleepInSecond(3);

		Assert.assertTrue(driver.findElement(By.cssSelector("div.fanpage iframe")).isDisplayed());
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		Assert.assertEquals(
				driver.findElement(By.xpath("//a[text() = 'Kyna.vn']/parent::div/following-sibling::div")).getText(),
				"165K likes");

		// Quay lại trang trước đó
		driver.switchTo().defaultContent();

		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
		driver.findElement(By.cssSelector("div.button_bar")).click();
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("Nguyen Thi Thu Huyen");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987654321");
		new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.xpath("//label[text() = 'Tin nhắn']/parent::label/following-sibling::textarea"))
				.sendKeys("Test");

		// Quay lại trang trước đó
		driver.switchTo().defaultContent();
		String keyword = "Excel";
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(5);

		List<WebElement> courseNameList = driver.findElements(By.cssSelector("div.content>h4"));

		for (WebElement course : courseNameList) {
			Assert.assertTrue(course.getText().contains(keyword));
		}
	}

//	@Test
	public void TC_02() {
		// Trang 1 chứa iframe 2
		driver.switchTo().frame("2");

		// Trang 2 chứa iframe 3
		driver.switchTo().frame("3");

		// Quay từ 3 về 2 (không hỗ trợ quay về 1)
		driver.switchTo().parentFrame();

		// Quay từ 2 về 1
		driver.switchTo().defaultContent();
	}

	@Test
	public void TC_03_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		sleepInSecond(5);

		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name = 'login_page']")));
		driver.findElement(By.cssSelector("input.form-control")).sendKeys("huyenmin");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(5);

		// swich to default content
		driver.switchTo().defaultContent();

		Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
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
