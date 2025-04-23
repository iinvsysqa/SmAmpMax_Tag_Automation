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

public class TC33_sampMax_Info_Max extends MobileAppWrappers {


	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC33_SampMax Info check";
		testDescription = " User should be allowed to edit and save the AC Info page values without any issue\r\n"
				+ "";
	}



	@Test(priority = 32)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}


	String Devicename ="testuserghggg";

	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);


		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			readwrite.openPort();
			Thread.sleep(2000);
			readwrite.write("reboot\r");
//			Thread.sleep(3000);
//			readwrite.write("factory_reset\r");

			adddevicepage.pair(1);
			adddevicepage.clickNextButtonsZephyrInfo();
			
			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();
			Thread.sleep(1000);
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			
			homepage.clickONOFFButton();
			Thread.sleep(2000);

			homepage.clickMenuBarButton();
			devicemenupage.ClickSzephyrInfoButton();
			adddevicepage.SampmaxInfoButtonClick();
			adddevicepage.enterSampMaxDevcieName("testuser008_1");
			adddevicepage.MaxinfoSaveButton();
			adddevicepage.MaxBackButton();
			Thread.sleep(1000);
//			devicemenupage.CheckSzephyrInfPageBrandName();
			
			Thread.sleep(1000);


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
