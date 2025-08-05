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
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC31_Ble_Factory_Reset_Max extends MobileAppWrappers{

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
		testCaseName = "TC31_Ble_with Router Factory Reset";
		testDescription = "Paired with device Ble with Router mode and Switch to Ble then try to do factory reset using via app";
	}



@Test(priority = 30)
public void TC31_Ble_with_Router_Factory_Reset() throws Exception {
	initAndriodDriver();
	pairBlewithoutRouter();
}



public void pairBlewithoutRouter() throws Exception {
	adddevicepage= new AddDevicePage(driver);
	homepage = new HomePage(driver);
	devicemenupage= new DeviceMenuPage(driver);
	storelog= new StoreLogPage(driver);
	
	logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
	try {
	readwrite.openPort();
	
	adddevicepage.pair(2);
	adddevicepage.clickNextButtonsZephyrInfo();
	adddevicepage.clickBleokbutton();
//	adddevicepage.checkdevicedetailstoast();
	adddevicepage.clickSubmitButtonDeviceSetting();
	adddevicepage.checkdevicesettingstoast();
	homepage.disableWIFI();
	homepage.enableBLE();
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
	
	Thread.sleep(1000);
	homepage.killandopen();
	Thread.sleep(7000);
	homepage.clickMenuBarButton();
	devicemenupage.clickDeviceSettingsButton();
	devicemenupage.clickResetDeviceButton();
	devicemenupage.clickResetConfirmationYesButton();
	adddevicepage.checkdeviceresettoast();
	devicemenupage.AddDevicePagedisplayed();
	homepage.enableWIFI();
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
