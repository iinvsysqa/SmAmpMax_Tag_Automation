package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.python.core.exceptions;
import org.testng.Assert;
import org.testng.annotations.Parameters;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import utils.PassSTComment;
import utils.logReadandWrite;
import wrappers.GenericWrappers;

public class AddDevicePage extends GenericWrappers {

	public AndroidDriver driver;

	public String serialno= loadProp("WIFIPAGE_SERIALNO");
	public String userName = loadProp("USERNAME");
	public String emaId = loadProp("EMAILID");
	public String wifiPassword = loadProp("WIFIPASSWORD");
	public String deviceDetailsUpdated = loadProp("deviceDetailsUpdated");
	public String devicesettingsupdatesuccess = loadProp("deviceSettingsUpdateSuccess");
	public String DeviceRemovedSuccessfully = loadProp("DeviceRemovedSuccessfully");
	public String YourDeviceResetSuccessfully = loadProp("YourDeviceResetSuccessfully");
	public String RouterAddedSuccessfully = loadProp("RouterAddedSuccessfully");

	// Locate all elements on the page

	@FindBy(xpath = "//*[@resource-id='Add_Devices_ButtonText']")
	private WebElement addDeviceButton;

	@FindBy(xpath = "//*[@resource-id='Pairing_mode_Checkbox']")
	private WebElement checkBoxPairing;

	@FindBy(xpath = "//*[@resource-id='Pairing_mode_ButtonText']")
	private WebElement nextButtonPairing;

	@FindBy(xpath = "//*[@resource-id='Start_Pairing_Button_Text']")
	private WebElement startPairingButton;

	@FindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']")
	private WebElement locationPopUp;

	@FindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']")
	private WebElement nearByPermisson;

