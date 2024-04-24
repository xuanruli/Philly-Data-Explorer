package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Analysis {

    public Map<String, Function<String, Boolean>> getExtraParamsPrompts() {
        return Map.of();
    }

    public abstract String getId();

    public abstract void analyze(Dataset dataset, ResultEmitter emitter, List<String> params);
}
