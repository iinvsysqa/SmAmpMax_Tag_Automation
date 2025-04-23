package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import wrappers.MobileAppWrappers;

public class Reportpage extends MobileAppWrappers {
	
	@FindBy(xpath = "//android.widget.TextView[@text='']")
	private WebElement deviceSettingsButton;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_Report']")
	private WebElement ClickReportpage;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\", Select issue type\"]")
	private WebElement Clickissuetype;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"App issue\"]")
	private WebElement ClickAppissue;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\", Select your device name\"]")
	private WebElement Clickdevicename;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"nee_1\"]")
	private WebElement ClickIssuename;
	
	@FindBy(xpath = "//*[@resource-id='Report_Your_Issue']")
	private WebElement ClickIssuedescription;
	
	@FindBy(xpath = "//android.view.ViewGroup[@resource-id=\"Report_FewTimes_Button\"]")
	private WebElement ClickIssueobsserved;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/Report_IssueObserve_Sub_Title\"]/android.view.ViewGroup")
	private WebElement Clickenterdate;
	
	@FindBy(xpath = "//android.view.View[@content-desc=\"18 September 2024\"]")
	private WebElement Enterdate;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement Clickokbutton;
	
	@FindBy(xpath = "//*[@resource-id='Report_UploadScreenshot_plus']")
	private WebElement Uploadscreenshort;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_all_button\"]")
	private WebElement Clickallowall;
	
	@FindBy(xpath = "//android.widget.ImageView[@resource-id=\"com.google.android.providers.media.module:id/icon_thumbnail\"])[2]")
	private WebElement selectphotos;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.google.android.providers.media.module:id/button_add\"]")
	private WebElement Clickaddoption;
	
	@FindBy(xpath = "//*[@resource-id='Report_Submit_ButtonText']")
	private WebElement Clicksubmitbutton;

	private AndroidDriver driver;
	

		
		public Reportpage(AndroidDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		public void ClickReportpage() {	
			clickbyXpath(ClickReportpage, " Report page button ");
		}
		
		public void Clickissuetype() {	
			clickbyXpath(Clickissuetype, " Click Issue Type ");
		}
		
		public void ClickAppissue() {	
			clickbyXpath(ClickAppissue, " Click App Issue ");
		}
		
		public void Clickdevicename() {	
			clickbyXpath(Clickdevicename, " Click Device Name ");
		}
		
		public void ClickIssuename() {	
			clickbyXpath(ClickIssuename, " Click Issue Name ");
		}
		
		public void ClickIssuedescription() {	
			clickbyXpath(ClickIssuedescription, " Click Issue Description ");
		}
		
		public void sendDesscription() {	
			ClickIssuedescription.sendKeys("App is not working properly");
		}
		
		public void ClickIssueobsserved() {	
			clickbyXpath(ClickIssueobsserved, " Click Issue Obsserved ");
		}
		
		public void Clickenterdate() {	
			clickbyXpath(Clickenterdate, " Click date option ");
		}
		
		public void Enterdate() {	
			clickbyXpath(Enterdate, " Enter Date ");
		}
		
		public void Clickokbutton() {	
			clickbyXpath(Clickokbutton, " Click OK Button ");
		}
		
		public void Uploadscreenshort() {	
			clickbyXpath(Uploadscreenshort, " Upload Screen Short ");
		}
		
		public void Clickallowall() {	
			clickbyXpath(Clickallowall, " Click Allow ALL ");
		}
		
		public void selectphotos() {	
			clickbyXpath(selectphotos, " Select Photos ");
		}
		

		public void Clickaddoption() {	
			clickbyXpath(Clickaddoption, " Select Add Option ");
		}
		
		public void Clicksubmitbutton() {	
			clickbyXpath(Clicksubmitbutton, " Clicl Submit Button ");
		}
		public void scrollpage() {

			driver.findElement(MobileBy.AndroidUIAutomator(
				    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"+\"));"));
			driver.findElement(By.xpath("//*[@resource-id='Report_UploadScreenshot_plus']"))
		    .click();
		}

}
