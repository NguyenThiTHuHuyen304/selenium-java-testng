package webdriver;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_27_Wait_PVIII_Fluent {
	WebDriver driver;
	FluentWait<WebDriver> fluentWaitDriver;
	FluentWait<WebElement> fluentWaitElement;
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

//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

//	@Test
	public void TC_01_GetText() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

//		// Tổng thời gian chờ là bao lâu
//		fluentWaitDriver.withTimeout(Duration.ofSeconds(10));
//
//		// Thời gian tìm lại là bao lâu
//		fluentWaitDriver.pollingEvery(Duration.ofMillis(100));
//
//		// Nếu trong quá trình tìm element mà không thấy sẽ throw ra ngoại lệ:
//		// Ignore exception này trong code
//		fluentWaitDriver.ignoring(NoSuchMethodException.class);

		// Setting time + exception
		fluentWaitDriver.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100))
				.ignoring(NoSuchElementException.class);

		// Condition
		String helloWordText = fluentWaitDriver.until(new Function<WebDriver, String>() {

			@Override
			public String apply(WebDriver driver) {
				return driver.findElement(By.cssSelector("div#finish>h4")).getText();
			}
		});

		Assert.assertEquals(helloWordText, "Hello World!");

	}

//	@Test
	public void TC_02_Equal() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

		// Setting time + exception
		fluentWaitDriver.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100))
				.ignoring(NoSuchElementException.class);

		// Condition
		fluentWaitDriver.until(new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.cssSelector("div#finish>h4")).getText().equals("Hello World!");
			}
		});

	}

//	@Test
	public void TC_03_Is_Displayed() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

		fluentWaitDriver.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(1000))
				.ignoring(NoSuchElementException.class);

		fluentWaitDriver.until(new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.xpath("//h4[text() = 'Hello World!']")).isDisplayed();
			}
		});

	}

	@Test
	public void TC_04_Element() {

		driver.get("https://automationfc.github.io/fluent-wait/");

		fluentWaitElement = new FluentWait<WebElement>(
				driver.findElement(By.cssSelector("div#javascript_countdown_time")));

		fluentWaitElement.withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofMillis(1000))
				.ignoring(NoSuchElementException.class);

		Boolean status = fluentWaitElement.until(new Function<WebElement, Boolean>() {

			@Override
			public Boolean apply(WebElement element) {
				String text = element.getText();
				System.err.println(text);
				return text.endsWith("00");
			}
		});

		Assert.assertTrue(status);
	}

	public WebElement findElement(By locator) {

		fluentWaitDriver = new FluentWait<WebDriver>(driver);

		fluentWaitDriver.withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofMillis(1000))
				.ignoring(NoSuchElementException.class);

		return fluentWaitDriver.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
}
