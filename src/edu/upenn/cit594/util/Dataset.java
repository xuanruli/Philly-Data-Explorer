package edu.upenn.cit594.util;

import java.util.List;

public class Dataset {
    private final List<Population> populations;
    private final List<Property> properties;
    private final List<CovidData> covidData;

    public Dataset(List<Population> populations, List<Property> properties, List<CovidData> covidData) {
        this.populations = populations;
        this.properties = properties;
        this.covidData = covidData;
    }

    public List<Population> getPopulations() {
        return populations;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<CovidData> getCovidData() {
        return covidData;
    }
}
