package sampMax_testcase;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC03_SignIn_Flow_Max extends MobileAppWrappers{
	LandingPage landingpage;
	SignInPage signinpage;
	HomePage homepage;
	OtpPage otppage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC03_SignIn_Flow";
		testDescription = "Try to Sign In with unregistered username";
}
	@Test(priority = 2)
	public void signIn() throws Exception {
		initAndriodDriver();
		signinpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		signuppage =new SignUpPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
//		readwrite.read();
		
		//signuppage.uninstall_reinstall();
		landingpage.clickSignInButton();
		double rand=Math.random()*100000000;
		signinpage.enterUserName("user"+(int)rand);
		signinpage.clickSignInButton();
		signinpage.checkUserNameNotFoundToast("User Not Found");

		readwrite.closePort();
		}
		catch (Exception e) {
			e.printStackTrace();
			readwrite.closePort();
			fail(e);
		}
	}


}
