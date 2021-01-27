package commitware.ayia.covid19.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.models.Location;

public class CSVReader {

    private final BasicApp context;

    //String fileName;

    List<Location> rows = new ArrayList<>();

    public CSVReader() {

        context = BasicApp.getInstance();

     //   this.fileName = fileName;
    }

    public List<Location> readCSV() throws IOException {

        InputStream is = context.getResources().openRawResource(R.raw.locations);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {

            String[] row = line.split(csvSplitBy);

            String continent = row[0];
            String country = row[2];
            String code = row[3];

            rows.add(new Location("", country, continent, code));
        }

        return rows;

    }
}
