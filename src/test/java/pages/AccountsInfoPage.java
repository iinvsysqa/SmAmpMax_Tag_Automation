package pages;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class AccountsInfoPage extends GenericWrappers{

	private AndroidDriver driver;

	// Locate all elements on the page

	@FindBy(xpath = "//*[@resource-id='menu_text_accounts']")
	private WebElement  AccountsinfoButtonbeforeadddevice;
	
	@FindBy(xpath = "//*[@resource-id='menu_text_accounts']")
	private WebElement AccountsinfoButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\", English\"]/android.widget.TextView[2]")
	private WebElement SelectLanguageButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Tamil\"]")
	private WebElement SelectLanguageTamilButton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"\"]")
	private WebElement SelectLanguageDropDownButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"English\"]")
	private WebElement SelectLanguageEnglishButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/Accounts_FirmwareAutoUpdate\"]/android.view.ViewGroup/android.view.ViewGroup")
	private WebElement FirmwareUpdateSettingButton;

	@FindBy(xpath = "//*[@resource-id='Accounts_DeleteAccount_SubTitle']")
	private WebElement DeleteAccountButton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement AccountDeleteButton;	

	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement AccountInfoBackButton;

	@FindBy(xpath = "//*[@resource-id='menu_icon_removeDevice']")
	private WebElement Clickremovedevicebutton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"YES\"]")
	private WebElement RemoveDeviceYesButton;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/Options_Icon\"]")
	private WebElement Clickmenubaricon;

	@FindBy(xpath = "//*[@resource-id='menu_icon_accounts']")
	private WebElement Clickaccountinfo;

	@FindBy(xpath = "//*[@resource-id='Accounts_DeleteAccount_SubTitle']")
	private WebElement Clickaccountdelete;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[2]")
	private WebElement Clickaccountdeleteyes;

	@FindBy(xpath = "//android.widget.TextView[@text=\"\"]")
	private WebElement menuBarButton;

	@FindBy(xpath = "//*[@resource-id='Accounts_YourName']")
	private WebElement Usernamefield;

	@FindBy(xpath = "//*[@resource-id='Accounts_YourEmail']")
	private WebElement emailIdfield;

	@FindBy(xpath = "//*[@resource-id='Accounts_Language_Sub_Title']")
	private WebElement languagesubtitle;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement accountsdelpop_up;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement accountsdelpop_up_withdevice;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement accountsdelpop_up_withoutdevice;
	

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button2\"]")
	private WebElement accountspop_upcancelbtn;

	@FindBy(xpath = "//android.widget.Toast[@text=\"User Not Found\"]")
	private WebElement toast;
	
	@FindBy(xpath = "//android.widget.Toast[@text=\"Device removed successfully\"]")
	private WebElement deviceremovedtoast;
	
	@FindBy(xpath = "//android.widget.Toast[@text=\"Your account has been deleted successfully\"]")
	private WebElement accountdeletedtoast;

	@FindBy(xpath = "//android.widget.TextView[@text='Sign Up']")
	private WebElement signUpButton;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_Username']")
	private WebElement usernamesignup;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_Email']")
	private WebElement emailidsignup;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_Checkbox']")
	private WebElement checkboxsignup;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_SignUpText']")
	private WebElement signUptxt;
	
	@FindBy(xpath = "//*[@resource-id='OTP_ButtonText']")
	private WebElement submitBtn;
	
	@FindBy(xpath = "//*[@resource-id='Add_Devices_ButtonText']")
	private WebElement addDeviceButton;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement DevicesettingsbackButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"YES\"]")
	private WebElement removeDevicePopupYesButton;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_removeDevice']")
	private WebElement removeDevice;
	
	@FindBy(xpath = "//*[@resource-id='OTP_VerifyText']")
	private WebElement otpVerificationTitle;
	
	@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[1]")
	private WebElement otpField1;

	@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[2]")
	private WebElement otpField2;
	
	@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[3]")
	private WebElement otpField3;
	
	@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[4]")
	private WebElement otpField4;
	
	
	
	public void verifyOTPVerificationTitle(String title) {
		verifyTextContainsByXpath(otpVerificationTitle, title, " OTP verification Page " );
	}
	
	


	public String accountdel_Toast="Your account has been deleted successfully";
	public String device_removed_Toast=loadProp("DeviceRemovedSuccessfully");
	public String userName=loadProp("USERNAME"); ; 
	public String emaId=loadProp("EMAILID");
	// Constructor to initialize the driver and instantiate elements using

	public AccountsInfoPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait=new WebDriverWait(driver, 30);

	}

	public void enterOTPField1(String otp1) {
		entervaluebyXpath(otpField1, " OTP Box 1 " , otp1);
	}
	
	public void enterOTPField2(String otp2) {
		entervaluebyXpath(otpField2, " OTP Box 2 ", otp2);
	}
	
	public void enterOTPField3(String otp3) {
		entervaluebyXpath(otpField3, " OTP Box 3 ", otp3);
	}
	
	public void enterOTPField4(String otp4) {
		entervaluebyXpath(otpField4, " OTP Box 4 " , otp4);
	}

	// Methods to be used as part of AccountInfo.

	public void clickAccountsInfoButton() {
		clickbyXpath(AccountsinfoButton, " Accounts Info Button  ");
	}

	public void clickSelectLanguageButton() {
		clickbyXpath(SelectLanguageButton, " Select Language Drop Box Button  ");
	}

	public void clickSelectLanguageTamilButton() {
		clickbyXpath(SelectLanguageTamilButton, " Selct Tamil Button ");
	}

	public void clickSelectLanguageDropDownButton() {
		clickbyXpath(SelectLanguageDropDownButton, " Select Language Drop Box Button  ");
	}

	public void clickSelectLanguageEnglishButton() {
		clickbyXpath(SelectLanguageEnglishButton, " Selct English Button ");

	}

	public void clickFirmwareUpdateSettingButton() {	
		clickbyXpath(FirmwareUpdateSettingButton, " Firmware auto update click Button ");
	}

	@SuppressWarnings("deprecation")
	public void clickDeleteAccountButton() {
		scrollToText("Delete Account");
		clickbyXpath(DeleteAccountButton, " Delete Account click Button ");
	}

	public void clickAccountDeleteButton() {	 
		clickbyXpath(AccountDeleteButton, " Account Delete Ok click Button ");
	}

	public void clickAccountInfoBackButton() {	 
		clickbyXpath(AccountInfoBackButton, " Account Info Back click Button ");
	}

	public void Clickremovedevicebutton() {	 
		clickbyXpath(Clickremovedevicebutton, " Click Remove device Button ");
	}

	public void clickRemoveDeviceYESButton() {	 
		clickbyXpath(RemoveDeviceYesButton, " Remove Device Yes Button ");
	}

	public void Clickmenubaricon() {	 
		clickbyXpath(Clickmenubaricon, " Click menubaricon ");
	}

	public void Clickaccountinfo() {	 
		clickbyXpath(Clickaccountinfo, " Click accountinfo ");
	}

	public void Clickaccountdelete() {	 
		clickbyXpath(Clickaccountdelete, " Click account delete ");
	}

	public void Clickaccountdeleteyes() {	 
		clickbyXpath(Clickaccountdeleteyes, " Click account delete yes ");
	}

	public void popuphandle() {

		if (accountsdelpop_up.isDisplayed()) {
			clickbyXpath(accountsdelpop_up, "Acceptpop_up");
		}
		//		else if (accountspop_upcancelbtn.isDisplayed()) {
		//			clickbyXpath(accountspop_upcancelbtn, "Acceptpop_up");
		//			
		//		} 
		else {
			System.out.println("No-popup displayed");
		}
	}
	public void popuphandlewithdevice() {
		
		if (accountsdelpop_up_withdevice.isDisplayed()) {
			clickbyXpath(accountsdelpop_up_withdevice, "Acceptpop_up");
		}
		//		else if (accountspop_upcancelbtn.isDisplayed()) {
		//			clickbyXpath(accountspop_upcancelbtn, "Acceptpop_up");
		//			
		//		} 
		else {
			System.out.println("No-popup displayed");
		}
	}

	public void checkAccountsinfousername_email_Language() throws Exception {

		clickbyXpath(menuBarButton, " Menu Bar ");
		clickbyXpath(AccountsinfoButton, " Accounts Info Button");
		verifyTextContainsByXpath(Usernamefield,userName , "Accounts_info_UserName_field");
		verifyTextContainsByXpath(emailIdfield,emaId , "Accounts_info_EmailId_field");
		clickbyXpath(SelectLanguageDropDownButton, "Select Language Drop Box Button");
		clickbyXpath(SelectLanguageTamilButton, " Select Tamil Button ");
		verifyTextContainsByXpath(languagesubtitle,"மொழியை தேர்ந்தெடுங்கள்" , "Languagesubtitle");
		clickbyXpath(SelectLanguageDropDownButton, "Select Language Drop Box Button");
		clickbyXpath(SelectLanguageEnglishButton, " Select English Button ");
		verifyTextContainsByXpath(languagesubtitle,"Select Language" , "Languagesubtitle");
		clickDeleteAccountButton();
		popuphandlewithdevice();
		
		clickbyXpath(DevicesettingsbackButton, " Device Setting Back Button ");
       
		clickbyXpath(menuBarButton, " Menu Bar ");
		clickbyXpath(removeDevice, " Click The Remove Device Button ");
		clickbyXpath(removeDevicePopupYesButton, " Click The Remove Device Pop-up YES Button ");
		



		
		
		verifyTextContainsByXpath(deviceremovedtoast, device_removed_Toast, "Device removed Toast message");
		
		clickbyXpath(menuBarButton, " Menu Bar ");
		clickbyXpath(AccountsinfoButtonbeforeadddevice, " Accounts Info Button");
		clickDeleteAccountButton();
		popuphandle();
		verifyTextContainsByXpath(accountdeletedtoast, accountdel_Toast, "Account deleted Toast message");
		


		verifyTextContainsByXpath(signUpButton, "Sign Up", "SignUp page");

		 clickbyXpath(signUpButton, " Sign Up Button " );
		 entervaluebyXpath(usernamesignup, " User Name " , userName);
		 entervaluebyXpath(emailidsignup, " emailid " , emaId);
		 clickbyXpath(checkboxsignup, "checkboxsignup");
		 clickbyXpath(signUptxt, "Signup text");
		 	enterOTPField1("1");
			enterOTPField2("2");
			enterOTPField3("3");
			enterOTPField4("4");
	
		 clickbyXpath(submitBtn, "OTPsubmitbutton");
		 if (isElementDisplayed(addDeviceButton,"Add device button")) {
			 System.out.println("SignUp successfull");
		 }

	}
	
	
	
}




