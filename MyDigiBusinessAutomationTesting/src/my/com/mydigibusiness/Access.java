package my.com.mydigibusiness;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Access extends BaseMethods {
	String accessUserPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\ScreenShots\\Access\\User Management.jpg";
	String accessEditUser = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\ScreenShots\\Access\\User Edit(Error Messages).jpg";
	String accessRolePath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\ScreenShots\\Access\\Role Management.jpg";
	String accessRoleDetailsPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\MyDigiBizzRepisitory\\ScreenShots\\Access\\Role Details.jpg";
	String userUrl = "https://examplewebsite.digi.com/";
	String roleUrl = "https://examplewebsite.digi.com/";

	public void accessUserTest(ExtentTest logger) throws Exception {
		try {
			// Proceeding to the Access User Management Page
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement accessUser;
			accessUser = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Access')]")));
			accessUser.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='page-header']//button[@type='button']")));
			Thread.sleep(1300);

			// User Managing
			logger.info("User Management Testing");
			takeSnapShot(accessUserPath);

			driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).isDisplayed();
			logger.pass("User Management Page Loaded Successfully!",
					MediaEntityBuilder.createScreenCaptureFromPath(accessUserPath).build());

			// Add New admin
			driver.findElement(By.xpath("//div[@class='page-header']//button[@type='button']")).click();
			driver.findElement(By.id("inputEmail")).sendKeys("test@digi.com.my");
			driver.findElement(By.id("inputPhoneNumber")).sendKeys("0123456789");
			driver.findElement(By.id("inputFirstName")).sendKeys("Shaun");
			driver.findElement(By.id("inputLastName")).sendKeys("Testing");
			driver.findElement(By.cssSelector(
					"div.modal-outer-box.center-heigh.popup-fade div.modal-internal div.modal-dialog div.modal-dialog div.modal-content div.modal-footer.btn-group-actions > div.btn.btn-blue:nth-child(2)"))
					.click();
			Thread.sleep(4000);

			// Search by Name
			logger.info("Searching By Name");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='form-group-i bg-lightgrey']//div[1]//input[1]"))
					.sendKeys("Nurdaa");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(text(),'Go')]")).click();
			// Search By Number Checking
			try {
				driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).isDisplayed();
				logger.pass("Search by Name SUCCESS");
			} catch (Exception e) {
				logger.fail("Search by Name FAILED");
				e.printStackTrace();
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@type='reset']")).click();
			Thread.sleep(2000);

			// Search by number
			logger.info("Searching By Phone Number");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Input Keyword (numbers)']")).sendKeys("0111111111111");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(text(),'Go')]")).click();
			
			// Search By Number Checking
			try {
				driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).isDisplayed();
				logger.pass("Search by Phone Number SUCCESS");
			} catch (Exception e) {
				logger.fail("Search by Phone Number FAILED");
				e.printStackTrace();
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@type='reset']")).click();
			Thread.sleep(2000);

			// Search by Email
			logger.info("Searching by E-mail");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='form-group-i bg-lightgrey']//div[3]//input[1]"))
					.sendKeys("test@digi.com.my");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(text(),'Go')]")).click();
			Thread.sleep(2000);
			// Search By E-mail Checking
			try {
				driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).isDisplayed();
				logger.pass("Search by E-mail SUCCESS");
			} catch (Exception e) {
				logger.fail("Search by E-mail FAILED");
				e.printStackTrace();
			}
			Thread.sleep(2000);

			// Edit User
			logger.info("Editing the User");
			try {
				driver.findElement(By.cssSelector(
						"div.page-body.m-top div.container div.white-box-table table.col-md-12.table-default.table-default-d.table-bordered tbody:nth-child(2) tr:nth-child(1) td.action.text-center:nth-child(8) > a.btn-action.btn-edit.cursor-pointer:nth-child(1)"))
						.click();
				Thread.sleep(2000);
				driver.findElement(By.id("inputFirstName")).clear();
				Thread.sleep(2000);
				driver.findElement(By.id("inputLastName")).clear();
				driver.findElement(By.id("inputLastName")).sendKeys(Keys.TAB);
				Thread.sleep(3000);
				driver.findElement(By.id("inputFirstName")).sendKeys("Nurdaulet");
				Thread.sleep(1300);
				driver.findElement(By.id("inputLastName")).sendKeys("Testt");
				Thread.sleep(1300);
				takeSnapShot(accessEditUser);
				driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
				logger.pass("Edited Successfully!");
				Thread.sleep(3000);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.fail("Edit FAILED");
				e.printStackTrace();
			}

			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Testt')]")));

			// Delete the User
			logger.info("Deleting The User");
			try {
				driver.findElement(By
						.xpath("/html[1]/body[1]/digi-ewp[1]/user-management[1]/div[1]/div[1]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[8]/a[2]/img[1]"))
						.click();
				Thread.sleep(2000);
				driver.findElement(By.id("delete")).click();
				Thread.sleep(2000);
				logger.pass("Delete User SUCCESS");
			} catch (Exception e1) {
				logger.fail("Delete User FAILED");
				e1.printStackTrace();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void accessRoleTest(ExtentTest logger) {
		try {
			// Proceeding to the Access Role Management Page
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement accessRole;
			accessRole = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Role')]")));
			accessRole.click();
			Thread.sleep(1300);
			logger.info("Role Management Testing");
			try {
				driver.findElement(By.xpath("//td[@class='col-xs-3 col-sm-3 col-md-3']")).isDisplayed();
				logger.pass("Role Management Page Loaded Successfully!",
						MediaEntityBuilder.createScreenCaptureFromPath(accessRolePath).build());
				takeSnapShot(accessRolePath);
				// Role Details
				try {
					driver.findElement(By.className("title")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputName")));
					Thread.sleep(1300);
					takeSnapShot(accessRoleDetailsPath);
					logger.pass("Role Details Loaded Successfully!",
							MediaEntityBuilder.createScreenCaptureFromPath(accessRoleDetailsPath).build());
				} catch (Exception e) {
					logger.fail("Role Details FAILED");
					e.printStackTrace();
				}
			} catch (Exception e) {
				logger.fail("There is No Role(s) Displayed in Role Management");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
