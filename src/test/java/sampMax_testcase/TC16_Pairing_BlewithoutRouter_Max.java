package sampMax_testcase;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC16_Pairing_BlewithoutRouter_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	StoreLogPage storelog;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC16 - Pairing BLE Without Router";
		testDescription = "TC-01-After Pairing check on/off remove device"+"<br>"+
		"TC-02-After Pairing check on/off remove device ,repair and check on/off "+"<br>"+
		"TC-03-With internet after Pairing check on/off ,initaite logout and login check for added device ,remove and repair check on/off .";
	}
	
	
//	(retryAnalyzer = Retry_analyser.class)
	@Test(priority = 15)
	public void TC16_Pairing_BlewithoutRouter() throws Exception {
		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		storelog= new StoreLogPage(driver);		

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickBleokbutton();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		homepage.clickONOFFButton();
		Thread.sleep(2000);
		
		homepage.clickONOFFButton();
		Thread.sleep(2000);
		
		
		homepage.clickMenuBarButton();
		devicemenupage.clickLogoutButton();
		devicemenupage.clickLogoutConfirmationButton();
		landingpage.clickSignInButton();
		loginpage.enterUserName(loadProp("USERNAME"));
		loginpage.clickSignInButton();
		otppage.verifyOTPVerificationTitle("OTP Verification");
		otppage.enterOTPField1("1");
		otppage.enterOTPField2("2");
		otppage.enterOTPField3("3");
		otppage.enterOTPField4("4");
		otppage.submitButton();
		
		
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		
		
		
		
		
        readwrite.closePort();
		}
		catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}
	}
}
