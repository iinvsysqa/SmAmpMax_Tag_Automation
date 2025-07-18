package sampMax_testcase;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC15_DeviceSettings_Max extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	StoreLogPage storelog;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC15_DeviceSettings_Pairing TIme Settings change";
		testDescription = "Pairing Time Change the Device Setting Value And Check MenuBar Device Settings Page Reflect The Same";
	}

	@Test(priority = 14)
	public void deviceSettings() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}

	public void pairBlewithoutRouter() throws Exception {
		adddevicepage = new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		storelog= new StoreLogPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));

		try {
			readwrite.openPort();

			adddevicepage.pair(3);
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.clickBleokbutton();
//			adddevicepage.checkdevicedetailstoast();
			devicemenupage.clickPairingTimeQuietLEDEnable();
			devicemenupage.clickInfinitePowerToggle();
			devicemenupage.clickHoursPlusButton();
			Thread.sleep(1000);
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();

			for (int i = 0; i < 2; i++) {
				homepage.clickONOFFButton();
				Thread.sleep(1000);
			}

			// Pairing Time Changed Data Should Reflected In Device Settings Page Duration
			// For ON
			homepage.clickMenuBarButton();
			devicemenupage.clickDeviceSettingsButton();
			devicemenupage.clickDurationForONButton();
			Thread.sleep(3000);
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
		} catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			readwrite.write("factory_reset\r");
			readwrite.closePort();
			fail(e);
		}
	}

}
