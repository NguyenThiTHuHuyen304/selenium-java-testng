package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_26_Wait_PVII_Mix_Implicit_Explicit {
	WebDriver driver;
	WebDriverWait expciliWait;
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
	}

//	@Test
	public void TC_01_Element_Found() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		expciliWait = new WebDriverWait(driver, 15);

		driver.get("https://www.facebook.com/");

		// Implicit
		System.out.println("Start: " + getDateTimeNow());
		driver.findElement(By.xpath("//button[text() = 'Log in']"));
		System.out.println("End: " + getDateTimeNow());

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		expciliWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Log in']")));
		System.out.println("End: " + getDateTimeNow());

	}

	@Test
	public void TC_02_Element_Not_Found_Only_Implicit() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		expciliWait = new WebDriverWait(driver, 0);
		driver.get("https://www.facebook.com/");

		// Implicit
//		System.out.println("Start: " + getDateTimeNow());
//		driver.findElement(By.xpath("//button[text() = 'Selenium']"));
//		System.out.println("End: " + getDateTimeNow());

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		try {
			expciliWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Selenium']")));
		} catch (Exception e) {
			System.out.println("End: " + getDateTimeNow());
		}

	}

//	@Test
	public void TC_03_Element_Not_Found_Implicit_And_Explicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expciliWait = new WebDriverWait(driver, 10);

		driver.get("https://www.facebook.com/");

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		try {
			expciliWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Selenium']")));
		} catch (Exception e) {
			System.out.println("End: " + getDateTimeNow());
		}

	}

//	@Test
	public void TC_04_Element_Not_Found_Only_Explicit_By() {

		expciliWait = new WebDriverWait(driver, 5);

		driver.get("https://www.facebook.com/");

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		try {
			expciliWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Selenium']")));
		} catch (Exception e) {
			System.out.println("End: " + getDateTimeNow());
		}
	}

//	@Test
	public void TC_04_Element_Not_Found_Only_Explicit_Element() {
		expciliWait = new WebDriverWait(driver, 5);

		driver.get("https://www.facebook.com/");

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		try {
			// Nếu như element được tìm thấy thì sẽ chạy tiếp đoạn wait visible
			// Nếu như element không tìm thấy thì ko chạy --> fail ngay luôn được
			// findElement
			expciliWait.until(
					ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text() = 'Selenium']"))));
		} catch (Exception e) {
			System.out.println("End: " + getDateTimeNow());
		}
	}

//	@Test
	public void TC_05_Element_Not_Found_Only_Explicit_Element() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expciliWait = new WebDriverWait(driver, 5);

		driver.get("https://www.facebook.com/");

		// Explicit
		System.out.println("Start: " + getDateTimeNow());
		try {
			// Nếu như element được tìm thấy thì sẽ chạy tiếp đoạn wait visible
			// Nếu như element không tìm thấy thì ko chạy --> fail ngay luôn được
			// findElement
			expciliWait.until(
					ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text() = 'Selenium']"))));
		} catch (Exception e) {
			System.out.println("End: " + getDateTimeNow());
		}
	}

	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
