package webdriver;

import java.io.File;

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

public class Topic_25_Wait_PVI_Explicit_Excercise {
	WebDriver driver;
	WebDriverWait explicitWait;
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
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();

		// Trạng thái element visible/invisible/presence/click/select...
		explicitWait = new WebDriverWait(driver, 30);

		// Tìm kiếm element/elements
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

//	@Test
	public void TC_01() {
		driver.get(
				"https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

		explicitWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")));

		WebElement textToday = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		System.out.println(textToday.getText());
		Assert.assertEquals(textToday.getText(), "No Selected Dates to display.");

		// Wait cho ngày cần click được clickable
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text() = '9']/parent::td")));

		// Click vào ngày hiện tai
		driver.findElement(By.xpath("//a[text() = '9']/parent::td")).click();

		// Wait cho loading icon biến mất
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("div:not([style = 'display:none;'])>div.raDiv")));

		// Wait cho ngày được chọn selected
		explicitWait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//a[text() = '9']/parent::td[@class = 'rcSelected']")));

		// Verify lại giá trị textToday
		textToday = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		Assert.assertEquals(textToday.getText(), "Thursday, March 9, 2023");
	}

	@Test
	public void TC_02_Upload() {

		driver.get("https://gofile.io/welcome");

		// Wait cho icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.spinner-border")));

		By uploadFile = By.cssSelector("button[type = 'button']");

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(uploadFile));
		driver.findElement(uploadFile).click();

		// Wait cho icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.spinner-border")));

		driver.findElement(By.xpath("//input[@type = 'file']"))
				.sendKeys(miennuiPhotoPath + "\n" + mienbienPhotoPath + "\n" + thanhphoPhotoPath);

		// Wait cho icon loading biến mất
		explicitWait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#mainUpload div.spinner-border")));

		// Wait cho tất cả progress-bar biến mất
		explicitWait.until(
				ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress-bar"))));

		// Wait cho sucess message hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@class = 'row justify-content-center mainUploadSuccess'] //div[text() = 'Your files have been successfully uploaded']")));

		// Click download link
		driver.findElement(By.xpath("//div[ @class = 'col-6 text-center']//a")).click();

		// Wait cho icon loading biến mất
		explicitWait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.col-auto div.spinner-border")));

		// Verify dowload
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + miennuiPhoto
				+ "']/parent::a/parent::div/following-sibling::div//span[text() = 'Download']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + mienbienPhoto
				+ "']/parent::a/parent::div/following-sibling::div//span[text() = 'Download']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + thanhphoPhoto
				+ "']/parent::a/parent::div/following-sibling::div//span[text() = 'Download']")).isDisplayed());

		// Verify play
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + miennuiPhoto
				+ "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'filesContentOptionPlay')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + mienbienPhoto
				+ "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'filesContentOptionPlay')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text() = '" + thanhphoPhoto
				+ "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'filesContentOptionPlay')]"))
				.isDisplayed());

	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
