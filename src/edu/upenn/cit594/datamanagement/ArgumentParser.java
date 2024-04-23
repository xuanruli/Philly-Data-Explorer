package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

    public static final Set<String> validArgs = new HashSet<>(Arrays.asList("population", "log", "covid", "properties"));

    public static Map<String, File> processRuntimeArgs(String[] args) {
        Map<String, File> fileMap = new HashMap<>();

        if (args.length != 4) {
            System.out.println("Invalid number of arguments");
            System.exit(0);
        }

        // regex pattern to match the runtime arguments
        Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");
        for (String arg : args) {
            Matcher match = pattern.matcher(arg);

            // check if argument format is valid
            if (!match.find()) {
                System.out.println("Invalid argument format: " + arg);
                System.exit(0);
            }

            String name = match.group("name");
            String value = match.group("value");

            // check if argument name is valid
            if (!validArgs.contains(name)) {
                System.out.println("Invalid argument name: " + name);
                System.exit(0);
            }

            // check if file path exists in file system
            File file = new File(value);
            if (!file.exists() || !file.canRead()) {
                System.out.println("Invalid file path: " + value);
                System.exit(0);
            }

            // check file name for covid data is json or csv, case-insensitive
            if (name.equals("covid") && !value.toLowerCase().endsWith(".json") && !value.toLowerCase().endsWith(".csv")) {
                System.out.println("Invalid file type for covid data: " + value);
                System.exit(0);
            }

            fileMap.put(name, file);
        }

        return fileMap;
    }
}
