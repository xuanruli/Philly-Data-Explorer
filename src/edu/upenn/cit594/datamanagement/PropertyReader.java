package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.Property;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertyReader extends DataReader<List<Property>> {
    private final CSVReader csvReader;

    public PropertyReader(File file) throws IOException {
        super(file);
        this.csvReader = new CSVReader(file);
    }

    @Override
    protected List<Property> getDataFromFile() throws Exception {
        List<Map<String, String>> csvData = csvReader.read();

        List<Property> propertyList = new ArrayList<>();
        for (Map<String, String> entry : csvData) {
            // Parse zipcode
            String entryZip = entry.get("zip_code");
            if (Property.isInvalidZip(entryZip)) {
                continue;
            }

            int entryZipInt = Integer.parseInt(entryZip.substring(0, 5));
            Double entryMarketValue = Property.getValidDouble(entry.get("market_value"));
            Double entryArea = Property.getValidDouble(entry.get("total_livable_area"));

            propertyList.add(new Property(entryMarketValue, entryArea, entryZipInt));
        }

        return propertyList;
    }
}
