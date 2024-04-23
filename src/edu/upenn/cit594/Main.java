package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.ui.UIManager;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

        CSVReader reader = new CSVReader(fileMap.get("population"));
        List<Map<String, String>> populationData = reader.read();
        System.out.println(populationData);

        CSVReader reader2 = new CSVReader(fileMap.get("covid"));
        List<Map<String, String>> covidData = reader2.read();
        System.out.println(covidData);
    }
}