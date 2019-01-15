package my.com.mydigibusiness;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ManageMembers extends BaseMethods {
	
	String nextPath = "//button[contains(text(),'Next')]";
	String backPath = "//small[contains(text(),'Go back')]";
	String deleteFirstRow = "/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[5]/div[1]/button[1]";
	
	public void proceedToManageMembers(ExtentTest logger, String accountCode) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		System.out.println("==================================================S");
		//Click manage members link
		driver.findElement(By.xpath("//a[contains(text(),'Manage Members')]")).click();

		//Wait until main body loads
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-8']")));
		
		//Check if page title is correct
		String pageTitle = "Manage Member Services";
		String workingTitle = driver.findElement(By.className("SIMReplacement")).getText();
		System.out.println("Account Code is: " + accountCode);
		String ordersPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Manage Members\\MembersPage [1].jpg";
		System.out.println("Page title is: " + workingTitle);
		if (workingTitle.contains(pageTitle)) {
			try {
				logger.log(Status.PASS, 
						"Manage Members Page Loaded Successfully", 
						MediaEntityBuilder.createScreenCaptureFromPath(ordersPath).build());
				takeSnapShot(ordersPath);
				System.out.println("Manage members page loaded successfully");
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public void validateSimNumbers(ExtentTest logger) throws InterruptedException {
		
		System.out.println("--------------------------------------------------");
		System.out.println("Testing Replacing SIM Numbers");
		WebElement mobile_dd;
		WebElement simNumberInput;
		WebElement reasonInput;
//		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		//Clicking Mobile Number dropdown and selecting a number
		mobile_dd = driver.findElement
				(By.xpath("/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[2]/div[1]/input[1]"));
		mobile_dd.click();
//		mobile_dd.sendKeys("60143275073");
		mobile_dd.sendKeys(Keys.ENTER);
		
		//Enter Sim Number into textfield
		simNumberInput = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[2]/tr[1]/td[4]/div[1]/div[1]/input[1]"));
		simNumberInput.click();
		simNumberInput.sendKeys("10200000000007014223");
		
		//Choose reason in dropdown
		reasonInput = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[2]/tr[1]/td[5]/div[1]/select[1]"));
		reasonInput.click();
		reasonInput.sendKeys(Keys.ARROW_DOWN);
		reasonInput.sendKeys(Keys.ENTER);

		//Click next button then back button
		Thread.sleep(3000);
		
		driver.findElement(By.xpath(nextPath)).click();
//		Thread.sleep(2000);
		
		logger.pass("Replace SIM numbers test completed successfully");
		driver.findElement(By.xpath(backPath)).click();
	}
	
	public void validateInternationalRoaming(ExtentTest logger) throws InterruptedException {
		WebElement mobile_dd;
		String serviceStatus;
		WebElement transactionType_dd;
//		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		System.out.println("--------------------------------------------------");
		System.out.println("Testing International Roaming");
		//Choosing International Roaming
		transactionType_dd = driver.findElement(By.xpath("/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[3]/select[1]"));
		transactionType_dd.click();
		transactionType_dd.sendKeys(Keys.DOWN);
		transactionType_dd.sendKeys(Keys.ENTER);	
		
		//Select mobile number
		mobile_dd = driver.findElement(By.xpath("//input[@placeholder='Select a Mobile Number']"));
		mobile_dd.click();
//		mobile_dd.sendKeys("60143206643");
		mobile_dd.sendKeys(Keys.ENTER);
		
		//Toggle New Service Status
		serviceStatus = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]"))
				.getText();
		
		if (serviceStatus.contains("OFF")) {
			driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/label[1]/span[1]"))
				.click();
		}
		
		//Click next button
		driver.findElement(By.xpath(nextPath)).click();
		Thread.sleep(2000);
		
		//Go back
		driver.findElement(By.xpath(backPath)).click();
		
		//Delete row
		Thread.sleep(2000);
		driver.findElement(By.xpath(
			"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[5]/div[1]/button[1]"))
			.click();
		
		logger.pass("International Roaming Test completed successfully.");
	}
	
	public void validateIDD(ExtentTest logger) throws InterruptedException {
		WebElement mobile_dd;
		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement transactionType_dd;
		String serviceStatus;
		String servicePath = "/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]";
		
		System.out.println("--------------------------------------------------");
		System.out.println("Testing IDD");
		
		//Select IDD transaction type
		transactionType_dd = driver.findElement(By.xpath("/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[3]/select[1]"));
		transactionType_dd.click();
		transactionType_dd.sendKeys(Keys.DOWN);
		transactionType_dd.sendKeys(Keys.DOWN);
		transactionType_dd.sendKeys(Keys.DOWN);
		transactionType_dd.sendKeys(Keys.ENTER);
		
		//Select mobile number
		Thread.sleep(2000);
		mobile_dd = driver.findElement(By.xpath(
				"//input[@placeholder='Select a Mobile Number']"));
		Thread.sleep(1000);
		mobile_dd.click();
		Thread.sleep(1000);
		mobile_dd.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
//		mobile_dd.sendKeys("60143275073");
		Thread.sleep(2000);
		
		//Toggle serviceStatus
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(servicePath)));
		serviceStatus = driver.findElement(By.xpath(servicePath)).getText();
		if (serviceStatus.contains("ON")) {
			driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/label[1]/span[1]"))
				.click();
			Thread.sleep(2000);
		}
		
		//Click next
		driver.findElement(By.xpath(nextPath)).click();
		Thread.sleep(2000);
		
		//Go back
		driver.findElement(By.xpath(backPath)).click();
		Thread.sleep(2000);
		
		logger.pass("IDD test completed successfully");
	}
	
	public void validateSMS(ExtentTest logger) throws InterruptedException {
		WebElement selectTransaction_dd;
		WebElement mobileSelect_dd;
		String status;
		
		System.out.println("--------------------------------------------------");
		System.out.println("Testing SMS");
		
		//Select SMS in dropdown
		selectTransaction_dd = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[3]/select[1]"));
		selectTransaction_dd.click();
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.ENTER);
		
		//Select mobile number
		Thread.sleep(2000);
		mobileSelect_dd = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[2]/div[1]/input[1]"));
		mobileSelect_dd.click();
