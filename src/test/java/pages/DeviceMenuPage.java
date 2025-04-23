package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import wrappers.GenericWrappers;

public class DeviceMenuPage extends GenericWrappers{

	private AndroidDriver driver;

	// Locate all elements on the page

	@FindBy(xpath = "//android.widget.TextView[@text='']")
	private WebElement deviceSettingsButton;

	@FindBy(xpath = "//*[@resource-id='DeviceSetting_resetDevice']")
	private WebElement resetDeviceButtom;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"YES\"]")
	private WebElement resetConfirmationYesButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Start Pairing']")
	private WebElement startPairingButton;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']")
	private WebElement locationPopUp;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_button']")
	private WebElement nearByPermisson;
	
	@FindBy(xpath = "//android.widget.EditText[@text='Enter Password']")
	private WebElement enterPasswordField;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Enter']")
	private WebElement enterButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Next']")
	private WebElement nextButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Submit']")
	private WebElement submitBtn;
	
	@FindBy(xpath = "//android.view.ViewGroup[@resource-id='Add_Device_Next_Button']")
	private WebElement sZephyrInfoNextButton;
	
	@FindBy(xpath = "//android.view.ViewGroup[@resource-id='UserConfig_Submit_Button']")
	private WebElement deviceSettingSubmitButton;
	
	
	@FindBy(xpath ="//*[@resource-id='menu_icon_geyserInfo']")
	private WebElement szephyr_info_button;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_geyserInfo']")
	private WebElement ClickSzephyrInfoButton;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_Report']")
	private WebElement clickReportButton;
	
	@FindBy(xpath = "//*[@resource-id='PairedGeyser_Img_svg_name_0']")
	private WebElement CheckSzephyrInfPageBrandName;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"G20\"]")
	private WebElement CheckSzephyrInfPageModelName;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"2\"]")
	private WebElement CheckSzephyrInfPageCapacity;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Medium\"]")
	private WebElement CheckSzephyrInfPageRoomSize;
	
	@FindBy(xpath = "//*[@resource-id='DeviceSetting_energysaving_Icon']")
	private WebElement ClickESMButton;
	
	@FindBy(xpath = "//*[@resource-id='AutoPowerON/OFF_Switch']")
	private WebElement EnableToogleSwtich;
	
	
	@FindBy(xpath = "//*[@resource-id='AutoPowerON/OFF_Switch']")
	private WebElement DisableToogleSwtich;
	
	@FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/com.horcrux.svg.SvgView[1]/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[1]")
	private WebElement Clickmoderatecooling;
	
	@FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[3]/com.horcrux.svg.SvgView[1]/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[1]")
	private WebElement ClickPropercooling;
	
	@FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/com.horcrux.svg.SvgView[1]/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[1]")
	private WebElement ClickLesscooling;
	
	@FindBy(xpath = "//*[@resource-id='DeviceON/OFF_Ok_Button']")
	private WebElement EsmOKbutton;
	
	@FindBy(xpath = "//android.widget.Toast[@text=\"Connect with device and try again\"]")
	private WebElement ToastForwithoutconnectivity;
	
	@FindBy(xpath = "//android.widget.Toast[@text=\"Energy saving mode updated successfully\"]")
	private WebElement SuccessfullyToast;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement ESMPageBackButton;

	@FindBy(xpath = "//*[@resource-id='menu_icon_logout']")
	private WebElement logoutButton;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_logout']")
	private WebElement logoutButtonAfterReset;

	
	@FindBy(xpath = "(//android.widget.TextView[@text=\"Logout\"])[2]")
	private WebElement logoutConfirmationButton;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button2\"]")
	private WebElement ClickCancelBluetooth;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement ClickCancelWifi;
	
	@FindBy(xpath =  "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement ClickOkButtonBLEpopUP;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Add Router, Choosing to add a router will add a router to your device.\"]")
	private WebElement ClickaddrouterButton;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement ClickDeviceSettingback;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_addDevice']")
	private WebElement clickAddDeviceButton;
	
	@FindBy(xpath = "//*[@resource-id='AddDevice_NewDevicePairing_ButtonText']")
	private WebElement clickNewDevicePairingButton;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"nee_1\"]")
	private WebElement clickFirstDeviceButton;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"nee_2\"]")
	private WebElement clickSecondDeviceButton;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"nee_3\"]")
	private WebElement clickThirdDeviceButton;
	
	@FindBy(xpath = "//*[@resource-id='DeviceSetting_highVoltageConfig']")
	private WebElement lowvoltageconfiguration;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement DevicesettingsbackButton;
	
	
	
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Add Router, Choosing to add a router will add a router to your device.\"]")
	private WebElement addRouterButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Remove router\"]")
	private WebElement removeRouterButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement removeRouterCancelButton;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Remove\"]")
	private WebElement RouterpopupRemoveButton;
	
	@FindBy(xpath = "//android.widget.CheckBox")
	private WebElement addRouterPopCheckBox;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Cancel\"]")
	private WebElement routerPopCancelButton;
	//*[@resource-id='settingDevice_toggle_switch']
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/DeviceSetting_LEDQuietMode\"]/android.view.ViewGroup")
	private WebElement quietLEDToggleEnable;
	
	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"com.szephyr:id/DeviceSetting_LEDQuietMode\"]/android.view.ViewGroup")
	private WebElement quietLEDToggleDisable;
	
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement deviceSettingsPageBackButton;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button3\"]")
	private WebElement shellallow;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button2\"]")
	private WebElement shelldeny;
	
	@FindBy(xpath = "//android.widget.Switch[@content-desc=\"com.szephyr:id/UserConfig_Switch4\"]")
	private WebElement infiniteToggle ;
	
	@FindBy(xpath = "(//android.widget.TextView[@text=\"\"])[1]")
	private WebElement hoursPlusButton ;
	
	@FindBy(xpath = "//*[@resource-id='UserConfig_Submit_ButtonText']")
	private WebElement infiniteSubmitButton ;
	
	@FindBy(xpath = "//*[@resource-id='DeviceSetting_DurationforON']")
	private WebElement durationForON;
	
	@FindBy(xpath = "//*[@resource-id='UserConfig_Switch3']")
	private WebElement pairingTimeQuietLEDEnable;
	
	@FindBy(xpath = "//*[@resource-id='menu_icon_removeDevice']")
	private WebElement removeDevice;
	
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
	private WebElement removeDevicePopupNoButton;
	
	//android.widget.TextView[@text=\"YES\"]
	@FindBy(xpath = "//android.widget.TextView[@text=\"YES\"]")
	private WebElement removeDevicePopupYesButton;
	
	
	@FindBy(xpath = "//*[@resource-id='Add_Devices_ButtonText']")
	private WebElement addDeviceButton;
	
	public DeviceMenuPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait=new WebDriverWait(driver, 30);
	}

	// Methods to be used as part of loginpage.
	
	public void clickDeviceSettingsButton() {
		clickbyXpath(deviceSettingsButton, " Device Settings Button  ");
	}

	public void clickResetDeviceButton() {
		clickbyXpath(resetDeviceButtom, "Reset Button clicked successfully ");
	}
	
	public void clickResetConfirmationYesButton() {	
		clickbyXpath(resetConfirmationYesButton, "Reset confirmation button clicked successfully");
	}
	public void clickszephyr_info_button() {	
		clickbyXpath(szephyr_info_button, " Szphyr_info_menubar");
	}
	
	

	public void clickLogoutButton() {
		clickbyXpath(logoutButton, " Logout button ");
	}
	
	public void clickLogoutButtonAfterReset() {
		clickbyXpath(logoutButtonAfterReset, " Logout button");
	}
	
	public void clickLogoutConfirmationButton() {
		clickbyXpath(logoutConfirmationButton, " Logout confirmation button ");
	}
	public void ClickSzephyrInfoButton() {	
		clickbyXpath(ClickSzephyrInfoButton, " Szephyr Info ");
	}
       //Szephyr Info page
	public void CheckSzephyrInfPageBrandName() {

		verifyTextContainsByXpath( CheckSzephyrInfPageBrandName, "testuserghggg", "Brand Name");
    }
	public void CheckSzephyrInfPageModelName() {

		verifyTextContainsByXpath( CheckSzephyrInfPageModelName, "G20", "Model Name");
    }
	public void CheckSzephyrInfPageCapacity() {

		verifyTextContainsByXpath( CheckSzephyrInfPageCapacity, "2", "Capacity");
    }
	public void CheckSzephyrInfPageRoomSize() {

		verifyTextContainsByXpath( CheckSzephyrInfPageRoomSize, "Medium", "Room Size");
    }

	//Engery Saving Elements	
	
	public void ToastForwithoutconnectivity() {

		verifyTextContainsByXpath( ToastForwithoutconnectivity, "Connect with device and try again", "esM");
    }
	
	public void checkSuccessfullyToast() {

		verifyTextContainsByXpath( SuccessfullyToast, "Energy saving mode updated successfully", "EnableToast");
    }
	
	public void CheckEsmButton() {	
		clickbyXpath(ClickESMButton, " Click energy Saving schedule ");
	}
	
	public void ClicktoggleButton() {	
		clickbyXpath(EnableToogleSwtich, " Click Enable toggle ");
	}
	
	public void ClickToggledisable() {	
		clickbyXpath(DisableToogleSwtich, " Click Disable toggle ");
	}
	
	public void CheckModeratefeature() {	
		clickbyXpath(Clickmoderatecooling, " Select Moderate ");
	}
	
	public void CheckProperfeature() {	
		clickbyXpath(ClickPropercooling, " Select Proper cooling ");
	}
	
	public void CheckLessfeature() {	
		clickbyXpath(ClickLesscooling, " Select Less cooling ");
	}
	
	public void ClickEsmOKButton() {	
		clickbyXpath(EsmOKbutton, " EnergySaving Button OK ");
	}
	
	public void ClickBackButtonESM() {	
		clickbyXpath(ESMPageBackButton, " Back button ESM page ");
	}
	
	@SuppressWarnings("deprecation")
	public void ClickaddrouterButton() {
//		expWaitforPairing(ClickaddrouterButton);
		driver.findElement(MobileBy.AndroidUIAutomator(
			    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Add Router\"));"))
			    .click();
//		clickbyXpath(ClickaddrouterButton, " add router button ");
	}
	public void checkcontentlowvoltage() {	
		verifyTextContainsByXpath(lowvoltageconfiguration, "Low voltage configuration" , "Device Settings");
	}
	
	public void clickDevicesettingsbackButton() {	
		clickbyXpath(DevicesettingsbackButton, " Device Setting Back Button ");
	}
	
	
	public void ClickDeviceSettingback() {	
		clickbyXpath(ClickDeviceSettingback, " Device setting back button ");
	}
	
	public void clickAddDeviceButton() {	
		clickbyXpath(clickAddDeviceButton, " Add Device ");
	}
	
	public void clickNewDevicePairingButton() {	
		clickbyXpath(clickNewDevicePairingButton, " Add New Device ");
	}
	
	public void clickFirstDeviceButton() {	
		clickbyXpath(clickFirstDeviceButton, " Add First Device ");
	}
	
	public void clickSecondDeviceButton() {	
		clickbyXpath(clickSecondDeviceButton, " Add Second Device ");
	}
	
	public void clickThirdDeviceButton() {	
		clickbyXpath(clickThirdDeviceButton, " Add Third Device ");
	}
	
	public void ClickCancelBluetooth() {	
		clickbyXpath(ClickCancelBluetooth, " Cancel Bluetooth Button ");
	}
	
	public void ClickOkButtonBLEpopUP() {
		expWaitforPairing(ClickOkButtonBLEpopUP);
		clickbyXpath(ClickOkButtonBLEpopUP, " Click OK  Bluetooth Button Popup ");
	}
	
	public void ClickCancelWifi() {
		expWaitforPairing(ClickCancelWifi);
		clickbyXpath(ClickCancelWifi, " Click Cancel Wifi Popup ");
	}
	public void clickRemoveRouterButton() {	
    	clickbyXpath(removeRouterButton, " Click Remove Router Button ");
	}
	
		public void clickRemoveRouterCancelButton() {	
			clickbyXpath(removeRouterCancelButton, " Click Remove Router Cancel Button ");
		}
		

        public void clickRemoveRouterRemoveButton() {	
	        clickbyXpath(RouterpopupRemoveButton, " Click Remove Router Remove Button ");
       }
	
        public void clickAddRouterCheckBox() {	
	        clickbyXpath(addRouterPopCheckBox, " Click Add Router Pop-up Check Box ");
        }
        
        public void clickRouterPopCancelButton() {	
	        clickbyXpath(routerPopCancelButton, " Click Add Router Pop-up Cancel Button ");
        }
        
        public void clickQuietLEDToggleForOn() {	
	        clickbyXpath(quietLEDToggleEnable, " Enable The Quiet LED Toggle ");
       }
        
        public void clickQuietLEDToggleForOff() {	
	        clickbyXpath(quietLEDToggleDisable, " Disable The Quiet LED Toggle ");
        }

		public void clickDeviceSettingsBackButton() throws InterruptedException {
			wait.until(ExpectedConditions.elementToBeClickable(deviceSettingsPageBackButton)).click();
//			clickbyXpath(deviceSettingsPageBackButton, " Click The Device Settings Page Back Button ");
        }
		
		
		public void clickInfiniteSubmitButton() {
			clickbyXpath(infiniteSubmitButton, " Click The Submit Button ");
			
		}
		
		public void clickDurationForONButton() {
			clickbyXpath(durationForON, " Click The ON Duration Button ");
			
		}

		public void clickInfinitePowerToggle() {
			clickbyXpath(infiniteToggle, " Disable The Infinite Toggle ");
			
		}

		public void clickHoursPlusButton() {
			clickbyXpath(hoursPlusButton, " Click The Hours Plus Button ");
			
		}
		
		public void clickPairingTimeQuietLEDEnable() {
			clickbyXpath(pairingTimeQuietLEDEnable, " Click The LED Quiet MOde Toggle ");
			
		}
		
		public void clickMenuBarRemoveDevice() {
			clickbyXpath(removeDevice, " Click The Remove Device Button ");
			
		}	
		
		public void clickRemoveDevicePopupNoButton() {
			clickbyXpath(removeDevicePopupNoButton, " Click The Remove Device Pop-up ON Button ");
			
		}	
		
		public void clickRemoveDevicePopupYesButton() {
			clickbyXpath(removeDevicePopupYesButton, " Click The Remove Device Pop-up YES Button ");
			
		}	
		
		public void shellAllowpopup() {	
			if (isElementDisplayedCheck(shellallow)) {
//				expWait(shellallow);
				clickbyXpath(shellallow, " allow shell ");
			}
			
		}
		public void shellDenypopup() {
			if (isElementDisplayedCheck(shelldeny)) {
				clickbyXpath(shelldeny, " deny shell ");
				
			} 
		}
		
		
