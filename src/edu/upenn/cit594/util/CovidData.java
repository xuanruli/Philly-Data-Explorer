package edu.upenn.cit594.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    public static boolean isInvalidZip(String zip) {
        return (zip == null || zip.length() != 5 || !zip.matches("\\d{5}"));
    }

    public static boolean isInvalidTimestamp(String timestamp) {
        return (timestamp == null || !timestamp.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    public static int getValidVaccinatedCount(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(obj.toString());
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

    public String getYMD() {
        return timestamp.substring(0, 10);
    }

    public int getNumPartiallyVaccinated(){
        return numPartiallyVaccinated;
    }

    public int getNumFullyVaccinated(){
        return numFullyVaccinated;
    }

    @Override
    public String toString() {
        return Map.of("zip", zip, "timestamp", timestamp, "partially_vaccinated", numPartiallyVaccinated, "fully_vaccinated", numFullyVaccinated).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        try {
            CovidData other = (CovidData) obj;
            return zip == other.zip && timestamp.equals(other.timestamp) && numPartiallyVaccinated == other.numPartiallyVaccinated && numFullyVaccinated == other.numFullyVaccinated;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
