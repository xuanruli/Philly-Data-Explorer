package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Population;
import java.util.ArrayList;
import edu.upenn.cit594.ui.UIManager;

public class PopulationProcessor {
    ArrayList<Population> population;


    /*
     * handles 3.2 Total Population for All ZIP Codes
     */
    public void calculateTotalPopulation(){
        int sum = 0;
        for (Population p: population){
            sum += p.getPopulation();
        }
        System.out.println(sum);
        UIManager.displayMenu();
    }
}
