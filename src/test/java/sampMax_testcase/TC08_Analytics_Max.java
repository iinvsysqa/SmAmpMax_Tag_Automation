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

public class TC08_Analytics_Max extends MobileAppWrappers {
	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	AnalyticsPage analyticspage;
	StoreLogPage storelog;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC08_Analytics_Max";
		testDescription = "Pairing mode=Ble without router <br> Connectivity :Ble <br>After connectivity established close application<br>Turn on device for 5min using device button  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	
}
	@Test(priority = 7)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		analyticspage= new AnalyticsPage(driver);
		storelog= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
		Thread.sleep(3000);
		readwrite.write("factory_reset\r");
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		Thread.sleep(3000);
		
		homepage.clickONOFFButton();
		Thread.sleep(60000);
		homepage.clickONOFFButton();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.getenergydurationvalue();
		closeApp();
		readwrite.write("button_press\r");
		Thread.sleep(1*60*1000);
		readwrite.write("button_press\r");
		openapp();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.checkenrgyduration(1);
		analyticspage.navigatehomepage();
		
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		devicemenupage.AddDevicePagedisplayed();
				
		
		}
		catch (Exception e) {
			storelog.CollectLogOnFailure(testCaseName, testDescription);
			e.printStackTrace();
			readwrite.closePort();
			fail(e);
		}
	}


}
