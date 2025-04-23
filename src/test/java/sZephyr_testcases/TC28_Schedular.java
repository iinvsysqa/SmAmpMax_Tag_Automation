package sZephyr_testcases;

import static org.testng.Assert.fail;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.Analytics;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.Schedularpage;
import pages.SignInPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC28_Schedular extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Schedularpage schedulepage;
	Analytics analytics;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC28_Schedular_SmartConfig Mode";
		testDescription = "Paired in Smartconfig mode <br>create a schedule and disable it <br> check schedule should not work";
	}
	
	@Test(priority = 27)
	public void schedule() throws Exception {
		initAndriodDriver();

		homepage=new HomePage(driver);
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		adddevicepage= new AddDevicePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		schedulepage=new Schedularpage(driver);
		analytics=new Analytics(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		analytics.navigateAnalyticsPage();
		analytics.getenergydurationvalue();
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();
		schedulepage.createSchedules(3, 1, 1);// mention the time to start ,how many schedules need to keep,interval
												// between next schedule

		schedulepage.backToHomepage();

		Thread.sleep(5 * 60 * 1000);// set thread values based on schedule duration kept .
		analytics.navigateAnalyticsPage();
		analytics.checkenrgyduration(1);
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();

		schedulepage.deleteschedule();
		schedulepage.backToHomepage();
		schedulepage.checkOffState();
		
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
