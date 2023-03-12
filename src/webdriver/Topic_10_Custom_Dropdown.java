package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
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
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInDropdown("#speed-button", "ul#speed-menu div[role='option']", "Slow");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),
				"Slow");

		selectItemInDropdown("#speed-button", "ul#speed-menu div[role='option']", "Medium");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),
				"Medium");

		selectItemInDropdown("#speed-button", "ul#speed-menu div[role='option']", "Fast");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),
				"Fast");

		selectItemInDropdown("#speed-button", "ul#speed-menu div[role='option']", "Faster");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),
				"Faster");

		selectItemInDropdown("#speed-button", "ul#speed-menu div[role='option']", "Slower");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),
				"Slower");
	}

//	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInDropdown("i.dropdown", "div[role='option']", "Jenny Hess");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui fluid selection dropdown']")).getText(),
				"Jenny Hess");

		selectItemInDropdown("i.dropdown", "div[role='option']", "Stevie Feliciano");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui fluid selection dropdown']")).getText(),
				"Stevie Feliciano");

		selectItemInDropdown("i.dropdown", "div[role='option']", "Matt");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui fluid selection dropdown']")).getText(),
				"Matt");
	}

//	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");

		selectItemInDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "First Option");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");

		selectItemInDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Third Option");
	}

	@Test
	public void TC_04_Edittable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		enterAndSelectItemInDropdown("input.search", ".item", "Angola");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span.text")).getText(), "Angola");

		enterAndSelectItemInDropdown("input.search", ".item", "Argentina");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span.text")).getText(), "Argentina");

		enterAndSelectItemInDropdown("input.search", ".item", "Australia");
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.cssSelector("span.text")).getText(), "Australia");

	}

	public void selectItemInDropdown(String parenCss, String allItemCss, String exceptedTextItem) {
		// 1- Click vào 1 thẻ bất kì để làm sao cho nó xổ ra hết các item của dropdown
		driver.findElement(By.cssSelector(parenCss)).click();
		sleepInSecond(1);
		// 2- Chờ cho tất cả item được load ra thành công

		List<WebElement> speedDropdownItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		// 3- Tìm item xem đúng cái đang cần hay không?
		for (WebElement tempItem : speedDropdownItems) {
			// 4 - Kiểm tra text của item có đúng với mong muốn không?
			if (tempItem.getText().trim().equals(exceptedTextItem)) {
				// 5 - Click vào item đó
				tempItem.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	public void enterAndSelectItemInDropdown(String textboxCss, String allItemCss, String exceptedTextItem) {

		// 1- Nhập excepted text item vào - xổ ra tất cả các item matching
		driver.findElement(By.cssSelector(textboxCss)).clear();
		driver.findElement(By.cssSelector(textboxCss)).sendKeys(exceptedTextItem);
		sleepInSecond(3);

		List<WebElement> speedDropdownItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		for (WebElement tempItem : speedDropdownItems) {
			if (tempItem.getText().trim().equals(speedDropdownItems)) {
				tempItem.click();
				sleepInSecond(1);
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
