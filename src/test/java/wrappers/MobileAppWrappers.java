package wrappers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

public class MobileAppWrappers extends GenericWrappers {
	protected String browserName;
	protected String dataSheetName;
	protected static String testCaseName;
	protected static String testDescription;
	private PrintStream originalOut;
    private PrintStream fileOut;
    String baseRemotePath = loadProp("BASEREMOTEPATH");  // Base FTP directory path
    String localDirectory =loadProp("LOCALAPPPATH") ;  // Local directory to save file
    String newFileName = loadProp("NEWFILENAME");  // New file name
    
    
    String server = "192.168.10.34";//192.168.10.34
	int port = 21;
	String user = "qa_usr";
	String pass = "nw9f2hgo@123";
	
	@BeforeSuite
	public void beforeSuite() throws FileNotFoundException, IOException, InterruptedException{
		
		
		// Enable below two lines of code to Get app from FTP
//		FTPUploader(server, port, user, pass);
//		getLatestApk(baseRemotePath, localDirectory, newFileName);
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
	}


	@AfterTest
	public void afterTest() throws IOException{

		
	}

	@AfterMethod
	public void afterMethod(){
//		quitBrowser();
		try {
			// FTP server credentials

			

			// Local log files
			String appLogPath = "./app_log.txt";
			String deviceLogPath = "./serial_log.txt";

			// FTP paths
			String existingDirectory = "/Internal_Project/FULL_VALIDATION_PACKAGES_LOGS/LOGS/2024/Automation_Logs/";
			String newSubDir = "logs_" + randomnumbers(6); // Subdirectory name
			// Initialize FTP connection
			FTPUploader(server, port, user, pass);

			// Create new subdirectory inside the existing directory
			createAndNavigateToSubdirectory(existingDirectory, newSubDir);

			// Upload files to the new subdirectory
			uploadFile(appLogPath,  testCaseName+"App.txt");
			uploadFile(deviceLogPath, testCaseName+".txt");
			

			String remotefilepath =existingDirectory+newSubDir;
			String Filename="/"+ testCaseName+".txt";
			Reporter.reportStep(" FTP Path : "+ remotefilepath +
					"<br>"
					+"Device Log File name:"+Filename, "INFO");
			
			// Disconnect from FTP server
			disconnect();

		} catch (IOException e) {
			e.printStackTrace();

		}
		driver.terminateApp(packages);
		driver.quit();
		
	}

	@DataProvider(name="fetchData")
	public Object[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);
	}

}
