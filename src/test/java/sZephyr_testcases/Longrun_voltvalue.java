package sZephyr_testcases;

import static org.testng.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.Alert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.Internal_longrun_task;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class Longrun_voltvalue  extends MobileAppWrappers {
	
	Internal_longrun_task ILT;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "LongrunforLowvoltage";
		testDescription = "Pair in Ble without router mode <br> create 3 schedule and disable it and check schedule worked or not <br> check device in off state after schedule completion";
	}
	
//	@Test
	public void checkLowvolt() throws Exception, IOException {

		initAndriodDriver();
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));

		try {
		readwrite.openPort();
		
		ILT=new Internal_longrun_task(driver);
		
		
		
		ILT.checkVoltValue();
		
		
		
		readwrite.closePort();
		
		}
		catch (Exception e) {
			e.printStackTrace();
			readwrite.closePort();
			fail( e);
		}
	}
	
//	@Test
	public void alert() throws FileNotFoundException, IOException {
		initAndriodDriver();
		ILT=new Internal_longrun_task(driver);
		ILT.alertcheck();
	}

}
