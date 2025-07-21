package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import wrappers.MobileAppWrappers;
import java.util.Base64;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


	public class Reporter extends MobileAppWrappers {

	    private static ExtentTest test;
	    private static ExtentReports extent;
	    private static ExtentSparkReporter htmlReporter;
	    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
	    private static LocalDateTime now = LocalDateTime.now();
	    private static String time = now.format(formatter);
	    private static String reportPath = "./reports/Spark_" + time + ".html";

	    public static void reportStep(String desc, String tcstatus) {

	    	
	    	String screenshotBase64 = null;

	        try {
	            TakesScreenshot ts = (TakesScreenshot) driver;
	            File screenshot = ts.getScreenshotAs(OutputType.FILE);
	            byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
	            screenshotBase64 = Base64.getEncoder().encodeToString(fileContent);
	        } catch (WebDriverException | IOException e) {
	            e.printStackTrace();
	        }

	        // Write if it is successful or failure or information
	        if (tcstatus.toUpperCase().equals("PASS")) {
//	            test.pass(desc, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
	            test.pass(desc);
	        } else if (tcstatus.toUpperCase().equals("FAIL")) {
	            test.fail(desc, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
	            throw new RuntimeException("FAILED");
	        } else if (tcstatus.toUpperCase().equals("INFO")) {
	            test.log(Status.INFO, desc);
	        } else if (tcstatus.toUpperCase().equals("SKIP")) {
	            test.skip(desc, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
	        } else if (tcstatus.toUpperCase().equals("FAIL&RUN")) {
	            test.fail(desc, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
	        } else if (tcstatus.toUpperCase().equals("WARNING")) {
	            test.log(Status.WARNING, desc);
	        }
	    }

	    // Start the report and delete old reports and images
	    public static void startResult() {
	        // Delete old reports and images
	        File reportDir = new File("./reports");
	        if (reportDir.exists()) {
	            try {
	                FileUtils.cleanDirectory(reportDir); // Delete all old files in the reports folder
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        // Create the new report
	        htmlReporter = new ExtentSparkReporter(reportPath);
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	    }

	    public static void startTestCase() {
	        test = extent.createTest(testCaseName, testDescription);
	    }

	    public static void endResult() {
	        extent.flush();
	    }
	}


