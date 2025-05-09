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
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

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
            serialPort.setParams(115200, 8, 1, 0);
            running.set(true);

            // Clear existing log file
            Files.writeString(logFilePath, "", 
                java.nio.file.StandardOpenOption.TRUNCATE_EXISTING, 
                java.nio.file.StandardOpenOption.CREATE);

            System.out.println("Port opened successfully.");
            
            // Start all worker threads
            startLogReaderThread();
            startLogWriterThread();
            startLogFlusherThread();
        } else {
            System.out.println("Port is already open.");
        }
    }

    private void startLogReaderThread() {
        logReaderThread = new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (running.get()) {
                try {
                    if (serialPort.isOpened()) {
                        int available = serialPort.getInputBufferBytesCount();
                        if (available > 0) {
                            int toRead = Math.min(available, buffer.length);
                            byte[] readBytes = serialPort.readBytes(toRead);
                            if (readBytes != null && readBytes.length > 0) {
                                String received = new String(readBytes, StandardCharsets.UTF_8);
                                logQueue.add(received);
                            }
                        }
                    }
                    Thread.sleep(1); // Short sleep to prevent CPU overload
                } catch (SerialPortException | InterruptedException e) {
                    if (running.get()) {
                        System.out.println("Error in log reader: " + e.getMessage());
                    }
                    break;
                }
            }
        });
        logReaderThread.setName("SerialPort-Reader");
        logReaderThread.start();
    }

    private void startLogWriterThread() {
        logWriterThread = new Thread(() -> {
            while (running.get()) {
                try {
                    String message = writeQueue.poll();
                    if (message != null) {
                        synchronized (serialWriteLock) {
                            if (serialPort.isOpened()) {
                                // Ensure message ends with newline
                                if (!message.endsWith("\n") && !message.endsWith("\r")) {
                                    message += "\n";
                                }
                                serialPort.writeBytes(message.getBytes(StandardCharsets.UTF_8));
                                System.out.println("Successfully wrote: " + message.trim());
                            }
                        }
                    } else {
                        Thread.sleep(1); // Short sleep when queue is empty
                    }
                } catch (SerialPortException e) {
                    System.out.println("Serial port write error: " + e.getMessage());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        logWriterThread.setName("SerialPort-Writer");
        logWriterThread.start();
    }

    private void startLogFlusherThread() {
        logFlusherThread = new Thread(() -> {
            while (running.get()) {
                flushLogsToFile();
                try {
                    Thread.sleep(100); // More frequent flushing (100ms)
                } catch (InterruptedException e) {
                    break;
                }
            }
            // Final flush before exiting
            flushLogsToFile();
        });
        logFlusherThread.setName("Log-Flusher");
        logFlusherThread.start();
    }

    public void write(String message) {
        if (message != null && !message.isEmpty()) {
            writeQueue.add(message);
            //System.out.println("Added to write queue: " + message);
        }
    }

    public void flushLogsToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(logFilePath.toFile(), StandardCharsets.UTF_8, true))) {
            String logEntry;
            while ((logEntry = logQueue.poll()) != null) {
                writer.write(logEntry);
                // Only add newline if the entry doesn't already end with one
                if (!logEntry.endsWith("\n") && !logEntry.endsWith("\r")) {
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    public synchronized void closePort() {
        if (running.compareAndSet(true, false)) {
            try {
                // Interrupt and wait for threads to finish
                if (logReaderThread != null) {
                    logReaderThread.interrupt();
                    logReaderThread.join(1000);
                }
                
                if (logWriterThread != null) {
                    logWriterThread.interrupt();
                    logWriterThread.join(1000);
                }
                
                if (logFlusherThread != null) {
                    logFlusherThread.interrupt();
                    logFlusherThread.join(1000);
                }
                
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                }
                
                System.out.println("Port closed successfully.");
            } catch (SerialPortException | InterruptedException e) {
                System.out.println("Error closing port: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}