	@FindBy(xpath = "//android.widget.EditText[@text='Enter Password']")
	private WebElement enterPasswordField;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Cancel\"]")
	private WebElement cancelButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Submit\"]")
	private WebElement enterButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Cancel']")
	private WebElement routerCancelButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Next']")
	private WebElement nextButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Submit']")
	private WebElement submitBtn;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\", Select Brand\"]")
	private WebElement ClickBrandName;

	@FindBy(xpath = "//*[@resource-id='Add_Device_YourDevice']")
	private WebElement afterpairDeviceName;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Carrier\"]")
	private WebElement ClickSelectName;

	@FindBy(xpath = "//*[@resource-id='Add_Device_Ac_ModelName_Input']")
	private WebElement enterAcModelName;

	@FindBy(xpath = "//*[@resource-id='Add_Device_Capacity_Input']")
	private WebElement enterCapacity;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Select room size\"]")
	private WebElement ClickRoomSizeButton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Medium\"]")
	private WebElement SelectRoomSizeOption;

	@FindBy(xpath = "//*[@resource-id='Add_Device_Next_Button_Text']")
	private WebElement sZephyrInfoNextButton;

	@FindBy(xpath = "//*[@resource-id='UserConfig_Submit_ButtonText']")
	private WebElement deviceSettingSubmitButton;

	@FindBy(xpath = "//*[@resource-id='UserConfig_Skip_ButtonText']")
	private WebElement deviceSettingSkipButton;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement connectbuttonWifipage;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.oplus.wirelesssettings:id/menu_save\"]")
	private WebElement savebuttonWifipage;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement devicewifipop_upOK;
	@FindBy(xpath = "//android.widget.TextView[@text=\"Please Connect Manually\"]")
	private WebElement devicewifipop_up;

	@FindBy(xpath = "//*[@resource-id='Launch_SignInText']")
	private WebElement signInButton;
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]")
	private WebElement locationpermissionpopup;
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
	private WebElement devicepermission;
	@FindBy(xpath = "//android.widget.TextView[@text=\"sZephyr and AC turned ON\"]")
	private WebElement Acturnondesc;
	@FindBy(xpath = "//android.widget.TextView[@text=\"Please ensure sZephyr is switched ON prior to operating your AC remote\"]")
	private WebElement acturnoffdesc;
	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"android:id/alertTitle\"]")
	private WebElement alertpopup;
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement shellallow;
	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"android:id/message\"]")
	private WebElement shellmessage;

	@FindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1']")
	private WebElement Blepopup_afterpairing;

	@FindBy(xpath = "//*[@resource-id='Retrying_header_line']")
	private WebElement Retrypagetext;

	@FindBy(xpath = "//*[@resource-id='Retrying_Retry_Button_Text']")
	private WebElement Retrypageretrybutton;

	@FindBy(xpath = "//*[@resource-id='Retrying_Cancel_Button_Text']")
	private WebElement Retrypagecancelbutton;

	@FindBy(xpath = "//*[@resource-id='home_main_on_off_swch']")
	private WebElement deviceONOFFButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Cancel\"]")
	private WebElement ClickCancelButtonWifi;

	@FindBy(xpath = "//*[@resource-id='Add_Device_YourDevice']")
	private WebElement szephyrDeviceName;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\", Select Brand\"]")
	private WebElement ACBrandNameClick;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Carrier\"]")
	private WebElement ACBrandNameCarrier;

	@FindBy(xpath = "//*[@resource-id='Add_Device_Ac_ModelName_Input']")
	private WebElement ACModelName;

	@FindBy(xpath = "//*[@resource-id='Add_Device_Capacity_Input']")
	private WebElement Capacity;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Select room size\"]")
	private WebElement RoomSizeselect;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Small\"]")
	private WebElement RoomSizesmall;

	@FindBy(xpath = "//*[@resource-id='UserConfig_Switch3']")
	private WebElement Ledquietmode;

	@FindBy(xpath = "//*[@resource-id='UserConfig_Switch4']")
	private WebElement infinitepoweron;

	@FindBy(xpath = "(//android.widget.TextView[@text=\"\"])[1]")
	private WebElement hoursplusbutton;

	@FindBy(xpath = "(//android.widget.TextView[@text=\"\"])[2]")
	private WebElement minutesplusbutton;

	@FindBy(xpath = "(//android.widget.TextView[@text=\"\"])[2]")
	private WebElement minutesminusbutton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement ClickCancelButtonBle;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement ClickOkButtonBLEpopUP;

	@FindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[7]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.CircleView[1]")
	private WebElement enterpasswordwifipge1;

	@FindBy(xpath = "//*[@resource-id='DeviceSetting_DurationforON']")
	private WebElement durationforON;

	@FindBy(xpath = "//android.widget.EditText[@text=\"0\"]")
	private WebElement hourstextbox;

	@FindBy(xpath = "//android.widget.EditText[@text=\"19\"]")
	private WebElement minutestextbox;

	@FindBy(xpath = "//*[@resource-id='DurationForON_Ok_Button']")
	private WebElement clickokdurationON;

	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement backbuttonDevicesettings;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement blePermissionOkButton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement blePermissionCancelButton;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.android.settings:id/password\"]")
	private WebElement enterpasswordwifipge;

	@FindBy(xpath = "//android.widget.EditText")
	private WebElement enterpasswordwifipgehighversion;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement BleOKpopup;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement BLEcancelpopup;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Cancel\"]")
	private WebElement wifiCancel;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement deviceofflinealertTitle;

	@FindBy(xpath = "//android.widget.TextView[@text=\"ALERT\"]")
	private WebElement buttonPressAlert;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement alertok;

