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

public class TC33_Ble_Factory_Reset extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC33_Ble Without Router Factory Reset";
		testDescription = "Paired with device Ble without router mode and try to do factory reset using via app";
	}



@Test(priority = 32)
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
//	Thread.sleep(3000);
//	readwrite.write("factory_reset\r");
	
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
	
	Thread.sleep(1000);
	homepage.killandopen();
	Thread.sleep(5000);
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
