package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CovidJsonReader extends CovidReader {
    public CovidJsonReader(File file) {
        getDataFromFile(file);
    }

    private void getDataFromFile(File file){
        JSONParser json = new JSONParser();
        try {
            JSONArray records = (JSONArray) json.parse(new FileReader(file));
            for (Object record: records){
                JSONObject entry = (JSONObject) record;
                //parse zipcode
                String entryZip = (String) entry.get("zip_code");
                if (!isValidZip(entryZip)){
                    continue;
                }
                int entryZipInt = Integer.parseInt(entryZip.substring(0,5));
                //parse timestamp
                String entryTime = (String) entry.get("etl_timestamp");
                if (!isValidTime(entryTime)){
                    continue;
                }
                int entryPartialNum = getInt(entry.get("partially_vaccinated"));
                int entryFullNum = getInt(entry.get("fully_vaccinated"));
                this.covidData.add(new CovidData(entryZipInt, entryTime, entryPartialNum, entryFullNum));
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidZip(String zip){
        return (zip != null && zip.length() == 5 && zip.matches("\\d{5}"));
    }

    private boolean isValidTime(String time){
        return (time != null && time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    private int getInt(Object obj){
        if (obj == null || obj.toString().isEmpty()){
            return 0;
        }
        return (int) obj;
    }
}
