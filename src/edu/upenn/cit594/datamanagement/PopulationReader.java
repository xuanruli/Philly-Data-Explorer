package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.Population;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PopulationReader extends DataReader<List<Population>> {

    private final CSVReader csvReader;

    public PopulationReader(File file) throws IOException {
        super(file);
        this.csvReader = new CSVReader(file);
    }

    @Override
    protected List<Population> getDataFromFile() throws Exception {
        List<Map<String, String>> csvData = csvReader.read();

        List<Population> populationList = new ArrayList<>();
        for (Map<String, String> entry : csvData) {
            int entryZip = Integer.parseInt(entry.get("zip_code").substring(0, 5));
            int entryPopulation = Integer.parseInt(entry.get("population"));
            populationList.add(new Population(entryZip, entryPopulation));
        }

        return populationList;
    }
}
