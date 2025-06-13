package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import utils.Reporter;
import wrappers.GenericWrappers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SignUpPage extends GenericWrappers {

	private AndroidDriver driver;
	
	// Locate all elements on the page
	@FindBy(xpath = "//*[@resource-id='SignUp_Username']")
	private WebElement userNameField;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_Email']")
	private WebElement userEmailIDField;
	
	@FindBy(xpath = "//*[@resource-id='Launch_SignUpLink']")
	private WebElement signUpLink;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_SignUpText']")
	private WebElement signUpButton;
	
	
	@FindBy(xpath = "//*[@resource-id='SignUp_TC']")
	private WebElement signUpTC;
	
	@FindBy(xpath = "//*[@resource-id='SingUp_PP']")
	private WebElement SignUpPPField;
	
	@FindBy(xpath = "//*[@resource-id='SingUp_PP_Des1']")
	private WebElement privacyPolicyContent;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_Checkbox']")
	private WebElement signUpCheckbox;
	//com.horcrux.svg.ImageView
	@FindBy(xpath = "//*[@resource-id='SingUp_PP_cancel_Icon']")
	private WebElement tcPopupCloseButton;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_UserName_Error']")
	private WebElement signUpUserNameError;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_EmailId_Error']")
	private WebElement signUpEmailIdError;

	@FindBy(xpath = "//*[@resource-id='SignUp_MobileNumber_Error']")
	private WebElement signUpMobileNumberError;
	
	@FindBy(xpath = "//*[@resource-id='SignUp_TC_Error']")
	private WebElement signUpTcError;
	
	@FindBy(xpath = "//*[@resource-id='SingUp_PP_Des33']")
	private WebElement ppContactUsContent;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Username and Email ID both are already exists\"]")
	private WebElement userNameExistToast;
	
	//android.widget.Toast[@text='Username and Email ID both are already exists']

	
	
	
	
	// Constructor to initialize the driver and instantiate elements using
	
	public SignUpPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Methods to be used as part of loginpage.
	
	public void enterUserName(String email) {
		entervaluebyXpath(userNameField, " User Name " , email);
	}
	
	
	public void enterEmailId(String email) {
		entervaluebyXpath(userEmailIDField, " Email ID Field " , email);
	}

	
	public void clickSignUpButton() {
		clickbyXpath(signUpButton, " Sign Up ");
		
	}
	
	public void clickSignUpTCButton() {
		clickbyXpath(signUpTC, " Sign Up Terms and Conditions ");
		
	}
	
	public void clickSignUpTCCheckBox() {
		clickbyXpath(signUpCheckbox, " Sign Up Terms and Conditions check box ");
		
	}
	
	public void clicktcPopupCloseButton() {
		clickbyXpath(tcPopupCloseButton, " Close button ");
		
	}
	
	public void clickSignUpcheckbox() {
		clickbyXpath(signUpCheckbox, " Sign Up Terms and Conditions check box ");
		
	}
	
	public void checkPpContentTitle(String content) {
		verifyTextContainsByXpath(SignUpPPField, content, "Privacy Policy Field ");
		
	}
	
	public void checkPpContent(String content) {
		verifyTextContainsByXpath(privacyPolicyContent, content, " Privacy Policy Content ");
		
	}
	
	public void checkPpContactUsContent(String content) {
		for(int i=1;i<9;i++) {
		scroll2();
		Reporter.reportStep("The Page is "+ i + " time scrolled", "PASS");
	}
		verifyTextContainsByXpath(ppContactUsContent, content, "Contact Us Content ");
		
	}
	
	public void checkUserNameExistToast(String content) {
		verifyToastContainsTextByXpath(userNameExistToast, content, " The Toast ");
		
	}
	

	
	
	public void scroll() {

		Dimension size = driver.manage().window().getSize();
		int startX = size.width;
		int startY = (int) (size.height * 0.8);
		int endY = (int) (size.height * 0.2);

		new TouchAction<>(driver).press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(startX, endY)).release().perform();

	}
	 
		public void scroll2() {
			int startX = driver.manage().window().getSize().getWidth() / 4;
			int startY = driver.manage().window().getSize().getHeight() / 2;
			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence scroll = new Sequence(finger, 0);
			int endY = (int) (driver.manage().window().getSize().getHeight());
			scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
			scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			scroll.addAction(
					finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, 0));
			scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(List.of(scroll));

}
		
		
		public void uninstall_reinstall() throws Exception {
			Properties prop =new Properties();
			prop.load(new FileInputStream(new File("./config.properties")));
			
			if (driver.isAppInstalled(packages)) {
			Runtime.getRuntime().exec("adb uninstall com.iinvsys.smampmax");
			driver.installApp(prop.getProperty("APP_PATH"));
			driver.activateApp(packages);
			}
			else {
				
				driver.installApp(prop.getProperty("APP_PATH"));
				driver.activateApp(packages);
			}
		}
		
}
