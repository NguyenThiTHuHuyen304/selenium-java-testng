package webdriver;

import org.testng.Assert;

public class Topic_01_Assert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fullName = "Automation Testing";
		//Mong đợi 1 đk trả về là đúng (true)
		Assert.assertTrue(fullName.contains("Auto"));
		
		//Mong đợi 1 đk trả về là sai (fail)
		Assert.assertFalse(fullName.contains("Manual"));
		
		//Mong đợi 2 đk bằng nhau
		Assert.assertEquals(fullName, "Automation Testing");
	}

}
