package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_PI_Element_Status {
	WebDriver driver;
	WebDriverWait explicitWait;
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

		// Tìm kiếm element/elements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Tìm kiếm trạng thái element: visible/invisible...
		explicitWait = new WebDriverWait(driver, 30);
	}

//	@Test
	public void TC_01_Visible_Displayed() {

		// Điều kiện 1: Element có trên UI và có trong cây HTML
		driver.get("https://www.facebook.com/");

		// Chờ cho element email hiển thị rồi mới sendkey
		// chờ trong thời gian 30s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
		driver.findElement(By.cssSelector("input#email")).sendKeys("huyen@gmail.com");
	}

//	@Test
	public void TC_02_Invisible_Undisplayed_Case_I() {
		// Điều kiện 2: ELement không hiển thị (không nhìn thấy) trên UI nhưng vẫn có
		// trong cây HTML
		driver.get("https://www.facebook.com/");

		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();

		// Comfirm Email textbox is undisplayed
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

		driver.findElement(By.cssSelector("input[name='reg_email__']")).sendKeys("huyen@gmail.com");

		// Comfirm Email textbox is displayed
		explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']")).sendKeys("huyen@gmail.com");
	}

//	@Test
	public void TC_02_Invisible_Undisplayed_Case_II() {
		// Điều kiện 3: ELement không hiển thị (không nhìn thấy) trên UI nhưng vẫn có
		// trong cây HTML
		driver.get("https://www.facebook.com/");

		// Confirm Email textbox is undisplayed
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

	}

//	@Test
	public void TC_03_Presence_Case_I() {

		// Điều kiện 1: Element có trên UI và có trong cây HTML
		driver.get("https://www.facebook.com/");
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#email")));

	}

//	@Test
	public void TC_03_Presence_Case_II() {
		// Điều kiện 2: ELement không hiển thị (không nhìn thấy) trên UI nhưng vẫn có
		// trong cây HTML
		driver.get("https://www.facebook.com/");
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		explicitWait.until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_04_Staleness() {
		// Có trong HTML và sau đó apply đk3
		driver.get("https://www.facebook.com/");

		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		explicitWait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[name = 'reg_email_confirmation__']")));

		// B1: Element phải có trong HTML
		WebElement confirmEmailTextbox = driver.findElement(By.cssSelector("input[name = 'reg_email_confirmation__']"));

		driver.findElement(By.xpath("//div[text() = 'Sign Up']/parent::div/preceding-sibling::img")).click();

		// Wait cho confirm email staleness --> chạy nhanh
		explicitWait.until(ExpectedConditions.stalenessOf(confirmEmailTextbox));

		// cách này làm chạy lâu hơn
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("input[name = 'reg_email_confirmation__']")));
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
