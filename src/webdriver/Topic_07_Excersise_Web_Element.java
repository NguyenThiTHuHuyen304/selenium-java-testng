 package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Excersise_Web_Element {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	By emailTextbox = By.cssSelector("#mail");
	By ageUnder18 = By.cssSelector("#under_18");
	By educationArea = By.cssSelector("#edu");
	By nameUser5 = By.xpath("//h5[text() = 'Name: User5']");

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

//	@Test
	public void TC01_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Check email textbox
		if (driver.findElement(emailTextbox).isDisplayed()) {
			driver.findElement(emailTextbox).sendKeys("huyen23813@gmail.com");
			System.out.println("Email textbox is Displayed");
		} else {
			System.out.println("Email texbox is not Displayed");
		}
		
		//Check hiển thị Age(Under 18) radio
		if (driver.findElement(ageUnder18).isDisplayed()) {
			driver.findElement(ageUnder18).click();
			System.out.println("Age Under 18 radio is Displayed");
		} else {
			System.out.println("Age Under 18 radio is not Displayed");
		}
		
		//Check hien thi Education Area
		if (driver.findElement(educationArea).isDisplayed()) {
			driver.findElement(educationArea).sendKeys("University Engineering and Technology");
			System.out.println("Education Area is Displayed");
		} else {
			System.out.println("Education Area is not Displayed");
		}
		
		//Check Name : User5 not displayed
		if (driver.findElement(nameUser5).isDisplayed()) {
			System.out.println("Name: User5 is Displayed");
		} else {
			System.out.println("Name: User5 is not Displayed");
		}
	}
	

//	@Test   
	public void TC02_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Check Email Textbox
		if (driver.findElement(emailTextbox).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Check Age Radio
		if (driver.findElement(ageUnder18).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Check Education Area
		if (driver.findElement(educationArea).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Job Role 01
		if (driver.findElement(By.cssSelector("#job1")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Job Role 02
		if (driver.findElement(By.cssSelector("#job2")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Interests (Development) Checkbox
		if (driver.findElement(By.cssSelector("#development")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Elementis Disabled");
		}
		
		//Slider01
		if (driver.findElement(By.cssSelector("#slider-1")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Check Disable
		
		//Password
		if (driver.findElement(By.cssSelector("#disable_password")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Age (Radio button is disable)
		if (driver.findElement(By.cssSelector("#radio-disabled")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Biography
		if (driver.findElement(By.cssSelector("#bio")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//job Role 03
		if (driver.findElement(By.id("job3")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled ");
		}
		
		//Interest (Checkbox is disabled)
		if (driver.findElement(By.id("radio-disabled")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
		
		//Slider 02 (Disabled)
		if (driver.findElement(By.id("slider-2")).isEnabled()) {
			System.out.println("Element is Enabled");
		} else {
			System.out.println("Element is Disabled");
		}
	}
	

//	@Test
	public void TC03_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		Assert.assertFalse(driver.findElement(ageUnder18).isSelected());
		Assert.assertFalse(driver.findElement(By.id("java")).isSelected());
		
		driver.findElement(ageUnder18).click();
		driver.findElement(By.id("java")).click();
		
		Assert.assertTrue(driver.findElement(By.id("java")).isSelected());
		Assert.assertTrue(driver.findElement(ageUnder18).isSelected());
		
		driver.findElement(By.id("java")).click();
		
		Assert.assertFalse(driver.findElement(By.id("java")).isSelected());
	}
	
	@Test
	public void TC04_MailChimp() {
		driver.get("https://login.mailchimp.com/signup/");
		
		driver.findElement(By.id("email")).sendKeys("huyen23813@gmail.com");
		
		By passwordTextBox = By.id("new_password");
		
		driver.findElement(passwordTextBox).sendKeys("abc");
		sleepInSecond(2);
		
		//verify abc
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'lowercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = '8-char not-completed']")).isDisplayed());
		
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("ABC");
		sleepInSecond(3);
		
		//Verify ABC
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'uppercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = '8-char not-completed']")).isDisplayed());	
		
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("123");
		sleepInSecond(3);
		//Verify 123
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = '8-char not-completed']")).isDisplayed());		
		
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("@#$");
		sleepInSecond(3);
		//Verify @#$
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'special-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = '8-char not-completed']")).isDisplayed());		
		
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("123456789");
		sleepInSecond(3);
		//Verify 12345678
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = 'special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class = '8-char completed']")).isDisplayed());			
		
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("123abcABC@#$");
		sleepInSecond(3);
		//Verify fulldata
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class = 'lowercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class = 'uppercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class = 'number-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class = 'special-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class = '8-char completed']")).isDisplayed());				
		
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
		//driver.quit();
	}
}
