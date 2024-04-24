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
    private final Map<Integer, Analysis> analyses = new HashMap<>();
    private final Map<Integer, List<String>> analysisResults = new HashMap<>();

    public DataProcessor(File populationFile, File propertyFile, File covidFile) throws Exception {
        PopulationReader populationReader = new PopulationReader(populationFile);
        PropertyReader propertyReader = new PropertyReader(propertyFile);
        CovidReader covidReader = new CovidReader(covidFile);

        this.dataset = new Dataset(populationReader.read(), propertyReader.read(), covidReader.read());
    }

    public void createAnalysis(int id, Analysis analysis) {
        analyses.put(id, analysis);
    }

    public void processAnalysis(int analysisId) {
        Analysis analysis = analyses.get(analysisId);

        if (analysis == null) {
            throw new IllegalArgumentException("Invalid analysis ID");
        }

        if (!analysisResults.containsKey(analysisId)) {
            ResultEmitter emitter = new ResultEmitter();
            analysis.analyze(dataset, emitter);
            analysisResults.put(analysisId, emitter.getResults());
        }

        showResults(analysisResults.get(analysisId));
    }

    public void showResults(List<String> results) {
        System.out.println("BEGIN OUTPUT");
        for (String line : results) {
            System.out.println(line);
        }
        System.out.println("END OUTPUT");
    }
}
