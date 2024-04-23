package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CovidReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.util.Dataset;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessor {
    private final Dataset dataset;
    private final Map<String, List<String>> analysisResults = new HashMap<>();


    public DataProcessor(File populationFile, File propertyFile, File covidFile) throws Exception {
        PopulationReader populationReader = new PopulationReader(populationFile);
        PropertyReader propertyReader = new PropertyReader(propertyFile);
        CovidReader covidReader = new CovidReader(covidFile);

        this.dataset = new Dataset(populationReader.read(), propertyReader.read(), covidReader.read());
    }

    public void process(Analysis analysis) {
        if (!analysisResults.containsKey(analysis.getId())) {
            ResultEmitter emitter = new ResultEmitter();
            analysis.analyze(dataset, emitter);
            analysisResults.put(analysis.getId(), emitter.getResults());
        }
        showResults(analysisResults.get(analysis.getId()));
    }

    public void showResults(List<String> results) {
        System.out.println("BEGIN OUTPUT");
        for (String line : results) {
            System.out.println(line);
        }
        System.out.println("END OUTPUT");
    }
}
