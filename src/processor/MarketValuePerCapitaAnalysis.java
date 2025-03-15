package processor;

import util.Dataset;
import util.Population;
import util.Property;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MarketValuePerCapitaAnalysis extends Analysis {
    @Override
    public String getId() {
        return "total_market_value_per_capita";
    }

    @Override
    public List<Map.Entry<String, Function<String, Boolean>>> getExtraParamsPrompts() {
        return List.of(
                Map.entry("Which is the 5-digit ZIP code?", s -> s.matches("\\d{5}"))
        );
    }

    @Override
    public Set<Dataset.DataType> getRequiredDeps() {
        return Set.of(Dataset.DataType.PROPERTY, Dataset.DataType.POPULATION);
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception {
        double totalMarketValue = dataset.getProperties().stream()
                .filter(property -> property.getPropertyZip() == Integer.parseInt(params.get(0)))
                .filter(property -> property.getMarketValue() != null)
                .mapToDouble(Property::getMarketValue)
                .sum();

        int totalPopulation = 0;
        for (Population population : dataset.getPopulations()) {
            if (population.getPopulationZip() == Integer.parseInt(params.get(0))) {
                totalPopulation = population.getPopulation();
                break;
            }
        }

        double marketValuePerCapita = totalPopulation == 0 ? 0 : totalMarketValue / totalPopulation;

        emitter.emit(Integer.toString((int) marketValuePerCapita));
    }
}
