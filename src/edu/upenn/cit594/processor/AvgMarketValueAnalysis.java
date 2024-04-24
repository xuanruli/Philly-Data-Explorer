package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Property;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class AvgMarketValueAnalysis extends Analysis {

    @Override
    public String getId() {
        return "average_market_value";
    }

    @Override
    public List<Map.Entry<String, Function<String, Boolean>>> getExtraParamsPrompts() {
        return List.of(
                Map.entry("Which is the 5-digit ZIP code?", s -> s.matches("\\d{5}"))
        );
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception {
        List<Double> marketValues = dataset.getProperties().stream()
                .filter(property -> property.getPropertyZip() == Integer.parseInt(params.get(0)))
                .map(Property::getMarketValue)
                .filter(Objects::nonNull)
                .toList();

        // calculate average market value
        double averageMarketValue = marketValues.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        emitter.emit(Integer.toString((int) averageMarketValue));
    }
}
