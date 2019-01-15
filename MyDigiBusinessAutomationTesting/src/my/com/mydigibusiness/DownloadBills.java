package my.com.mydigibusiness;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class DownloadBills extends BaseMethods {

	public void proceedToDownloadBills(ExtentTest logger) throws Exception {
		// Proceeding to the Download Bills Page
		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement billsAndPayment;
		billsAndPayment = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Bills & Payment')]")));
		billsAndPayment.click();
		
		String accountCode = driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]/div[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]")).getText();
		String billPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Bills & Payment\\Download Bills\\Download Bills Page.jpg";
		
		try {
			//Wait until main body appears
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]")));

			// Validation for the Download Bills Page
			String title = driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]/div[1]/div[1]/strong[1]")).getText();
			if (title.contains("Download Bils")) {
				System.out.println("Download Bills Page Loaded Successfully");
				
				logger.pass("Download Bills Page Loaded Successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(billPath).build());

			} else {
				System.out.println("Download Bills Page: FAILED");
				logger.fail("Download Bills Page: FAILED!");
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[contains(text(),'Account')]")));
			takeSnapShot(billPath);

		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.fail("Proceeding to the Download Bills Page FAILED",
					MediaEntityBuilder.createScreenCaptureFromPath(billPath).build());
		}
	}

	public void downloadIndividualBill(ExtentTest logger, File folder) {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		try {

			//Downloading individual bill
			Thread.sleep(2000);
			System.out.println("Downloading individual bill...");
			driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]/div[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/button[1]")
					).click();
			
			//Wait for download button to be present again
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]/div[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/button[1]")));
			Thread.sleep(2000);

		} catch (Exception e) {
			logger.skip("There is no data to be tested.");
			throw new SkipException("Skipping download individual bill");
		}
		
		//Accessing download file using array
		File listOfFiles[] = folder.listFiles();
		System.out.println("Number of files in folder: " + listOfFiles.length);
		if (listOfFiles.length == 1) {

			System.out.println("------------------------------------------------");
			System.out.println("File exists: Download Individual Bill PASSED");
			logger.pass("File exists: Download Individual Bill PASSED");
			System.out.println("==============================================================");

		} else {
			System.out.println("------------------------------------------------");
			System.out.println("File does not exist: Download Individual Bills FAILED");
			logger.fail("File does not exist: Download Individual Bills FAILED");
			validate();
		}
		
		// Make sure that downloaded file is not empty
		for (File file : listOfFiles) {
			boolean checkFile = file.length() > 0;
			String fileName = file.getName();
			if (checkFile) {
				System.out.println(fileName + ": Downloaded Successfully!");
				logger.pass(fileName + ": Downloaded Successfully!");
//				System.out.println("Deleting FILE");
//				file.delete();
			} else {
				logger.fail(fileName + ": File is Empty!");
			}

			Assert.assertTrue(checkFile);
		}
		
		//Deleting file
		for (File file : folder.listFiles()) {
			file.delete();
		}	
	}

	public void downloadBillsValidation(ExtentTest logger, File folder) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		try {

			// Clicking download selected bills button
			Thread.sleep(2000);
			System.out.println("Downloading all bills...");
			driver.findElement(By.xpath("//button[contains(text(), 'Download Bill')]")).click();
			System.out.println("Clicked Download All");
			
			//Wait for download to finish and for main body to appear
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/downloadbill[1]/div[2]/div[1]/div[1]/div[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/button[1]")));

		} catch (Exception e) {
			logger.skip("There is no data to be tested.");
			throw new SkipException("Skipping download all bills exception");
		}
		
		//Accessing downloaded files using array
		File listOfFiles[] = folder.listFiles();
		System.out.println("Number of files in folder: " + listOfFiles.length);
		if (listOfFiles.length >= 1) {

			System.out.println("------------------------------------------------");
			System.out.println("File exists: Download Bills PASSED");
			logger.pass("File exists: Download Bills PASSED");
			System.out.println("==============================================================");

		} else {
			System.out.println("------------------------------------------------");
			System.out.println("File does not exist: Download Bills FAILED");
			logger.fail("File does not exist: Download Bills FAILED");
			validate();
		}

		// Make sure that downloaded file is not empty
		for (File file : listOfFiles) {
			boolean checkFile = file.length() > 0;
			String fileName = file.getName();
			if (checkFile) {
				System.out.println(fileName + ": Downloaded Successfully!");
				logger.pass(fileName + ": Downloaded Successfully!");
//				file.delete();
			} else {
				System.out.println(fileName + ": File is Empty!");
				logger.fail(fileName + ": File is Empty!");
			}
		}
		
		//Deleting file
		for (File file : folder.listFiles()) {
			file.delete();
		}	
	}
	
}
