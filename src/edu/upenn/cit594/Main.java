package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.DataProcessor;
import edu.upenn.cit594.processor.PopulationAnalysis;
import edu.upenn.cit594.ui.UIManager;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

        DataProcessor processor = new DataProcessor(
            fileMap.get("population"),
            fileMap.get("properties"),
            fileMap.get("covid")
        );

        PopulationAnalysis populationAnalysis = new PopulationAnalysis();

        processor.createAnalysis(1, populationAnalysis);

        processor.processAnalysis(1);
    }
}