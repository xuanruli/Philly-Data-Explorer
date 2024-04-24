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
    private final Map<String, Map<String, List<String>>> analysisResults = new HashMap<>();

    public DataProcessor(File populationFile, File propertyFile, File covidFile) throws Exception {
        PopulationReader populationReader = new PopulationReader(populationFile);
        PropertyReader propertyReader = new PropertyReader(propertyFile);
        CovidReader covidReader = new CovidReader(covidFile);

        this.dataset = new Dataset(populationReader.read(), propertyReader.read(), covidReader.read());
    }

    public void process(Analysis analysis) {
        process(analysis, List.of());
    }

    public void process(Analysis analysis, List<String> extraArgs) {
        if (extraArgs == null || extraArgs.size() != analysis.getExtraArgsPrompts().size()) {
            throw new IllegalArgumentException("Invalid extra arguments");
        }

        if (!analysisResults.containsKey(analysis.getId())) {
            analysisResults.put(analysis.getId(), new HashMap<>());
        }

        Map<String, List<String>> results = analysisResults.get(analysis.getId());

        String extraArgsKey = extraArgs.toString();
        if (!results.containsKey(extraArgsKey)) {
            ResultEmitter emitter = new ResultEmitter();
            analysis.analyze(dataset, emitter, extraArgs);
            results.put(extraArgsKey, emitter.getResults());
        }

        showResults(results.get(extraArgsKey));
    }

    public void showResults(List<String> results) {
        System.out.println("BEGIN OUTPUT");
        for (String line : results) {
            System.out.println(line);
        }
        System.out.println("END OUTPUT");
    }
}
