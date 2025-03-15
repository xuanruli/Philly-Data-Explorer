package datamanagement;

import util.CovidData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CovidCSVReader extends DataReader<List<CovidData>> {
    private final CSVReader csvReader;

    public CovidCSVReader(File file) throws IOException {
        super(file);
        this.csvReader = new CSVReader(file);
    }

    @Override
    protected List<CovidData> getDataFromFile() throws Exception {
        List<Map<String, String>> csvData = csvReader.read();

        List<CovidData> covidDataList = new ArrayList<>();
        for (Map<String, String> entry : csvData) {
            // Parse zipcode
            String entryZip = entry.get("zip_code");
            if (CovidData.isInvalidZip(entryZip)) {
                continue;
            }

            int entryZipInt = Integer.parseInt(entryZip.substring(0, 5));

            // Parse timestamp
            String entryTime = entry.get("etl_timestamp");
            if (CovidData.isInvalidTimestamp(entryTime)) {
                continue;
            }

            int entryPartialNum = CovidData.getValidVaccinatedCount(entry.get("partially_vaccinated"));
            int entryFullNum = CovidData.getValidVaccinatedCount(entry.get("fully_vaccinated"));

             covidDataList.add(new CovidData(entryZipInt, entryTime, entryPartialNum, entryFullNum));
        }

        return covidDataList;
    }
}
