package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Action_Part_II {
	WebDriver driver;
	Actions action;
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
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		// Chứa 12 số /item trong list này
		List<WebElement> allItems = driver.findElements(By.cssSelector("ol#selectable li"));

		// 1- Click vào số 1 (source) -> 2- Vẫn giữ chuột chưa nhả ra
		action.clickAndHold(allItems.get(0))
				// 3- Di chuột tới số (target)
				.moveToElement(allItems.get(7))
				// 4- Nhả chuột trái ra
				.release()
				// 5 - thực thi câu lệnh
				.perform();

//		action.clickAndHold(allItems.get(0)).moveToElement(allItems.get(8)).release().perform();

		sleepInSecond(3);

		// verify
		List<WebElement> listSelected = driver.findElements(By.cssSelector("ol#selectable li.ui-selected"));
		assertEquals(listSelected.size(), 8);
	}

	@Test
	public void TC_02_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		Keys key = null;
		if (osName.contains("Windos")) {
			key = key.CONTROL;
		} else {
			key = key.COMMAND;
		}

		// Chứa 12 số /item trong list này
		List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable li"));

		// Nhấn ctrl xuống
		action.keyDown(key).perform();

		// Click chọn các số random
		action.click(listNumbers.get(0)).click(listNumbers.get(3)).click(listNumbers.get(5)).click(listNumbers.get(10))
				.perform();

		// Nhả phím ctrl ra
		action.keyUp(key).perform();
		sleepInSecond(5);
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
