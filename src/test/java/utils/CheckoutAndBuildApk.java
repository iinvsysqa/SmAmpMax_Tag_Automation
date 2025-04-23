package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

public class CheckoutAndBuildApk {

	public static void runPythonScript(String pythonFilePath) {

		// Build the command to execute the Python script
		String[] command = { "python", pythonFilePath };
		System.out.println(" Build Process Started: Please wait for Build to Complete.......   ");

		ProcessBuilder processBuilder = new ProcessBuilder(command);

		try {
			// Start the process to execute the Python script
			Process process = processBuilder.start();
			// Get the standard output from the script and print it
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line); // Print the output of the Python script
				}

			}

			// Get the error output (if any) and print it

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.err.println(line); // Print error output (if any)
				}

			}

			// Wait for the Python process to finish and check the exit code

			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Python script executed successfully.");
			} else {
				System.err.println("Python script execution failed with exit code: " + exitCode);
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error occurred while executing the Python script: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static boolean checkoutFromSVN(String svnRepoURL, String svnUser, String svnPassword, String localPath) {
		boolean success = false;
		System.out.println("SVN Checkout is in Progress.......");

		try {
			// Initialize SVN repository URL and authentication manager
			SVNURL repositoryURL = SVNURL.parseURIEncoded(svnRepoURL);
			SVNClientManager clientManager = SVNClientManager.newInstance();
			clientManager.setAuthenticationManager(new BasicAuthenticationManager(svnUser, svnPassword));

			// Checkout the repository or update if already present
			File workingCopy = new File(localPath);
			if (!workingCopy.exists()) {
				workingCopy.mkdirs();
			}

			// Checkout using the updated SVNKit 1.9+ API

			SVNUpdateClient updateClient = clientManager.getUpdateClient();
			SVNRevision revision = SVNRevision.HEAD; // Checkout the latest revision
			long revisionNumber = updateClient.doCheckout(repositoryURL, workingCopy, revision, revision, true);

			System.out.println("Checked out revision: " + revisionNumber);
			success = true;
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return success;

	}

	public void buildAPK() {

		String svnRepoURL = "http://192.168.10.2:82/svn/iinvsys_sw/staging/Apps/szephyr_dev_team_staging";
		String svnRepoURLCommonFile = "http://192.168.10.2:82/svn/iinvsys_sw/staging/Application_Configuration_Modules";
		String svnUser = "kirupakaranp"; // SVN username
		String svnPassword = "K!rk&4dA"; // SVN password

		String userHome = System.getProperty("user.home");
		// Construct the path for the directory on the Desktop
		Path path = Paths.get(userHome, "Desktop", "apk_build");

		// Assign the created path to the localProjectPath variable
		String localProjectPath = path.toString();

		System.out.println("Local project path is: " + localProjectPath);

		String localProjectPathCommon = path.resolve("Application_Configuration_Modules").toString();

		// String localProjectPathCommon =
		// localProjectPath+"Application_Configuration_Modules";

		System.out.println(localProjectPathCommon);

		// Checkout or update files from SVN
		if (checkoutFromSVN(svnRepoURL, svnUser, svnPassword, localProjectPath)
				&& checkoutFromSVN(svnRepoURLCommonFile, svnUser, svnPassword, localProjectPathCommon)) {
			System.out.println("Successfully checked out files from SVN at: " + LocalDateTime.now());
		}

		// Replace with the path to your Python file

		// Construct the path for the directory on the Desktop
		Path pythonFilePath = Paths.get(userHome, "Desktop", "apk_build\\android\\script.py");
		String pythonFile = pythonFilePath.toString();
		runPythonScript(pythonFile);

	}

}