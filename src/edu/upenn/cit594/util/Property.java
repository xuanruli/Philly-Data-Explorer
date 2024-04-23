package edu.upenn.cit594.util;

public class Property {
    private final int marketValue;
    private final int area;
    private final int propertyZip;

    public Property(int marketValue, int area, int propertyZip){
        this.marketValue = marketValue;
        this.area = area;
        this.propertyZip = propertyZip;
    }

    public int getPropertyZip(){
        return propertyZip;
    }

    public int getArea(){
        return area;
    }

    public int getMarketValue(){
        return marketValue;
    }
}
