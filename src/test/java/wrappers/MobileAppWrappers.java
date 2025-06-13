package wrappers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.runtime.SwitchBootstraps;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import utils.CheckoutAndBuildApk;
import utils.DataInputProvider;
import utils.GetAppLog;
import utils.Reporter;
import utils.RunFlashScript;
import utils.RunRelayFromPython;
import utils.SwitchWiFi;
import utils.UnzipBin;

public class MobileAppWrappers extends GenericWrappers {
	protected String browserName;
	protected String dataSheetName;
	protected static String testCaseName;
	protected static String testDescription;
	private PrintStream originalOut;
    private PrintStream fileOut;
    String baseRemotePath = loadProp("BASEREMOTEPATH");  // Base FTP directory path
    String localDirectory =loadProp("LOCALAPPPATH") ;  // Local directory to save file
    String newFileName = loadProp("NEWFILENAME");
    String newFileNameBin = loadProp("NEWFILENAME_BIN");// New file name
    
    
    String server = "192.168.10.34";//192.168.10.34
	int port = 21;
	String user = "qa_usr";
	String pass = "nw9f2hgo@123";
	
	@BeforeSuite
	public void beforeSuite() throws FileNotFoundException, IOException, InterruptedException{
		RunRelayFromPython.powerOndeviceViaRelay("on");
		// Switch Tp Link Wifi for Flashing
		
	//	SwitchWiFi.switchwifi(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
		// Flash 1 for Smazer, 2 - Smamax, 3 - sZphyer
		//RunFlashScript.runFlashScript("2");
		
		// Switch to IINVSYS Wifi After Flashing
	//	SwitchWiFi.switchwifi(loadProp("REMOTEWIFINAME"), loadProp("REMOTEWIFIPASSWORD"));
		
		// Enable below two lines of code to Get app from FTP
		FTPUploader(server, port, user, pass);
//		getLatestApk(baseRemotePath, localDirectory, newFileName);
//		getLatestBin(baseRemotePath, localDirectory, newFileNameBin);
//		UnzipBin.unzip();
		
//		
//		disconnect();
		
		// Enable below 2 lines of code to check out latest app from Sharepath
		
//		CheckoutAndBuildApk build= new CheckoutAndBuildApk();
//		build.buildAPK();
		
		Reporter.startResult();//START TESTNG RESULT
		
	        // Redirect console output to a file
//		originalOut = System.out;
//	        fileOut = new PrintStream(new FileOutputStream("test-output/console-output.txt"));
//	        System.setOut(fileOut);
//	        System.out.println("Test suite started. Output is redirected to file.");
	        
	        //START GETTING APP LOG
		GetAppLog applog= new GetAppLog();
		applog.startLogProcess();

		
	}

	@BeforeTest
	public void beforeTest() throws FileNotFoundException, IOException, Exception{
		
	}

	@BeforeMethod 
	public void beforeMethod(){ 
		Reporter.startTestCase();

	}

	@AfterSuite
	public void afterSuite(){
		Reporter.endResult();
		System.out.println("Test suite finished. Closing output redirection.");
        fileOut.close();
        System.setOut(originalOut);
        RunRelayFromPython.powerOndeviceViaRelay("off");
	}


	@AfterTest
	public void afterTest() throws IOException{

		
	}

	@AfterMethod
	public void afterMethod(){
//		quitBrowser();
	
		driver.terminateApp(packages);
		driver.quit();
		
	}

	@DataProvider(name="fetchData")
	public Object[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);
	}

}
