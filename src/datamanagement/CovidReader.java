package datamanagement;

import util.CovidData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CovidReader extends DataReader<List<CovidData>> {

    public CovidReader(File file) {
        super(file);
    }

    @Override
    protected List<CovidData> getDataFromFile() throws Exception {
        String fileType = file.getName().substring(file.getName().lastIndexOf("."));

        switch (fileType) {
            case ".csv":
                CovidCSVReader csvReader = new CovidCSVReader(file);
                return csvReader.getDataFromFile();

            case ".json":
                CovidJSONReader jsonReader = new CovidJSONReader(file);
                return jsonReader.getDataFromFile();

            default:
                throw new IOException("Invalid file extension.");
        }
    }

}
