package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Property;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class AvgLivableAreaAnalysis extends Analysis {

    @Override
    public String getId() {
        return "avg_livable_area";
    }

    @Override
    public Map<String, Function<String, Boolean>> getExtraArgsPrompts() {
        return Map.of("Which is the 5-digit ZIP code?", s -> s.matches("\\d{5}"));
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> extraArg) {
        List<Double> livableAreas = dataset.getProperties().stream()
                .filter(property -> property.getPropertyZip() == Integer.parseInt(extraArg.get(0)))
                .map(Property::getArea)
                .filter(Objects::nonNull)
                .toList();

        // calculate average livable area
        double averageLivableArea = livableAreas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        emitter.emit(Integer.toString((int) averageLivableArea));
    }
}
