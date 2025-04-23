
package sZephyr_testcases;

import static org.testng.Assert.fail;

import java.util.Properties;

import org.testng.Reporter;
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


public class  TC13_BlewithoutRouter extends MobileAppWrappers {

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
		testCaseName = "TC13_BlewithoutRouter_CONNECTIVITY";
		testDescription = "CONNECTIVITY_MOD_1_TC_01-BLE connectivity establishment"+ "<br>"+"CONNECTIVITY_MOD_1_TC_02-APP kill and re Open "+ "<br>"+"CONNECTIVITY_MOD_1_TC_03-5 times App ON/OFF";
	}
	

	@Test(priority = 12)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		szephyrinfoPage= new Szephyr_info_Page(driver);
		
		//CONNECTIVITY_MOD_1_TC_01//////////BLE connectivity establishment//////////////////////////////////////
	
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			
		
		readwrite.openPort();
		Thread.sleep(3000);
		readwrite.write("reboot\r");
//		readwrite.read();
//		Thread.sleep(3000);
//		readwrite.write("factory_reset\r");
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
			
		homepage.clickMenuBarButton();
        devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		adddevicepage.checkdeviceresettoast();
		devicemenupage.AddDevicePagedisplayed();
		
		
		///CONNECTIVITY_MOD_1_TC_02//////APP kill and re Open//////////////////////////////////////////////////////////
        
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		homepage.clickONOFFButton();
		
		homepage.clickONOFFButton();
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
		homepage.killandopen();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		adddevicepage.checkdeviceresettoast();
		devicemenupage.AddDevicePagedisplayed();
		
//		///CONNECTIVITY_MOD_1_TC_03--5 times App ON/OFF////////////////////////////////////////////////////////////////
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
		
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
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
		