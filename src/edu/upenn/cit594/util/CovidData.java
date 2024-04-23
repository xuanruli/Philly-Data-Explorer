package edu.upenn.cit594.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CovidData {
    private final int zip;
    private final String timestamp;
    private final int numPartiallyVaccinated;
    private final int numFullyVaccinated;

    public CovidData(int zip, String timestamp, int numPartiallyVaccinated, int numFullyVaccinated) throws ParseException {
        this.zip = zip;
        this.timestamp = timestamp;
        this.numPartiallyVaccinated = numPartiallyVaccinated;
        this.numFullyVaccinated = numFullyVaccinated;
    }

    public int getCovidDataZip(){
        return zip;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(timestamp);
    }

    public int getNumPartiallyVaccinated(){
        return numPartiallyVaccinated;
    }

    public int getNumFullyVaccinated(){
        return numFullyVaccinated;
    }
}
