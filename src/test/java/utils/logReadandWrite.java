package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import jssc.SerialPort;
import jssc.SerialPortException;

//import jssc.SerialPort;
//import jssc.SerialPortException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//
//public class logReadandWrite {
//    private SerialPort serialPort;
//    private volatile boolean isWriting = false;
//    private volatile boolean running = true;  // Flag to control the reading loop
//    private final Path logFilePath = Path.of("serial_log.txt");
//
//    public logReadandWrite(String portName) {
//        serialPort = new SerialPort(portName);
//    }
//
//    public synchronized void openPort() {
//        try {
//            if (!serialPort.isOpened()) {
//                serialPort.openPort();
//                serialPort.setParams(115200, 8, 1, 0);
//                running = true;  // Reset running flag when port is opened
//                System.out.println("Port opened successfully.");
//            } else {
//                System.out.println("Port is already open.");
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in opening port: " + ex.getMessage());
//        }
//    }
//
//    public synchronized void write(String comment) {
//        try {
//            if (serialPort.isOpened()) {
//                isWriting = true;  // Set writing flag
//                serialPort.writeBytes(comment.getBytes());
//                System.out.println("Comment sent: " + comment);
//            } else {
//                System.out.println("Error: Cannot write, port is not open.");
//                System.out.println(serialPort.isOpened());
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in writing to port: " + ex.getMessage());
//        } finally {
//            isWriting = false;  // Reset writing flag
//        }
//    }
//
//    public void read() {
//        new Thread(() -> {
//            while (running) {  // Run only when the running flag is true
//                if (!isWriting) {  // Only read if not writing
//                    try {
//                        if (serialPort.isOpened()) {
//                            String received = serialPort.readString();
//                            if (received != null) {
//                                String trimmedResponse = received.trim();
//                                saveToFile(trimmedResponse);
////                                System.out.println("Received: " + trimmedResponse);
//                            }
//                        } else {
//                            System.out.println("Error: Cannot read, port is not open.");
//                            break;  // Exit the loop if port is not open
//                        }
//                    } catch (SerialPortException ex) {
//                        System.out.println("Error in reading from port: " + ex.getMessage());
//                    }
//                }
//            }
//        }).start();
//    }
//
//    public synchronized void closePort() {
//        try {
//            if (serialPort.isOpened()) {
//                running = false;  // Stop the reading loop
//                serialPort.closePort();
//                System.out.println("Port closed successfully.");
//            } else {
//                System.out.println("Port is already closed.");
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in closing port: " + ex.getMessage());
//        }
//    }
//
//    private void saveToFile(String data) {
//        try {
//            Files.writeString(logFilePath, data + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }
//}

//
//import jssc.SerialPort;
//import jssc.SerialPortException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//
//public class logReadandWrite {
//    private SerialPort serialPort;
//    private volatile boolean isWriting = false;
//    private volatile boolean running = false;  // Flag to control the reading loop
//    private final Path logFilePath = Path.of("serial_log.txt");
//    private Thread logThread;  // Thread to handle logging
//
//    public logReadandWrite(String portName) {
//        serialPort = new SerialPort(portName);
//    }
//
//    public synchronized void openPort() {
//        try {
//            if (!serialPort.isOpened()) {
//                serialPort.openPort();
//                serialPort.setParams(115200, 8, 1, 0);
//                running = true;  // Set running flag to true when port is opened
//                System.out.println("Port opened successfully.");
//                startLogThread();  // Start the logging thread when the port opens
//            } else {
//                System.out.println("Port is already open.");
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in opening port: " + ex.getMessage());
//        }
//    }
//
//    private void startLogThread() {
//        // Only start the thread if it's not already running
//        if (logThread == null || !logThread.isAlive()) {
//            logThread = new Thread(() -> {
//                while (running) {  // Keep running as long as the flag is true
//                    if (!isWriting) {  // Only read if not writing
//                        try {
//                            if (serialPort.isOpened()) {
//                                String received = serialPort.readString();
//                                if (received != null) {
//                                    String trimmedResponse = received.trim();
//                                    saveToFile(trimmedResponse);
//                                }
//                            } else {
//                                System.out.println("Error: Cannot read, port is not open.");
//                                break;  // Exit the loop if port is not open
//                            }
//                        } catch (SerialPortException ex) {
//                            System.out.println("Error in reading from port: " + ex.getMessage());
//                        }
//                    }
//                }
//            });
//            logThread.start();  // Start the logging thread
//        }
//    }
//
//    public synchronized void write(String comment) {
//        try {
//            if (serialPort.isOpened()) {
//                isWriting = true;  // Set writing flag
//                serialPort.writeBytes(comment.getBytes());
//                System.out.println("Comment sent: " + comment);
//            } else {
//                System.out.println("Error: Cannot write, port is not open.");
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in writing to port: " + ex.getMessage());
//        } finally {
//            isWriting = false;  // Reset writing flag
//        }
//    }
//
//    public synchronized void closePort() {
//        try {
//            if (serialPort.isOpened()) {
//                running = false;  // Stop the reading loop
//                logThread.interrupt();  // Interrupt the logging thread if running
//                serialPort.closePort();
//                System.out.println("Port closed successfully.");
//            } else {
//                System.out.println("Port is already closed.");
//            }
//        } catch (SerialPortException ex) {
//            System.out.println("Error in closing port: " + ex.getMessage());
//        }
//    }
//
//    private void saveToFile(String data) {
//        try {
//            Files.writeString(logFilePath, data + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }
//}

