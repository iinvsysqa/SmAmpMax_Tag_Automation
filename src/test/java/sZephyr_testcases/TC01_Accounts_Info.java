package sZephyr_testcases;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import pages.AccountsInfoPage;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.SignInPage;
import utils.logReadandWrite;
import pages.OtpPage;
import wrappers.MobileAppWrappers;

import static org.testng.Assert.fail;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

public class TC01_Accounts_Info extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	AccountsInfoPage accountinfopage;
	DeviceMenuPage devicesettingpage;


	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC_01_Account_Info_Changes";
		//check login username and Accounts info username are same or not 
		//check for language selection 
		//pair with device try to del account and check for popup and try to remove device and try to del acnt and check add device page .

		testDescription = "Change language and check changed language ,before removing device try to delete account then try to remove device and delete account";
	}


	@Test(priority = 0)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}


	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		accountinfopage= new AccountsInfoPage(driver);
		devicesettingpage= new DeviceMenuPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			readwrite.openPort();
			Thread.sleep(2000);
			readwrite.write("reboot\r");
			Thread.sleep(3000);

			adddevicepage.pair(1);
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();




			homepage.clickONOFFButton();
			Thread.sleep(2000);
			homepage.VerifyONdesc();
			
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			homepage.VerifyOFFdesc();

			accountinfopage.checkAccountsinfousername_email_Language();

			readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}
	}


}	

