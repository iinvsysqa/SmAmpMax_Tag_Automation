package sZephyr_testcases;

import static org.testng.Assert.fail;

import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC07_SignIn_Logout extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC07_Logout_After Login, Pair with device and logout from device is working";
		testDescription = "After Login, Pair with device and logout from device is working";
	}
	
	@Test(priority = 6)
	public void login() throws Exception {
		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		homepage=new HomePage(driver);
		signuppage =new SignUpPage(driver);
		
		

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		signuppage.uninstall_reinstall();
		landingpage.clickSignInButton();
		driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.ACCESS_FINE_LOCATION"));
		driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.BLUETOOTH_SCAN"));
		driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.BLUETOOTH_CONNECT"));
		loginpage.enterUserName("testuser@gmail.com");
		loginpage.clickSignInButton();
		otppage.verifyOTPVerificationTitle("OTP Verification");
		otppage.enterOTPField1("1");
		otppage.enterOTPField2("2");
		otppage.enterOTPField3("3");
		otppage.enterOTPField4("4");
		otppage.submitButton();
         
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		for(int i=0;i<2;i++) {
			homepage.clickONOFFButton();
			Thread.sleep(1000);
			}
		
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		homepage.clickMenuBarButton();
		devicemenupage.clickLogoutButtonAfterReset();
		devicemenupage.clickLogoutConfirmationButton();
		landingpage.clickSignUpLink();
		readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");		
			readwrite.closePort();
			fail(e);
		}
	}
		
	
}
