package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.ui.UIManager;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

//        CSVReader reader = new CSVReader(fileMap.get("population"));
//        List<Map<String, String>> populationData = reader.read();
//        System.out.println(populationData);

//        CSVReader reader2 = new CSVReader(fileMap.get("covid"));
//        List<Map<String, String>> covidData = reader2.read();
//        System.out.println(covidData.subList(0, 10));

        CovidCSVReader covidCSVReader = new CovidCSVReader(fileMap.get("covid"));
        List<CovidData> covidDataList = covidCSVReader.read();

        File file = new File("covid_data.json");
        CovidJSONReader covidJSONReader = new CovidJSONReader(file);
        List<CovidData> covidDataList2 = covidJSONReader.read();

        assert covidDataList.size() == covidDataList2.size();
        System.out.println("Size of covidDataList1: " + covidDataList.size());
        System.out.println("Size of covidDataList2: " + covidDataList2.size());
        for (int i = 0; i < covidDataList.size(); i++) {
            if (!covidDataList.get(i).equals(covidDataList2.get(i))) {
                System.out.println("i: " + i);
                System.out.println("covidDataList1: " + covidDataList.get(i));
                System.out.println("covidDataList2: " + covidDataList2.get(i));
            }
        }

        System.out.println("All covid data are the same");
    }
}