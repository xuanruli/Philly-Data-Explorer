package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static Logger logger = new Logger();
    private PrintWriter logWriter = null;

    // singleton pattern
    private Logger() {}

    public static Logger getInstance() {
        return logger;
    }

    // set or update the log file destination
    public synchronized void setLoggerDestination(String filename) {
        if (logWriter != null) {
            logWriter.close();
        }

        // terminate if cannot create/open the specified log file for writing
        try {
            logWriter = new PrintWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            System.err.println("Failed to create logger destination file.");
        }
    }

    public void logEvent(String event) {
        if (logWriter == null) {
            System.err.println("Logger destination file has not been set up.");
            return;
        }


        logWriter.println(event);
        // ensure buffered data is written immediately to destination
        logWriter.flush();
    }

    public void closeLogger() {
        if (logWriter != null) {
            logWriter.close();
        }
    }

}

