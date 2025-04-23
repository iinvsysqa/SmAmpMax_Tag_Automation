package sZephyr_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OTA_Status_monitor;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import pages.Szephyr_info_Page;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class  TC18_UI_CONNECTIVITY_MOD_0_TC_01_TO_03 extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Szephyr_info_Page szephyrinfoPage;
	OTA_Status_monitor ota_Status_monitor;
	SignUpPage signuppage;	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC18_UI Check_Check sZephyr Info values";
		testDescription = "In Ble without router mode ,change sZephyr info values ,click on submit button and check for same in Szephyr info page";
	}
	

	@Test(priority = 17)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}

	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		szephyrinfoPage= new Szephyr_info_Page(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
//		Thread.sleep(3000);
//		readwrite.write("factory_reset\r");
		
		adddevicepage.pair(1);
		Thread.sleep(3000);
		adddevicepage.aCBrandNameClick();
		adddevicepage.aCBrandNameCarrierclick();
		adddevicepage.enterAcModelName("V20");
		adddevicepage.enterCapacity("2");
		adddevicepage.roomSizeselect();
		adddevicepage.roomSizesmall();
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		for(int i=0;i<2;i++) {
		homepage.clickONOFFButton();
		
		Thread.sleep(1000);
		}
		homepage.clickMenuBarButton();
		devicemenupage.ClickSzephyrInfoButton();
		szephyrinfoPage.brandnametext();
		szephyrinfoPage.Modelnametext();
		szephyrinfoPage.Capacity_field();
		szephyrinfoPage.Roomsize_field();
		Thread.sleep(3000);
		szephyrinfoPage.clickbackButton();
		homepage.clickMenuBarButton();
	    devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		adddevicepage.checkdeviceresettoast();
		devicemenupage.AddDevicePagedisplayed();
		
		//CONNECTIVITY_MOD_0_TC_02//
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.LEDquietmode();
		adddevicepage.Infinitepoweron();
		adddevicepage.Minutesminusbutton();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.bleConnectivityCheck();
		homepage.clickMenuBarButton();
	    devicemenupage.clickDeviceSettingsButton();
	    adddevicepage.DurationforON();
	    adddevicepage.Hourstextbox();
	    adddevicepage.Minutestextbox();
	    adddevicepage.ClickokdurationON();
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
		