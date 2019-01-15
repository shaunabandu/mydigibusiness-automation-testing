package my.com.mydigibusiness;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseMethods extends TestCases  {
	
	String url = "";
	
	public ExtentReports createReport(ExtentHtmlReporter reporter, ExtentReports extent){
		// Extent Report
		reporter = new ExtentHtmlReporter("MyDigiBizzRepisitory\\Extent REPORT\\MyDigiBizzReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		return extent;
	}

	public File createFolder(File folder){
		
		//Creates Random Folder for Downloaded Files
		folder = new File(UUID.randomUUID().toString());
		folder.mkdir();
		try {
			//Changing Default Download folder directory
			System.setProperty("webdriver.chrome.driver","chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", folder.getAbsolutePath());
			prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("disable-infobars"); 
			driver = new ChromeDriver(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folder;
		
	}
	public void invokeBrowser() {
		try {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginToMyDigiBusiness(String username, String password) throws InterruptedException {
		
		// Proceeding to the MyDigiBusiness.com.my
		url = "https://mydigibusiness.digi.com.my/";
		driver.get(url);
		
		// Entering User Credentials
		driver.findElement(By.id("phone")).sendKeys(username);
		Thread.sleep(1300);
		driver.findElement(By.id("next")).click();
		driver.findElement(By.id("enterPassword")).sendKeys(password);
		Thread.sleep(1300);
		driver.findElement(By.id("next")).click();
	}

	public void closeBrowser(ExtentReports extent,File folder) {
		folder.delete();
		driver.close();
	}

	// Taking Screenshot method
	public void takeSnapShot(String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

	// Soft Assertion Validation
	public void validate() {
		SoftAssert softAssert1 = new SoftAssert();
		softAssert1.assertTrue(false);
		softAssert1.assertAll();
	}
}