//	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
//	private WebElement exitPairingok_popup;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Device details updated successfully!\"]")
	private WebElement sZhephyrInfotoast;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Device settings updated successfully!\"]")
	private WebElement Devicesettingstoast;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Device removed successfully\"]")
	private WebElement deviceremovedtoast;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Your device reset successfully\"]")
	private WebElement deviceresettoast;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Router added successfully\"]")
	private WebElement routeraddedsuccessfullytoast;

	@FindBy(xpath = "//android.widget.TextView[@text=\"⚠️ Unregistered Device Detected\"]")
	private WebElement unregisteredpopup;

	@FindBy(xpath = "//android.widget.TextView[@text=\"\"]")
	private WebElement menuBarButton;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_ble_0_blue']")
	private WebElement bleConnectivity;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_ble_0_grey']")
	private WebElement bleNonConnectivity;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_STA_0_blue']")
	private WebElement staConnectivity;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_STA_0_grey']")
	private WebElement staNonConnectivity;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_remote_0_blue']")
	private WebElement remoteConnectivity;
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_remote_0_grey']")
	private WebElement remoteNonConnectivity;
	@FindBy(xpath = "//*[@resource-id='menu_icon_removeDevice']")
	private WebElement removeDevice;
	@FindBy(xpath = "//*[@resource-id='GeyserInfo_ButtonText']")
	private WebElement SampmaxInfoEditname;
	@FindBy(xpath = "//*[@resource-id='EditDevice_YourDevice']")
	private WebElement EnetrDeviceName;
	@FindBy(xpath = "//*[@resource-id='EditDevice_Save_ButtonText']")
	private WebElement MaxSaveButton;
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/Device_BackIcon\"]")
	private WebElement BackButton;


	// Constructor to initialize the driver and instantiate elements using

	public AddDevicePage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.js = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, 10);

	}

	JavascriptExecutor js = (JavascriptExecutor) driver;

	// Methods to be used as part of loginpage.
	
	public void MaxBackButton () {
		clickbyXpath(BackButton, "Click Back Button");
	}
	public void MaxinfoSaveButton () {
		clickbyXpath(MaxSaveButton, "Click button Save Name");
	}
	 public void clearText() {
		 EnetrDeviceName.clear();
	    }
	
	public void enterSampMaxDevcieName(String DeviceName) {
		 EnetrDeviceName.clear();
		 entervaluebyXpath(EnetrDeviceName, "Device Model Name " ,DeviceName);
	}
	
	public void SampmaxInfoButtonClick () {
		clickbyXpath(SampmaxInfoEditname, "Click button Edit Name");
	}
	public AddDevicePage clickAddDeviceButton() {
		clickbyXpath(addDeviceButton, " Add Device Button  ");
		return this;
	}

	public AddDevicePage checkBoxPairing() {
		clickbyXpath(checkBoxPairing, " Pairing Mode Check Box ");
		return this;
	}

	public void nextButtonPairing() {
		clickbyXpath(nextButtonPairing, " Pairing mode Next Button ");
	}

	public void startPairingButton() {
		clickbyXpath(startPairingButton, " Start Pairing ");
	}

	public void locationPopUpPermission() throws InterruptedException {
		if (isElementDisplayedCheck(locationPopUp)) {
			clickbyXpath(locationPopUp, "Location pop-up");
		} else {
			System.out.println("not asked for precise or approx location");

		}
	}

	public void nearByPermission() throws InterruptedException {
		if (isElementDisplayedCheck(nearByPermisson)) {

			clickbyXpathwithoutReport(nearByPermisson, " Near by devices Permission  ");
		}
	}

	public void enterWiFiPassword(String password) {
		entervaluebyXpath(enterPasswordField, " Wifi Password  ", password);
	}

	public void clickRouterCancelButton() {
		clickbyXpathwithoutReport(routerCancelButton, " Add router Cancel button");
	}

	public void clickEnterButton() {

		clickbyXpath(enterButton, " Enter Button  ");

	}

	public void clickNextButton() {
		clickbyXpath(nextButton, " Enter Button  ");
	}

	public void submitButton() {
		clickbyXpath(submitBtn, " Submit Button ");
	}

	public void clickNextButtonsZephyrInfo() {
		if (isElementDisplayednext(sZephyrInfoNextButton, "sZephyr info Next button ")) {
			clickbyXpath(sZephyrInfoNextButton, " Next Button ");
		} else {
			driver.activateApp(loadProp("APP_PACKAGE"));
			expWaitforPairing(sZephyrInfoNextButton);
			clickbyXpath(sZephyrInfoNextButton, " Next Button ");

		}

	}

	public void ClickBrandName() {
		expWaitforPairing(ClickBrandName);
		clickbyXpath(ClickBrandName, " Ac Brand Name ");
	}

	public void ClickSelectName() {
		clickbyXpath(ClickSelectName, " Select Brand Name ");
	}

	public void enterAcModelName(String entermodelname) {
		entervaluebyXpath(enterAcModelName, " AC Model Name ", entermodelname);
	}

	public void enterCapacity(String Capacity) {
		entervaluebyXpath(enterCapacity, " AC Model Name ", Capacity);
	}

	public void ClickRoomSizeButton() {
		clickbyXpath(ClickRoomSizeButton, " Room Size ");
	}

	public void SelectRoomSizeOption() {
		clickbyXpath(SelectRoomSizeOption, " Select room size ");
	}

	public void ClickCancelButtonBle() throws Exception {

		clickbyXpathwithoutReport(ClickCancelButtonBle, " Cancel Button ");

		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void cancelButton() throws Exception {
		expWaitforPairing(cancelButton);
		clickbyXpathwithoutReport(cancelButton, " Wifi cancel Button ");
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void ClickOkButtonBLEpopUP() throws Exception {
		expWaitforPairing(ClickOkButtonBLEpopUP);
		clickbyXpathwithoutReport(ClickOkButtonBLEpopUP, " Ok Ble Button ");
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void clickSubmitButtonDeviceSetting() {
		clickbyXpath(deviceSettingSubmitButton, " Next Button ");
	}

	public void clickBlePermissionCancelbutton() {
		clickbyXpath(blePermissionCancelButton, " Ble Popup Cancel Button ");
	}

	public void clickBlePermissionOkbutton() {
		expWaitforPairing(blePermissionOkButton);
		clickbyXpath(blePermissionOkButton, " Ble Popup Cancel Button ");
	}

	public void verifyAddDevicePage(String title) {
		verifyTextContainsByXpath(addDeviceButton, title, " ADD device Page ");
	}

	public void turnOnBluetooth() {

		try {
			Runtime.getRuntime().exec("adb shell svc bluetooth enable");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void turnOffBluetooth() {

		try {
			Runtime.getRuntime().exec("adb shell svc bluetooth disable");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void turnOnWifi() throws Exception {

		try {
			Runtime.getRuntime().exec("adb shell svc wifi enable");
			Thread.sleep(3000);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void turnOffWifi() {

		try {
			Runtime.getRuntime().exec("adb shell svc wifi disable");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clickWifiCancelButton() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(wifiCancel));
		clickbyXpath(wifiCancel, "Wificancel button");
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void clickBleokbutton() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(BleOKpopup));
		clickbyXpath(BleOKpopup, "Ble okbutton");
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void clickBleCancelbutton() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(BLEcancelpopup));
		clickbyXpath(BLEcancelpopup, "Ble Cancelbutton");
		if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
			driver.activateApp(packages); // Bring it back
		}
	}

	public void aCBrandNameClick()

	{
		expWaitforPairing(ACBrandNameClick);
		clickbyXpath(ACBrandNameClick, "Acbrandname");
	}

	public void aCBrandNameCarrierclick()

	{
		expWaitforPairing(ACBrandNameClick);
		clickbyXpath(ACBrandNameCarrier, "Acbrandname");
	}

	public void roomSizeselect() {
		clickbyXpath(RoomSizeselect, " room Size ");
	}

	public void roomSizesmall() {
		clickbyXpath(RoomSizesmall, "Select room size ");
	}

	public void clickLedquietmode() {
		clickbyXpath(Ledquietmode, "LED enable disable ");
	}

	public void Alertpopup() {
		clickbyXpath(alertpopup, "Inn_alertpopup");
	}

	public void LEDquietmode() {
		clickbyXpath(Ledquietmode, "enabling LED Quiet mode");
	}

	public void Infinitepoweron() {
		clickbyXpath(infinitepoweron, "Clicking INFINITE Power On disable");
	}

	public void hoursplusbutton() {
		clickbyXpath(hoursplusbutton, "Clicking Hours plus button set 0");
	}

	public void Minutesminusbutton() {
		clickbyXpath(minutesminusbutton, "Clicking Hours plus button set 0");
	}

	public void DurationforON() {
		clickbyXpath(durationforON, "Clicking Duration for ON");
	}

	public void Hourstextbox() {
		verifyTextContainsByXpath(hourstextbox, "0", "Hours");
	}

	public void Minutestextbox() {
		verifyTextContainsByXpath(minutestextbox, "19", "Minutes");
	}

	public void ClickokdurationON() {
		// wait.until(ExpectedConditions.visibilityOf(clickokdurationON));
		clickbyXpath(clickokdurationON, "OKbutton_Duration for ON");
	}

	public void BackbuttonDevicesettings() {
		clickbyXpath(backbuttonDevicesettings, "back button click");
	}

	public void alertOkButton() {
		try {
			alertok.click();
		} catch (Exception e) {
			//
		}
	}
	

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	DeviceMenuPage devicemenupage;
	PassSTComment passcommand;

	public void pair(int mode) throws Exception {
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		homepage = new HomePage(driver);
		otppage = new OtpPage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		passcommand = new PassSTComment();
		
		//verifysigninpage();
		initiatepairing(mode);
	}

	@Parameters({ "os" })
	public void verifysigninpage() throws Exception {

		// Backgrounds app for 10 seconds
		homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));

		turnOnBT();
		/*
		 * if (isElementDisplayedCheck(blePermissionOkButton)) {
		 * clickbyXpath(blePermissionOkButton, "Allowing Ble permission pop-up");
		 * checkappinforeground(); }
		 */	
		
		try {
			Thread.sleep(5000);
			if(isElementDisplayedCheck(signInButton)) {
				landingpage.clickSignInButton();
				loginpage.enterUserName(userName);
				loginpage.clickSignInButton();
				otppage.enterOTPField1("1");
				otppage.enterOTPField2("2");
				otppage.enterOTPField3("3");
				otppage.enterOTPField4("4");
				otppage.submitButton();

			} 
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			System.out.println("App is already logged in and opening the previous state");
		}
	}

	public void initiatepairing(int mode) throws Exception {

//		if (isElementDisplayedCheck(locationpermissionpopup)) {
//			clickbyXpath(locationpermissionpopup, "Location pop-up");
//
//			// Check if device permission popup appears after location permission
//			if (isElementDisplayedCheck(devicepermission)) {
//				clickbyXpath(devicepermission, "Device permission pop-up");
//			}
		turnOnBT();
		/*
		 * if (isElementDisplayedCheck(blePermissionOkButton)) { clickbyXpath(blePermissionOkButton,
		 * "Allowing Ble permission pop-up"); }
		 */

//		}

		proceedToAddDevice(mode);
	}

	// mode=1-Ble without router ,2-Ble with router,3-Smartconfig,4-wifi with
	// roouter ,5 wifi without router
	logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));

	public void proceedToAddDevice(int mode) throws Exception {
		

		if (isElementDisplayedCheck(addDeviceButton)) {

			clickAddDeviceButton();
			checkBoxPairing();
			nextButtonPairing();

			switch (mode) {
			case 1:
				turnOnBT();
				startPairingButton();
				readwrite.write("factory_reset\r");
//				blepermissionokpopup();
//				locationPopUpPermission();
//				nearByPermission();

				Thread.sleep(7000);
//				blepermissionokpopup();
          
				clickWifiCancelButton();
				Thread.sleep(25000);
				blepermissionokpopup();
				if(!isElementDisplayedCheck(sZephyrInfoNextButton)) {
				retrypagecheck(mode);
				unregistereddevicepopup();
				}
				break;

			case 2:
				homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
				turnOnBT();
				startPairingButton();
//				blepermissionokpopup();
//				locationPopUpPermission();
//				nearByPermission();
				readwrite.write("factory_reset\r");
				blepermissionokpopup();
				enterWiFiPassword(wifiPassword);
				clickEnterButton();
				Thread.sleep(30000);
				alertOkButton();
				if(!isElementDisplayedCheck(sZephyrInfoNextButton))  {
				retrypagecheck(mode);
				unregistereddevicepopup();
				}
				break;
				
			case 3:
				homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
				turnOffBT();
				startPairingButton();
//				blepermissionokpopup();
//				locationPopUpPermission();
//				nearByPermission();
				readwrite.write("factory_reset\r");
				blepermissionokpopup();
				
				enterWiFiPassword(wifiPassword);
				clickEnterButton();
				
				
				Thread.sleep(1000 * 1 * 10);
				
				Thread.sleep(30000);
				blepermissionokpopup();
				alertOkButton();
				if(!isElementDisplayedCheck(sZephyrInfoNextButton)) {
					retrypagecheck(mode);
					unregistereddevicepopup();
				}
				
				break;
				
			case 4:
				homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
				turnOffBT();
				startPairingButton();
				readwrite.write("factory_reset\r");
				
				blepermissioncancelpopup();
//				locationPopUpPermission();
//				nearByPermission();

				Thread.sleep(1000 * 10 * 3);

				enterWiFiPassword(loadProp("WIFIPASSWORD"));
				Thread.sleep(10000);
				clickEnterButton();

				Thread.sleep(5*20*1000);
				alertOkButton();
				if(!isElementDisplayedCheck(devicewifipop_upOK)) {
					retrypagecheck(mode);
					unregistereddevicepopup();
				}
				
				connectwithmobilewifipage();
				homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
				
				Runtime.getRuntime().exec("adb shell am force-stop com.android.settings");
				if(isElementDisplayedCheck(devicewifipop_upOK,"Could not connect with router popup"))  {
					
					clickbyXpath(devicewifipop_upOK, "Cliked on not connected with router pop-up");
				}
				blepermissionokpopup();
				break;

			case 5:
				homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));

				turnOffBT();

				startPairingButton();

			    blepermissioncancelpopup();
//				Thread.sleep(3000);
//				locationPopUpPermission();
//				nearByPermission();

//				if (isElementDisplayedCheck(BLEcancelpopup)) {
//					clickBleCancelbutton();
//					if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
//						driver.activateApp(packages); // Bring it back
//						Thread.sleep(3000);
//					}
//				} else {
//					System.out.println("Alert pop-up not displayed");
//				}
			    readwrite.write("factory_reset\r");
				clickWifiCancelButton();
				Thread.sleep(5*20*1000);

				if(!isElementDisplayedCheck(devicewifipop_upOK))  {
					retrypagecheck(mode);
					unregistereddevicepopup();
				}
				connectwithmobilewifipage();
				
				Thread.sleep(5000);
				Runtime.getRuntime().exec("adb shell am force-stop com.android.settings");
				if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
					driver.activateApp(packages); // Bring it back

				}
				Thread.sleep(5000);
				blepermissionokpopup();
				turnOnBT();
				break;

			default:
				System.out.println("Pairing not done");
				break;
			}

		} else {

			System.out.println("Device is already in paired state removing the device");

			readwrite.write("factory_reset\r");

			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupYesButton();
			Thread.sleep(2000);//or5000
			if (isElementDisplayedCheck(deviceofflinealertTitle)) {
				String text = deviceofflinealertTitle.getText();
				System.out.println(text + "  Alert pop-up displayed");
				clickbyXpath(alertok, "Alert ok button");
				proceedToAddDevice(mode);

			} else if (isElementDisplayedCheck(buttonPressAlert)) {
				String text = buttonPressAlert.getText();
				System.out.println(text + "  Alert pop-up displayed");
				clickbyXpath(alertok, "Alert ok button");
				proceedToAddDevice(mode);

			} else {

				proceedToAddDevice(mode);
			}
		}
	}

	
	private boolean isElementDisplayedCheck(WebElement devicewifipop_upOK2, String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private void connectwithmobilewifipage() throws Exception {
		if (isElementDisplayedCheck(devicewifipop_upOK)) {
			clickbyXpath(devicewifipop_upOK, "click on Device wifi OK popup");
			
			Runtime.getRuntime().exec("adb shell am force-stop com.android.settings");
			Thread.sleep(5000);
			Runtime.getRuntime().exec("adb shell am start -a android.settings.WIFI_SETTINGS");

			Thread.sleep(3000);
			WebElement element = driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\""
							+ serialno + "\"))"));
//			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			if (isElementDisplayedCheck(enterpasswordwifipge)) {
				entervaluebyXpath(enterpasswordwifipge, "wifipage password", "mypassword");
				hidekeyboard();

				clickbyXpath(connectbuttonWifipage, "connect button");
			} else if (isElementDisplayedCheck(enterpasswordwifipgehighversion
					)) {

				entervaluebyXpath(enterpasswordwifipge, "wifipage password", "mypassword");
				hidekeyboard();

				clickbyXpath(savebuttonWifipage, "save button");
			} else {
				System.out.println("Already password saved ");
			}

			if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
				driver.activateApp(packages); // Bring it back
				// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			}
			if (isElementDisplayedCheck(Blepopup_afterpairing)) {

				clickbyXpath(Blepopup_afterpairing, "oK button of Ble alert pop-up");
			}

			// Now click the OK button once it is visible and clickable

		} else {
			System.out.println("unable to connect with device hotspot");
		}
		
	}
	private void unregistereddevicepopup() {
		if (isElementDisplayedCheck(unregisteredpopup)) {

			clickbyXpath(alertpopup, "alertokpop-up");
		}
	}
	private void retrypagecheck(int mode) throws Exception {
		if (isiconDisplayed(Retrypagetext, "Retry page")) {

			fail(new Exception("Retry page displayed"));
//			clickbyXpath(Retrypageretrybutton, "retrypage");

//			readwrite.write("factory_reset\r");
//			driver.navigate().back();
//			clickbyXpath(alertok, "clicking on exit pop-up ");
//			proceedToAddDevice(mode);
		} else {
			System.out.println("Retry page not displayed proceeding to next step");
		}
		
	}
	
	public void blepermissionokpopup() throws Exception {
		if (isElementDisplayedCheck(BleOKpopup)) {
			BleOKpopup.click();
			Thread.sleep(2000);
			checkappinforeground();

		} else {
			System.out.println("No alert pop ups displayed");
		}
		
	}
	private void blepermissioncancelpopup() throws Exception {
		if (isElementDisplayedCheck(BLEcancelpopup)) {
			BLEcancelpopup.click();
			if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
				driver.activateApp(packages); // Bring it back
				Thread.sleep(2000);
			}
		} else {
			System.out.println("Alert pop-up not displayed");
		}
	}
	public void hidekeyboard() {
		// Scroll up
		Map<String, Object> params = new HashMap<>();
		params.put("direction", "up");
		driver.executeScript("mobile: hideKeyboard", params);
	}

	public void checkdevicedetailstoast() {

		verifyToastContainsTextByXpath(sZhephyrInfotoast, deviceDetailsUpdated,
				"Device details updated successfully! toast");
	}

	public void checkdevicesettingstoast() {

		verifyToastContainsTextByXpath(Devicesettingstoast, devicesettingsupdatesuccess,
				"Device settings updated successfully! toast");
	}

	public void checkdeviceremovedtoast() {

		verifyToastContainsTextByXpath(deviceremovedtoast, DeviceRemovedSuccessfully, "  DeviceRemovedSuccessfully toast");
	}

	public void checkdeviceresettoast() {

		verifyToastContainsTextByXpath(deviceresettoast, YourDeviceResetSuccessfully, "  YourDeviceResetSuccessfully toast");
	}

	public void checkrouteraddedsuccessfultoast() {

		verifyToastContainsTextByXpath(routeraddedsuccessfullytoast, RouterAddedSuccessfully,
				" RouterAddedSuccessfully toast");
	}

	public void bleConnectivityCheck() {
		connectivitycheck(bleConnectivity, "Ble connectivity homepage icon");

	}

	public void staConnectivityCheck() {
		connectivitycheck(staConnectivity, "STA connectivity homepage icon");

	}

	public void remoteConnectivityCheck() {
		connectivitycheck(remoteConnectivity, "Remote connectivity homepage icon");

	}
