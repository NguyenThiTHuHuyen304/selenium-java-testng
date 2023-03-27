package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_21_Wait_PII_FindElement {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	List<WebElement> elements;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.get("https://www.facebook.com/reg");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_FindElement() {

		// 3 trường hợp
		// Case 1: Chỉ tìm thấy 1 element
		// FindElement sẽ apply cái timeout của implicitWait
		// Vì nó vào và tìm thấy element luôn nên không phải chờ hết timeout 10s
		driver.findElement(By.cssSelector("input[name = 'firstname']"));

		// Case 2: Tìm thấy nhiều hơn 1 element
		// Nó sẽ luôn lấy element đầu tiên để sử dụng
		// Vì nó vào và tìm thấy elemnet luon nên không phải chờ hết timeout 10s
		driver.findElement(By.cssSelector("input[name = 'firstname']")).sendKeys("Automation FC");

		// Case 3: Không tìm thấy element
		// Nó sẽ không tìm thấy => tiếp tục tìm đi tìm lại sau mỗi 0.5s
		// Nếu tìm lại: Thấy => thực hiện như case 1,2
		// Nếu tìm lại không thấy và hết time out => Ném ra 1 exception: NoSuchElement
		driver.findElement(By.xpath("//div[text() = \"What's your name?\"]"));
	}

	@Test
	public void TC_02_FindElements() {

		// 3 trường hợp
		// Case 1: Chỉ tìm thấy 1 element
		elements = driver.findElements(By.cssSelector("input[name = 'firstname']"));
		System.out.println("Case 1:Tìm thấy 1 element = " + elements.size());

		// Case 2: Tìm thấy nhiều hơn 1 element
		// Lấy ra hết tất cả các element được tìm thấy
		elements = driver.findElements(By.cssSelector("input[type= 'text']"));
		System.out.println("Case 2: Tìm thấy nhiều hơn 1 element: " + elements.size());

		// Case 3: Không tìm thấy element
		// Nó sẽ không tìm thấy => tiếp tục tìm đi tìm lại sau mỗi 0.5s
		// Nếu tìm lại: Thấy => thực hiện như case 1,2
		// Nếu tìm lại không thấy và hết time out => không đánh fail testcase, ko show
		// exception
		// Trả về 1 list có độ dài = 0
		elements = driver.findElements(By.xpath("//div[text() = \"What's your name?\"]"));
		System.out.println("Case 3: Không tìm thấy element: " + elements.size());
		Assert.assertEquals(elements.size(), 0);
	}

	@Test
	public void TC_03() {

	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
