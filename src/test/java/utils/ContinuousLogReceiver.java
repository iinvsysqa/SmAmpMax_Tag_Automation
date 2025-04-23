package utils;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.testng.annotations.Test;

public class ContinuousLogReceiver implements Runnable  {
    private volatile boolean running = true; // Control the running state
    private volatile boolean loggingEnabled = true; // Control the logging state
    private final Path logFilePath = Path.of("serial_log.txt"); // File path for logging

    public void logrReceiver() {
        // List available ports
        String[] portNames = SerialPortList.getPortNames();

        if (portNames.length == 0) {
            System.out.println("No serial ports found.");
            return;
        }

        // Display available ports
        System.out.println("Available Serial Ports:");
        for (String portName : portNames) {
            System.out.println(portName);
        }

        // Use the first available port
        String selectedPort = portNames[1];

        // Create a SerialPort instance
        SerialPort serialPort = new SerialPort(selectedPort);

        try {
            // Open the serial port
            serialPort.openPort();
            System.out.println("Opened serial port: " + selectedPort);

            // Set serial port parameters
            serialPort.setParams(115200, 8, 1, 0); // baud rate, data bits, stop bits, parity

            // Start a separate thread for reading
            Thread readerThread = new Thread(() -> {
                while (running) {
                    try {
                        // Read available data
                        String response = serialPort.readString();
                        if (response != null) {
                            String trimmedResponse = response.trim();
                            System.out.println("Received: " + trimmedResponse);
                            // Save to file if logging is enabled
                            if (loggingEnabled) {
                                saveToFile(trimmedResponse);
                            }
                        }
                        // Add a small delay to prevent overwhelming the CPU
                        Thread.sleep(100);
                    } catch (SerialPortException | InterruptedException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            });
            readerThread.start();

            // Wait for the reader thread to finish
            readerThread.join();

        } catch (SerialPortException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted: " + e.getMessage());
        } finally {
            // Close the serial port
            try {
                serialPort.closePort();
                System.out.println("Serial port closed.");
            } catch (SerialPortException ex) {
                System.out.println("Error closing port: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        // When the thread starts, it will execute this method
        logrReceiver();
    }
    // Method to stop logging
    public void stopLogging() {
        loggingEnabled = false;
        System.out.println("Logging stopped.");
    }

    // Method to stop the receiver
    public void stopReceiver() {
        running = false;
    }

    private void saveToFile(String data) {
        try {
            Files.writeString(logFilePath, data + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    @Test
    public void runner() {
        ContinuousLogReceiver receiver = new ContinuousLogReceiver();
        System.out.println("Device Log is started.....");
        //Start the logging receiver in a new thread
        Thread receiverThread = new Thread(receiver::logrReceiver);
        receiverThread.start();

        // Example: Stop logging after 10 seconds (for demonstration)
        try {
            Thread.sleep(10000); // Wait for 10 seconds
            receiver.stopLogging(); // Call the method to stop logging
            receiver.stopReceiver(); // Stop the receiver thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        // Wait for the receiver thread to finish
        try {
            receiverThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for receiver thread: " + e.getMessage());
        }

        System.out.println("Program terminated.");
    }
}
