package my.com.mydigibusiness;

import java.io.File;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import lib.ExcelDataConfig;

public class TestCases {
	ExtentHtmlReporter reporter;
	ExtentReports extent;
	BaseMethods base;
	OverviewPage overview;
	MembersDetails membersDetails;
	ManageMembers manageMembers;
	DownloadBills bills;
	ReportsAndAnalytics reports;
	PaymentProcess payment;
	ExtentTest logger;
	JavascriptExecutor jse;
	File folder;
	public static WebDriver driver;
	
	@BeforeClass
	public void invoker() {
		base = new BaseMethods();
		// Creates ExtentReport
		extent = base.createReport(reporter, extent);
		folder = base.createFolder(folder);
		// Invokes Browser
		base.invokeBrowser();
	}

	// Login to MyDigi Business
	@Test(dataProvider = "MyDigiBizData", priority = 0)
	public void loginner(String username, String password) {
		try {
			base.loginToMyDigiBusiness(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	// Overview Page Testing
	@Test(priority = 1)
	public void overviewTest() {
		overview = new OverviewPage();
		logger = extent.createTest("1: Overview Test",
				"This testcase validates whether the Overview Page has loaded successfully or not.");
		try {
			logger.info("Logged-in to MyDigi Business Successfully!");
			overview.validateOverviewPage(logger);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fail("Overview Test FAILED");
		}
	}

	//Account and Members Test
	@Test(priority = 2)
	public void accountMembers() {
		membersDetails = new MembersDetails();
		logger = extent.createTest("2: Accounts & Members Test", "This test case validates whether the Account and Members page loaded successfully or not.");
		try {
			logger.info("Logged-in to MyDigi Business Successfully!");
			membersDetails.proceedToMembersDetails(logger);
		} catch (Exception e) {
			logger.log(Status.INFO, "Sorry, there's no account that you can test with.");
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test(priority = 3)
	public void searchFunctions() {
		try {
			logger.log(Status.INFO, "Testing search functions in Members page.");
			membersDetails.testSearchFunctions(logger);
		} catch (Exception e) {
			logger.log(Status.INFO, "Sorry, there's no account that you can test with.");
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Parameters({"accountCode"})
	//Manage Members Test
	@Test(priority = 4)
	public void manageMembers(String accountCode) {
		manageMembers = new ManageMembers();
		logger = extent.createTest("3: Manage Members Test", 
				"This test case validates whether the Manage Members Page has loaded successfully or not.");
		try {
			logger.info("Logged-in to MyDigiBusiness Successfully!");
			manageMembers.proceedToManageMembers(logger, accountCode);
		} catch (Exception e) {
			logger.log(Status.INFO, "Sorry, there's no account that you can test with.");
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
//	
//	//Replacing Sim Tests
//	@Test(priority = 5, enabled=false)
//	public void replaceSims() {
//		try {
//			logger.info("Running tests on SIM Replacement Pages");
//			manageMembers.validateSimNumbers(logger);
//			
//		} catch (Exception e) {
//			logger.log(Status.FAIL, "Unable to run this test.");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 6, enabled=false)
//	public void testInternationalRoaming() {
//		try {
//			logger.info("Running test on same SIM number selected");
//			manageMembers.validateInternationalRoaming(logger);
//		} catch (Exception e) {
//			logger.log(Status.FAIL, "Unable to run this test");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 7, enabled=true)
//	public void testingIDD() {
//		try {
//			logger.info("Running test on IDD");
//			manageMembers.validateIDD(logger);
//		} catch (Exception e) {
//			logger.log(Status.FAIL, "Unable to run this test");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 8, enabled=false)
//	public void testingSMS() {
//		try {
//			logger.info("Running tests on SMS validation");
//			manageMembers.validateSMS(logger);
//		} catch (Exception e) {
//			logger.log(Status.FAIL, "Unable to run this test");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 9, enabled = false) 
//	public void testingLostStolen() {
//		try {
//			logger.info("Running tests on SMS validation");
//			manageMembers.validateLostStolen(logger);
//		} catch (Exception e) {
//			logger.log(Status.FAIL, "Unable to run this test");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	//Download Bills Test
//	@Test(priority = 8)
//	public void billsPageTest() {
//		bills = new DownloadBills();
//		logger = extent.createTest("3: Bills & Payment (Download Bills Test)",
//				"This testcase validates if the download bills page has been loaded successfully or not. \n It also validates the process of Downloading Bills");
//		try {
//			logger.info("Logged-in to MyDigi Business Successfully!");
//			bills.proceedToDownloadBills(logger);
//		} catch (Exception e) {
//			logger.fail("Download Bills FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 9)
//	public void individualBill() {
//		try {
//			logger.log(Status.INFO, "Downloading Individual Bill");
//			bills.downloadIndividualBill(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Download Individual Bill Failed");
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 10)
//	public void billsWithChild() {
//		try {
//			logger.log(Status.INFO, "Downloading Bills");
//			bills.downloadBillsValidation(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Download ALL Bills Failed");
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 11)
//	public void paymentsTest() {
//		payment = new PaymentProcess();
//		logger = extent.createTest("4: Bills & Payment (Payment Test)",
//				"This test case validates whether the Payment  Page has been loaded successfully or not. \n It also validates the process of payment for Make Payment including Child Accounts and for Group only");
//		try {
//			logger.info("Logged-in to MyDigi Business Successfully!");
//			payment.proceedToPaymentPage(logger);
//		} catch (Exception e) {
//			logger.log(Status.INFO, "Sorry, there's no corporate account that you can test with in Payment.");
//			logger.skip("Payment Test SKIPPED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 12)
//	public void paymentAllAccounts() {
//		try {
//			logger.info("Testing payment for all accounts");
//			payment.makePaymentAll(logger);
//		} catch (Exception e) {
//			logger.fail("Testing payment for all accounts FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(priority = 13)
//	public void paymentChildAccount() {
//		try {
//			logger.info("Testing payment for parent account only");
//			payment.makePaymentChildAccountOnly(logger);
//		} catch (Exception e) {
//			logger.fail("Testing payment for parent account only FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 14)
//	public void paymentParentAccount() {
//		try {
//			logger.info("Testing payment for parent account only");
//			payment.makePaymentParentAccountOnly(logger);
//		} catch (Exception e) {
//			logger.fail("Testing payment for parent account only FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 15)
//	public void reportsTest() {
//		reports = new ReportsAndAnalytics();  
//		logger = extent.createTest("5: Reports & Analytics (Download Reports Testing)",
//				"This testcase checks if the Reports And Analytics Page has loaded successfully or not. \n It also validates the process of Downloading Reports");
//		try {
//			logger.info("Logged-in to MyDigi Business Successfully!");
//			reports.proceedToReportsAndAnalytics(logger);
//		} catch (Exception e) {
//			logger.fail("Reports & Analytics Page:FAILED");
//			logger.skip("Download Reports: SKIPPED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 16)
//	public void reportsStatementAccount() {
//		try {
//			reports.downloadStatementAccount(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Statement of Account Testing FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 17)
//	public void reportsSummarizedUsage() {
//		try {
//			reports.downloadSummarizedUsageReport(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Summarized Usage Report Testing FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 18)
//	public void reportsItemizedUsage() {
//		try {
//			reports.downloadItemizedUsageReport(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Itemized Usage Report Download Testing FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 19)
//	public void reportsTaxReport() {
//		try {
//			reports.downloadTaxReport(logger, folder);
//		} catch (Exception e) {
//			logger.fail("Tax Report Download Testing FAILED");
//			Assert.assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//
//	@Test(priority = 20)
//	public void reportsCount() {
//		reports.countReports(logger);
//	}

//	@Test(priority = 21)
//	public void accessUser() throws Exception {
//		Access access = new Access();
//		logger = extent.createTest("5: Access (User Management)",
//				"This Testcase validates the User Management Page Loaded Successfull or Not and validates the process of Editing/Adding/Deleting the User");
//		access.accessUserTest(logger);
//	}
//
//	@Test(priority = 22)
//	public void accessRole() {
//		Access access = new Access();
//		logger = extent.createTest("6: Access (Role Management)",
//				"This Testcase validates the Role Management Page Loaded Successfull or Not and validates Role Details");
//		access.accessRoleTest(logger);
//	}

	@AfterClass
	public void afterTest() {
		extent.flush();
		// Deleting files
		for (File file : folder.listFiles()) {
			file.delete();
		}
	}

	@AfterClass
	public void close() {
		base.closeBrowser(extent, folder);
		folder.delete();
	}

	@DataProvider(name = "MyDigiBizData")
	public Object[][] passData() {

		ExcelDataConfig config = new ExcelDataConfig("MyDigiBizzRepisitory\\TestData.xlsx");
		int rows = config.getRowCount(0);
		Object[][] data = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);
		}
		return data;
	}
}
