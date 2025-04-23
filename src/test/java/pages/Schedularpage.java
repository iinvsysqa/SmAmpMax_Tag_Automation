package pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import wrappers.GenericWrappers;

public class Schedularpage extends GenericWrappers {
	public static AndroidDriver driver;

//	public AndroidElement element;

	public Schedularpage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.js = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, 30);
	}

	JavascriptExecutor js = (JavascriptExecutor) driver;

	@FindBy(xpath = "//*[@resource-id='Home_Navigation_Button']")
	private WebElement Schedulebutton;

	@FindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]")
	private WebElement Scehduletitle;
	
	@FindBy(xpath = "//*[@resource-id='SCH_ADD_PLUS_ICON']")
	private WebElement plusIcon;
	
	@FindBy(xpath = "//*[@resource-id='Header']")
	private WebElement yourScheduleheader;
	
	@FindBy(xpath = "//*[@resource-id='Scheduler_OtherUsersSchedules']")
	private WebElement otherScheduleheader;
	
	@FindBy(xpath = "//*[@resource-id='Scheduler_YouDoNotHaveAnySchedules']")
	private WebElement schedulePlaceholder;

	@FindBy(xpath = "//*[@resource-id='Edit_Schedule_Save_ButtonText']")
	private WebElement savebtn;
	
	@FindBy(xpath = "//*[@resource-id='Edit_Schedule_Cancel_Button']")
	private WebElement cancelbtn;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Delete']")
	private WebElement deleteBtn;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]")
	private WebElement locationpermissionpopup;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
	private WebElement devicepermission;
	
	@FindBy(xpath = "//*[@resource-id='Edit_Schedule_HeatingDuration']")
	private WebElement durationtext;
	
	@FindBy(xpath = "//*[@resource-id='Scheduler_Switch']")
	private WebElement disableschedule;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"sZephyr and AC turned ON\"]")
	private WebElement Acturnondesc;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Please ensure sZephyr is switched ON prior to operating your AC remote\"]")
	private WebElement acturnoffdesc;
	
	@FindBy(xpath = "//*[@resource-id='Edit_Schedule_Minutes1']")
	private WebElement minute1;
	
	@FindBy(xpath = "//*[@resource-id='Edit_Schedule_Duration_Hours_Minutes']")
	private WebElement duration;

	@FindBy(xpath = "//android.widget.Toast[@text=\"Schedule has been deleted\"]")
	private WebElement scheduleDeletedToast;

//	@FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup")
//	private List allSchedules;
	@FindBy(xpath = "//*[@resource-id='Scheduler_Time']")
	private WebElement createdSchedule;
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement backButton;

	@FindBy(xpath = "//android.widget.TextView[@text=\"testuser008_1\"]")
	private WebElement userName;

	public String scheduleDeletedtoast = loadProp("thisScheduleHasBeenDeleted");

