package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, File> fileMap = ArgumentParser.processRuntimeArgs(args);

        DataProcessor processor = new DataProcessor(
            fileMap.get("population"),
            fileMap.get("properties"),
            fileMap.get("covid")
        );

//        Analysis analysis = new VaccinationAnalysis();
//        processor.process(analysis, List.of("full", "2021-03-25"));

//        Analysis analysis = new AvgMarketValueAnalysis();
//        processor.process(analysis, List.of("19132"));


    }
}