package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidCsvReader extends DataReader<List<CovidData>> {

    public CovidCsvReader(File file) {
        super(file);
    }

    @Override
    public List<CovidData> getDataFromFile() throws IOException, CSVFormatException {
        CharacterReader charReader = new CharacterReader(file.getName());
        CSVLexer csvLexer = new CSVLexer(charReader);

        String[] headerRow = csvLexer.readRow();
        List<String[]> rowList = new ArrayList<>();
        System.out.println(headerRow);

        while (true) {
            String[] row = csvLexer.readRow();
            System.out.println(row);
            if (row == null) break;

            rowList.add(row);
        }

        return null;
    }
}
