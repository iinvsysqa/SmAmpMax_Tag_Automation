package sampMax_testcase;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC05_SignIn_SuccessFlow_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	SignUpPage signuppage;
	DeviceMenuPage devicemenupage;
	StoreLogPage storelog;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC05 - SignIn_SuccessFlow";
		testDescription = "During Sign Up or Sign In, enter valid OTP";
}
	
	@Test(priority = 4)
	public void login() throws Exception {
		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		signuppage =new SignUpPage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		homepage= new HomePage(driver);
		storelog= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		
		try {
		readwrite.openPort();
		
		landingpage.clickSignInButton();
		loginpage.enterUserName(loadProp("USERNAME"));
		loginpage.clickSignInButton();
		otppage.verifyOTPVerificationTitle("OTP Verification");
		otppage.enterOTPField1("1");
		otppage.enterOTPField2("2");
		otppage.enterOTPField3("3");
		otppage.enterOTPField4("4");
		otppage.submitButton();
		adddevicepage.verifyAddDevicePage("Add Device");
		homepage.clickMenuBarButton();
		devicemenupage.clickLogoutButtonAfterReset();
		devicemenupage.clickLogoutConfirmationButton();
		readwrite.closePort();
		}
		catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			e.printStackTrace();
			readwrite.closePort();
			fail(e);
		}
	}
}
