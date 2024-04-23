package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.ui.UIManager;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, CSVFormatException, ParseException {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

        CharacterReader charReader = new CharacterReader(fileMap.get("population").getName());
        CSVLexer csvLexer = new CSVLexer(charReader);

        while (true) {
            String[] row = csvLexer.readRow();
            if (row == null) break;

            System.out.println(Arrays.toString(row));
        }

        // "2021-03-25 17:20:02"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2021-03-25 17:20:02");
        System.out.println(date);
    }
}