package utils;
import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GetAppLog {
	
	 private final Path logFile = Path.of("app_log.txt");

	 private String getDesktopPath() {
	        String homeDir = System.getProperty("user.home");
	        return homeDir + File.separator + "Desktop";
	    }

	    // Get current formatted time for adb command
	    private String getCurrentFormattedTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
	        return dateFormat.format(new Date());
	    }

	    // Check if adb devices are present
	    private List<String> checkADBisPresent() throws IOException, InterruptedException {
	        List<String> devices = new ArrayList<>();
	        ProcessBuilder pb = new ProcessBuilder("adb", "devices");
	        Process process = pb.start();

	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.contains("device") && !line.contains("List")) {
	                String deviceID = line.split("\t")[0];
	                devices.add(deviceID);
	            }
	        }

	        process.waitFor(5, TimeUnit.SECONDS);
	        return devices;
	    }

	    // Run adb logcat command to capture React Native logs (asynchronous)
	    private void runAndroidLogAsync(String logFile2) {
	    	System.out.println(logFile2);
	        Thread logThread = new Thread(() -> {
	            try {
	                String currentTime = getCurrentFormattedTime();
	                String adbCmd = "adb logcat *:S ReactNative:V ReactNativeJS:V -T \"" + currentTime + "\"";

	                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", adbCmd);
	                Process adbProcess = pb.start();
	                
	                // Create the log file or clear if it already exists
	                new FileWriter(logFile2).close();

	                // Write the output to the file
	                BufferedReader reader = new BufferedReader(new InputStreamReader(adbProcess.getInputStream()));
	                String line;
	                try (FileWriter fileWriter = new FileWriter(logFile2, true)) {
	                    while ((line = reader.readLine()) != null) {
	                        fileWriter.write(line + "\n");
//	                        System.out.println(line);
	                    }
	                }

	                // Handle process exit
	                int exitCode = adbProcess.waitFor();
	                System.out.println("adb logcat process exited with code " + exitCode);
	            } catch (IOException | InterruptedException e) {
	                e.printStackTrace();
	            }
	        });

	        logThread.start(); // Start the logcat thread
	    }

	    // Public method to initiate log capturing (exposed for external class to use)
	    public void startLogProcess() throws IOException, InterruptedException {
	        //String logFile = getDesktopPath() + File.separator + "React-Log-" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
	        
	        // Check ADB devices
	        List<String> adbDevices = checkADBisPresent();
	        if (adbDevices.isEmpty()) {
	            System.out.println("No adb device is connected");
	            try (FileWriter fileWriter = new FileWriter(logFile.toString(), true)) {
	                fileWriter.write("No adb device is connected\n");
	            }
	            TimeUnit.SECONDS.sleep(5);
	            return;
	        }
	        
	        // Run adb logcat asynchronously in a separate thread
	        runAndroidLogAsync(logFile.toString());
	    }
	}