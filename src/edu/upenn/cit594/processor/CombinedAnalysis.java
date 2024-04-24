package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Dataset;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CombinedAnalysis extends Analysis {

    @Override
    public String getId() {
        return "full_vaccinations_over_market_value_per_capita_ratio";
    }

    @Override
    public List<Map.Entry<String, Function<String, Boolean>>> getExtraParamsPrompts() {
        return List.of(
                Map.entry("Which is the 5-digit ZIP code?", s -> s.matches("\\d{5}")),
                Map.entry("What is the date in YYYY-MM-DD format?", s -> s.matches("\\d{4}-\\d{2}-\\d{2}"))
        );
    }

    @Override
    public void analyze(Dataset dataset, ResultEmitter emitter, List<String> params) throws Exception {
        int zip  = Integer.parseInt(params.get(0));
        String date = params.get(1);

        List<CovidData> covidData = dataset.getCovidData().stream()
                .filter(data -> data.getTimestamp().startsWith(date))
                .collect(Collectors.toList());

        // find full vaccination number for specified zipcode
        int fullVax = 0;
        for (CovidData data : covidData) {
            if (data.getCovidDataZip() == zip){
                fullVax = data.getNumFullyVaccinated();
            }
        }

        double totalMarketValue = dataset.getProperties().stream()
                .filter(property -> property.getPropertyZip() == zip)
                .filter(property -> property.getMarketValue() != null)
                .mapToDouble(Property::getMarketValue)
                .sum();

        int totalPopulation = 0;
        for (Population population : dataset.getPopulations()) {
            if (population.getPopulationZip() == zip) {
                totalPopulation = population.getPopulation();
                break;
            }
        }

        double marketValuePerCapita = totalPopulation == 0 ? 0 : totalMarketValue / totalPopulation;

        double ratio = 0;
        if (marketValuePerCapita != 0) {
            ratio = fullVax / marketValuePerCapita;
        }

        emitter.emit(String.format("%.4f", ratio));

    }
}
