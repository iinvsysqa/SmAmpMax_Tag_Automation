package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import jssc.SerialPort;
import jssc.SerialPortException;

public class logReadandWrite {
    private static logReadandWrite instance = null;
    private SerialPort serialPort;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Path logFilePath = Path.of("device.log");

    private final Queue<String> logQueue = new ConcurrentLinkedQueue<>();
    private final Queue<String> writeQueue = new ConcurrentLinkedQueue<>();

    private Thread logReaderThread;
    private Thread logWriterThread;
    private Thread logFlusherThread;

    private final Object serialWriteLock = new Object();
    private int currentBaudRate; // Variable to store the current baud rate

    private logReadandWrite(String portName) {
        this.serialPort = new SerialPort(portName);
    }

    public static synchronized logReadandWrite getInstance(String portName) {
        if (instance == null) {
            instance = new logReadandWrite(portName);
        }
        return instance;
    }

    public synchronized void openPort() throws SerialPortException, IOException {
        if (!serialPort.isOpened()) {
            serialPort.openPort();
            // Ensure baud rate and other params are optimal for your device.
            // Higher baud rates (if supported by device) reduce transmission time.
            this.currentBaudRate = SerialPort.BAUDRATE_115200; // Store the baud rate
            serialPort.setParams(this.currentBaudRate, // Use the stored baud rate
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            // serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE); // Explicitly if needed

            running.set(true);

            Files.writeString(logFilePath, "", // Clear log file on open
                    java.nio.file.StandardOpenOption.TRUNCATE_EXISTING,
                    java.nio.file.StandardOpenOption.CREATE);

            // Use the stored currentBaudRate in the print statement
            System.out.println("Port opened successfully: " + serialPort.getPortName() + " at " + this.currentBaudRate + " baud.");

            startLogReaderThread();
            startLogWriterThread();
            startLogFlusherThread();
            //startMonitorThread();
        } else {
            System.out.println("Port " + serialPort.getPortName() + " is already open.");
        }
    }

    private void startLogReaderThread() {
        logReaderThread = new Thread(() -> {
            byte[] buffer = new byte[8192]; // Increased buffer for reading more data per syscall
            StringBuilder incompleteLog = new StringBuilder(1024); // Stores partial lines

            while (running.get()) {
                try {
                    if (serialPort.isOpened()) {
                        int available = serialPort.getInputBufferBytesCount();
                        if (available > 0) {
                            int toRead = Math.min(available, buffer.length);
                            // This readBytes call will block until 'toRead' bytes are available
                            byte[] readBytes = serialPort.readBytes(toRead);

                            if (readBytes != null && readBytes.length > 0) {
                                String received = new String(readBytes, StandardCharsets.UTF_8);
                                incompleteLog.append(received);

                                // Process complete lines efficiently
                                int lastIndex = 0;
                                int newlineIndex;
                                while ((newlineIndex = incompleteLog.indexOf("\n", lastIndex)) != -1) {
                                    String line = incompleteLog.substring(lastIndex, newlineIndex);
                                    // Handle potential preceding '\r' for "\r\n" sequences
                                    if (line.endsWith("\r")) {
                                        line = line.substring(0, line.length() - 1);
                                    }
                                    logQueue.add(line + "\n"); // Add raw line with a standard newline
                                    lastIndex = newlineIndex + 1;
                                }

                                // Remove processed part from StringBuilder
                                if (lastIndex > 0) {
                                    incompleteLog.delete(0, lastIndex);
                                }
                            }
                        } else {
                            // No data currently in OS input buffer for this port.
                            // Sleep briefly to prevent busy-waiting and consuming CPU unnecessarily.
                            Thread.sleep(1); // Adjust if needed; too long might miss data, too short uses CPU.
                                             // Thread.yield() is another option.
                        }
                    } else {
                         if (!running.get()) break; // Exit if stopped
                        Thread.sleep(100); // Port not open, wait before checking 'running' again
                    }
                } catch (SerialPortException e) {
                    if (running.get()) {
                        System.err.println("LogReader - SerialPortException on port " + e.getPortName() + " (" + e.getMethodName() + "): " + e.getExceptionType());
                    }
                    // If port is definitively closed or encountered a critical error, stop the thread.
                    if (!serialPort.isOpened() || SerialPortException.TYPE_PORT_NOT_OPENED.equals(e.getExceptionType())) {
                         if (running.get()) System.err.println("LogReader - Port appears closed. Stopping reader thread.");
                        break;
                    }
                    // For other transient errors, short sleep and continue
                    try { Thread.sleep(50); } catch (InterruptedException interEx) { Thread.currentThread().interrupt(); break; }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Preserve interrupt status
                    System.out.println("[INFO] Log Reader thread interrupted, exiting.");
                    break;
                } catch (Exception e) { // Catch any other unexpected errors
                    if (running.get()) {
                        System.err.println("LogReader - Unexpected error: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break; // Stop on critical errors
                }
            }

            // Process any remaining data in incompleteLog when thread is shutting down
            if (incompleteLog.length() > 0) {
                String finalLog = incompleteLog.toString();
                // Remove any existing trailing newlines/carriage returns
                while (finalLog.endsWith("\n") || finalLog.endsWith("\r")) {
                    finalLog = finalLog.substring(0, finalLog.length() - 1);
                }
                if (!finalLog.isEmpty()) { // Add only if something remains after stripping
                    logQueue.add(finalLog + "\n");
                }
            }
            System.out.println("[INFO] Log Reader thread exiting. Final logQueue size: " + logQueue.size());
        });
        logReaderThread.setName("SerialPort-Reader");
        // Optionally, slightly increase priority. Use with caution.
        // logReaderThread.setPriority(Thread.NORM_PRIORITY + 1);
        logReaderThread.start();
    }

    private void startLogWriterThread() {
        logWriterThread = new Thread(() -> {
            while (running.get() || !writeQueue.isEmpty()) {
                try {
                    String message = writeQueue.poll();
                    if (message != null) {
                        synchronized (serialWriteLock) {
                            if (serialPort.isOpened()) {
                                if (!message.endsWith("\n") && !message.endsWith("\r")) {
                                    message += "\n";
                                }
                                serialPort.writeBytes(message.getBytes(StandardCharsets.UTF_8));
                                System.out.println("Successfully wrote: " + message.trim());
                            } else if (running.get()) {
                                System.out.println("[WARN] Log writer: Serial port not open, message not sent: " + message.trim());
                            }
                        }
                    } else {
                        Thread.sleep(10);
                    }
                } catch (SerialPortException e) {
                    System.err.println("Serial port write error: " + e.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[INFO] Log Writer thread exiting.");
        });
        logWriterThread.setName("SerialPort-Writer");
        logWriterThread.start();
    }

    private void startLogFlusherThread() {
        logFlusherThread = new Thread(() -> {
            while (running.get() || !logQueue.isEmpty()) {
                boolean shouldFlush = false;
                if (logQueue.size() >= 100) { // Threshold flush
                    shouldFlush = true;
                } else if (!running.get() && !logQueue.isEmpty()) { // Final flush if stopping
                    shouldFlush = true;
                }

                if (shouldFlush) {
                    // System.out.println("[DEBUG] Flushing logs. Queue size: " + logQueue.size());
                    flushLogsToFile();
                }

                try {
                    // Sleep briefly. If not running and queue is empty, this loop will exit.
                    // If running, this is the periodic flush interval if threshold not met.
                    Thread.sleep(running.get() ? 50 : 100); // Shorter sleep if active, bit longer if just draining queue
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            flushLogsToFile(); // Final flush to ensure everything is written
            System.out.println("[INFO] Log Flusher thread exiting.");
        });
        logFlusherThread.setName("Log-Flusher");
        logFlusherThread.start();
    }

    public void write(String message) {
        if (message != null && !message.isEmpty()) {
            writeQueue.add(message);
        }
    }

    public void flushLogsToFile() {
        synchronized (logFilePath.toString().intern()) { // Synchronize on the file path string
            if (logQueue.isEmpty()) {
                return; // Nothing to flush
            }
            int writtenCount = 0;
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(logFilePath.toFile(), StandardCharsets.UTF_8, true))) {
                String logEntry;
                while ((logEntry = logQueue.poll()) != null) {
                    // logReaderThread now ensures logEntry ends with "\n"
                    writer.write(logEntry);
                    writtenCount++;
                }
                if (writtenCount > 0) {
                    writer.flush(); // Flush the underlying stream
                    // System.out.println("[DEBUG] Flushed " + writtenCount + " log entries to file.");
                }
            } catch (IOException e) {
                System.err.println("[ERROR] Writing to log file failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public synchronized void closePort() {
        if (running.compareAndSet(true, false)) {
            System.out.println("Attempting to close port and stop threads...");

            if (logReaderThread != null) logReaderThread.interrupt();
            if (logWriterThread != null) logWriterThread.interrupt();
            if (logFlusherThread != null) logFlusherThread.interrupt();

            joinThread(logReaderThread, "Reader");
            joinThread(logWriterThread, "Writer");
            joinThread(logFlusherThread, "Flusher");

            try {
                if (serialPort != null && serialPort.isOpened()) {
                    System.out.println("Closing serial port.");
                    serialPort.closePort();
                }
                System.out.println("Port closed successfully.");
            } catch (SerialPortException e) {
                System.err.println("Error closing serial port: " + e.getMessage());
            }
            System.out.println("Performing final log flush after thread shutdown.");
            flushLogsToFile(); // Ensure all pending logs are written
        } else {
            System.out.println("Port already closed or in the process of closing.");
        }
        instance = null; // Allow re-initialization
    }

    private void joinThread(Thread thread, String name) {
        if (thread != null) {
            try {
                System.out.println("Waiting for " + name + " thread to join...");
                thread.join(2000); // Wait up to 2 seconds
                if (thread.isAlive()) {
                    System.err.println("[WARN] " + name + " thread did not join in time.");
                } else {
                    System.out.println(name + " thread joined successfully.");
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting for " + name + " thread to join.");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void startMonitorThread() {
        new Thread(() -> {
            while (running.get()) {
                boolean readerAlive = logReaderThread != null && logReaderThread.isAlive();
                boolean writerAlive = logWriterThread != null && logWriterThread.isAlive();
                boolean flusherAlive = logFlusherThread != null && logFlusherThread.isAlive();

                System.out.printf("[Monitor] Reader: %s | Writer: %s | Flusher: %s | LogQueue: %d | WriteQueue: %d%n",
                        readerAlive ? "Alive" : "Dead/Null",
                        writerAlive ? "Alive" : "Dead/Null",
                        flusherAlive ? "Alive" : "Dead/Null",
                        logQueue.size(), writeQueue.size());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[INFO] Monitor thread exiting.");
        }, "Thread-Monitor").start();
    }
}