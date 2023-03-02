package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstName = "Automation";
	String lastName = "FC";
	String passportNumber = "40517-402-96-7202";
	String comment = "This is generated data \nof real people";
	String password = "Password123!!!";

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

	@Test
	public void TC_01_Create_New_Employee() {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		sleepInSecond(3);
		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		String employeeID = driver
				.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"))
				.getAttribute("value");
		String username = "abc" + employeeID;
		driver.findElement(By.xpath("//p[text()='Create Login Details']//following-sibling::div//span")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input"))
				.sendKeys(username);
		driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input"))
				.sendKeys(password);
		driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input"))
				.sendKeys(password);
		driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
		sleepInSecond(8);

		// Verify
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"))
						.getAttribute("value"),
				employeeID);
		// Click Immigration
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button/i")).click();
		sleepInSecond(2);

		driver.findElement(By.xpath("//label[text()='Number']//parent::div/following-sibling::div/input"))
				.sendKeys(passportNumber);
		driver.findElement(By.xpath("//label[text()='Comments']//parent::div/following-sibling::div/textarea"))
				.sendKeys(comment);
		driver.findElement(By.xpath("//button[contains(string(), 'Save')]")).click();
		sleepInSecond(7);

		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);

		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Number']//parent::div/following-sibling::div/input"))
						.getAttribute("value"),
				passportNumber);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Comments']//parent::div/following-sibling::div/textarea"))
						.getAttribute("value"),
				comment);
		driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		sleepInSecond(5);

		// Login again
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//span[text() = 'My Info']")).click();
		sleepInSecond(3);

		// Verify
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"))
						.getAttribute("value"),
				employeeID);
		// Click Immigration
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(5);
		driver.findElement(By.cssSelector(".bi-pencil-fill")).click();
		sleepInSecond(3);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Number']//parent::div/following-sibling::div/input"))
						.getAttribute("value"),
				passportNumber);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Comments']//parent::div/following-sibling::div/textarea"))
						.getAttribute("value"),
				comment);

	}

	@Test
	public void TC_02_Verify_Employee() {

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
