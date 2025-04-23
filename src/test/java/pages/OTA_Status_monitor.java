package pages;

import java.time.Duration;

import org.bouncycastle.pqc.jcajce.provider.lms.LMSSignatureSpi.generic;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class OTA_Status_monitor extends GenericWrappers{

	private AndroidDriver driver;

	@FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup")
	private WebElement statusBarElement;

	// Constructor to initialize the driver and instantiate elements using

	public OTA_Status_monitor(AndroidDriver driver)
	{
		this.driver = driver;
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(driver, this);
	}

		// Methods to be used as part of szephyr info page.
		
		public void waitforstatusbar() 
		{
			expWaitforPairing(statusBarElement);
		
		}

		public void monitorStatusBar(WebElement statusBarElement)
		{
			int previousPercentage = -1; // Initialize to an invalid percentage value
			int unchangedCount = 0;
			int maxUnchangedLimit = 5; // The number of checks with no increment before flagging an issue

			while (true) {
				try {
					// Fetch the current percentage from the status bar
					String percentageText = statusBarElement.getText(); // Assuming it returns something like "45%"
					int currentPercentage = Integer.parseInt(percentageText.replace("%", ""));

					// Check if the percentage has changed
					if (currentPercentage > previousPercentage) {
						System.out.println("Progress: " + currentPercentage + "%");
						previousPercentage = currentPercentage;
						unchangedCount = 0; // Reset unchanged count when progress is made
					} else {
						unchangedCount++;
					}

					// If the percentage hasn't changed for several checks, flag the issue
					if (unchangedCount >= maxUnchangedLimit) {
						System.out.println("Warning: Status bar progress has stopped incrementing at " + currentPercentage + "%!");
						break; // You can choose to break the loop or handle the issue accordingly
					}

					// Pause before checking again
					Thread.sleep(2000); // Wait 2 seconds before checking again

				} catch (Exception e) {
					System.out.println("Error while monitoring status bar: " + e.getMessage());
					break;
				}
			}
		}

	}
