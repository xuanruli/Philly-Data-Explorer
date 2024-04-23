package edu.upenn.cit594.util;

import java.util.List;

public class Dataset {
    List<Population> populations;
    List<Property> properties;
    List<CovidData> covidData;

    public Dataset(List<Population> populations, List<Property> properties, List<CovidData> covidData) {
        this.populations = populations;
        this.properties = properties;
        this.covidData = covidData;
    }
}
