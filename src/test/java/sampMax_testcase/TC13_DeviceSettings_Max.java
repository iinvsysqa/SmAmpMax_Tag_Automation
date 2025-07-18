package sampMax_testcase;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC13_DeviceSettings_Max extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	StoreLogPage storelog;

	@BeforeClass
	public void deviceSettings() {
		testCaseName = "TC13_DeviceSettings_Max";
		testDescription = "Remove the Added Router Details Test Case";
	}

	@Test(priority = 12)
	public void removerepair() throws Exception {
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

			adddevicepage.pair(2);
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.clickBleokbutton();
//			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();
			Thread.sleep(2000);
			homepage.clickMenuBarButton();
			Thread.sleep(1000);
//			homepage.WifiSwitch(loadProp("REMOTEWIFINAME"), loadProp("REMOTEWIFIPASSWORD"));
			devicemenupage.clickDeviceSettingsButton();
			Thread.sleep(3000);
			// Remove Router Test Case

			devicemenupage.removerouter();
			Thread.sleep(5000);
			turnOffBT();
			Thread.sleep(3000);
			devicemenupage.shellAllowpopup();
			Thread.sleep(20000);
			turnOnBT();
			devicemenupage.shellAllowpopup();

//			devicemenupage.clickDeviceSettingsButton();
			Thread.sleep(3000);

			homepage.getCurrentvalue();
			homepage.getVoltvalue();
			homepage.getPowervalue();
			for (int i = 0; i < 2; i++) {
				homepage.clickONOFFButton();
				Thread.sleep(1000);
			}

			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupNoButton();
			Thread.sleep(1000);
			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupYesButton();
			adddevicepage.checkdeviceremovedtoast();
			devicemenupage.AddDevicePagedisplayed();
			readwrite.closePort();
			
		} catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			e.printStackTrace();
			readwrite.closePort();
			fail(e);
		}
	}

}
