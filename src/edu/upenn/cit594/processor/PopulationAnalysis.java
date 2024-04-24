package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Population;

public class PopulationAnalysis extends Analysis {

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter) {
        int total = 0;

        for (Population population : dataset.getPopulations()) {
            total += population.getPopulation();
        }

        emitter.emit(Integer.toString(total));
    }
}
