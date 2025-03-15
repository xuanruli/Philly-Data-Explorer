package util;

import java.util.Map;

public class Property {
    private final Double marketValue;
    private final Double area;
    private final int propertyZip;

    public Property(Double marketValue, Double area, int propertyZip) {
        this.marketValue = marketValue;
        this.area = area;
        this.propertyZip = propertyZip;
    }

    public static boolean isInvalidZip(String zip){
        if (zip == null || zip.length() < 5) {
            return true;
        }

        String zipcode = zip.substring(0, 5);
        return !zipcode.matches("[0-9]+");
    }

    public static Double getValidDouble(String strDouble) {
        if (strDouble == null) {
            return null;
        }

        try {
            return Double.parseDouble(strDouble);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String displayedMarketValue = marketValue == null ? "null" : marketValue.toString();
        String displayedArea = area == null ? "null" : area.toString();
        return Map.of("market_value", displayedMarketValue, "total_livable_area", displayedArea, "zip_code", Integer.toString(propertyZip)).toString();
    }

    public int getPropertyZip(){
        return propertyZip;
    }

    public Double getArea(){
        return area;
    }

    public Double getMarketValue(){
        return marketValue;
    }
}
