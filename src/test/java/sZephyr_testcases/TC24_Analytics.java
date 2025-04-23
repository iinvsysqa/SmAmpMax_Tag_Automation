package sZephyr_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.Analytics;
import pages.DeviceMenuPage;
import pages.HomePage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC24_Analytics  extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Analytics analyticspage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC_24_Analytics_BLE without Router_App Close";
		testDescription = "Pairing mode=Ble without router <br> Connectivity :Ble <br>After connectivity established close application<br>Turn on device for 5min using device button  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	}
	
	@Test(priority = 23)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		analyticspage= new Analytics(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		Thread.sleep(2000);
		readwrite.write("reboot\r");
//		Thread.sleep(3000);
//		readwrite.write("factory_reset\r");
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.getenergydurationvalue();
		closeApp();
		readwrite.write("button_press\r");
		Thread.sleep(1*60*1000);
		readwrite.write("button_press\r");
		openapp();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.checkenrgyduration(1);
		
				
		
		}
		catch (Exception e) {
			readwrite.write("factory_reset\r");
			readwrite.closePort();
			fail(e);
		}
	}

}
