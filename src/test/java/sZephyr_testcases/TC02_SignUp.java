package sZephyr_testcases;

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
import utils.GetAppLog;
import utils.PassSTComment;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC02_SignUp extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC02_Sign Up with already registered username";
		testDescription = "Try to Sign Up with already registered username";
	}


	@Test(priority = 1)
	public void signUp() throws Exception {

		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		signuppage =new SignUpPage(driver);
//		GetAppLog applog= new GetAppLog();
//		applog.startLogProcess();
		

		/*
		 * logReadandWrite readwrite=new logReadandWrite("COM4"); readwrite.openPort();
		 * readwrite.read(); Thread.sleep(2000); readwrite.write("button_press\r");
		 */
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();

		
		signuppage.uninstall_reinstall();
		landingpage.clickSignUpLink();

		
		signuppage.enterUserName("testuser");
		signuppage.enterEmailId("testuser@gmail.com");
		signuppage.clickSignUpTCCheckBox();
		signuppage.clickSignUpButton();
		//readwrite.write("button_press\r");
		signuppage.checkUserNameExistToast("Username and Email ID both are already exists");
		readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}

	}

}