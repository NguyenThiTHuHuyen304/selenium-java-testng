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

public class Topic_22_Wait_PIII_Implicit_Wait {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAddress;
	String fullname;
	String firstName;
	String middleName;
	String lastName;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		rand = new Random();
		emailAddress = "huyen" + rand.nextInt(9999) + "@gmail.com";
		firstName = "Huyen";
		middleName = "Thi Thu";
		lastName = "Nguyen";
		fullname = firstName + " " + middleName + " " + lastName;
	}

//	@Test
	public void TC_01_Timeout_Less_Than_5_Seconds() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
	}

//	@Test
	public void TC_02_Timeout_equals_5_Seconds() {

		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
	}

//	@Test
	public void TC_03_Timeout_More_Than_5_Seconds() {

		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//button[text() = 'Start']")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_04_Create_New_Account() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[@class='button']//span[text()='Create an Account']")).click();

		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("middlename")).sendKeys(middleName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys("Huyen304!");
		driver.findElement(By.id("confirmation")).sendKeys("Huyen304!");
		driver.findElement(By.xpath("//button[@class='button']//span[text()='Register']")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg")).getText(),
				"Thank you for registering with Main Website Store.");

		String contactInf = driver
				.findElement(By.xpath(
						"//h3[text()='Contact Information']/parent::div[@class='box-title']/following-sibling::div/p"))
				.getText();
		Assert.assertTrue(contactInf.contains(fullname));
		Assert.assertTrue(contactInf.contains(emailAddress));

		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//div[@id='header-account']//a[@title='Log Out']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src, 'logo.png')]")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
