package my.com.mydigibusiness;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class PaymentProcess extends BaseMethods {

	// Variables that keeps URL's for the Validation Process
	String currentUrl;
	String paymentUrl = "https://examplewebsite.digi.com.my/payment";
	String makepaymentUrl = "https://examplewebsite.digi.com.my/makepayment";
	String ipay88Url = "https:/examplewebsite.com";
	String cancelledUrl = "https://examplewebsite.digi.com.my/makepayment?status=0";
	
	WebElement makePaymentButton;

	public void selectAccountLink() {
		WebElement selectAccountLink;
		WebDriverWait wait = new WebDriverWait(driver, 50);
		selectAccountLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//a[contains(text(), 'Select Account')]")));
		selectAccountLink.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/payment[1]/div[1]/div[1]/div[2]/div[2]/div[1]/table[1]/tbody[2]/tr[1]/td[2]/span[1]")));
	}
	
	public void proceedToPaymentPage(ExtentTest logger) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try {

			// Clicking Payment from Bills & Payment dropdown
			driver.get("https://examplewebsite.digi.com.my/payment");
			String accountCode = driver.findElement(By.className("AccountNameForPayment")).getText();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/payment[1]/div[1]/div[1]/div[2]/div[2]")));	
			String paymentPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
					+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\1 - PaymentPage.jpg";
			
			// Validation for the Payment Page
			currentUrl = driver.getCurrentUrl();
			if (currentUrl.equals(paymentUrl)) {
				try {
					System.out.println("------------------------------------------------");
					System.out.println("Payment Page Loaded Successfully");
					// Extent Report
					logger.log(Status.PASS, "Payment Page Loaded Successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(paymentPath).build());

				} catch (Exception e) {
					WebDriverWait wait2 = new WebDriverWait(driver, 30);
					WebElement Errorbox;
					Errorbox = wait2.until(ExpectedConditions.presenceOfElementLocated(By.className("error-service")));
					Errorbox.isDisplayed();
					Assert.assertTrue(false);
				}

			} else {
				System.out.println("Payment Page SKIPPED");
			}
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("AccountRMForPayment")));
			takeSnapShot(paymentPath);
			Assert.assertEquals(currentUrl, paymentUrl);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
	}
	
	public void makePaymentAll(ExtentTest logger) {
		String accountCode = driver.findElement(By.className("AccountNameForPayment")).getText();
		String makepaymentPath1 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\"
				+ "ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\2 - All Accounts Confirmation Page.jpg";
		String cancelledTransactionPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\"
				+ "5 - All Accounts Cancelled Transaction Page.jpg";
		String errorBoxUrl = "https://examplewebsite.digi.com.my/error-box"; 
		WebDriverWait wait = new WebDriverWait(driver, 150);
		
		try {
			//Clicking All checkbox
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/payment[1]/div[1]/div[1]/div[2]/div[2]/div[1]/table[1]/tbody[2]/tr[1]/td[2]/span[1]")));
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("//tr[@class='table_header']//span[@class='checkmark']")).click();

			//Clicking Make Payment Button
			makePaymentButton = driver.findElement(By.xpath("//button[contains(text(),'Make Payment')]"));
			Thread.sleep(3000);

			//If Make Payment button is disabled because total is 0.00
			if (makePaymentButton.isEnabled()) {
				makePaymentButton.click();

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
				
				//If error page appears
				if (currentUrl.equals(errorBoxUrl)) {
					System.out.println("---------------------------------------------");
					System.out.println("Error page for All Accounts Confirmation");
					System.out.println("---------------------------------------------");
					logger.log(Status.PASS, "Error page for All Accounts Confirmation");
					Thread.sleep(2000);
					selectAccountLink();
				} 
				
				//If Payment Confirmation Page loads successfully
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"/html[1]/body[1]/digi-ewp[1]/makepayment[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]")));
				System.out.println("-----------------------------------------------------------");
				System.out.println("Confirmation Page for All Accounts Only Loaded Successfully");
				logger.log(Status.PASS, "Confirmation for All Accounts Page Loaded Successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(makepaymentPath1).build());
				
				//Take screenshot of confirmation page
				takeSnapShot(makepaymentPath1);
				
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("scroll(0, 800);");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
				WebElement payBillButton = driver.findElement(By.xpath(("//button[@type='submit']")));
				if (payBillButton.isEnabled()) {
					payBillButton.click();
					
					//In ipay88 page
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]")));
					logger.pass("iPay88 Page for All Accounts Loaded Successfully");
					System.out.println("iPay88 Page for All Accounts Loaded Successfully");
					
					//Clicking cancel transaction
					driver.findElement(By.id("btncancel")).click();
					Thread.sleep(2000);
					
					//Clicking OK button in alert window
					driver.switchTo().alert().accept();
					Thread.sleep(2000);

					logger.pass("Cancelled Transaction Page Loaded Successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(cancelledTransactionPath).build());
					takeSnapShot(cancelledTransactionPath);
					driver.findElement(By.xpath("//button[@type='submit']")).click();
					Thread.sleep(2000);
				}
			}
			
			else {
				//If disabled, reclick checkbox
				driver.findElement(By.xpath("//tr[@class='table_header']//span[@class='checkmark']")).click();
				System.out.println("Make payment for all accounts not clicked");
				logger.log(Status.INFO, "Total is RM0.00");
			}
			logger.log(Status.PASS, "Payment of All Accounts Successful");
			
		} catch (Exception e1) {
			System.out.println("-------------------------------------------------------------------");
			logger.log(Status.SKIP, "There is no test data to be tested.");
			throw new SkipException("Skipping this test because there are no accounts to be tested.");
		}
	}

	public void makePaymentParentAccountOnly(ExtentTest logger) throws Exception {
		String accountCode = driver.findElement(By.className("AccountNameForPayment")).getText();
		String makepaymentPath2 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\3 - Parent Account Confirmation Page.jpg";
		String cancelledTransactionPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\6 - Parent Account Cancelled Transaction Page.jpg";
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String errorBoxUrl = "https://examplewebsite.digi.com.my/error-box"; 
		WebElement makePaymentButton;
	
		try {
			// Clicking Parent Account Only Checkbox
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/payment[1]/div[1]/div[1]/div[2]/div[2]/div[1]/table[1]/tbody[2]/tr[1]/td[2]/span[1]")));
			Thread.sleep(2000);
			driver.findElement(By.xpath(
					"//table[@id='bill']//tbody[2]//tr[1]//td[4]//span[1]//label[1]//span[1]")).click();
			Thread.sleep(2000);
			
			//If Make Payment button is disabled or not
			makePaymentButton = driver.findElement(By.xpath("//button[contains(text(),'Make Payment')]"));
			
			if (makePaymentButton.isEnabled()) {
				makePaymentButton.click();
				
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
//				Thread.sleep(5000);
				//If error page appears
				if (currentUrl.equals(errorBoxUrl)) {
					System.out.println("-----------------------------------------------------------");
					System.out.println("Error page for Parent Account only");
					logger.log(Status.FAIL, "Error page for Parent Account only");
					Thread.sleep(2000);
					selectAccountLink();
				}

				//If Payment Confirmation Page loads successfully
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"/html[1]/body[1]/digi-ewp[1]/makepayment[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]")));
				System.out.println("-----------------------------------------------------------");
				System.out.println("Confirmation Page for Parent Account Only Loaded Successfully");
				logger.log(Status.PASS, "Confirmation for Parent Account Only Loaded Successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(makepaymentPath2).build());
				takeSnapShot(makepaymentPath2);
				
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
				WebElement payBillButton = driver.findElement(By.xpath(("//button[@type='submit']")));
				if (payBillButton.isEnabled()) {

					System.out.println("Clicking pay bill for parent account...");
					payBillButton.click();

					//In ipay88 page
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]")));
					Thread.sleep(2000);
					System.out.println("iPay88 Page for Parent Account Only Loaded Successfully");
					logger.pass("iPay88 Page for Parent Account Loaded Successfully");

					
					//Clicking cancel transaction
					driver.findElement(By.id("btncancel")).click();
					Thread.sleep(2000);
					
					//Clicking OK in alert window
					driver.switchTo().alert().accept();
					Thread.sleep(2000);
					
					//In Payment Confirmation Cancelled Page
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"/html[1]/body[1]/digi-ewp[1]/makepayment[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/table[1]/tbody[1]/tr[2]/td[1]")));
					logger.pass("Cancelled Transaction Path Loaded Successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(cancelledTransactionPath).build());
					takeSnapShot(cancelledTransactionPath);
					System.out.println("Payment Overview page Loaded Successfully");
					driver.findElement(By.xpath("//button[@type='submit']")).click();
					Thread.sleep(2000);
				}
			}
			
			else {
				
				//If disabled, reclick checkbox
				driver.findElement(By.xpath(""
						+ "//table[@id='bill']//tbody[2]//tr[1]//td[4]//span[1]//label[1]//span[1]")).click();
				System.out.println("Make payment for parent account not clicked");
				logger.log(Status.INFO, "Total is RM0.00");
			}
			
			logger.log(Status.PASS, "Payment of Parent Account Only Successful");
		
		} catch (Exception e1) {
			logger.log(Status.SKIP, "There is no parent account data to be tested.");
			throw new SkipException("Skipping this test because there are no parent accounts to be tested.");
		}

	}
		
	public void makePaymentChildAccountOnly(ExtentTest logger) throws Exception {
		String accountCode = driver.findElement(By.className("AccountNameForPayment")).getText();
		String makepaymentPath3 = "C:\\Users\\t931391\\eclipse-workspace\\MDBParallelTesting\\MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\4 - Individual Account Confirmation Page.jpg";
		String cancelledTransactionPath = "C:\\Users\\t931391\\eclipse-workspace\\MDBParallelTesting\\MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Payment\\6 - Parent Account Cancelled Transaction Page.jpg";
		WebDriverWait wait = new WebDriverWait(driver, 100);
		WebElement makePaymentButton;
		String errorBoxUrl = "https://examplewebsite.digi.com.my/error-box";
		try {
			// Clicking Child Account Only Checkbox
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/payment[1]/div[1]/div[1]/div[2]/div[2]/div[1]/table[1]/tbody[2]/tr[2]/td[2]/span[1]")));
			Thread.sleep(2000);
			driver.findElement(By.xpath(
					"//table[@id='bill']//tr[2]//td[4]//span[1]//span[1]")).click();
			Thread.sleep(2000);
			
			//If Make Payment button is disabled or not
			makePaymentButton = driver.findElement(By.xpath("//button[contains(text(),'Make Payment')]"));
			
			if (makePaymentButton.isEnabled()) {
				makePaymentButton.click();
				
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
//				Thread.sleep(5000);
				//If error page appears
				if (currentUrl.equals(errorBoxUrl)) {
					System.out.println("-----------------------------------------------------------");
					System.out.println("Error page for Parent Account only");
					logger.log(Status.FAIL, "Error page for Parent Account only");
					Thread.sleep(2000);
					selectAccountLink();
				}

				//If Payment Confirmation Page loads successfully
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"/html[1]/body[1]/digi-ewp[1]/makepayment[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]")));
				System.out.println("-----------------------------------------------------------");
				System.out.println("Confirmation Page for Parent Account Only Loaded Successfully");
				logger.log(Status.PASS, "Confirmation for Parent Account Only Loaded Successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(makepaymentPath3).build());
				takeSnapShot(makepaymentPath3);
				
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
				WebElement payBillButton = driver.findElement(By.xpath(("//button[@type='submit']")));
				if (payBillButton.isEnabled()) {

					System.out.println("Clicking pay bill for individual account...");
					payBillButton.click();

					//In ipay88 page
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]")));
					Thread.sleep(2000);
					System.out.println("iPay88 Page for Individual Account Only Loaded Successfully");
					logger.pass("iPay88 Page for Individual Account Loaded Successfully");

					
					//Clicking cancel transaction
					driver.findElement(By.id("btncancel")).click();
					Thread.sleep(2000);
					
					//Clicking OK in alert window
					driver.switchTo().alert().accept();
					Thread.sleep(2000);
					
					//In Payment Confirmation Cancelled Page
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"/html[1]/body[1]/digi-ewp[1]/makepayment[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/table[1]/tbody[1]/tr[2]/td[1]")));
					logger.pass("Cancelled Transaction Path Loaded Successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(cancelledTransactionPath).build());
					takeSnapShot(cancelledTransactionPath);
					driver.findElement(By.xpath("//button[@type='submit']")).click();
					Thread.sleep(2000);
				}
			}
			
			else {
				
				//If disabled, reclick checkbox
				driver.findElement(By.xpath("//table[@id='bill']//tr[2]//td[4]//span[1]//span[1]")).click();
				System.out.println("Make payment for individual account not clicked");
				logger.log(Status.INFO, "Total is RM0.00");
			}
			
			logger.log(Status.PASS, "Payment of Individual Account Only Successful");
		
		} catch (Exception e1) {
			logger.log(Status.SKIP, "There is no individual account data to be tested.");
			throw new SkipException("Skipping this test because there are no parent accounts to be tested.");
		}
	}
}
