package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.*;
import edu.upenn.cit594.ui.UIManager;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class Main {

    public static void main(String[] args) throws Exception {

        Map<String, File> fileMap;
        try {
            fileMap = ArgumentParser.processRuntimeArgs(args);
        } catch (IllegalArgumentException e) {
            System.out.println("BEGIN OUTPUT");
            System.out.println(e.getMessage());
            System.out.println("END OUTPUT");
            return;
        }

        DataProcessor processor = new DataProcessor(
            fileMap.get("population"),
            fileMap.get("properties"),
            fileMap.get("covid")
        );
        UIManager uiManager = new UIManager(processor);

        // Add analysis dependencies
        uiManager.addAnalysis(
            "Show the total population for all ZIP Codes",
            new PopulationAnalysis()
        );
        uiManager.addAnalysis(
            "Show the total vaccinations per capita for each ZIP Code for the specified date",
            new VaccinationPerCapitaAnalysis()
        );
        uiManager.addAnalysis(
            "Show the average market value for properties in a specified ZIP Code",
            new AvgMarketValueAnalysis()
        );
        uiManager.addAnalysis(
            "Show the average total livable area for properties in a specified ZIP Code",
            new AvgLivableAreaAnalysis()
        );
        uiManager.addAnalysis(
            "Show the total market value of properties, per capita, for a specified ZIP Code",
            new MarketValuePerCapitaAnalysis()
        );

        // Run the UI component
        uiManager.run();
    }
}
