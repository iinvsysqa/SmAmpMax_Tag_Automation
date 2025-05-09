package sampMax_testcase;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.AnalyticsPage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC11_Analytics_Max extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	AnalyticsPage analyticspage;
	StoreLogPage storelog;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC11_Analytics_Max";
		testDescription = "Pairing mode=Wifi With Router <br> Connectivity :STA <br>Turn on device for 5min using relay  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	
}
	@Test(priority = 10)
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
		
		adddevicepage.pair(4);
		adddevicepage.blepermissionokpopup();
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
		analyticspage.navigatehomepage();
		Thread.sleep(2000);
		homepage.clickONOFFButton();
		Thread.sleep(1*60*1000);
		homepage.clickONOFFButton();
		analyticspage.navigateAnalyticsPage();
		analyticspage.checkenrgyduration(1);
		
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
