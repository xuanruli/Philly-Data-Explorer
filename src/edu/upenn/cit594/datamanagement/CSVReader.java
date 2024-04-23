package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CSVReader extends DataReader<List<Map<String, String>>> {

    private final CSVParser csvLexer;

    public CSVReader(File file) throws IOException {
        super(file);

        CharacterReader charReader = new CharacterReader(file.getName());
        this.csvLexer = new CSVParser(charReader);
    }

    @Override
    protected List<Map<String, String>> getDataFromFile() throws Exception {
        List<String[]> rowList = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<>();

        String[] headerRow = csvLexer.readRow();
        while (true) {
            String[] row = csvLexer.readRow();

            if (row == null) {
                break;
            }

            assert row.length == headerRow.length;
            rowList.add(row);
        }

        for (String[] row : rowList) {
            data.add(createRowMap(headerRow, row));
        }

        return data;
    }

    private Map<String, String> createRowMap(String[] headerRow, String[] row) {
        Map<String, String> rowMap = new HashMap<>();

        for (int i = 0; i < headerRow.length; i++) {
            rowMap.put(headerRow[i], row[i]);
        }

        return rowMap;
    }
}
