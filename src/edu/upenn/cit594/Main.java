package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.ArgumentParser;
import edu.upenn.cit594.datamanagement.CovidReader;
import edu.upenn.cit594.ui.UIManager;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

        List<CovidData> covidDataList = CovidReader.getCovidData(fileMap.get("covid"));

        System.out.println(covidDataList);
        // UIManager.displayMenu();
    }

}