package sampMax_testcase;

import static org.testng.Assert.fail;

import org.testng.Assert;
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

public class TC01_SignUp_Max extends MobileAppWrappers{
	
	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;
	StoreLogPage storelog;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC01_SignUp";
		testDescription = "Try to Sign Up with already registered username";

}

	@Test(priority = 0)
	public void signUp() throws Exception {

		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		signuppage =new SignUpPage(driver);
		storelog= new StoreLogPage(driver);
//		GetAppLog applog= new GetAppLog();
//		applog.startLogProcess();
		

		/*
		 * logReadandWrite readwrite=new logReadandWrite("COM4"); readwrite.openPort();
		 * readwrite.read(); Thread.sleep(2000); readwrite.write("button_press\r");
		 */
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();

		
		landingpage.clickSignUpLink();

		
		signuppage.enterUserName(loadProp("USERNAME"));
		signuppage.enterEmailId(loadProp("EMAILID"));
		signuppage.clickSignUpTCCheckBox();
		signuppage.clickSignUpButton();
		//readwrite.write("button_press\r");
		//Username and Email ID both are already exists
		signuppage.checkUserNameExistToast("Username and Email ID both are already exists");
		readwrite.closePort();
		}
		catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			e.printStackTrace();
			readwrite.closePort();
			Assert.fail("Failed due to this exception", e);
			
		}

	}

}



