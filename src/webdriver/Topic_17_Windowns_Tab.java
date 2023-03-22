package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Windowns_Tab {
	WebDriver driver;
	Alert alert;
	WebDriverWait expliciWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new ChromeDriver();
		expliciWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Github_With_Two_Windown_Tab() {
		// Driver đang ở trang Github
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Trả về ID driver đang đứng (chỉ trả về 1 id duy nhất)
		String githubID = driver.getWindowHandle();
		System.out.println("ID Github: " + githubID);
		System.out.println("Page title - Github: " + driver.getTitle());

		// click vào page Google
		driver.findElement(By.xpath("//a[text() = 'GOOGLE']")).click();
		sleepInSecond(3);
		System.out.println("Page title - Github: " + driver.getTitle());

		// chuyển driver qua page Google
		switchToWindownByID(githubID);
		System.out.println("Page title - Google: " + driver.getTitle());

		String googleID = driver.getWindowHandle();
		System.out.println("ID google: " + googleID);

		// Chuyển driver về github page
		switchToWindownByID(googleID);
		System.out.println("Page title - Github: " + driver.getTitle());
	}

//	@Test
	public void TC_02_Github_Greater_Windown_Tab() {

		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(3);

		String githubID = driver.getWindowHandle();

		driver.findElement(By.xpath("//a[text() = 'GOOGLE']")).click();
		sleepInSecond(3);

		// switch đến trang google
		switchToWindownByTitle("Google");
		System.out.println("Page title - Google: " + driver.getTitle());

		driver.findElement(By.xpath("//input[@name = 'q']")).sendKeys("Automation FC");
		driver.findElement(By.xpath("//input[@name = 'q']")).sendKeys(Keys.ENTER);
		sleepInSecond(3);

		// về lại trang Github
		switchToWindownByTitle("Selenium WebDriver");
		System.out.println("Page title - Github: " + driver.getTitle());

		// switch qua Facebook
		driver.findElement(By.xpath("//a[text() = 'FACEBOOK']")).click();
		sleepInSecond(3);

		switchToWindownByTitle("Facebook – log in or sign up");
		System.out.println("Page title - Facebook: " + driver.getTitle());
		driver.findElement(By.cssSelector("input#email")).sendKeys("huyen23813@gmail.com");
		driver.findElement(By.cssSelector("input#pass")).sendKeys("abcABC123!@#");
		sleepInSecond(3);

		// về Github
		switchToWindownByTitle("Selenium WebDriver");
		System.out.println("Page title - Github: " + driver.getTitle());

		// switch tới Tiki
		driver.findElement(By.xpath("//a[text() = 'TIKI']")).click();
		sleepInSecond(3);

		switchToWindownByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		System.out.println("Page title - Tiki: " + driver.getTitle());

		closeAllWindownWithoutParentID(githubID);
	}

//	@Test
	public void TC_03_Techpanda() {
		driver.get("http://live.techpanda.org/");
		sleepInSecond(5);

		driver.findElement(By.xpath("//a[text()='Mobile']")).click();

		driver.findElement(
				By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div//a[text()='Add to Compare']"))
				.click();
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(),
				"The product Sony Xperia has been added to comparison list.");

		driver.findElement(
				By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div//a[text()='Add to Compare']"))
				.click();
		sleepInSecond(7);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(),
				"The product Samsung Galaxy has been added to comparison list.");

		driver.findElement(By.xpath("//span[text()='Compare']/parent::span/parent::button")).click();
		sleepInSecond(10);

		switchToWindownByTitle("Products Comparison List - Magento Commerce");
		sleepInSecond(3);
		System.out.println("Title page: " + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");

		driver.close();

		switchToWindownByTitle("Mobile");
		System.out.println("title page: " + driver.getTitle());
		driver.findElement(By.xpath("//a[text() = 'Clear All']")).click();
		sleepInSecond(5);

		alert = expliciWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(),
				"Are you sure you would like to remove all products from your comparison?");
		alert.accept();
		sleepInSecond(5);

		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),
				"The comparison list was cleared.");

	}

//	@Test
	public void TC_04_Dictionary() {
		driver.get("https://dictionary.cambridge.org/vi/");
		sleepInSecond(1);
		driver.findElement(By.xpath("//header//span[text()= 'Đăng nhập']")).click();
		sleepInSecond(1);

		switchToWindownByTitle("Login");
		driver.findElement(By.cssSelector("input[value='Log in']")).click();
		sleepInSecond(5);
		Assert.assertEquals(
				driver.findElement(By.cssSelector("div#login_content span[data-bound-to = 'loginID']")).getText(),
				"This field is required");
		Assert.assertEquals(
				driver.findElement(By.cssSelector("div#login_content span[data-bound-to = 'password']")).getText(),
				"This field is required");
		driver.close();
		sleepInSecond(3);

		switchToWindownByTitle("Cambridge Dictionary | Từ điển tiếng Anh, Bản dịch & Từ điển từ đồng nghĩa");
		String keyword = "automation";
		driver.findElement(By.cssSelector("input#searchword")).sendKeys(keyword);
		driver.findElement(By.cssSelector("input#searchword")).sendKeys(Keys.ENTER);
		sleepInSecond(3);

		switchToWindownByTitle(keyword.toUpperCase() + " " + "| Định nghĩa trong Từ điển tiếng Anh Cambridge");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@data-id= 'cald4']//span[@class = 'hw dhw']")).getText(), keyword);

	}

	// Dùng cho trường hợp 2 windown/tab trở lên
	public void switchToWindownByTitle(String pageTitle) {
		// Lấy ra list id
		Set<String> allIDs = driver.getWindowHandles();

		// duyệt qua tất cả id có trong list
		for (String id : allIDs) {
			// switch driver
			driver.switchTo().window(id);
			String actualPageTitle = driver.getTitle();
			if (actualPageTitle.equals(pageTitle)) {
				break;
			}
		}
	}

	public void closeAllWindownWithoutParentID(String parentID) {
		Set<String> allIDs = driver.getWindowHandles();

		for (String id : allIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	// Chỉ sử dụng cho trường hợp 2 windown
	public void switchToWindownByID(String pageID) {
		// lấy ra list id
		Set<String> allIDs = driver.getWindowHandles();

		for (String id : allIDs) {
			if (!id.equals(pageID)) {
				driver.switchTo().window(id);
				sleepInSecond(3);
				break;
			}
		}
	}

	public void sleepInSecond(long timeInSleep) {
		try {
			Thread.sleep(timeInSleep * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
