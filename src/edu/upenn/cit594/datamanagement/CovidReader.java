package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CovidReader extends DataReader<List<CovidData>> {

    public CovidReader(File file) {
        super(file);
    }

    @Override
    public List<CovidData> getDataFromFile() throws CSVFormatException, IOException, ParseException {
        String fileType = file.getName().substring(file.getName().lastIndexOf("."));

        return switch (fileType) {
            case ".csv" -> {
                CovidCsvReader csvReader = new CovidCsvReader(file);
                yield csvReader.getDataFromFile();
            }
            case ".json" -> {
                CovidJsonReader jsonReader = new CovidJsonReader(file);
                yield jsonReader.getDataFromFile();
            }
            default -> throw new IOException("Invalid file extension.");
        };
    }
}
