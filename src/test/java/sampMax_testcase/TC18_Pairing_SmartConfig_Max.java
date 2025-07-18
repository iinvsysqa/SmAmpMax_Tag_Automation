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

public class TC18_Pairing_SmartConfig_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	StoreLogPage storelog;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC18 - Pairing in Smart Config Mode";
		testDescription = "If already Signin skip signin and  Start Pairing Smartconfig mode, else Signin and pair Smartconfig mode";
	}

//	Properties prop= new Properties();
	
	@Test(priority = 17)
	public void pairingSmartConfig() throws Exception {
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
		Thread.sleep(2000);
		adddevicepage.pair(3);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();

		Thread.sleep(10000);
		homepage.clickONOFFButton();
//		homepage.VerifyONdesc();
		homepage.clickONOFFButton();
//		homepage.VerifyOFFdesc();
		
		
				
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
		
		Thread.sleep(8000);
//		homepage.VerifyOFFdesc();
		adddevicepage.blepermissionokpopup();
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
