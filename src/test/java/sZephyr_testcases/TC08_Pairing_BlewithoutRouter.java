package sZephyr_testcases;

import static org.testng.Assert.fail;

import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import utils.Retry_analyser;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC08_Pairing_BlewithoutRouter extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC08 - Pairing BLE Without Router";
		testDescription = "TC-01-After Pairing check on/off remove device"+"<br>"+
		"TC-02-After Pairing check on/off remove device ,repair and check on/off "+"<br>"+
		"TC-03-With internet after Pairing check on/off ,initaite logout and login check for added device ,remove and repair check on/off .";
	}
	
	
//	(retryAnalyzer = Retry_analyser.class)
	@Test(priority = 7)
	public void removerepair() throws Exception {
		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
		Thread.sleep(3000);
//		readwrite.write("factory_reset\r");
		
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
		
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		Thread.sleep(8000);
		homepage.clickONOFFButton();
		homepage.VerifyONdesc();
		homepage.clickONOFFButton();
		homepage.VerifyOFFdesc();
				
		homepage.clickMenuBarButton();
		devicemenupage.clickLogoutButton();
		devicemenupage.clickLogoutConfirmationButton();
		landingpage.clickSignInButton();
		loginpage.enterUserName("testuser007@gmail.com");
		loginpage.clickSignInButton();
		otppage.verifyOTPVerificationTitle("OTP Verification");
		otppage.enterOTPField1("1");
		otppage.enterOTPField2("2");
		otppage.enterOTPField3("3");
		otppage.enterOTPField4("4");
		otppage.submitButton();
		homepage.VerifyOFFdesc();
		
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		Thread.sleep(8000);
		homepage.clickONOFFButton();
		homepage.VerifyONdesc();
		homepage.clickONOFFButton();
		homepage.VerifyOFFdesc();
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		
        readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}
	}

	//newly added SKIP in extent report 
	//newly added toast reader in add device page 
	//newly added info 
	
	//To add toast in all page,need to do
}
