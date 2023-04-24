package priority;

import org.testng.annotations.Test;

public class Priority {

	@Test(priority = 3)
	public void deleteCustomer() {

	}

	@Test(priority = 1)
	public void readCustomer() {

	}

	@Test(priority = 0)
	public void newCustomer() {

	}

	@Test(priority = 2)
	public void editCustomer() {

	}
}