public class logReadandWrite {
    private static logReadandWrite instance = null;  // Singleton instance
    private SerialPort serialPort;
    private volatile boolean isWriting = false;
    private volatile boolean running = false;  // Flag to control the reading loop
    private final Path logFilePath = Path.of("serial_log.txt");
    private Thread logThread;  // Thread to handle logging

    public logReadandWrite(String portName) {
        serialPort = new SerialPort(portName);
    }

    // Singleton getInstance method
    public static logReadandWrite getInstance(String portName) {
        if (instance == null) {
            instance = new logReadandWrite(portName);
        }
        return instance;
    }

    public synchronized void openPort() {
        try {
            if (!serialPort.isOpened()) {
                serialPort.openPort();
                serialPort.setParams(115200, 8, 1, 0);
                running = true;  // Set running flag to true when port is opened
                System.out.println("Port opened successfully.");
                startLogThread();  // Start the logging thread when the port opens
            } else {
                System.out.println("Port is already open.");
            }
        } catch (SerialPortException ex) {
            System.out.println("Error in opening port: " + ex.getMessage());
        }
    }

    private void startLogThread() {
        // Only start the thread if it's not already running
        if (logThread == null || !logThread.isAlive()) {
            logThread = new Thread(() -> {
                while (running) {  // Keep running as long as the flag is true
                    if (!isWriting) {  // Only read if not writing
                        try {
                            if (serialPort.isOpened()) {
                                String received = serialPort.readString();
                                if (received != null) {
                                    String trimmedResponse = received.trim();
                                    saveToFile(trimmedResponse);
                                }
                            } else {
                                System.out.println("Error: Cannot read, port is not open.");
                                break;  // Exit the loop if port is not open
                            }
                        } catch (SerialPortException ex) {
                            System.out.println("Error in reading from port: " + ex.getMessage());
                        }
                    }
                }
            });
            logThread.start();  // Start the logging thread
        }
    }

    public synchronized void write(String comment) {
        try {
            if (serialPort.isOpened()) {
                isWriting = true;  // Set writing flag
                serialPort.writeBytes(comment.getBytes());
                System.out.println("Comment sent: " + comment);
            } else {
                System.out.println("Error: Cannot write, port is not open.");
            }
        } catch (SerialPortException ex) {
            System.out.println("Error in writing to port: " + ex.getMessage());
        } finally {
            isWriting = false;  // Reset writing flag
        }
    }

    public synchronized void closePort() {
        try {
            if (serialPort.isOpened()) {
                running = false;  // Stop the reading loop
                logThread.interrupt();  // Interrupt the logging thread if running
                serialPort.closePort();
                System.out.println("Port closed successfully.");
            } else {
                System.out.println("Port is already closed.");
            }
        } catch (SerialPortException ex) {
            System.out.println("Error in closing port: " + ex.getMessage());
        }
    }

    private void saveToFile(String data) {
        try {
            Files.writeString(logFilePath, data + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
