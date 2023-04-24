package group;

import org.testng.annotations.Test;

public class Payment_Type {

	@Test(groups = "pay")
	public void Visa() {
		System.out.println("Visa");
	}

	@Test(groups = "pay")
	public void Cheque() {
		System.out.println("Cheque");
	}

	@Test(groups = "pay")
	public void QR() {
		System.out.println("QR");
	}
}
