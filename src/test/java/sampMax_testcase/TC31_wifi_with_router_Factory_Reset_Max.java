package sampMax_testcase;

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

public class TC31_wifi_with_router_Factory_Reset_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC31_wifi_with_router Factory Reset";
		testDescription = "Paired with device wifi_with_router mode and Switch to Ble then try to do factory reset using via app";
	}



@Test(priority = 30)
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
	
	adddevicepage.pair(4);
	adddevicepage.clickNextButtonsZephyrInfo();
	adddevicepage.checkdevicedetailstoast();
	adddevicepage.clickSubmitButtonDeviceSetting();
	adddevicepage.checkdevicesettingstoast();
	homepage.disableWIFI();
	homepage.enableBLE();
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
//	homepage.VerifyONdesc();
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
//	homepage.VerifyOFFdesc();
	
	Thread.sleep(1000);
	homepage.killandopen();
	Thread.sleep(7000);
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