//		============================
				public void enterWiFiPassword(String password) {
			entervaluebyXpath(enterPasswordField, " Wifi Password  ", password);
		}
		public void scrolldown() {
			TouchAction action = new TouchAction(driver);
			action
			    .press(PointOption.point(500, 400)) // Start point (X, Y) near the top
			    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))) // Duration of press
			    .moveTo(PointOption.point(500, 1600)) // End point (X, Y) near the bottom
			    .release() // Release the press
			    .perform();
			
		}
		
		public void removerouter() {

			driver.findElement(MobileBy.AndroidUIAutomator(
				    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Router Details\"));"));
//			if (isElementDisplayed(addRouterButton,"Add router button")) {
//				clickbyXpath(addRouterButton, "Add router button ");
//				enterWiFiPassword("12345678908");
//				clickAddRouterCheckBox();
//				clickbyXpath(submitBtn, " Enter Button  ");
//			}
			 if (isElementDisplayed(removeRouterButton,"Remove router  button")) {
				clickbyXpath(removeRouterButton, "Remove router button ");
				clickRemoveRouterCancelButton();
				clickbyXpath(removeRouterButton, "Remove router button ");
				clickRemoveRouterRemoveButton();
				
				clickDevicesettingsbackButton();

			}
			else {
				System.out.println("Add router or remove router button is not clickable");
			}
			
			
		}
		
		public void AddDevicePagedisplayed() {

			if (isElementDisplayed(addDeviceButton,"Add device button")) {
				System.out.println("Add device page displayed");
			}
		}
		
		
}
