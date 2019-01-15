package my.com.mydigibusiness;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class OverviewPage extends BaseMethods {
	
	String accountCode = driver.findElement(By.xpath(
			"/html[1]/body[1]/digi-ewp[1]/companyinfo[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/button[1]/span[1]"))
			.getAttribute("innerHTML").replace(" ", "");

	
	public void validateOverviewPage(ExtentTest logger) throws Exception {

		String overviewPath;
		String overviewPath2;
		String overviewPath3;
		String overviewPath4;
		String title = driver.findElement(By.className("boxtitle")).getText();

		overviewPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Overview Page\\OverviewPage.jpg";
		overviewPath2 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Overview Page\\OverviewPage2.jpg";
		overviewPath3 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Overview Page\\OverviewPage3.jpg";
		overviewPath4 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
		+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Overview Page\\OverviewPage4.jpg";
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		System.out.println("Account Code is: " + accountCode);
		try {
			// Overview Page is Loading
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

			// Validation for The Overview Page
			boolean companyProfile = driver.findElement(By.className("address"))
					.isDisplayed();
			if (companyProfile) {

				// Extent Report, building path for screenshots
				logger.log(Status.PASS, "SUCCESS - Company Profile Loaded",
						MediaEntityBuilder.createScreenCaptureFromPath(overviewPath).build());
				logger.log(Status.PASS, "SUCCESS - Corporate Account Loaded",
						MediaEntityBuilder.createScreenCaptureFromPath(overviewPath2).build());
				
				System.out.println("------------------------------------------------");
				System.out.println("Company Profile Entered Successfully!");
			} else {
				System.out.println("FAILED - Company Profile Not Loaded");
				logger.fail("FAILED - Company Profile Failed",
						MediaEntityBuilder.createScreenCaptureFromPath(overviewPath).build());
			}

			Assert.assertTrue(companyProfile);

			// Taking Screenshot of Overview Page
			takeSnapShot(overviewPath);

			// Scrolling Down
			jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 400)");
			
			//Default account number in Select An Account dropdown
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/companyinfo[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/button[1]/span[1]")));
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("CompanyName")));
			
			//Click on select an account button
			driver.findElement(By.className("select_acc_button_box")).click();
			Thread.sleep(2000);
			
			//Select first account
			driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/companyinfo[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/button[1]"))
					.click();
			
			// Taking Screenshot of Overview Page(2)
			takeSnapShot(overviewPath2);
			
			//If console account, test editing boxes
			title = driver.findElement(By.className("boxtitle")).getText();
			if (title.equals("Console Account")) {
				System.out.println("Searching for editing cards account section...");
				
				//Wait until elements have finished loading
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Billing Address')]")));
				
				//Billing Address window
				driver.findElement(By.xpath(
						"//div[@class='col-md-8']//div[@class='row']//div[@class='row']//div[1]//div[1]//div[1]//span[1]//button[1]"))
						.click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-content']")));
				System.out.println("Testing billing address window...");
				Thread.sleep(2000);
				logger.info("Billing Address window opened.", MediaEntityBuilder.createScreenCaptureFromPath(overviewPath3).build());
				takeSnapShot(overviewPath3);
				driver.findElement(By.className("close")).click();
				Thread.sleep(2000);
				
				//Billing Information
				driver.findElement(By.xpath(
						"//div[@class='row']//div[3]//div[1]//div[1]//span[1]//button[1]")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-content']")));
				System.out.println("Testing bill information window...");
				Thread.sleep(2000);
				logger.info("Billing Address window opened.", MediaEntityBuilder.createScreenCaptureFromPath(overviewPath4).build());
				takeSnapShot(overviewPath4);
				driver.findElement(By.className("close")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'Billing Cycle')]")));
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
