package commitware.ayia.covid19.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.Location;
import commitware.ayia.covid19.utils.CSVReader;

public class CountriesData {

   private static  List<Location> list;

   static {
       list = new ArrayList<>();
       CSVReader csvReader = new CSVReader();
       try {
           list = csvReader.readCSV();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static List<Location> getList() {
        return list;
    }
}