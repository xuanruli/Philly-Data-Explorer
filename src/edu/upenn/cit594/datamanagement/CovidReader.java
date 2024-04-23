package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.CovidData;
import java.io.File;
import java.util.ArrayList;

public abstract class CovidReader {
    protected ArrayList<CovidData> covidData = new ArrayList<>();

    private static CovidReader readCovidFile(File file) {
        if (file != null && file.exists()){
            String fileType = file.getName().substring(file.getName().lastIndexOf("."));
            if (fileType.equals(".csv") || fileType.equals(".json")) {
                switch(fileType){
                    case ".csv":
                        return new CovidCsvReader(file);
                    case ".json":
                        return new CovidJsonReader(file);
                    default:
                        System.out.println("Invalid file extension.");
                }
            }
        }

        return null;
    }

    public static ArrayList<CovidData> getCovidData(File file) {
        CovidReader reader = readCovidFile(file);
        return reader.covidData;
    }
}
