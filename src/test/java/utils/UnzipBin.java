package utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException; // For handling IOExceptions in streams
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// StandardCopyOption is not explicitly used in the final move, but good to be aware of.
// import java.nio.file.StandardCopyOption; 
import java.util.Comparator; // For sorting paths for deletion
import java.util.Optional;
import java.util.stream.Stream; // For using Files.walk and Files.list
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipBin {

    /**
     * Main method to find a zip file in a predefined source directory,
     * extract it, find a 'bin' folder within the extracted contents,
     * and move this 'bin' folder to a predefined destination directory.
     * If a 'bin' folder already exists at the destination, it will be deleted first.
     */
    public static void unzip() {
        // Define source and destination directories using platform-independent Path objects
        Path sourceDirectoryPath = Paths.get(System.getProperty("user.home"), "Desktop", "APK");
        Path destDirectoryPath = Paths.get(System.getProperty("user.home"), "Desktop", "Flash script");

        String sourceDirectory = sourceDirectoryPath.toString();
        String destDirectory = destDirectoryPath.toString(); // Base directory for 'bin' and temp extraction

        try {
            // Step 1: Find the first zip file in the source directory
            Optional<Path> zipFilePathOpt = findFirstZipFile(sourceDirectory);

            if (zipFilePathOpt.isPresent()) {
                Path zipFilePath = zipFilePathOpt.get();
                System.out.println("Found zip file: " + zipFilePath);

                // Step 2: Create a temporary directory for extraction to avoid conflicts
                Path tempExtractDir = Paths.get(destDirectory, "temp_extracted_" + System.currentTimeMillis());
                Files.createDirectories(tempExtractDir); // Ensure the temp directory is created

                // Step 3: Unzip the file into the temporary directory
                System.out.println("Extracting to temporary directory: " + tempExtractDir);
                unzipFile(zipFilePath.toString(), tempExtractDir.toString());
                System.out.println("Extraction complete.");

                // Step 4: Find the 'bin' folder within the extracted contents
                Path binFolderPathInTemp = findBinFolder(tempExtractDir.toString());

                if (binFolderPathInTemp != null) {
                    System.out.println("Found 'bin' folder in temporary location: " + binFolderPathInTemp);
                    Path destinationBinPath = Paths.get(destDirectory, "bin"); // Target path for the 'bin' folder

                    // Step 5: Check if the destination 'bin' folder already exists. If so, delete it.
                    if (Files.exists(destinationBinPath)) {
                        System.out.println("Destination 'bin' folder already exists. Deleting it first: " + destinationBinPath);
                        deleteDirectory(destinationBinPath); // This will throw IOException on failure
                        System.out.println("Successfully deleted existing 'bin' folder at: " + destinationBinPath);
                    }

                    // Step 6: Move the new 'bin' folder from temp to the final destination
                    Files.move(binFolderPathInTemp, destinationBinPath);
                    System.out.println("Successfully moved new 'bin' folder to: " + destinationBinPath);
                } else {
                    System.err.println("No 'bin' folder found in the extracted archive at: " + tempExtractDir);
                }

                // Step 7: Clean up by deleting the temporary extraction directory
                System.out.println("Cleaning up temporary extraction directory: " + tempExtractDir);
                deleteDirectory(tempExtractDir);
                System.out.println("Cleaned up temporary files.");

            } else {
                System.err.println("No zip file found in directory: " + sourceDirectory);
            }
        } catch (IOException e) {
            System.err.println("An error occurred during the unzip process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds the first file with a .zip extension in the given directory.
     * @param directory The directory to search in.
     * @return An Optional<Path> containing the path to the zip file if found, otherwise empty.
     * @throws IOException If an I/O error occurs when listing directory contents.
     */
    private static Optional<Path> findFirstZipFile(String directory) throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            return paths
                    .filter(path -> path.toString().toLowerCase().endsWith(".zip") && Files.isRegularFile(path))
                    .findFirst();
        }
    }

    /**
     * Unzips a given zip file to a specified destination directory.
     * @param zipFilePath Path to the .zip file.
     * @param destDirectory Path to the directory where contents will be extracted.
     * @throws IOException If an I/O error occurs during unzipping.
     */
    private static void unzipFile(String zipFilePath, String destDirectory) throws IOException {
        Path destDirPath = Paths.get(destDirectory);
        Files.createDirectories(destDirPath); // Ensure base destination directory exists

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                Path currentEntryPath = destDirPath.resolve(entry.getName());

                // Basic Zip Slip vulnerability check: ensure the entry path is within the destination directory
                if (!currentEntryPath.normalize().startsWith(destDirPath.normalize())) {
                    throw new IOException("Bad zip entry (Zip Slip attempt): " + entry.getName());
                }

                if (!entry.isDirectory()) {
                    extractSingleFile(zipIn, currentEntryPath);
                } else {
                    Files.createDirectories(currentEntryPath);
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    /**
     * Extracts a single file from the ZipInputStream to the given filePath.
     * @param zipIn The ZipInputStream to read from.
     * @param filePath The Path to write the extracted file to.
     * @throws IOException If an I/O error occurs.
     */
    private static void extractSingleFile(ZipInputStream zipIn, Path filePath) throws IOException {
        // Ensure parent directories for the file exist
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
            byte[] bytesIn = new byte[8192]; // Using a common buffer size (e.g., 8KB)
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    /**
     * Finds a directory named 'bin' (case-insensitive) within the given directory.
     * It searches the entire directory tree.
     * @param directory The directory to search within.
     * @return Path to the 'bin' folder if found, otherwise null.
     * @throws IOException If an I/O error occurs during walking the directory.
     */
    private static Path findBinFolder(String directory) throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) { // Walk entire tree
            return walk
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase("bin") && Files.isDirectory(path))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Recursively deletes a directory and its contents.
     * If any deletion fails, it throws an IOException.
     * @param path The Path to the directory to be deleted.
     * @throws IOException If the directory or any of its contents cannot be deleted.
     */
    private static void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder()) // Delete contents before parent directories
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            // Wrap in UncheckedIOException to allow throwing from lambda,
                            // then catch and rethrow the original IOException.
                            throw new UncheckedIOException(new IOException("Failed to delete: " + p, e));
                        }
                    });
            } catch (UncheckedIOException e) {
                // Unwrap and rethrow the original IOException
                if (e.getCause() instanceof IOException) {
                    throw (IOException) e.getCause();
                }
                throw e; // Should not happen if we always wrap an IOException
            }
        }
    }

    
}