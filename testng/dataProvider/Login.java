package dataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Login {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	By emailTextbox = By.xpath("//*[@id = 'email']");
	By passwordTextbox = By.xpath("//*[@id = 'pass']");
	By loginButton = By.xpath("//*[@id = 'send2']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "loginData")
	public void TC_01_loginToSystem(String username, String password) {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");

		driver.findElement(emailTextbox).sendKeys(username);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(loginButton).click();

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'col-1']//p")).getText().contains(username));
		driver.findElement(By.xpath("//header[@id = 'header']//span[text() = 'Account']")).click();
		driver.findElement(By.xpath("//header[@id = 'header']//a[text() = 'Log Out']")).click();
	}

	@DataProvider(name = "loginData")
	public Object[][] UserAndPasswordData() {
		return new Object[][] { { "nguyenhuyen01@gmail.com", "123123" }, { "nguyenhuyen02@gmail.com", "123123" },
				{ "nguyenhuyen03@gmail.com", "123123" } };
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
