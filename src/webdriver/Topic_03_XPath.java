package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.grid.internal.DefaultGridRegistry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_XPath {
	WebDriver driver;
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Empty_Data() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//Verify
		Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");		
	}

	@Test   
	public void TC_02_Invalid_Email() {
		//vao web
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		// nhap du lieu test
		driver.findElement(By.id("txtFirstname")).sendKeys("John Kennedy");
		driver.findElement(By.id("txtEmail")).sendKeys("123@456@789");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@456@789");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
		
		//click button dang nhap
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//verify ket qua
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
		
	}

	@Test
	public void TC_03_Incorrect_Email() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		// nhap du lieu test
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("Johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("Johnwick@gmail.net");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		
		//click button dang nhap
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//verify ket qua
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");		
	}
	
	@Test
	public void TC_04_Invalid_Password() {
		//vao trang chu
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		//test textbox
		driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.net");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.net");
		driver.findElement(By.id("txtPassword")).sendKeys("123");
		driver.findElement(By.id("txtCPassword")).sendKeys("123");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		
		//click button dang nhap
		driver.findElement( By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//verify 
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự" );
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự" );
		
	}
	
	
	@Test
	public void TC_05_Incorrect_Passwordl() {
		//vao trang chu
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		// nhap du lieu test textbox
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0987654321");
		
		//click button dang nhap
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		// so sanh dau ra mong muon
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu bạn nhập không khớp");
	}
	
	@Test
	public void TC_06_Invalid_Phone() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		//Action 1
		driver.findElement(By.id("txtFirstname")).sendKeys("John Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("johnwick@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtPhone")).sendKeys("09876");
		
		//click button dang nhap
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//Verify 1
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");
		
		
		//Action2
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("098765432111");
		
		//click button dang nhap
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();	
		
		//Verify 2
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");
		
		//Action3
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("123456789");
		
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		//Verify3
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
		
	}
	
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
