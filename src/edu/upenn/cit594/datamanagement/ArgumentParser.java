package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArgumentParser {

    public static final Set<String> validArgs = new HashSet<>(Arrays.asList("population", "log", "covid", "properties"));

    public static Map<String, File> processRuntimeArgs(String[] args) {
        Map<String, File> fileMap = new HashMap<>();

        if (args.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

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

            // check if file path exists in file system
            File file = new File(value);

            if (!file.exists() || !file.canRead()) {
                throw new IllegalArgumentException("Invalid file path: " + value);
            }

            // check file name for covid data is json or csv, case-insensitive
            if (name.equals("covid") && !value.toLowerCase().endsWith(".json") && !value.toLowerCase().endsWith(".csv")) {
                throw new IllegalArgumentException("Invalid file type for covid data: " + value);
            }

            fileMap.put(name, file);
        }

        Logger logger = Logger.getInstance();
        logger.setLoggerDestination(fileMap.get("log").getName());

        String command = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        logger.logEvent("[COMMAND] " + command);

        return fileMap;
    }
}
