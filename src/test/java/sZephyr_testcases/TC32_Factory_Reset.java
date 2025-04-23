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
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC32_Factory_Reset extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC32_FactoryReset check";
		testDescription = "After paired with device Try to do factory reset using via app";
	}



	@Test(priority = 31)
	public void removerepair() throws Exception {
			initAndriodDriver();
			pairBlewithoutRouter();
	}



	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
//		Thread.sleep(3000);
//		readwrite.write("factory_reset\r");

		adddevicepage.pair(3);
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
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		adddevicepage.checkdeviceresettoast();
		devicemenupage.AddDevicePagedisplayed();
		 readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");
			readwrite.closePort();
			fail(e);
		}
	}

}
