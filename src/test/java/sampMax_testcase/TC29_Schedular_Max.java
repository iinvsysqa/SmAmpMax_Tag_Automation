package sampMax_testcase;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.AnalyticsPage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.Schedularpage;
import pages.SignInPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC29_Schedular_Max extends MobileAppWrappers{

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Schedularpage schedulepage;
	AnalyticsPage analyticspage;
	StoreLogPage storelog;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC29_Schedular_Ble-without_Router_app_close";
		testDescription = "Pair in Ble-without_Router mode <br> create 3 schedule and check schedule worked or not <br> check device in off state after schedule completion";
	}

	@Test(priority = 28)
	public void Schedular_Ble_without_Router() throws Exception {
		initAndriodDriver();
		landingpage = new LandingPage(driver);
		loginpage = new SignInPage(driver);
		homepage=new HomePage(driver);
		otppage = new OtpPage(driver);
		adddevicepage = new AddDevicePage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		schedulepage = new Schedularpage(driver);
		analyticspage=new AnalyticsPage(driver);
		storelog= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.getenergydurationvalue();
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();
		schedulepage.createSchedules(5, 3, 1);//mention the time to start ,how many schedules need to keep,interval between next schedule
		schedulepage.backToHomepage();
		
		Thread.sleep(9*60*1000);//set thread values based on schedule duration kept .
		analyticspage.navigateAnalyticsPage();
		analyticspage.checkenrgyduration(3);
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();
		schedulepage.deleteschedule();
		schedulepage.backToHomepage();
		
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
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
