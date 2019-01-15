package my.com.mydigibusiness;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ReportsAndAnalytics extends BaseMethods {

	
	// Variables that keeps URL's for the Validation Process
	String currentUrl;
	String paidBillsPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
			+ "MyDigiBizzRepisitory\\ScreenShots\\Reports & Analytics\\Paid Bills.jpg";
	String reportsPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
			+ "MyDigiBizzRepisitory\\ScreenShots\\Reports & Analytics\\ReportsMain.jpg";
	
	// Counter of Report
	int countReport = 0;

	//Select account number from dropdown
	public void selectAccount() {
		WebElement account_dd;
		String billPeriod;
		jse = (JavascriptExecutor) driver;
		
		try {
			// Scroll Up
			Thread.sleep(1300);

			jse.executeScript("scroll(0,-1000)");
			Thread.sleep(2000);
			
			// Selecting Account
			account_dd = driver.findElement(By.tagName("input"));
			account_dd.click();
			Thread.sleep(1000);
			driver.findElement(By.tagName("input")).sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			
			//Get default billing period after choosing account
			billPeriod = driver.findElement(By.id("selectBillingPeriod")).getText();
			
			//If there is no billing period to select, clear account dropdown and and click second option
			if (billPeriod.equals("")) {
				Thread.sleep(2000);
				account_dd.sendKeys(Keys.CONTROL + "a");
				account_dd.sendKeys(Keys.BACK_SPACE);
				Thread.sleep(1000);
				account_dd.sendKeys(Keys.DOWN);
				account_dd.sendKeys(Keys.ENTER);
				Thread.sleep(3000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Download Files as PDF and CSV
	public void downloadFiles() {
		try {
			// Download as CSV
			driver.findElement(By.
					xpath("//button[contains(text(), 'Download as Excel')]")).click();
			Thread.sleep(20000);
			
			// Download as PDF
			driver.findElement(By.
					xpath("//button[contains(text(), 'Download as PDF')]")).click();
			Thread.sleep(20000);	
		}
		
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Select Billing Period
	public void selectBillingPeriod() {
		WebElement date_dd = driver.findElement(By.id("selectBillingPeriod"));
		date_dd.click();
		try {
			Thread.sleep(1300);
			Select date = new Select(date_dd);
			date.selectByIndex(0);
			Thread.sleep(1300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Click back button in every download page
	public void clickBackButton() throws InterruptedException {
		driver.findElement(By.xpath("//button[contains(text(),'Back')]")).click();
	}

	public void proceedToReportsAndAnalytics(ExtentTest logger) throws Exception {
		try {
			// Proceeding to the Reports and Analytics Page
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement reports;
			reports = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//a[contains(text(),'Reports & Analytics')]")));
			Thread.sleep(1300);
			reports.click();

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.
							xpath("//div[@class='col-xs-12 col-sm-12 col-md-12']//div[1]//div[1]//div[1]//div[1]")));
			
			// Validation for the Reports and Analytics Page
			currentUrl = driver.findElement(By.xpath("//h4[@class='CompanyProfile']")).getText();
			if (currentUrl.contains("Download Reports")) {
				System.out.println("------------------------------------------------");
				System.out.println("Reports and Analytics Loaded Successfully");
			} else {
				System.out.println("------------------------------------------------");
				System.out.println("Reports and Analytics: FAILED");
			}
			
			logger.pass("Reports & Analytics Page Loaded Successfully");

			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.fail("Reports & Analytics Page FAILED");
			e.printStackTrace();
		}
	}

	public void downloadStatementAccount(ExtentTest logger, File folder) throws Exception {
		try {

			try {
				
				//Clicking "Download Now" for Statement Account
				driver.findElement(By.xpath(
						"//div[@class='col-xs-12 col-sm-12 col-md-12']//div[1]//div[1]//div[1]//div[3]"))
						.click();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Selecting Account and Billing period
			selectAccount();
			Thread.sleep(2000);
			
			//Setting path for screenshot
			String accountCode = driver.findElement(By.id("1")).getAttribute("value");
			System.out.println("Account number is: " + accountCode);
			String accountStatementPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
					+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Reports & Analytics\\Account Statement.jpg";
			logger.info("Statement of Account Testing",
					MediaEntityBuilder.createScreenCaptureFromPath(accountStatementPath).build());
			
			//Taking screenshot
			takeSnapShot(accountStatementPath);
			
			//Download Files
			downloadFiles();
			
			// Taking Screenshot
			Thread.sleep(2000);
			
			// Accessing Downloaded files using Array
			File listOfFiles[] = folder.listFiles();
			// Counting Reports
			
			System.out.println("Folder name: " + folder.getName());
			countReport = countReport + listOfFiles.length;
			
			// Validation of Files
			if (listOfFiles.length == 2) {
				System.out.println("==============================================================");
				System.out.println("Statement of Account CSV and PDF files are:");
				logger.info("Statement of Account CSV and PDF files are:");
				for (File file : listOfFiles) {
					System.out.println(file.getName() + ": File Downloaded Successfully!");
					logger.pass(file.getName() + ": File Downloaded Successfully!");
				}
	
				System.out.println("------------------------------------------------");
				System.out.println("Statement of Account: PASSED");
				logger.pass("Statement of Account: PASSED");
				System.out.println("==============================================================");
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("------------------------------------------------");
				System.out.println("Statement of Account: FAILED");
				logger.fail("Statement of Account: FAILED");
				validate();
			}

			// Deleting files
			for (File file : folder.listFiles()) {
				file.delete();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread.sleep(1300);
		
		clickBackButton();
		
	}

	public void downloadSummarizedUsageReport(ExtentTest logger,File folder) throws Exception {
		try {
			try {

				//Clicking "Download Now" for Summarized Usage Report
				driver.findElement(By.xpath(
						"//div[@class='col-xs-12 col-sm-12 col-md-12']//div[2]//div[1]//div[1]//div[3]"))
						.click();

				Thread.sleep(2000);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Selecting an account
			selectAccount();
			
			//Setting path for screenshot
			String accountCode = driver.findElement(By.id("1")).getAttribute("value");
			String summarizedUsagePath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
					+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Reports & Analytics\\Summarized Usage.jpg";
			logger.info("Summarized Usage Report Testing",
					MediaEntityBuilder.createScreenCaptureFromPath(summarizedUsagePath).build());
			
			// Taking Screenshot
			takeSnapShot(summarizedUsagePath);
			
			//Downloading Files
			downloadFiles();
			
			// Accessing Downloaded files using Array
			File listOfFiles[] = folder.listFiles();
			
			// Counting Reports
			countReport = countReport + listOfFiles.length;

			// Validation of Files
			if (listOfFiles.length == 2) {
				System.out.println("==============================================================");
				System.out.println("Summarized Usage Report CSV and PDF files are:");
				logger.info("Summarized Usage Report CSV and PDF files are:");
				for (File file : listOfFiles) {
					System.out.println(file.getName() + ": File Downloaded Successfully!");
					logger.info(file.getName() + ": File Downloaded Successfully!");
				}
				
				System.out.println("------------------------------------------------");
				System.out.println("Summarized Usage Report Usage: PASSED");
				logger.pass("Summarized Usage Report: PASSED");
				System.out.println("==============================================================");
				Thread.sleep(1300);
			} else {
				System.out.println("------------------------------------------------");
				System.out.println("Summarized Usage Report Usage: FAILED");
				logger.fail("Summarized Usage Report: FAILED");
				validate();
			}
			
			// Deleting files
			for (File file : folder.listFiles()) {
				file.delete();
			}
			
		} catch (InterruptedException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Reports & Analytics')]")).click();
			logger.fail("Summarized Usage Report: FAILED");
			e.printStackTrace();
		}
		Thread.sleep(1300);

		clickBackButton();
	}

	public void downloadItemizedUsageReport(ExtentTest logger,File folder) throws Exception {
		try {
			try {

				
				//Clicking "Download Now" for Itemized Usage Report
				driver.findElement(
						By.xpath("//div[@class='col-xs-12 col-sm-12 col-md-12']//div[3]//div[1]//div[1]//div[3]")).click();

			} catch (Exception e) {
				e.printStackTrace();
			}

			jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0,-1000)");
			Thread.sleep(1300);
			
			// Selecting Account, Billing period, Account Type and MSISDN
			selectAccount();
			selectBillingPeriod();

			//Setting path for screenshot
			String accountCode = driver.findElement(By.id("1")).getAttribute("value");
			String itemizedUsagePath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
					+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Reports & Analytics\\Itemized Usage.jpg";
			logger.info("Itemized Usage Report Testing",
					MediaEntityBuilder.createScreenCaptureFromPath(itemizedUsagePath).build());
			
			//Download Files
			downloadFiles();
			
			// Taking Screenshot
			takeSnapShot(itemizedUsagePath);
			
			// Accessing Downloaded files using Array
			File listOfFiles[] = folder.listFiles();
			
			// Counting Reports
			countReport = countReport + listOfFiles.length;

			// Validation of Files
			if (listOfFiles.length == 2) {
				System.out.println("==============================================================");
				System.out.println("Itemized Usage Report CSV and PDF files are:");
				logger.info("Itemized Usage Report CSV and PDF files are:");
				for (File file : listOfFiles) {
					System.out.println(file.getName() + ": File Downloaded Successfully!");
					logger.pass(file.getName() + ": File Downloaded Successfully!");
				}
				System.out.println("------------------------------------------------");
				System.out.println("Itemized Usage Report: PASSED");
				logger.pass("Itemized Usage Report: PASSED");
				System.out.println("==============================================================");
				Thread.sleep(1300);
			} else {
				System.out.println("------------------------------------------------");
				System.out.println("Itemized Usage Report: FAILED");
				logger.fail("Itemized Usage Report: FAILED");
				validate();
			}
			
			// Deleting files
			for (File file : folder.listFiles()) {
				file.delete();
			}
			
			// Go Back
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, 250);");
		
		} catch (InterruptedException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Reports & Analytics')]")).click();
			logger.fail("Itemized Usage Report: FAILED");
			e.printStackTrace();
		}
		
		Thread.sleep(1300);
		clickBackButton();

	}
	
	public void downloadTaxReport(ExtentTest logger,File folder) throws Exception {
		try {
			try {

				//Clicking "Download Now" for Tax Report
				driver.findElement(By.xpath(""
						+ "//div[@class='col-xs-12 col-sm-12 col-md-12']//div[4]//div[1]//div[1]//div[3]")).click();
				logger.info("Tax Report Testing",
						MediaEntityBuilder.createScreenCaptureFromPath(paidBillsPath).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(1300);
			
			selectAccount();
			
			downloadFiles();
			
			// Taking Screenshot
			takeSnapShot(paidBillsPath);
			
			// Accessing Downloaded files using Array
			File listOfFiles[] = folder.listFiles();
			
			// Counting Reports
			countReport = countReport + listOfFiles.length;

			// Validation of Files
			if (listOfFiles.length == 2) {
				System.out.println("==============================================================");
				System.out.println("Tax Report CSV and PDF files are:");
				logger.info("Tax Report CSV and PDF files are:");
				for (File file : listOfFiles) {
					System.out.println(file.getName() + ": File Downloaded Successfully!");
					logger.pass(file.getName() + ": File Downloaded Successfully!");
				}
				
				System.out.println("------------------------------------------------");
				System.out.println("Tax Report: PASSED");
				logger.pass("Tax Report: PASSED");
				System.out.println("==============================================================");
				Thread.sleep(1300);
			} else {
				System.out.println("------------------------------------------------");
				System.out.println("Tax Report: FAILED");
				logger.fail("Tax Report: FAILED");
				validate();
			}
			
			// Deleting files
			for (File file : folder.listFiles()) {
				file.delete();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void countReports(ExtentTest logger) {
		System.out.println("Expected Number of Reports: 8");
		System.out.println("Total Downloaded Number of Reports: " + countReport);
		logger.info("Expected Number of Reports: 8");
		logger.info("Total Downloaded Number of Reports: " + countReport);
		if (countReport != 8) {
			logger.fail("Reports & Analytics Testing FAILED");
			Assert.assertTrue(false);
		}
	}
}
