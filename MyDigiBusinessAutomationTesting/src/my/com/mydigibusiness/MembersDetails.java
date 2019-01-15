package my.com.mydigibusiness;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class MembersDetails extends BaseMethods {
	String accountCode = driver.findElement(By.xpath(
			"/html[1]/body[1]/digi-ewp[1]/companyinfo[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/button[1]/span[1]"))
			.getAttribute("innerHTML").replace(" ", "");
	
	public void proceedToMembersDetails(ExtentTest logger) throws Exception {		
		
		String accountMembersPath = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Members\\1 - Line Members Page.jpg";

		WebDriverWait wait = new WebDriverWait(driver, 60);
		
		driver.findElement(By.xpath("//a[contains(text(),'Account & Members')]")).click();
		
		//Click Members link
		driver.findElement(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/ewp-header[1]/div[1]/nav[1]/div[1]/div[2]/ul[2]/li[1]/ul[1]/li[2]/a[1]")).click();
		
		//Scroll up
		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -300)");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[1]/div[2]/div[1]")));
		logger.pass("Line Members Page Loaded Successfully",
				MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath).build());
		System.out.println("--------------------------------------------");
		System.out.println("Line Members Page Loaded Successfully");
		
		takeSnapShot(accountMembersPath);
		
	}
	
	public void testSearchFunctions(ExtentTest logger) {
		String simSerial = "";
		String mobileNum = "";
		String simResult = "";
		String mobileResult = "";
		WebElement textfield;
		WebElement downloadTextField;
		WebElement account_dd;
		String randomString = "9213891238";
		WebDriverWait wait = new WebDriverWait(driver, 60);

//		String accountCode = driver.findElement(By.xpath(
//				"/html[1]/body[1]/digi-ewp[1]/companyinfo[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/button[1]/span[1]"))
//				.getAttribute("innerHTML").replace(" ", "");
		
		String accountMembersPath2 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Members\\2 - SIM Serial Result.jpg";
		
		String accountMembersPath3 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Members\\3 - MSISDN Result.jpg";
		
		String accountMembersPath4 = "C:\\Users\\t931391\\eclipse-workspace\\MyDigiBusinessAutomationTesting\\"
				+ "MyDigiBizzRepisitory\\ScreenShots\\" + accountCode + "\\Members\\4 - No Result.jpg";
		
		//Selecting default account from dropdown
		System.out.println("Selecting an account from dropdown...");
		account_dd = driver.findElement(By.className("accName"));
		account_dd.click();
		
		//Selecting first account
		try {
			Thread.sleep(2000);

			driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/button[1]"))
					.click();
	
			//Click download button
			driver.findElement(By.xpath("//input[@value='Download']")).click();
			
			//Wait for modal to appear
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]")));
			System.out.println("Displaying download modal...");
			
			//Testing email address and cancelling function
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(text(),'Change my email address')]")).click();
			downloadTextField = driver.findElement(By.xpath("//input[@placeholder='Type new email address below']"));
			downloadTextField.click();
			downloadTextField.sendKeys("email@website.com");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
			
			//Exit modal
			Thread.sleep(2000);
			driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/button[1]")).click();
			
			//-- SEARCHING FOR A SIM SERIAL NUMBER -- //
		
			//Getting sim serial number from first row in table
			simSerial = driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[1]/div[2]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[2]")).getText();
			
			//Finding and clicking search field
			textfield = driver.findElement(By.cssSelector("input[type='text']"));
			textfield.click();
			
			//Entering serial sim number into search field
			textfield.sendKeys(simSerial);
			
			//Selecting SIM SERIAL radio button
			driver.findElement(By.cssSelector("input[value='iccid']")).click();
			Thread.sleep(3000);
			
			//Get text from search result being displayed
			simResult = driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[1]/div[2]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[2]")).getText();
			if (simSerial.equals(simResult)) {
				logger.pass("Sim Serial Result found successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath2).build());
				System.out.println("=============================================");
				System.out.println("Sim Serial Result found successfully");
			}
			
			else {
				logger.fail("Sim Serial Result not found",
						MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath2).build());
				System.out.println("Sim Serial Result Not Found");
			}
			
			takeSnapShot(accountMembersPath2);
			
			//Click refresh button on search bar
			WebElement searchIcon = driver.findElement(By.className("searchIcon"));
			Thread.sleep(3000);
			searchIcon.click();
			
			//-- SEARCHING FOR A MOBILE NUMBER -- //
			
			//Getting first mobile number in list
			Thread.sleep(2000);
			driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[1]/div[2]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]")).click();
			mobileNum = driver.findElement(By.xpath(
					"/html[1]/body[1]/digi-ewp[1]/members_page[1]/div[1]/div[1]/div[2]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]")).getText();
			
			System.out.println("MSISDN is: " + mobileNum);
			
			//Selecting mobile number radio button
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[value='msisdn']")).click();
			Thread.sleep(2000);
			
			//Entering mobile number into search field
			textfield.click();
			Thread.sleep(3000);
			textfield.sendKeys(mobileNum);
			Thread.sleep(2000);
			textfield.sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			
			//Getting result of search
			mobileResult = driver.findElement(By.className("mb-5")).getText();
			if (mobileNum.equals(mobileResult)) {
				logger.pass("Mobile Number Result found successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath3).build());
				System.out.println("Mobile Number Result found successfully");
			}
			
			else {
				logger.fail("Mobile Number Result not found",
						MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath3).build());
				System.out.println("Mobile Result not found");
	
			}
			
			takeSnapShot(accountMembersPath3);
			Thread.sleep(2000);
			
			//Getting no result
			System.out.println("Displaying no result...");
			driver.findElement(By.className("searchIcon")).click();
			Thread.sleep(2000);
			textfield.click();
			Thread.sleep(1500);
			textfield.sendKeys(randomString);
			Thread.sleep(2000);
			textfield.sendKeys(Keys.ENTER);
			
			WebElement noResult = driver.findElement(By.xpath("//td[contains(text(), 'No Results Found')]"));
			if (noResult.isDisplayed()) {
				logger.pass("No result displayed test PASSED",
						MediaEntityBuilder.createScreenCaptureFromPath(accountMembersPath4).build());
				System.out.println("No result displayed successfully");
				System.out.println("=============================================");
			}
			
			takeSnapShot(accountMembersPath4);
			
			driver.findElement(By.className("searchIcon")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
