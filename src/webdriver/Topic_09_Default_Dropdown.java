package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Default_Dropdown {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firtName, lastName, emailTextbox, companyName, password, day, month, year;
	String countryName, provinceName, CityName, addressName, postalName, phoneNumber;

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

		firtName = "Automation";
		lastName = "FC";
		emailTextbox = "AutomationFC" + randomNumber() + "@gmail.com";
		companyName = "CMC Technology and Solution";
		password = "123Lalala!";
		day = "30";
		month = "April";
		year = "1997";
		countryName = "United States";
		provinceName = "Florida";
		CityName = "Miami";
		addressName = "123 PO Box";
		postalName = "33111";
		phoneNumber = "+13055555584";

	}

	@Test
	public void TC_01_Register_New_Account() {
		driver.get("https://demo.nopcommerce.com/");

		// input data
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		sleepInSecond(3);
		driver.findElement(By.id("FirstName")).sendKeys(firtName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		new Select(driver.findElement(By.name("DateOfBirthDay"))).selectByVisibleText(day);
		new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByVisibleText(month);
		new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText(year);
		driver.findElement(By.id("Email")).sendKeys(emailTextbox);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

		// Click button
		driver.findElement(By.id("register-button")).click();
		sleepInSecond(5);

		// Verify register complete
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");

		// Login
		driver.findElement(By.xpath("//a[text()='Log in']")).click();
		driver.findElement(By.id("Email")).sendKeys(emailTextbox);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.xpath("//button[text()='Log in']")).click();

		// My Account
		driver.findElement(By.xpath("//div[@class='header-links']//a[text()='My account']")).click();
		Assert.assertEquals(
				new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(), day);
		Assert.assertEquals(
				new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText(), month);
		Assert.assertEquals(
				new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(), year);
	}

	@Test
	public void TC_02_Add_Address() {
		driver.findElement(By.xpath("//div[@class='listbox']//a[text()='Addresses']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//button[text()='Add new']")).click();
		sleepInSecond(3);

		// input data
		driver.findElement(By.id("Address_FirstName")).sendKeys(firtName);
		driver.findElement(By.id("Address_LastName")).sendKeys(lastName);
		driver.findElement(By.id("Address_Email")).sendKeys(emailTextbox);
		driver.findElement(By.id("Address_Company")).sendKeys(companyName);
		new Select(driver.findElement(By.id("Address_CountryId"))).selectByVisibleText(countryName);
		new Select(driver.findElement(By.id("Address_StateProvinceId"))).selectByVisibleText(provinceName);

		Assert.assertFalse(new Select(driver.findElement(By.id("Address_CountryId"))).isMultiple());
		Assert.assertFalse(new Select(driver.findElement(By.id("Address_StateProvinceId"))).isMultiple());

		driver.findElement(By.id("Address_City")).sendKeys(CityName);
		driver.findElement(By.id("Address_Address1")).sendKeys(addressName);
		driver.findElement(By.id("Address_ZipPostalCode")).sendKeys(postalName);
		driver.findElement(By.id("Address_PhoneNumber")).sendKeys(phoneNumber);

		// click button
		driver.findElement(By.xpath("//button[text()='Save']")).click();

		// Verify
		Assert.assertEquals(driver.findElement(By.cssSelector(".name")).getText(), firtName + " " + lastName);
		Assert.assertEquals(driver.findElement(By.cssSelector(".email")).getText(), "Email:" + " " + emailTextbox);
		Assert.assertEquals(driver.findElement(By.cssSelector(".phone")).getText(),
				"Phone number:" + " " + phoneNumber);
		Assert.assertEquals(driver.findElement(By.cssSelector(".company")).getText(), companyName);
		Assert.assertEquals(driver.findElement(By.cssSelector(".address1")).getText(), addressName);
		Assert.assertTrue(driver.findElement(By.cssSelector(".city-state-zip")).getText().contains(CityName));
		Assert.assertTrue(driver.findElement(By.cssSelector(".city-state-zip")).getText().contains(provinceName));
		Assert.assertTrue(driver.findElement(By.cssSelector(".city-state-zip")).getText().contains(postalName));
		Assert.assertEquals(driver.findElement(By.cssSelector(".country")).getText(), countryName);
	}

	public int randomNumber() {
		Random random = new Random();
		return random.nextInt(9999);
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
