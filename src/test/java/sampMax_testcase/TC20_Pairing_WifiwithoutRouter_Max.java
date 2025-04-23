package sampMax_testcase;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC20_Pairing_WifiwithoutRouter_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC20 - Pairing in Wifi without router Mode";
		testDescription = "If already Signin skip signin and  Start Pairing Wifi without router mode else Signin and pair Wifi without router mode";
	}


	@Test(priority = 19)
	public void removerepair() throws Exception {
		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);

	 
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
		Thread.sleep(3000);
		readwrite.write("factory_reset\r");
		
		adddevicepage.pair(5);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		Thread.sleep(8000);
		homepage.clickONOFFButton();
		homepage.VerifyONdesc();
		homepage.clickONOFFButton();
		homepage.VerifyOFFdesc();
				
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		
		adddevicepage.pair(5);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		Thread.sleep(8000);
		homepage.clickONOFFButton();
		homepage.VerifyONdesc();
		homepage.clickONOFFButton();
		homepage.VerifyOFFdesc();
				
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
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
