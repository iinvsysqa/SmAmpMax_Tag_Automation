package sZephyr_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class TC22_DeviceSettings extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC22_DeviceSettings_Pairing TIme Settings change";
		testDescription = "Pairing Time Change the Device Setting Value And Check MenuBar Device Settings Page Reflect The Same";
	}
	

	@Test(priority = 21)
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
		
		adddevicepage.pair(5);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		devicemenupage.clickPairingTimeQuietLEDEnable();
		devicemenupage.clickInfinitePowerToggle();
		devicemenupage.clickHoursPlusButton();
		Thread.sleep(1000);
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		for(int i=0;i<2;i++) {
		homepage.clickONOFFButton();
		Thread.sleep(1000);
		}
		
//Pairing Time Changed Data Should Reflected In Device Settings Page Duration For ON	
			homepage.clickMenuBarButton();
			devicemenupage.clickDeviceSettingsButton();
			devicemenupage.clickDurationForONButton();
			devicemenupage.clickDeviceSettingsBackButton();
			devicemenupage.clickDeviceSettingsBackButton();
			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupNoButton();
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
