package util;

import datamanagement.PopulationReader;
import datamanagement.PropertyReader;
import datamanagement.CovidReader;

import java.util.List;

public class Dataset {
    private List<Population> populations;
    private List<Property> properties;
    private List<CovidData> covidData;

    private final PopulationReader populationReader;
    private final PropertyReader propertyReader;
    private final CovidReader covidDataReader;

    public enum DataType {
        POPULATION,
        PROPERTY,
        COVID
    }

    public Dataset(PopulationReader populationReader, PropertyReader propertyReader, CovidReader covidDataReader) {
        this.populationReader = populationReader;
        this.propertyReader = propertyReader;
        this.covidDataReader = covidDataReader;
    }

    public List<Population> getPopulations() throws Exception {
        if (populations == null) {
            populations = populationReader.read();
        }
        return populations;
    }

    public List<Property> getProperties() throws Exception {
        if (properties == null) {
            properties = propertyReader.read();
        }
        return properties;
    }

    public List<CovidData> getCovidData() throws Exception {
        if (covidData == null) {
            covidData = covidDataReader.read();
        }
        return covidData;
    }
}
