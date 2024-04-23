package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;

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

        return switch (fileType) {
            case ".csv" -> {
                CovidCSVReader csvReader = new CovidCSVReader(file);
                yield csvReader.getDataFromFile();
            }
            case ".json" -> {
                CovidJSONReader jsonReader = new CovidJSONReader(file);
                yield jsonReader.getDataFromFile();
            }
            default -> throw new IOException("Invalid file extension.");
        };
    }

}
