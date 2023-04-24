package annotation;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Assertion {

	Boolean status = false;
	String fullName = "Selenium WebDriver";

	@Test
	public void TC_01() {

		// Đúng hoặc sai: Trả về boolean --> assertTrue/ assertFalse
		// isXXX: isDisplayed/ isEnabled/ isSelected
		Assert.assertTrue(status);

		// Bằng với mong đợi: assertEquals
		Assert.assertEquals(fullName, "Selenium Grid");
	}
}