public void removingDevice() throws InterruptedException {

	int n=5;
	while (n>0) {
		
		if (isElementDisplayed(menuBarButton,"Menu Bar button")) {
			homepage.clickMenuBarButton();
			if (isElementDisplayed(removeDevice, "Remove device button")) {
				devicemenupage.clickMenuBarRemoveDevice();
				devicemenupage.clickRemoveDevicePopupYesButton();
				checkdeviceremovedtoast();
				Thread.sleep(2000);//or5000
				if (isElementDisplayedCheck(deviceofflinealertTitle)) {
					String text = deviceofflinealertTitle.getText();
					System.out.println(text + "  Alert pop-up displayed");
					clickbyXpath(alertok, "Alert ok button");

				} else if (isElementDisplayedCheck(buttonPressAlert)) {
					String text = buttonPressAlert.getText();
					System.out.println(text + "  Alert pop-up displayed");
					clickbyXpath(alertok, "Alert ok button");

				} else {
					System.out.println("removed");
				}
			}
			
			devicemenupage.AddDevicePagedisplayed();
			break;
		}
		else if (isElementDisplayed(signInButton,"signin button displayed")) {
			System.out.println("signin page displayed");
			break;
		}
		else {
			System.out.println("something went wrong");
			n--;
		}
		
	}
}

}
