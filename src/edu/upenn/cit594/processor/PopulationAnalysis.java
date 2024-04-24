package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Population;

import java.util.List;

public class PopulationAnalysis extends Analysis {

    @Override
    public String getId() {
        return "total_population";
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> extraArgs) {
        int total = 0;

        for (Population population : dataset.getPopulations()) {
            total += population.getPopulation();
        }

        emitter.emit(Integer.toString(total));
    }
}
