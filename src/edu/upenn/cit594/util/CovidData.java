package edu.upenn.cit594.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CovidData {
    private final int zip;
    private final Date timestamp;
    private final int numPartiallyVaccinated;
    private final int numFullyVaccinated;

    public CovidData(int zip, Date timestamp, int numPartiallyVaccinated, int numFullyVaccinated){
        this.zip = zip;
        this.timestamp = timestamp;
        this.numPartiallyVaccinated = numPartiallyVaccinated;
        this.numFullyVaccinated = numFullyVaccinated;
    }

    public CovidData(int zip, String timestamp, int numPartiallyVaccinated, int numFullyVaccinated) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(timestamp);

        this.zip = zip;
        this.timestamp = date;
        this.numPartiallyVaccinated = numPartiallyVaccinated;
        this.numFullyVaccinated = numFullyVaccinated;
    }

    public int getCovidDataZip(){
        return zip;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public int getNumPartiallyVaccinated(){
        return numPartiallyVaccinated;
    }

    public int getNumFullyVaccinated(){
        return numFullyVaccinated;
    }
}
