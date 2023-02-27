package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Excercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	By myAcc = By.xpath("//div[@class='footer']//a[text()='My Account']");
	By createAcc = By.xpath("//a[@title='Create an Account']");

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
	public void TC01_Url() {
		//Truy cap vao trang
		driver.get("http://live.techpanda.org/");
		
		//Click MyAccount
		driver.findElement(myAcc).click();
		sleepInSecond(3);
		
		//verify url login page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		
		//Click Create An Account
		driver.findElement(createAcc).click();
		sleepInSecond(3);
		
		//Verify url Register Page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test   
	public void TC02_Title() {
		//Truy cap trang
		driver.get("http://live.techpanda.org/");
		
		//click MyAccount on footer
		driver.findElement(myAcc).click();
		sleepInSecond(3);
		
		//Verify title Login Page
		Assert.assertEquals(driver.getTitle(),"Customer Login");
		
		//Click Create an account button
		driver.findElement(createAcc).click();
		sleepInSecond(3);
		
		//Verify title Register page
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}

	@Test
	public void TC03_Navigate() {
		//truy cap trang
		driver.get("http://live.techpanda.org/");
		
		//Click My Account on footer
		driver.findElement(myAcc).click();
		sleepInSecond(3);
		
		//Click Create An Account button
		driver.findElement(createAcc).click();
		sleepInSecond(3);
		
		//Verify URL Register Page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		
		//Back Login Page
		driver.navigate().back();
		sleepInSecond(2);
		
		//Verify URL Login Page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		
		//Forward Register Page
		driver.navigate().forward();
		sleepInSecond(2);
		
		//Verify title Register Page
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	@Test
	public void TC04_Page_Source_HTML() {
		
		//Truy cap trang
		driver.get("http://live.techpanda.org/");
		
		//Click My Account Link on footer
		driver.findElement(myAcc).click();
		sleepInSecond(3);
		
		//Verify login page "Login or Create an Account"
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
		
		//Click Create an account button
		driver.findElement(createAcc).click();
		sleepInSecond(3);
		
		//Verify Register page chua text: "Create an Account"
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
