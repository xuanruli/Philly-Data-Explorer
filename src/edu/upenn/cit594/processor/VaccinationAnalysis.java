package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;

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
    public String extraArgPrompt() {
        return "Which type of vaccination result, \"partial\" or \"full\"?";
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, String extraArg) {
        VaccinationType vaccinationType = getVaccinationType(extraArg);
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
