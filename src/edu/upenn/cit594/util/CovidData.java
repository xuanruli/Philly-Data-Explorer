package edu.upenn.cit594.util;

public class CovidData {
    private final int zip;
    private final String timestamp;
    private final int numPartiallyVaccinated;
    private final int numFullyVaccinated;

    public CovidData (int zip, String timestamp, int partiallyVaxedNum, int fullyVaxedNum){
        this.zip = zip;
        this.timestamp = timestamp;
        this.numPartiallyVaccinated = partiallyVaxedNum;
        this.numFullyVaccinated = fullyVaxedNum;
    }

    public int getCovidDataZip(){
        return zip;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public int getNumPartiallyVaccinated(){
        return numPartiallyVaccinated;
    }

    public int getNumFullyVaccinated(){
        return numFullyVaccinated;
    }
}
