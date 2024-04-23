package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidJSONReader extends DataReader<List<CovidData>> {

    public CovidJSONReader(File file) {
        super(file);
    }

    @Override
    protected List<CovidData> getDataFromFile() throws IOException, ParseException, java.text.ParseException {
        JSONParser json = new JSONParser();
        JSONArray records = (JSONArray) json.parse(new FileReader(file));

        List<CovidData> covidDataList = new ArrayList<>();
        for (Object record: records){
            JSONObject entry = (JSONObject) record;

            // Parse zipcode
            String entryZip = Long.toString((Long) entry.get("zip_code"));
            if (CovidData.isInvalidZip(entryZip)){
                continue;
            }

            int entryZipInt = Integer.parseInt(entryZip.substring(0,5));

            // Parse timestamp
            String entryTime = (String) entry.get("etl_timestamp");
            if (CovidData.isInvalidTimestamp(entryTime)){
                continue;
            }

            int entryPartialNum = CovidData.getValidVaccinatedCount(entry.get("partially_vaccinated"));
            int entryFullNum = CovidData.getValidVaccinatedCount(entry.get("fully_vaccinated"));

            // Add to covidData
            covidDataList.add(new CovidData(entryZipInt, entryTime, entryPartialNum, entryFullNum));
        }

        return covidDataList;
    }


}