//		mobileSelect_dd.sendKeys("60143275073");
		mobileSelect_dd.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
		//Select/deselect toggle button
		status = driver.findElement(By.xpath("//div[@class='label label-success barredLabel statusStyle']")).getText();
		System.out.println(status);
		if (status.equals("ON")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[@class='slider round']")).click();
		}
		
		//Click next
		Thread.sleep(2000);
		driver.findElement(By.xpath(nextPath)).click();
		
		//Go back
		Thread.sleep(2000);
		driver.findElement(By.xpath(backPath)).click();
		
		//Delete row
		Thread.sleep(2000);
		driver.findElement(By.className("ic_delete")).click();
		
		logger.pass("SMS test completed successfully.");
	}
	
	public void validateLostStolen(ExtentTest logger) throws InterruptedException {
		WebElement selectTransaction_dd;
		WebElement mobileSelect_dd;
		String status;
		
		System.out.println("--------------------------------------------------");
		System.out.println("Testing Lost & Stolen");
		
		//Select SMS in dropdown
		selectTransaction_dd = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/app-order[1]/div[1]/div[1]/div[1]/div[2]/div[3]/select[1]"));
		selectTransaction_dd.click();
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.DOWN);
		selectTransaction_dd.sendKeys(Keys.ENTER);
		
		//Select mobile number
		Thread.sleep(2000);
		mobileSelect_dd = driver.findElement(By.xpath(
				"//input[@placeholder='Select a Mobile Number']"));
		mobileSelect_dd.click();
		mobileSelect_dd.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
		//Select/deselect toggle button
		status = driver.findElement(By.xpath("//div[@class='label label-success barredLabel statusStyle']")).getText();
		System.out.println(status);
		if (status.contains("ON")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[@class='slider round']")).click();
		}
		
		//Click next
		Thread.sleep(2000);
		driver.findElement(By.xpath(nextPath)).click();
		
		//Go back
		Thread.sleep(2000);
		driver.findElement(By.xpath(backPath)).click();
		
		//Delete row
		Thread.sleep(2000);
		driver.findElement(By.xpath("ic_delete")).click();
		
		logger.pass("Lost & Stolen test completed successfully.");
	}
}
