package commitware.ayia.covid19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtils {


    public static final String LIST_REQUEST = "location";

    public static final String LIST_TYPE = "listType";
    public static final String LIST_TYPE_SERVER = "server";
    public static final String LIST_TYPE_LOCAL = "local";

    public static final String LIST_TYPE_SETUP = "setup";


    public static final String LOCATION_STATE = "state";
    public static final String LOCATION_COUNTRY = "country";


    public static final String NO_INFO = "...";


    public static final String LIST_INTENT = "list_intent";
    public static final String SLIDER_INTENT = "slider_intent";




    public static String DateFormat(String oldstringDate){
        String newDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            if (date != null) {
                newDate = dateFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = locale.getLanguage();
        return country.toLowerCase();
    }



}
