package datamanagement;

import logging.Logger;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArgumentParser {

    public static final Set<String> validArgs = new HashSet<>(Arrays.asList("population", "log", "covid", "properties"));

    public static Map<String, File> processRuntimeArgs(String[] args) {
        Map<String, File> fileMap = new HashMap<>();

        // regex pattern to match the runtime arguments
        Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");
        for (String arg : args) {
            Matcher match = pattern.matcher(arg);

            // check if argument format is valid
            if (!match.find()) {
                throw new IllegalArgumentException("Invalid argument format: " + arg);
            }

            String name = match.group("name");
            String value = match.group("value");

            // check if argument name is valid
            if (!validArgs.contains(name)) {
                throw new IllegalArgumentException("Invalid argument name: " + name);
            }

            // check for duplicate arguments
            if (fileMap.containsKey(name)) {
                throw new IllegalArgumentException("Duplicate argument: " + name);
            }

            // check if file path exists in file system
            File file = new File(value);

            // check if log file can be opened for writing
            if (name.equals("log")) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to create log file: " + value);
                }
            }

            if (!file.exists() || !file.canRead()) {
                throw new IllegalArgumentException("Invalid file path: " + value);
            }

            // check file name for covid data is json or csv, case-insensitive
            if (name.equals("covid") && !value.toLowerCase().endsWith(".json") && !value.toLowerCase().endsWith(".csv")) {
                throw new IllegalArgumentException("Invalid file type for covid data: " + value);
            }

            fileMap.put(name, file);
        }

        // check if log file is provided
        if (!fileMap.containsKey("log")) {
            String defaultLogFileName = "events.log";
            File logFile = new File(defaultLogFileName);
            fileMap.put("log", logFile);
        }

        Logger logger = Logger.getInstance();
        logger.setLoggerDestination(fileMap.get("log").getName());

        String command = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        logger.logEvent("[COMMAND] " + command);

        return fileMap;
    }
}
