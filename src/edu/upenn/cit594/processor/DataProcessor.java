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
    private final Map<String, Map<String, String>> analysisResults = new HashMap<>();

    public DataProcessor(File populationFile, File propertyFile, File covidFile) throws Exception {
        PopulationReader populationReader = new PopulationReader(populationFile);
        PropertyReader propertyReader = new PropertyReader(propertyFile);
        CovidReader covidReader = new CovidReader(covidFile);

        this.dataset = new Dataset(populationReader, propertyReader, covidReader);
    }

    public void process(Analysis analysis) throws Exception {
        process(analysis, List.of());
    }

    public String process(Analysis analysis, List<String> extraArgs) throws Exception {
        if (extraArgs == null || extraArgs.size() != analysis.getExtraParamsPrompts().size()) {
            assert extraArgs != null;
            throw new IllegalArgumentException("Invalid extra arguments: " + extraArgs.toString());
        }

        if (!analysisResults.containsKey(analysis.getId())) {
            analysisResults.put(analysis.getId(), new HashMap<>());
        }

        Map<String, String> results = analysisResults.get(analysis.getId());

        String extraArgsKey = extraArgs.toString();
        if (!results.containsKey(extraArgsKey)) {
            ResultEmitter emitter = new ResultEmitter();
            analysis.analyze(dataset, emitter, extraArgs);
            results.put(extraArgsKey, String.join("\n", emitter.getResults()));
        }

        return results.get(extraArgsKey);
    }
}
