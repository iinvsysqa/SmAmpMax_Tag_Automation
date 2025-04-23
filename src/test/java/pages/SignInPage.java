package pages;

import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class SignInPage extends GenericWrappers {

	private AndroidDriver driver;

	// Locate all elements on the page
	@FindBy(xpath = "//*[@resource-id='SignIn_Email_or_UserName']")
	private WebElement userNameField;

	@FindBy(xpath = "//*[@resource-id='SignIn_SignInText']")
	private WebElement signInButton;
	
	@FindBy(xpath = "//android.widget.Toast[@text='User Not Found']")
	private WebElement userNotFoundToast;
	
	
	

	// Constructor to initialize the driver and instantiate elements using
	
	public SignInPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Methods to be used as part of loginpage.
	
	public SignInPage enterUserName(String email) {
		entervaluebyXpath(userNameField, " User Name " , email);
		return this;
	}

	
	public SignInPage clickSignInButton() {
		clickbyXpath(signInButton, " Sign In ");
		return this;
		
	}


	public SignInPage checkUserNameNotFoundToast(String content) {
		verifyTextContainsByXpath(userNotFoundToast, content, " The Toast ");
		return this;
		
	}
}
