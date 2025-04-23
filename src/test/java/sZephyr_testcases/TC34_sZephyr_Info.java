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

public class TC34_sZephyr_Info extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC34_Szephyr Info check";
		testDescription = " User should be allowed to edit and save the AC Info page values without any issue\r\n"
				+ "";
	}



	@Test
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}


	String modelname ="G20";
	String capacity="2";
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
			//adddevicepage.ClickOkButtonBLEpopUP();
			adddevicepage.ClickBrandName();
			adddevicepage.ClickSelectName();
			adddevicepage.enterAcModelName("G20");
			adddevicepage.enterCapacity("2");
			adddevicepage.ClickRoomSizeButton();
			adddevicepage.SelectRoomSizeOption();
			Thread.sleep(1000);
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();
			Thread.sleep(1000);
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			homepage.VerifyONdesc();
			
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			homepage.VerifyOFFdesc();

			homepage.clickMenuBarButton();
			devicemenupage.ClickSzephyrInfoButton();
			Thread.sleep(1000);
			devicemenupage.CheckSzephyrInfPageBrandName();
			devicemenupage.CheckSzephyrInfPageModelName();
			devicemenupage.CheckSzephyrInfPageCapacity();
			devicemenupage.CheckSzephyrInfPageRoomSize();
			devicemenupage.clickDeviceSettingsBackButton();
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
