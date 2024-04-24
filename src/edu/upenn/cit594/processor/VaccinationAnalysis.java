package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class VaccinationAnalysis extends Analysis {

    enum VaccinationType {
        PARTIAL,
        FULL
    }

    @Override
    public String getId() {
        return "total_vaccinations_per_capita";
    }

    @Override
    public Map<String, Function<String, Boolean>> getExtraArgsPrompts() {
        return Map.of(
            "Which type of vaccination result, \"partial\" or \"full\"?",
            s -> s.equals("partial") || s.equals("full"),
            "What is the date in YYYY-MM-DD format?",
            s -> s.matches("\\d{4}-\\d{2}-\\d{2}")
        );
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> extraArgs) {
        VaccinationType vaccinationType = getVaccinationType(extraArgs.get(0));
        String date = extraArgs.get(1);


    }

    private VaccinationType getVaccinationType(String extraArg) {
        if (extraArg.equals("partial")) {
            return VaccinationType.PARTIAL;
        } else if (extraArg.equals("full")) {
            return VaccinationType.FULL;
        } else {
            throw new IllegalArgumentException("Invalid vaccination type");
        }
    }
}
