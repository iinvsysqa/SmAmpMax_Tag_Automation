package utils;

import java.io.*;

public class RunRelayFromPython {

    public static void powerOndeviceViaRelay(String relayCommand) {
        try {
            // Get Eclipse project root directory
            String projectRoot = System.getProperty("user.dir");
            String scriptName = "relay_control.py";
            String pythonScriptPath = projectRoot + File.separator + scriptName;

            // Your desired command: either "on" or "off"
            //String relayCommand = "on";  // Change this to "off" as needed

            // Pass the argument to Python
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath, relayCommand);
            pb.redirectErrorStream(true);

            // Start the process
            Process process = pb.start();

            // Read and print Python script output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
