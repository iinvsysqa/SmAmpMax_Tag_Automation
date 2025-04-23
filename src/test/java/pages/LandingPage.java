package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class LandingPage extends GenericWrappers{
	    
	    private AndroidDriver driver;
	    
	 // Locate all elements on the page
	    
	    @FindBy(xpath = "//android.widget.TextView[@text='Sign In']")
	    private WebElement signInButton;
	    
	    @FindBy(xpath = "//*[@resource-id='SignUp_SignUpText']")
	    private WebElement signUpButton;
	    
	    @FindBy(id = "loginButton")
	    private WebElement loginButton;
	
	    @FindBy(xpath = "//*[@resource-id='Launch_SignUpLink']")
		private WebElement signUpLink;
	    //div[text()='Home']//following-sibling::div[1]
	    
	    // Constructor
	    
	    public LandingPage(AndroidDriver driver) {
	        this.driver=driver;
	        PageFactory.initElements(driver, this);
	    }
	    
	    // Methods to interact with elements
	    
	    public void clickSignInButton() {
	    	clickbyXpath(signInButton, " Sign In Button " );
	    }
	    
	    
	    public void clickSignUpLink() {
	    	clickbyXpath(signUpLink, " Sign Up Button " );
	    }
	    public void clickSignUpButton() {
	    	clickbyXpath(signUpButton, " Sign Up Button " );
	    }
	    
}
