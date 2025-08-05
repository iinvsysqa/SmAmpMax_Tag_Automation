package sampMax_testcase;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.AnalyticsPage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC10_Analytics_Max extends MobileAppWrappers{

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	AnalyticsPage analyticspage;
	StoreLogPage storelog;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC10_Analytics_Max";
		testDescription = "Pairing mode=Smartconfig <br> Connectivity :Remote <br>After connectivity established close application<br>Turn on device for 5min using device button  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";

	}

	@Test(priority = 9)
	public void TC10_analyticsofflineSession() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}

	public void pairBlewithoutRouter() throws Exception {
		adddevicepage = new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		analyticspage = new AnalyticsPage(driver);
		storelog= new StoreLogPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			readwrite.openPort();

			adddevicepage.pair(3);
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.clickBleokbutton();
//			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();

			homepage.WifiSwitch(loadProp("REMOTEWIFINAME"), loadProp("REMOTEWIFIPASSWORD"));
			

			Thread.sleep(15000);
			
			homepage.checkRemoteConnectivity();
			//homepage.clickONOFFButton();
			//Thread.sleep(60000);
			//homepage.clickONOFFButton();

			analyticspage.navigateAnalyticsPage();
			analyticspage.getenergydurationvalue();
			closeApp();
			readwrite.write("button_press\r");
			Thread.sleep(1 * 60 * 1000);
			readwrite.write("button_press\r");
			openapp();
			Thread.sleep(10000);
			adddevicepage.blepermissionokpopup();
			analyticspage.navigateAnalyticsPage();
			analyticspage.checkenrgyduration(1);
			analyticspage.navigatehomepage();
			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupYesButton();
			devicemenupage.AddDevicePagedisplayed();

		} catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			e.printStackTrace();
			readwrite.closePort();
			fail(e);
		}
	}
}
