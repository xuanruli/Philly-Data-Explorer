import datamanagement.*;
import logging.Logger;
import processor.*;
import ui.UIManager;

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

        // create a DataProcessor component
        DataProcessor processor = new DataProcessor(
            fileMap.get("population"),
            fileMap.get("properties"),
            fileMap.get("covid")
        );

        // create a UIManager component
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
        uiManager.addAnalysis(
            "Show the ratio of full vaccination count / total market value per capita in a specified zipcode for a given date",
            new CombinedAnalysis()
        );

        // Run the UI component
        uiManager.run();
    }
}