//	com.iinvsys.szephyr:id/ScrollPicker_Hours
	String x1 = "//*[@resource-id='ScrollPicker_Hours']";
	String x2 = "//*[@resource-id='ScrollPicker_Minutes']";
	String x3 = "//*[@resource-id='ScrollPicker_AM_PM']";

	String resourseId1 = "ScrollPicker_Hours";
	String resourseId2 = "ScrollPicker_Minutes";
	String resourseId3 = "ScrollPicker_AM_PM";



	public void createSchedules(int timetostart, int intervals, int gapBetweenNextSchedule) {
		// Get the current time and calculate the start time for the first schedule
		LocalTime currentTime = LocalTime.now();
		LocalTime timet = currentTime.plusMinutes(timetostart);

		// Generate schedule times based on intervals and gap
		List<LocalTime> scheduleTimes = generateSchedule(timet, intervals, gapBetweenNextSchedule);

		// Loop through each time and create the schedule
		for (LocalTime time : scheduleTimes) {
			// Split time into hour, minute, and AM/PM
			int hour = time.getHour() % 12;
			if (hour == 0) {
				hour = 12; // Convert 0 hour to 12 for 12-hour format
			}

			// Format the minute as two digits
			int minute = time.getMinute();
			String formattedMinute = String.format("%02d", minute);
			String amPm = time.getHour() >= 12 ? "PM" : "AM";

			System.out.println("Creating schedule for: " + hour + ":" + formattedMinute + " " + amPm);

			// Navigate to the screen to create a schedule
			clickbyXpath(plusIcon, "plusbutton");

			// Scroll to the desired time using the method that scrolls until the element is
			// visible
			selectTimeUsingBounds(hour, minute, amPm);

			clickonDuration();
			// Save the schedule
			saveSchedule();

			System.out.println("Schedule created for: " + hour + ":" + formattedMinute + " " + amPm);
		}
	}

	private List<LocalTime> generateSchedule(LocalTime startTime, int intervals, int gap) {
		List<LocalTime> schedule = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			schedule.add(startTime.plusMinutes(i * gap));
		}
		return schedule;
	}

	private void selectTimeUsingBounds(int hour, int minute, String amPm) {

		// Create an ExecutorService with 3 threads for the three scroll tasks
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		// Convert Runnable tasks to Callable<Void> to make it compatible with invokeAll
		Callable<Void> scrollHoursTask = () -> {
			scrollColumnUntilValueAtIndex0Hour(x1, resourseId1, String.valueOf(hour));
			return null;
		};

		Callable<Void> scrollMinutesTask = () -> {
			scrollColumnUntilValueAtIndex0Min(x2, resourseId2, String.valueOf(minute));
			return null;
		};

		Callable<Void> scrollAmPmTask = () -> {
			scrollColumnUntilValueAtIndex1(x3, resourseId3, String.valueOf(amPm));
			return null;
		};

		// Create a list of Callable tasks
		List<Callable<Void>> tasks = Arrays.asList(scrollHoursTask, scrollMinutesTask, scrollAmPmTask);

		try {
			// Run all scrolling tasks in parallel
			executorService.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
	}

	private void saveSchedule() {
		// Click the save button after setting the time
		clickbyXpath(savebtn, "saveschedule");

		// Validate if the schedule is saved successfully
		verifyTextContainsByXpath(yourScheduleheader, "Schedule", "Schedule page header");

	}

	private void scrollColumnUntilValueAtIndex0Hour(String xpath, String resourceId, String expectedValue) {

		// Find the container element using resourceId
		WebElement columnElement = driver.findElement(By.xpath(xpath));

		boolean valueAtIndex1 = false;

		while (!valueAtIndex1) {
			// Get the list of elements in the column (assuming TextView contains the
			// displayed values)
			List<WebElement> elements = columnElement.findElements(By.className("android.widget.TextView"));

			// Check if the expected value is at index 1 (typically the visible element)
			int centerIndex = elements.size() / 2;
			WebElement elAtCenter = elements.get(centerIndex);
			String currentValue = elAtCenter.getText();

			WebElement firstIndex = elements.get(0);
			String elFirst = firstIndex.getText();
			System.out.println("First index 0 value" + elFirst);

			if (extractintvalue(currentValue) == extractintvalue(expectedValue)) {
				valueAtIndex1 = true;
				System.out.println("Element with value " + expectedValue + " is now at centre index ");
			} else {
				// Compare current value with expected value to decide scroll direction
				int compareResult = comparePickerValues(resourceId, currentValue, expectedValue);

				if (compareResult < 0) {
					// Scroll forward (expected value is greater)
					System.out.println("Scrolling forward for: " + resourceId);
					swipeElement(xpath, true);
				} else {
					// Scroll backward (expected value is smaller)
					System.out.println("Scrolling backward for: " + resourceId);
					swipeElement(xpath, false);
				}
			}
			
			if (extractintvalue(expectedValue) == 1) {
				valueAtIndex1 = true;
				System.out.println("Element with value " + expectedValue + " is now at index 1.");
			}
		
		}

	}

	private void scrollColumnUntilValueAtIndex0Min(String xpath, String resourceId, String expectedValue) {

		// Find the container element using resourceId
		WebElement columnElement = driver.findElement(By.xpath(xpath));

		boolean valueAtIndex1 = false;

		while (!valueAtIndex1) {
			// Get the list of elements in the column (assuming TextView contains the
			// displayed values)
			List<WebElement> elements = columnElement.findElements(By.className("android.widget.TextView"));

			int centerIndex = elements.size() / 2;
			WebElement elAtCenter = elements.get(centerIndex);
			String currentValue = elAtCenter.getText();

			WebElement firstIndex = elements.get(0);
			String elFirst = firstIndex.getText();

			if (extractintvalue(currentValue) == extractintvalue(expectedValue)) {

				valueAtIndex1 = true;
				System.out.println("Element with value " + expectedValue + " is now at correct index .");
			} else {
				// Compare current value with expected value to decide scroll direction
				int compareResult = comparePickerValues(resourceId, currentValue, expectedValue);

				if (compareResult < 0) {
					// Scroll forward (expected value is greater)
					System.out.println("Scrolling forward for: " + resourceId);
					swipeElement(xpath, true);
				} else {
					// Scroll backward (expected value is smaller)
					System.out.println("Scrolling backward for: " + resourceId);
					swipeElement(xpath, false);
				}
			}
			if (extractintvalue(expectedValue) == 00) {
				valueAtIndex1 = true;
				System.out.println("Element with value " + expectedValue + " is now at index 1.");
			}
		}

	}

	private void scrollColumnUntilValueAtIndex1(String xpath, String resourceId, String expectedValue) {
		// Find the container element using resourceId
		WebElement columnElement = driver.findElement(By.xpath(xpath));

		boolean valueAtIndex1 = false;

		while (!valueAtIndex1) {
			// Get the list of elements in the column (assuming TextView contains the
			// displayed values)
			List<WebElement> elements = columnElement.findElements(By.className("android.widget.TextView"));

			 int centerIndex = elements.size();
			 System.out.println(centerIndex);
	            WebElement elAtCenter = elements.get(centerIndex);
	            String currentValue = elAtCenter.getText();
	            System.out.println(currentValue);

			if (currentValue.equals(expectedValue)) {
				valueAtIndex1 = true;
				System.out.println("Element with value " + expectedValue + " is now at index 1.");
			} else {
				// Compare current value with expected value to decide scroll direction
				int compareResult = comparePickerValues(resourceId, currentValue, expectedValue);

				if (compareResult < 0) {
					// Scroll forward (expected value is greater)
					System.out.println("Scrolling forward for: " + resourceId);
					swipeElement(xpath, true);
				} else {
					// Scroll backward (expected value is smaller)
					System.out.println("Scrolling backward for: " + resourceId);
					swipeElement(xpath, false);
				}
			}
		}
	}

	private int comparePickerValues(String resourceId, String currentValue, String expectedValue) {
		if (resourceId.equals(resourseId1)) { // Hour comparison
			int currentHour = Integer.parseInt(currentValue);
			int expectedHour = Integer.parseInt(expectedValue);
			return Integer.compare(currentHour, expectedHour);
		} else if (resourceId.equals(resourseId2)) { // Minute comparison
			int currentMinute = Integer.parseInt(currentValue);
			int expectedMinute = Integer.parseInt(expectedValue);
			return Integer.compare(currentMinute, expectedMinute);
		} else if (resourceId.equals(resourseId3)) { // AM/PM comparison
			// Handle AM/PM comparison manually
			return compareAmPm(currentValue, expectedValue);
		} else {
			throw new IllegalArgumentException("Unknown resource ID: " + resourceId);
		}
	}

	private int compareAmPm(String currentValue, String expectedValue) {
		if (currentValue.equals("AM") && expectedValue.equals("PM")) {
			return -1; // AM is less than PM
		} else if (currentValue.equals("PM") && expectedValue.equals("AM")) {
			return 1; // PM is greater than AM
		} else {
			return 0; // Both are the same
		}
	}

	public void disableschedule(int min) throws Exception, IOException, InterruptedException {

		clickbyXpath(disableschedule, "disable schedule");
		Thread.sleep(min * 60 * 1000);
		driver.navigate().back();
//		verifyTextContainsByXpath(acturnoffdesc,
//				"Please ensure sZephyr is switched ON prior to operating your AC remote", "AC truned OFF description");

	}



	public void clickSchedulebtn() {

		clickbyXpath(Schedulebutton, "schedulebutton");
	}

	public void clickonDuration() {

		clickbyXpath(minute1, "1 min on duration minutes ");

	}

	public void clickondeletebutton() {

		clickbyXpath(deleteBtn, "Delete schedule button ");

	}

	public void checktoast() {
		verifyTextContainsByXpath(scheduleDeletedToast, scheduleDeletedtoast, "schedule Deleted Toast");
	}

	public void clickplusbtn() {
		clickbyXpath(plusIcon, "plusicon");
	}

	public void deleteschedule() {

		try {

			while (createdSchedule.isDisplayed()) {

				clickbyXpath(createdSchedule, "created schedules");
				clickbyXpath(deleteBtn, "deleteButton");
				checktoast();

			}

		} catch (Exception e) {
			System.out.println("All created schedules are deleted");
		}

	}

	public void backToHomepage() {

		clickbyXpath(backButton, "back button");
		verifyTextContainsByXpath(userName, loadProp("USERNAMEINAPP"), "DeviceName");

	}

	public void swipeElement(String columnXPath, boolean forward) {
		WebElement columnElement = driver.findElement(By.xpath(columnXPath));

		// Get the location and size of the column
		int columnCenterX = columnElement.getLocation().getX() + (columnElement.getSize().getWidth() / 2);
		int startY = forward ? columnElement.getLocation().getY() + (int) (columnElement.getSize().getHeight() * 0.6) // Swipe
																														// up
																														// if
																														// forward
				: columnElement.getLocation().getY() + (int) (columnElement.getSize().getHeight() * 0.2); // Swipe down
																											// if
																											// backward
		int endY = forward ? columnElement.getLocation().getY() + (int) (columnElement.getSize().getHeight() * 0.2) // End
																													// at
																													// top
																													// for
																													// forward
																													// swipe
				: columnElement.getLocation().getY() + (int) (columnElement.getSize().getHeight() * 0.6); // End at
																											// bottom
																											// for
																											// backward
																											// swipe

		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1)
				.addAction(
						finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), columnCenterX, startY))
				.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())).addAction(finger
						.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), columnCenterX, endY))
				.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Arrays.asList(swipe));
	}
	
	public void checkOffState() {

		verifyTextContainsByXpath(acturnoffdesc, "Please ensure sZephyr is switched ON prior to operating your AC remote", "OFF state");
	}

}
