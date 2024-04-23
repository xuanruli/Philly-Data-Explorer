package edu.upenn.cit594.util;

import java.util.Map;

public class Population {
    private final int populationZip;
    private final int population;

    public Population(int populationZip, int population){
        this.population = population;
        this.populationZip = populationZip;
    }

    public int getPopulation() {
        return population;
    }

    public int getPopulationZip() {
        return populationZip;
    }

    @Override
    public String toString() {
        return Map.of("zip_code", populationZip, "population", population).toString();
    }
}
