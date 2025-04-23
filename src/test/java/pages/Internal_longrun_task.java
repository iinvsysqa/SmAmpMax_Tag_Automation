package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class Internal_longrun_task extends GenericWrappers{

	private AndroidDriver driver;

	@FindBy(xpath = "//*[@resource-id='Home_VOLT_Box']")
	private WebElement voltBoxValue;
	@FindBy(xpath = "//*[@resource-id='Home_WATT_Box']")
	private WebElement powerValue;
	@FindBy(xpath = "//*[@resource-id='Home_AMP_Box']")
	private WebElement currentValue;
	
	@FindBy(xpath = "")
	private WebElement  onOffButton;
	@FindBy(xpath = "//android.widget.TextView[@text=\"sZephyr device is offline\"]")
	private WebElement alertTitle;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"OK\"]")
	private WebElement alertok;

	public Internal_longrun_task(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait=new WebDriverWait(driver, 30);
	}


	public boolean checkVoltValue() {

		//		driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"com.szephyr:id/Home_VOLT_Box\"]\""));
		WebElement element = voltBoxValue.findElement(By.className("android.widget.TextView"));
		

		boolean bReturn = false;
		try {
			double val=100.0;
			String stringvalue="100.0V";

			while (val>0) {
				expWait(element);
				String text = element.getText();
				String numericPart = text.replaceAll("[^0-9.]", "");
				
				double displayedValue = Double.parseDouble(numericPart);
				
				if (displayedValue<=val) {
					verifyTextContainsByXpath(element,stringvalue, "lowvolt");
					bReturn = true;
					break;
				}
				System.out.println("volt value displayed :  "+text);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("someone closed the app ");
		}
		return bReturn;
	} 
	
	public void alertcheck() {

		alertTitle.isDisplayed();
		alertok.click();
	}

}
