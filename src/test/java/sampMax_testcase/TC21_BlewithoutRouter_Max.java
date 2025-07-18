package sampMax_testcase;

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
import pages.StoreLogPage;
import pages.Szephyr_info_Page;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC21_BlewithoutRouter_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Szephyr_info_Page szephyrinfoPage;
	OTA_Status_monitor ota_Status_monitor;
	SignUpPage signuppage;	
	StoreLogPage storelog;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC21_BlewithoutRouter_CONNECTIVITY";
		testDescription = "CONNECTIVITY_MOD_1_TC_01-BLE connectivity establishment"+ "<br>"+"CONNECTIVITY_MOD_1_TC_02-APP kill and re Open "+ "<br>"+"CONNECTIVITY_MOD_1_TC_03-5 times App ON/OFF";
	}
	

	@Test(priority = 20)
	public void connectivityBlewithoutRouter() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		szephyrinfoPage= new Szephyr_info_Page(driver);
		storelog= new StoreLogPage(driver);
		
	
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			
		
		readwrite.openPort();
				
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		homepage.clickONOFFButton();
		Thread.sleep(2000);
		homepage.clickONOFFButton();
		Thread.sleep(2000);
			
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
		homepage.killandopen();
		Thread.sleep(2000);
		homepage.clickONOFFButton();
		
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
		Thread.sleep(2000);
		homepage.clickONOFFButton();
		Thread.sleep(2000);
		
		homepage.clickMenuBarButton();
        devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		adddevicepage.checkdeviceresettoast();
		devicemenupage.AddDevicePagedisplayed();
		
        
	
		
	
		
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
