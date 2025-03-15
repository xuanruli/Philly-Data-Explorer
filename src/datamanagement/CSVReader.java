package datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CSVReader extends DataReader<List<Map<String, String>>> {

    public CSVReader(File file) {
        super(file);
    }

    @Override
    protected List<Map<String, String>> getDataFromFile() throws Exception {
        CharacterReader charReader = new CharacterReader(file.getName());
        CSVParser csvLexer = new CSVParser(charReader);

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
