package parameter;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MultipleEnvironment {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	By emailTextbox = By.xpath("//*[@id = 'email']");
	By passwordTextbox = By.xpath("//*[@id = 'pass']");
	By loginButton = By.xpath("//*[@id = 'send2']");

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(@Optional("chrome") String browserName) {
		System.out.println("Browser name= " + browserName);

		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("edge")) {
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("Browers name is invalid.");
		}

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Parameters("environment")
	@Test
	public void TC_01_login(@Optional("live") String environmentName) {
		driver.get(getEnvironmentUrl(environmentName) + "/index.php/customer/account/login/");

		driver.findElement(emailTextbox).sendKeys("nguyenhuyen03@gmail.com");
		driver.findElement(passwordTextbox).sendKeys("123123");
		driver.findElement(loginButton).click();

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'col-1']//p")).getText()
				.contains("nguyenhuyen03@gmail.com"));
		driver.findElement(By.xpath("//header[@id = 'header']//span[text() = 'Account']")).click();
		driver.findElement(By.xpath("//header[@id = 'header']//a[text() = 'Log Out']")).click();
	}

	public String getEnvironmentUrl(String environmentName) {
		String url = null;
		if (environmentName.toLowerCase().equals("dev")) {
			url = "http://dev.live.techpanda.org";
		} else if (environmentName.toLowerCase().equals("testing")) {
			url = "http://testing.live.techpanda.org";
		} else if (environmentName.toLowerCase().equals("staging")) {
			url = "http://staging.live.techpanda.org";
		} else if (environmentName.toLowerCase().equals("live")) {
			url = "http://live.techpanda.org";
		} else {
			throw new RuntimeException("Environment name is invalid.");
		}
		return url;
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
