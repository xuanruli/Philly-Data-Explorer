package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MarketValueAnalysis extends Analysis {
    @Override
    public String getId() {
        return "total_market_value_per_capita";
    }

    @Override
    public Map<String, Function<String, Boolean>> getExtraArgsPrompts() {
        return Map.of("Which is the 5-digit ZIP code?", s -> s.matches("\\d{5}"));
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> extraArg) {
        double totalMarketValue = dataset.getProperties().stream()
                .filter(property -> property.getPropertyZip() == Integer.parseInt(extraArg.get(0)))
                .filter(property -> property.getMarketValue() != null)
                .mapToDouble(Property::getMarketValue)
                .sum();

        int totalPopulation = 0;
        for (Population population : dataset.getPopulations()) {
            if (population.getPopulationZip() == Integer.parseInt(extraArg.get(0))) {
                totalPopulation = population.getPopulation();
                break;
            }
        }

        double marketValuePerCapita = totalPopulation == 0 ? 0 : totalMarketValue / totalPopulation;

        emitter.emit(Integer.toString((int) marketValuePerCapita));
    }
}
