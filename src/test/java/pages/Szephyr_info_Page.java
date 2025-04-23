package pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class Szephyr_info_Page extends GenericWrappers{

	private AndroidDriver driver;

	// Locate all elements on the page

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Download Firmware\"]")
	private WebElement download_firmwareButton;
    
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Firmware Update\"]")
	private WebElement firmware_update;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement firmware_update_popup_button;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement OTA_OK_Button;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement backButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Carrier\"]")
	private WebElement Carriertext;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"V20\"]")
	private WebElement Modelnametext;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"2\"]")
	private WebElement Capacitytext;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Small\"]")
	private WebElement Roomsizetext;
	
	
	
	
	
	

	
// Constructor to initialize the driver and instantiate elements using
	
	public Szephyr_info_Page(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Methods to be used as part of szephyr info page.
	
	public void clickdownload_firmwareButton() {
		clickbyXpath(download_firmwareButton , " software download  ");
	}
	
	public void clickfirmware_update() {
		expWaitforPairing(firmware_update);
		clickbyXpath(firmware_update, " Firware_update ");
	}
	
	public void clickfirmware_update_popup_button() {	
		clickbyXpath(firmware_update_popup_button, " popup_open ");
	}
	
	public void clickOTA_OK_Button() {	
		expWaitforFirmware(OTA_OK_Button);
		clickbyXpath(OTA_OK_Button, " OTA_Update Success");
	}
	
	public void clickbackButton() {	
		clickbyXpath(backButton, " Device_BackIcon");
	}
	public void brandnametext()
	{	
		verifyTextContainsByXpath( Carriertext, "Carrier", "AC Brand Name");
	}
	
	public void Modelnametext()
	{	
		verifyTextContainsByXpath( Modelnametext, "V20", "AC Model Name");
	}
	
	public void Capacity_field()
	{	
		verifyTextContainsByXpath( Capacitytext, "2", "Capacity");
	}
	
	public void Roomsize_field()
	{	
		verifyTextContainsByXpath(Roomsizetext , "Small", "Room Size");
	}
	
	}	
	

