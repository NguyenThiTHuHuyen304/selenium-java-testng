package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Popup_Part_III {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAddress = "testdemo" + getRandomNumber() + "@gmail.com";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Random_Popup_In_DOM() {
		driver.get("https://www.javacodegeeks.com/");
		sleepInSecond(15);

		By lePopup = By.cssSelector("div.lepopup-form-inner>div[data-type='rectangle']");

		if (driver.findElement(lePopup).isDisplayed()) {

			// input email address
			driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAddress);
			driver.findElement(By.xpath("//span[text() = 'Get the Books' or text()= 'OK']")).click();
			sleepInSecond(10);

			// Verify message
			Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText()
					.contains("Thank you!"));
			Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>p")).getText()
					.contains("Your sign-up request was successful. We will contact you shortly. Please "));

			sleepInSecond(15);
		}

		String articleName = "Agile Testing Explained";

		driver.findElement(By.cssSelector("input#search-input")).sendKeys(articleName);
		sleepInSecond(3);
		driver.findElement(By.cssSelector("button#search-submit")).click();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector("ul#posts-container>li:first-child h2 a")).getText(),
				articleName);
	}

//	@Test
	public void TC_02_Random_Popup_In_DOM() {
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(20);

		if (driver.findElement(By.cssSelector("div.thrv_wrapper.thrv-columns")).isDisplayed()) {
			driver.findElement(By.cssSelector("svg.tcb-icon")).click();
			sleepInSecond(3);
		}

		driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")).click();
		sleepInSecond(5);
		Assert.assertEquals(driver.getTitle(), "Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");
	}

	@Test
	public void TC_03_Random_Popup_Not_In_DOM() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(5);

		By popupBy = By.cssSelector("section#popup");

		if (driver.findElements(popupBy).size() > 0 && driver.findElements(popupBy).get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("input#popup-name")).sendKeys("Nguyen Thi Thu Huyen");
			driver.findElement(By.cssSelector("input#popup-email")).sendKeys("huyen23813@gmail.com");
			driver.findElement(By.cssSelector("input#popup-phone")).sendKeys("0987654321");
			sleepInSecond(3);

			driver.findElement(By.cssSelector("button#close-popup")).click();
			sleepInSecond(3);

		}

		driver.findElement(By.xpath("//a[text() = 'Tất cả khóa học']")).click();
		sleepInSecond(3);

		String courseName = "Khóa học Thiết kế và Thi công Hệ thống BMS";
		driver.findElement(By.id("search-courses")).sendKeys(courseName);
		driver.findElement(By.id("search-course-button")).click();
		sleepInSecond(5);

		Assert.assertEquals(driver.findElement(By.cssSelector("div.course-content.hachium h4")).getText(), courseName);

	}

	public int getRandomNumber() {
		return new Random().nextInt(9999);
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
