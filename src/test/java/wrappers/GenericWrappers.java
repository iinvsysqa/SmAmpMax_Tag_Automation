package wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.appmanagement.ApplicationState;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Log;
import com.google.common.collect.ImmutableMap;
import org.testng.Assert;

import utils.Reporter;
import utils.logReadandWrite;

public class GenericWrappers {
 
	
	public String packages=loadProp("APP_PACKAGE");
	public static AndroidDriver<AndroidElement> driver;
	public WebDriverWait wait;
	static ExtentTest test;
	static ExtentReports report;
	public String sUrl, primaryWindowHandle, sHubUrl, sHubPort;
	
	public static String loadProp(String property) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./config.properties")));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}

	public static boolean initAndriodDriver() throws FileNotFoundException, IOException {

		boolean bReturn = false;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./config.properties")));
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", prop.getProperty("PLATFORM_NAME"));
			caps.setCapability("appium:platformVersion", prop.getProperty("PLATFORM_VERSION"));
			caps.setCapability("appium:udid", prop.getProperty("UDID"));
			caps.setCapability("appium:deviceName", prop.getProperty("DEVICE_NAME"));

			caps.setCapability("appium:automationName", "uiautomator2");
			caps.setCapability("appium:ignoreHiddenApiPolicyError", "true");
			caps.setCapability("newCommandTimeout", 999999);
//			caps.setCapability("appium:autoGrantPermissions", true);
			

			//			keepSessionAlive(driver);
			
			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723"), caps);

			Reporter.reportStep("Appium server started successfully ", "INFO");
			Reporter.reportStep(
				    "Platform name: " + prop.getProperty("PLATFORM_NAME") + "<br>" + 
				    "Platform version: " + prop.getProperty("PLATFORM_VERSION") + "<br>" + 
				    "Device UDID: " + prop.getProperty("UDID") + "<br>" + 
				    "Device Name: " + prop.getProperty("DEVICE_NAME")+ "<br>" +
				    "App Revision No: "+prop.getProperty("APP_REVISION_NO")+"<br>"+
				    "Device Revision No: "+prop.getProperty("DEVICE_REVISION_NO")+"<br>"+
				    "Router Name: "+prop.getProperty("WIFINAME")+"<br>"+
				    "Remote Router Name: "+prop.getProperty("REMOTEWIFINAME"), 
				    
				    "INFO"
				);


			


			String appPackage = prop.getProperty("APP_PACKAGE");
			if (driver.isAppInstalled(appPackage)) {
				System.out.println("App is already installed. Launching the app...");
				driver.activateApp(appPackage); // Open the app
				
			} else {
				System.out.println("App is not installed. Installing and launching...");
				driver.installApp(prop.getProperty("APP_PATH"));
				driver.activateApp(appPackage); // Launch the app after installation
				Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.ACCESS_FINE_LOCATION");
				Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.BLUETOOTH_SCAN");
				Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.BLUETOOTH_CONNECT");
				Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.CAMERA");
				Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.POST_NOTIFICATIONS");
			
			}
			
			if (driver.isAppInstalled(appPackage)) {
				Reporter.reportStep("The app:" + appPackage + " launched successfully", "PASS");
			}
			else {
				Reporter.reportStep("The app:" + appPackage + " not launched", "FAIL");
				
			}
			Reporter.reportStep("App opened successfully", "INFO");
			Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.ACCESS_FINE_LOCATION");
			Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.BLUETOOTH_SCAN");
			Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.BLUETOOTH_CONNECT");
			Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.CAMERA");
			Runtime.getRuntime().exec("adb shell pm grant com.iinvsys.smampmax android.permission.POST_NOTIFICATIONS");
			bReturn = true;

		} catch (MalformedURLException e) {
			System.out.println("App not launched" + e.getMessage());
			e.printStackTrace();
			Reporter.reportStep("The app not launched", "FAIL");
		}
		return bReturn;
	}

	//	public boolean invokeApp(String browser,String url) {
	//		boolean bReturn = false;
	//		try {
	//
	//			DesiredCapabilities dc = new DesiredCapabilities();
	//			dc.setBrowserName(browser);
	//			dc.setPlatform(Platform.WINDOWS);
	//			if(browser.equalsIgnoreCase("chrome")){
	//				WebDriverManager.chromedriver().setup();
	//				webDriver = new ChromeDriver();
	//				
	//			} else if(browser.equalsIgnoreCase("Edge")){
	//				WebDriverManager.edgedriver();
	//				webDriver = new EdgeDriver();
	//				
	//			} else if(browser.equalsIgnoreCase("Firefox")) {
	//				WebDriverManager.firefoxdriver();
	//				webDriver = new FirefoxDriver();
	//			}
	//
	//			webDriver.manage().window().maximize();
	//			webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	//			webDriver.get(url);
	//
	//			primaryWindowHandle = driver.getWindowHandle();
	//			
	//			Reporter.reportStep("The URL : "+ url + " launched successfully in"+ browser + " browser " , "PASS");
	//			bReturn = true;
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			Reporter.reportStep("The browser:" + browser + " could not be launched", "FAIL");
	//		}
	//		return bReturn;
	//	}

	public static void keepSessionAlive(AndroidDriver driver) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			try {
				driver.currentActivity();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 5, TimeUnit.MINUTES);
	}

	public static boolean launchApplication(String url) {
		boolean bReturn = false;
		try {
			driver.get(url);
			Reporter.reportStep("The browser:" + url + " launched successfully", "PASS");
			bReturn = true;
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.reportStep("The browser:" + url + " could not be launched", "FAIL");

		}
		return bReturn;
	}

	public static boolean clickbyXpath(WebElement xpath, String button) {
		boolean bReturn = false;
		try {
			expshortWait(xpath);
			xpath.click();
			Reporter.reportStep(button + " is clicked Successfully.", "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The Field " + button + " could not be clicked.", "FAIL");
		}
		return bReturn;

	}

	public static boolean clickbyXpathwithoutReport(WebElement xpath, String button) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			xpath.click();
			Reporter.reportStep(button + " is clicked Successfully.", "PASS");
			bReturn = true;

		} catch (Exception e) {
			// Reporter.reportStep("The Field "+button+" could not be clicked.", "FAIL");
		}
		return bReturn;

	}

	public boolean verifyTitle(String title) {
		boolean bReturn = false;
		try {
			if (driver.getTitle().equalsIgnoreCase(title)) {
				Reporter.reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				Reporter.reportStep(
						"The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"SUCCESS");
			}

		} catch (Exception e) {
			Reporter.reportStep("The title did not match", "FAIL");
		}

		return bReturn;
	}

	public boolean selectById(WebElement id, int value, String fieldName) {
		boolean bReturn = false;
		try {
			expWait(id);
			new Select(id).selectByIndex(value);
			Reporter.reportStep("The element with id: " + fieldName + " is selected with value :" + value, "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The value: " + value + " could not be selected.", "FAIL");
		}
		return bReturn;
	}

	public boolean entervaluebyXpath(WebElement xpath, String fieldname, String value) {
		boolean bReturn = false;
		try {
			expshortWait(xpath);
			xpath.sendKeys(value);
			Reporter.reportStep(fieldname + " field is entered with value : " + value, "PASS");

		} catch (Exception e) {
			Reporter.reportStep("The value: " + value + " could not be entered.", "FAIL");
		}
		return bReturn;
	}

	public boolean entertoiFrame(WebElement xpath, String fName) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			WebElement frame = xpath;
			driver.switchTo().frame(frame);
			Reporter.reportStep("iframe " + fName + " entered successfully", "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("iframe could not be entered :", "FAIL");
		}
		return bReturn;
	}

	public boolean selectByVisibleText(WebElement xpath, String fieldName) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			List<WebElement> size = new Select(xpath).getOptions();
			for (WebElement s : size) {
				if (s.isEnabled()) {
					new Select(xpath).selectByVisibleText(s.getText());
					break;
				}
				Reporter.reportStep("The dropdown: " + fieldName + " is selected", "PASS");
				bReturn = true;
			}
		} catch (Exception e) {
			Reporter.reportStep("The dropdown: " + fieldName + " is not selected", "FAIL");
		}
		return bReturn;
	}

	public boolean verifyTextContainsByXpath(WebElement xpath, String text, String field) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			String sText = xpath.getText();
			System.out.println(sText);
			if (sText.trim().contains(text)) {
				Reporter.reportStep(field + "contains " + sText, "PASS");
				bReturn = true;
			} else {
				Reporter.reportStep(field + " did not contain :" + sText, "FAIL");
			}
		} catch (Exception e) {
			Reporter.reportStep(field + " not displayed", "FAIL&RUN");
			e.printStackTrace();
		}
		return bReturn;
	}

	public boolean verifyToastContainsTextByXpath(WebElement xpath, String text, String field) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			String sText = xpath.getText();
			System.out.println(sText);
			if (sText.trim().contains(text)) {
				Reporter.reportStep(field + "contains " + text, "PASS");
				bReturn = true;
			} else {
				Reporter.reportStep(field + " did not contain :" + text, "FAIL");
			}
		} catch (Exception e) {
			//
		}
		return bReturn;
	}


	public static void quitBrowser() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			Reporter.reportStep("The browser could not be closed.", "FAIL");
		}

	}

	public static void expWait(WebElement xpath) {
		try {

			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);



		}

	}
	public static void expshortWait(WebElement xpath) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);
			
			
			
		}
		
	}
	public static void expshortWaittwenty(WebElement xpath) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);
			
			
			
		}
		
	}

	public void expWaitforPairing(WebElement xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);
		}

	}



	//	========================================

	public int extractintvalue(String str) {
		// Use regular expression to remove all non-digit characters
		String numbersOnly = str.replaceAll("\\D+", "");

		// Convert the extracted string to an integer (optional)
		int extractedValue = Integer.parseInt(numbersOnly);

		//          System.out.println("Extracted numbers: " + numbersOnly);
		System.out.println("Extracted integer value: " + extractedValue);
		return extractedValue;
	}

	public String randomnumbers(int num) {

		String numbers = "123456789";

		// Create a StringBuilder to store the random numbers
		StringBuilder sb = new StringBuilder();

		// Create an object of Random class
		Random random = new Random();

		// Specify the length of the random string
		int length = num;

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(numbers.length());
			char randomNum = numbers.charAt(index);
			sb.append(randomNum);
		}
		String randomString = sb.toString();
		return randomString;
	}

	public boolean turnOnBT() {
		boolean bReturn = false;

		try {
			Runtime.getRuntime().exec("adb shell svc bluetooth enable");
			bReturn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bReturn;

	}

	public boolean turnOffBT() throws Exception {

		boolean bReturn = false;
		try {
			Runtime.getRuntime().exec("adb shell svc bluetooth disable");
			bReturn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bReturn;
	}









	public void closeApp() {

		try {
			if (driver != null) {
				// Kill the app (terminate it)
				driver.terminateApp(packages);
				Reporter.reportStep("The app was killed successfully.", "PASS");
			}
		} catch (Exception e) {
			Reporter.reportStep("The app could not killed .", "FAIL");
		}
	}
	
	public void openapp() {
		try {
			
				// Kill the app (terminate it)
				driver.activateApp(packages);
				Reporter.reportStep("The app was opened successfully.", "PASS");
			
		} catch (Exception e) {
			Reporter.reportStep("The app  not opened .", "FAIL");
		}
	}
	
	public void killAndReopenApp() {
		try {
			if (driver != null) {
				// Kill the app (terminate it)
				driver.terminateApp(packages);
				Reporter.reportStep("The app was killed successfully.", "PASS");

				// Wait for a few seconds before reopening the app
				Thread.sleep(3000);

				// Reopen the app, it should maintain its previous state (same page)
				driver.activateApp(packages);
				Reporter.reportStep("The app was reopened successfully.", "PASS");
			}
		} catch (Exception e) {
			Reporter.reportStep("The app could not be killed and reopened.", "FAIL");
		}
	}


	public static void expWaitforFirmware(WebElement xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,300);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		}
		catch(Exception e) {
			System.out.println(e); 
		}

	}
	public static void expWaitstatusbar(WebElement xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,300);
			wait.until(ExpectedConditions.visibilityOf(xpath));
		}
		catch(Exception e) {
			System.out.println(e); 
		}
	}





	public void enableWiFi() {

		try {
			//Runtime.getRuntime().exec("adb shell svc bluetooth disable");
			Runtime.getRuntime().exec("adb shell svc wifi enable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void disableWiFi() {

		try {
			//Runtime.getRuntime().exec("adb shell svc bluetooth disable");
			Runtime.getRuntime().exec("adb shell svc wifi disable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void switchToSpecificWifiUsingCommand() {
		try {
			String command = "nmcli dev wifi connect 'realme6' password '12345222'";
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			System.out.println("Switched to Wi-Fi network: YourWiFiSSID");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void connectToWiFi(String wifiName, String wifiPassword) {
		try {
			
			// Open WiFi settings on the Android device
			Runtime.getRuntime().exec("adb shell svc wifi enable");
			Runtime.getRuntime().exec("adb shell am start -a android.settings.WIFI_SETTINGS");
			// Wait for the WiFi settings to open
			Thread.sleep(3000);

			// Scroll to the WiFi network by name
			WebElement wifiElement = driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\""
							+ wifiName + "\"))"));


			// Click on the WiFi network
			clickbyXpath(wifiElement, "Clicked on " + wifiName + " on Wi-Fi page");

		
			// Check if the password entry field is displayed
			try {
				WebElement enterPasswordField = driver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.android.settings:id/password\"]")); // Replace with the actual XPath
				WebElement enterPasswordFieldOnePlus = driver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.oplus.wirelesssettings:id/edittext_container\"])[1]")); // Replace with the actual XPath
				if (isElementDisplayedCheck(enterPasswordField)) {
					// Enter the WiFi password
					enterValueByXpathwifipage(enterPasswordField, "Wi-Fi password", wifiPassword);

					// Click on the connect button
					WebElement connectButton = driver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id='android:id/button1']")); 
					// Replace with the actual XPath
					if (isElementDisplayedCheck(connectButton)) {
						
						clickbyXpath(connectButton, "Connect button");
						
						Thread.sleep(3000);}

				}
				
				else if (isElementDisplayedCheck(enterPasswordFieldOnePlus)) {
					enterValueByXpathwifipage(enterPasswordFieldOnePlus, "Wi-Fi password", wifiPassword);
					WebElement savebutton = driver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.oplus.wirelesssettings:id/menu_save\"]")); 
				 if (isElementDisplayedCheck(savebutton)) {
					clickbyXpath(savebutton, "save button");
					
					Thread.sleep(3000);
				}
					
				} 
				else {
					System.out.println("Already connected or password is saved.");
				
				}
			} catch (NoSuchElementException e) {
			    System.out.println("WIFI Password is Already Provided, continuing to the next step.");
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Helper method to check if the element is displayed
	public boolean isElementDisplayed(WebElement element,String Field) {
		try {
			expshortWait(element);// Introduce a small delay before checking visibility
			  
			if (element.isDisplayed()) {
				
				Reporter.reportStep(Field +"  Element displayed", "PASS");
			}
			else {
			Reporter.reportStep(Field+"Element not displayed", "FAIL");
				
			}
			return true;
		} catch (NoSuchElementException e) {
			Reporter.reportStep(Field+"Element not displayed", "FAIL");
			return false;
		}
	}
	public boolean isElementDisplayednext(WebElement element,String Field) {
		try {
			expshortWaittwenty(element);// Introduce a small delay before checking visibility
			
			if (element.isDisplayed()) {
				
				Reporter.reportStep(Field +"  Element displayed", "PASS");
			}
			else {
				Reporter.reportStep(Field+"Element not displayed", "FAIL");
				
			}
			return true;
		} catch (NoSuchElementException e) {
			Reporter.reportStep(Field+"Element not displayed", "FAIL");
			return false;
		}
	}
	
	

	public boolean retryWait(WebElement element) {
		try {
			Thread.sleep(80*1000);  // Introduce a small delay before checking visibility
			Reporter.reportStep(element+"Element displayed", "PASS");
			return element.isDisplayed();
		} catch (NoSuchElementException | InterruptedException e) {
//			Reporter.reportStep(element+"Element not displayed", "INFO");
			return false;
		}
	}


	public void enterValueByXpathwifipage(WebElement element, String fieldName, String value) {
		element.sendKeys(value);
		System.out.println("Entered value in " + fieldName + ": " + value);
	}

	public static void runPythonScript() {
		try {
			// Update the path to the Python interpreter and the Python script
			ProcessBuilder processBuilder = new ProcessBuilder("C:/Python312/python.exe", "C:/Users/Invcuser_106/Desktop/Python code/serialport.py");
			// Start the process
			Process process = processBuilder.start();

			// Capture the script output (stdout)
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			System.out.println("Output of the Python script:");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			// Wait for the process to complete
			int exitCode = process.waitFor();
			System.out.println("Python script exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		driver.terminateApp(packages);
		driver.quit();
	}
	public void checkappinforeground() throws Exception {
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
			Thread.sleep(3000);
		}
	}
	
	public boolean connectivitycheck(WebElement element,String field) {

		try {
			expWait(element);// Introduce a small delay before checking visibility
			  
			if (element.isDisplayed()) {
				
				Reporter.reportStep(field +"  Element displayed", "PASS");
			}
			else {
			Reporter.reportStep(field+"Element not displayed", "INFO");
				
			}
			return true;
		} catch (NoSuchElementException e) {
			Reporter.reportStep(field+"Element not displayed", "INFO");
			return false;
		}
	
	}
	public boolean isiconDisplayed(WebElement element,String field) {
		try {
			expWaitforPairing(element);// Introduce a small delay before checking visibility
			  
			if (element.isDisplayed()) {
				
				Reporter.reportStep(field +"  Element displayed", "PASS");
			}
			else {
			Reporter.reportStep(field+"Element not displayed", "INFO");
				
			}
			return true;
		} catch (NoSuchElementException e) {
			Reporter.reportStep(field+"Element not displayed", "INFO");
			return false;
		}
	}
	
	public WebElement scrollToText(String text) {
        try {
            return driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"));"
            ));
            
        } catch (Exception e) {
            System.out.println("Unable to scroll to text: " + text);
            Reporter.reportStep("Unable to scroll to Field"+text, "FAIL");
            return null;
        }
    }
	
	
	
	private FTPClient ftpClient;

	String server2="ftp.iinvsys.com";
	int port2=2121;
	// Constructor to connect and login to FTP server
	public void FTPUploader(String server, int port, String user, String pass) throws IOException {

		ftpClient = new FTPClient();
		if (!pingServer(server)) {
			System.out.println(server + " is not reachable. Trying " + server2);
			connectToServer(server2, port2, user, pass);
		} else {
			connectToServer(server, port, user, pass);
		}



	}

	private void connectToServer(String server, int port, String user, String pass) throws IOException {
		ftpClient.connect(server, port);
		boolean login = ftpClient.login(user, pass);

		if (!login) {
			throw new IOException("FTP login failed for server: " + server);
		}

		ftpClient.enterLocalPassiveMode(); // Set passive mode for FTP
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // Use binary file type
	}
	private boolean pingServer(String server) {
		try {
			InetAddress address = InetAddress.getByName(server);
			return address.isReachable(2000); // Timeout after 2000 ms
		} catch (IOException e) {
			return false; // If there's an exception, the server is not reachable
		}
	}
	// Method to create a subdirectory and change the working directory to it
	public void createAndNavigateToSubdirectory(String existingDirectory, String newSubDir) throws IOException {
		// Navigate to the existing directory
		if (ftpClient.changeWorkingDirectory(existingDirectory)) {
			System.out.println("Navigated to directory: " + existingDirectory);

			// Create a new subdirectory
			if (ftpClient.makeDirectory(newSubDir)) {
				System.out.println("Created new subdirectory: " + newSubDir);

				// Change the working directory to the new subdirectory
				if (ftpClient.changeWorkingDirectory(newSubDir)) {
					System.out.println("Changed to new subdirectory: " + newSubDir);
				} else {
					throw new IOException("Failed to change to the new subdirectory");
				}
			} else {
				throw new IOException("Failed to create new subdirectory: " + newSubDir);
			}
		} else {
			throw new IOException("Failed to change directory to: " + existingDirectory);
		}
	}

	// Method to upload a file to the current directory
	public void uploadFile(String localFilePath, String remoteFileName) throws IOException {
		try (FileInputStream fis = new FileInputStream(new File(localFilePath))) {
			boolean success = ftpClient.storeFile(remoteFileName, fis);
			if (success) {
				System.out.println("File uploaded successfully to FTP: " + remoteFileName);
				
			} else {
				System.out.println("File upload failed.");
			}
		}
	}
	
	
	

	// Close the FTP connection
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}

	}

	
	public void getLatestApk(String baseRemotePath,String localDirectory,String newFileName) throws IOException {
		// Add current week to the path
        String weekFolder = getCurrentWeekFolder();
        String remotePathWithWeek = baseRemotePath+weekFolder+"/";
        System.out.println("Looking in directory: " + remotePathWithWeek);

        // Get the latest folder within the week directory
        String latestFolder = getLatestFolder(ftpClient, remotePathWithWeek);
        if (latestFolder != null) {
            String targetDirectory = remotePathWithWeek + latestFolder + "/";
            System.out.println("Latest folder found: " + targetDirectory);

            File localFolder = new File(localDirectory);
            deleteAllFilesInFolder(localFolder);
            
            // Search for the file containing "szhephyr" in the latest folder
            FTPFile[] files = ftpClient.listFiles(targetDirectory);
            for (FTPFile file : files) {
            	System.out.println(file);//////////////////////
                if (file.isFile() && file.getName().contains("Android_SZephyr")) {
                    String downloadedFileName = file.getName();
                    File localFile = new File(localDirectory + File.separator + downloadedFileName);

                    // Download the file
                    try (FileOutputStream outputStream = new FileOutputStream(localFile)) {
                        boolean success = ftpClient.retrieveFile(targetDirectory + downloadedFileName, outputStream);
                        if (success) {
                            System.out.println("Downloaded: " + downloadedFileName);
                        } else {
                            System.out.println("Failed to download: " + downloadedFileName);
                        }
                    }

                    // Rename the downloaded file
                    File renamedFile = new File(localDirectory + File.separator + newFileName);
                    boolean renamed = localFile.renameTo(renamedFile);
                    if (renamed) {
                        System.out.println("File renamed to: " + newFileName);
                    } else {
                        System.out.println("Failed to rename file.");
                    }

                    break;
                } else {
                	 System.out.println("APK file not found at: " + localDirectory);
                     // Fail the entire suite if APK is missing
                     Assert.fail("APK file is required to run the test suite but was not found.");
                }
            }
        } else {
        	 System.out.println("APK file not found at: " + localDirectory);
             // Fail the entire suite if APK is missing
             Assert.fail("APK file is required to run the test suite but was not found.");
            System.out.println("No latest folder found for the week.");
        }
	}
	
	public void getLatestBin(String baseRemotePath,String localDirectory,String newFileName) throws IOException {
		// Add current week to the path
        String weekFolder = getCurrentWeekFolder();
        String remotePathWithWeek = baseRemotePath+weekFolder+"/";
        System.out.println("Looking in directory: " + remotePathWithWeek);

        // Get the latest folder within the week directory
        String latestFolder = getLatestFolder(ftpClient, remotePathWithWeek);
        if (latestFolder != null) {
            String targetDirectory = remotePathWithWeek + latestFolder + "/";
            System.out.println("Latest folder found: " + targetDirectory);

            File localFolder = new File(localDirectory);
            deleteAllFilesInFolder(localFolder);
            
            // Search for the file containing "szhephyr" in the latest folder
            FTPFile[] files = ftpClient.listFiles(targetDirectory);
            for (FTPFile file : files) {
            	System.out.println(file);//////////////////////
                if (file.isFile() && file.getName().matches("rel_device_bin_\\d+_prt_enbl\\.zip")) {
                    String downloadedFileName = file.getName();
                    File localFile = new File(localDirectory + File.separator + downloadedFileName);

                    // Download the file
                    try (FileOutputStream outputStream = new FileOutputStream(localFile)) {
                        boolean success = ftpClient.retrieveFile(targetDirectory + downloadedFileName, outputStream);
                        if (success) {
                            System.out.println("Downloaded: " + downloadedFileName);
                        } else {
                            System.out.println("Failed to download: " + downloadedFileName);
                        }
                    }

                    // Rename the downloaded file
                    File renamedFile = new File(localDirectory + File.separator + newFileName);
                    boolean renamed = localFile.renameTo(renamedFile);
                    if (renamed) {
                        System.out.println("File renamed to: " + newFileName);
                    } else {
                        System.out.println("Failed to rename file.");
                    }

                    break;
                } else {
                	 System.out.println("APK file not found at: " + localDirectory);
                     // Fail the entire suite if APK is missing
                   //  Assert.fail("APK file is required to run the test suite but was not found.");
                }
            }
        } else {
        	 System.out.println("APK file not found at: " + localDirectory);
             // Fail the entire suite if APK is missing
             Assert.fail("APK file is required to run the test suite but was not found.");
            System.out.println("No latest folder found for the week.");
        }
	}
	
	// Get the latest folder from a given directory on the FTP server
    private static String getLatestFolder(FTPClient ftpClient, String directoryPath) throws IOException {
    	
    	
    	
        FTPFile[] directories = ftpClient.listDirectories(directoryPath);

        if (directories.length == 0) {
            return null;
        }

        FTPFile latestDir = null;
        for (FTPFile dir : directories) {
            if (dir.isDirectory()) {
                if (latestDir == null || dir.getTimestamp().getTime().after(latestDir.getTimestamp().getTime())) {
                    latestDir = dir;
                }
            }
        }

        return latestDir != null ? latestDir.getName() : null;
    }
    private static String getCurrentWeekFolder() {
        Calendar calendar = Calendar.getInstance();
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        
        return "W"+(weekOfYear-1);
    }
    
    private static void deleteAllFilesInFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
	
    public void fail(Exception e) {
    	 System.err.println("Failure occurred: " + e.getMessage());
    	 Reporter.reportStep(e+"Testcase failed", "FAIL");
    	 throw new RuntimeException(e);
	}
    
    public boolean isElementDisplayedCheck(WebElement element) {
        try {
        	expshortWaittwenty(element);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
 
    
    public static int extractMinutes(String timeText) {
	    // Regular expression to find digits followed by 'm'
	    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+)m").matcher(timeText);
	    
	    if (matcher.find()) {
	        // group(1) is the captured number before 'm'
	        return Integer.parseInt(matcher.group(1));
	    } else {
	        // If 'm' not found, you can decide what to return (0 or -1)
	        return 0;
	    }
	}
    
}
