package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Population;

import java.util.List;
import java.util.Set;

public class PopulationAnalysis extends Analysis {

    @Override
    public String getId() {
        return "total_population";
    }

    @Override
    public Set<Dataset.DataType> getRequiredDeps() {
        return Set.of(Dataset.DataType.POPULATION);
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception {
        int total = 0;

        for (Population population : dataset.getPopulations()) {
            total += population.getPopulation();
        }

        emitter.emit(Integer.toString(total));
    }
}
