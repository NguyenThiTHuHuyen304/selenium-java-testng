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

public class Topic_07_Web_Element_PIII {
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

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		emailAddress = "huyen" + rand.nextInt(9999) + "@gmail.com";
		firstName = "Huyen";
		middleName = "Thi Thu";
		lastName = "Nguyen";
		fullname = firstName + " " + middleName + " " + lastName;
	}

	@Test
	public void TC_Create_New_Account() {
		driver.get("http://live.techpanda.org/");
		sleepInSecond(2);
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		sleepInSecond(2);
		driver.findElement(By.xpath("//a[@class='button']//span[text()='Create an Account']")).click();
		sleepInSecond(2);

		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("middlename")).sendKeys(middleName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys("Huyen304!");
		driver.findElement(By.id("confirmation")).sendKeys("Huyen304!");
		driver.findElement(By.xpath("//button[@class='button']//span[text()='Register']")).click();
		sleepInSecond(2);

		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg")).getText(),
				"Thank you for registering with Main Website Store.");

		String contactInf = driver
				.findElement(By.xpath(
						"//h3[text()='Contact Information']/parent::div[@class='box-title']/following-sibling::div/p"))
				.getText();
		Assert.assertTrue(contactInf.contains(fullname));
		Assert.assertTrue(contactInf.contains(emailAddress));

		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		sleepInSecond(4);
		driver.findElement(By.xpath("//div[@id='header-account']//a[@title='Log Out']")).click();
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src, 'logo.png')]")).isDisplayed());
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
