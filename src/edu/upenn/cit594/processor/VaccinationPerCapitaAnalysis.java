package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Dataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class VaccinationPerCapitaAnalysis extends Analysis {

    enum VaccinationType {
        PARTIAL,
        FULL
    }

    @Override
    public String getId() {
        return "total_vaccinations_per_capita";
    }

    @Override
    public Map<String, Function<String, Boolean>> getExtraParamsPrompts() {
        return Map.of(
            "Which type of vaccination result, \"partial\" or \"full\"?",
            s -> s.equals("partial") || s.equals("full"),
            "What is the date in YYYY-MM-DD format?",
            s -> s.matches("\\d{4}-\\d{2}-\\d{2}")
        );
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception {
        VaccinationType vaccinationType = getVaccinationType(params.get(0));
        String date = params.get(1);

        List<CovidData> covidData = dataset.getCovidData().stream()
            .filter(data -> data.getTimestamp().startsWith(date))
            .toList();

        // zip code to total partial/full vaccinations
        Map<Integer, Integer> zipToVaccinations = new HashMap<>();
        for (CovidData data : covidData) {
            int count = vaccinationType == VaccinationType.PARTIAL
                ? data.getNumPartiallyVaccinated()
                : data.getNumFullyVaccinated();
            zipToVaccinations.put(data.getCovidDataZip(), zipToVaccinations.getOrDefault(data.getCovidDataZip(), 0) + count);
        }

        // total population for each zip code
        Map<Integer, Integer> zipToPopulation = new HashMap<>();
        for (var population : dataset.getPopulations()) {
            zipToPopulation.put(population.getPopulationZip(), population.getPopulation());
        }

        // iterate over each population zip code
        for (var entry : zipToPopulation.entrySet()) {
            int zip = entry.getKey();
            int population = entry.getValue();
            int vaccinations = zipToVaccinations.getOrDefault(zip, 0);
            float vaccinationsPerCapita = population == 0 ? 0 : (float) vaccinations / population;
            emitter.emit(String.format("%d %.4f", zip, vaccinationsPerCapita));
        }
    }

    private VaccinationType getVaccinationType(String extraArg) {
        if (extraArg.equals("partial")) {
            return VaccinationType.PARTIAL;
        } else if (extraArg.equals("full")) {
            return VaccinationType.FULL;
        } else {
            throw new IllegalArgumentException("Invalid vaccination type: " + extraArg);
        }
    }
}
