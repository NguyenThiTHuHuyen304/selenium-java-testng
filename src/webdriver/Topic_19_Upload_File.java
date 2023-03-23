package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_Upload_File {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	String miennuiPhoto = "Mien Nui.jpg";
	String mienbienPhoto = "Mien Bien.jpg";
	String thanhphoPhoto = "Thanh Pho.jpg";

	String miennuiPhotoPath = projectPath + File.separator + "uploadfiles" + File.separator + miennuiPhoto;
	String mienbienPhotoPath = projectPath + File.separator + "uploadfiles" + File.separator + mienbienPhoto;
	String thanhphoPhotoPath = projectPath + File.separator + "uploadfiles" + File.separator + thanhphoPhoto;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver");
		}

		driver = new EdgeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_One_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		By uploadFile = By.xpath("//input[@type = 'file']");

		// Load từng file 1
		driver.findElement(uploadFile).sendKeys(miennuiPhotoPath);
		sleepInSecond(2);

		driver.findElement(uploadFile).sendKeys(mienbienPhotoPath);
		sleepInSecond(2);

		driver.findElement(uploadFile).sendKeys(thanhphoPhotoPath);
		sleepInSecond(2);

		// Verify các file được load thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + miennuiPhoto + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + mienbienPhoto + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + thanhphoPhoto + "']")).isDisplayed());

		// Click upload cho từng file
		List<WebElement> allStartButtons = driver
				.findElements(By.cssSelector("table.table-striped button.btn-primary"));
		for (WebElement start : allStartButtons) {
			start.click();
			sleepInSecond(1);
		}

		// Verify các file được upload lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + miennuiPhoto + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + mienbienPhoto + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + thanhphoPhoto + "']")).isDisplayed());

		// Verify các hình này upload lên là hình thực
		Assert.assertTrue(isImageLoaded("//a[@title = '" + miennuiPhoto + "']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title = '" + mienbienPhoto + "']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title = '" + thanhphoPhoto + "']/img"));
	}

	@Test
	public void TC_02_Multiple_Per_Time() {

		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		By uploadFile = By.xpath("//input[@type = 'file']");

		// Load 3 file 1 lúc
		driver.findElement(uploadFile).sendKeys(miennuiPhotoPath + "\n" + mienbienPhotoPath + "\n" + thanhphoPhotoPath);
		sleepInSecond(2);

		// Verify các file được load thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + miennuiPhoto + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + mienbienPhoto + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class ='name' and text() = '" + thanhphoPhoto + "']")).isDisplayed());

		// Click upload cho từng file
		List<WebElement> allStartButtons = driver
				.findElements(By.cssSelector("table.table-striped button.btn-primary"));
		for (WebElement start : allStartButtons) {
			start.click();
			sleepInSecond(3);
		}

		// Verify các file được upload lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + miennuiPhoto + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + mienbienPhoto + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '" + thanhphoPhoto + "']")).isDisplayed());

		// Verify các hình này upload lên là hình thực
		Assert.assertTrue(isImageLoaded("//a[@title = '" + miennuiPhoto + "']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title = '" + mienbienPhoto + "']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title = '" + thanhphoPhoto + "']/img"));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	private WebElement getElement(String locator) {
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
