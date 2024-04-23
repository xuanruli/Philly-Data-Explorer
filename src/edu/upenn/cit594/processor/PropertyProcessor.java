package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Property;

import java.util.ArrayList;

public class PropertyProcessor {
    ArrayList<Property> property;

    /*
     * handles 3.4 Average Market Value
     */
    public void calculateAverageMarketValue(String userZipcode){
        int userZip = Integer.parseInt(userZipcode);
        int totalMarketValue = 0;
        int propertyCount = 0;
        for (Property p: property){
            if (p.getPropertyZip() == userZip){
                totalMarketValue += p.getMarketValue();
                propertyCount++;
            }
        }

        if (propertyCount == 0 ){
            System.out.println("0");
        } else {
            int averageMarketValue = totalMarketValue/propertyCount;
            System.out.println(averageMarketValue);
        }
    }

    /*
     * handles 3.5 Average Total Livable Area
     */
    public void calculateAverageTotalLivableArea(String userZipcode){
        int userZip = Integer.parseInt(userZipcode);
        int totalLivableArea = 0;
        int propertyCount = 0;
        for (Property p: property){
            if (p.getPropertyZip() == userZip){
                totalLivableArea += p.getArea();
                propertyCount++;
            }
        }

        if (propertyCount == 0 ){
            System.out.println("0");
        } else {
            int averageTotalLivableArea = totalLivableArea/propertyCount;
            System.out.println(averageTotalLivableArea);
        }
    }
}
