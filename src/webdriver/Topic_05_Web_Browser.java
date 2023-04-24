package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By emailTextBoxBy = By.id("email");
	By passwordTextBoxBy = By.id("password");


	@ BeforeClass
	public void BeforClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_01() {
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
		
		WebElement element = driver.findElement(By.name(""));
		
		//xoá dữ dữ liệu trước khi nhập text
		element.clear();
		
		//Nhập liệu
		element.sendKeys("");
		
		//click vào các button/checkbox...
		element.click();
		
		//lấy ra element=css
		driver.findElement(By.cssSelector("a.ico-login"));
		
		//search store
		String searchAttribute = element.getAttribute("placeholder");
		String emailTextboxAttribute = element.getAttribute("value");
		//GUI: font/size/color...
		element.getCssValue("background-color");
		
		//Vị trí element so với web(bên ngoài)
		Point point = element.getLocation();
		point.x = 324;
		point.y = 324;
		
		//kích thước element(bên trong)
		Dimension di = element.getSize();
		di.getHeight();
		di.getWidth();
		
		System.out.println("Height: " +di.getHeight());
		System.out.println("Width: " +di.getWidth());
		// location + size
		Rectangle rec = element.getRect();
		//Chụp hình khi testcase fails
		element.getScreenshotAs(OutputType.FILE);
		
		//lấy ra tên thẻ HTMl
		String emailTextboxTagname = driver.findElement(By.id("email")).getTagName();
		driver.findElement(By.xpath("//" + emailTextboxTagname + "[@id='email']")); 
		
		// lấy text từ error message/label (khi text nằm bên ngoài, không nằm trong 1 attribute nào)
		element.getText();
		//lấy text khi text nằm bên trong 
		element.getAttribute("");
		
		// dùng để verify 1 attribute hiện thị hoặc không
		//phạm vi: tất cả element
		element.isDisplayed();
		
		//Verify 1 element có hiển thị được hay không
		//phạm vi: tất cả element
		Assert.assertTrue(element.isDisplayed());
		Assert.assertFalse(element.isDisplayed());
		
		//Verify 1 element có thao tác được hay không
		//phạm vi: tất cả element
		Assert.assertTrue(element.isEnabled());
		Assert.assertFalse(element.isEnabled());
		
		//Verify 1 element có thao tác được hay không
		//phạm vi: checkbox/radio
		Assert.assertTrue(element.isSelected());
		Assert.assertFalse(element.isSelected());		
		
		// element nằm trong thẻ form
		element.submit();
	}

	public void TC_register() {
		driver.get("");
		driver.findElement(emailTextBoxBy).sendKeys("abc@gmail.com");
		driver.findElement(passwordTextBoxBy).sendKeys("123456");
	}
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
