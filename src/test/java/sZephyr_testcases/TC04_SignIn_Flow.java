package sZephyr_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.SignInPage;
import pages.OtpPage;
import pages.SignUpPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC04_SignIn_Flow extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage signinpage;
	HomePage homepage;
	OtpPage otppage;
	SignUpPage signuppage;
	AddDevicePage adddevicepage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC04_SignIn_Try to Sign In with unregistered username";
		testDescription = "Try to Sign In with unregistered username";
	}


	@Test(priority = 3)
	public void signIn() throws Exception {
		initAndriodDriver();
		signinpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		signuppage =new SignUpPage(driver);
		adddevicepage=new AddDevicePage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		signuppage.uninstall_reinstall();
		landingpage.clickSignInButton();
		double rand=Math.random()*100000000;
		signinpage.enterUserName("user"+(int)rand);
		signinpage.clickSignInButton();
		signinpage.checkUserNameNotFoundToast("User Not Found");

		readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}
	}

}