package edu.upenn.cit594.util;

public class CovidData {
    private final int zip;
    private final String timestamp;
    private final int partiallyVaxedNum;
    private final int fullyVaxedNum;

    public CovidData (int zip, String timestamp, int partiallyVaxedNum, int fullyVaxedNum){
        this.zip = zip;
        this.timestamp = timestamp;
        this.partiallyVaxedNum = partiallyVaxedNum;
        this.fullyVaxedNum = fullyVaxedNum;
    }

    public int getCovidDataZip(){
        return zip;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public int getPartiallyVaxedNum(){
        return partiallyVaxedNum;
    }

    public int getFullyVaxedNum(){
        return fullyVaxedNum;
    }
}
