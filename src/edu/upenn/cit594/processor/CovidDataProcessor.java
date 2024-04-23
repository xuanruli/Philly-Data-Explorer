package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CovidDataProcessor {
    private ArrayList<CovidData> covidData;
    private File file;

    private static CovidDataProcessor instance;

    public static CovidDataProcessor getInstance() {
        if(instance == null) instance = new CovidDataProcessor();
        return instance;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /*
     * Total Partial or Full Vaccinations Per Capita
     */
    public void getVaxPerCapitaByDate(String condition, ArrayList<Population> population, String date){
        Map<Integer, Float> map = new TreeMap<>();
        float vaxPerCapita;
        int count = 0;
        int populationCount = 0;
        for (CovidData data: covidData){
            String parsedDate = data.getTimestamp().split(" ")[0];
            if (parsedDate.equals(date)){
                if (condition.equals("partial")){
                    count = data.getNumPartiallyVaccinated();
                } else if (condition.equals("full")){
                    count = data.getNumFullyVaccinated();
                }
                int relatedZip = data.getCovidDataZip();
                for (Population p: population){
                    if (p.getPopulationZip() == (relatedZip)){
                        populationCount = p.getPopulation();
                        if (populationCount != 0){
                            vaxPerCapita = (float) count /populationCount;
                            DecimalFormat df = new DecimalFormat("#.####"); // Format to four decimal places
                            vaxPerCapita = Float.parseFloat(df.format(vaxPerCapita));
                            map.put(relatedZip, vaxPerCapita);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("BEGIN OUTPUT");
        for (Map.Entry<Integer, Float> entry : map.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        System.out.println("END OUTPUT");
    }
}
