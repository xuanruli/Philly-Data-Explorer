package processor;

import util.Dataset;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class Analysis {

    public List<Map.Entry<String, Function<String, Boolean>>> getExtraParamsPrompts() {
        return List.of();
    }

    public abstract Set<Dataset.DataType> getRequiredDeps();

    public abstract String getId();

    public abstract void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception;
}
