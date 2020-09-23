package commitware.ayia.covid19.controllers;

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



    public static final String BASE_URL_NEWS = "https://newsapi.org/v2/";
    public static final String ENDPOINT_TOP_HEADLINE_NEWS = "top-headlines";
    public static final String ENDPOINT_TOP_HEADLINE = "top-headlines";

    public static final String COUNTRY_URL = "https://corona.lmao.ninja/v2/countries/";
    public static final String GLOBE_URL = "https://corona.lmao.ninja/v2/all";
    public static final String CONTINENT_URL = "https://corona.lmao.ninja/v2/continents/";

    public static final String STATE_URL = "https://covidnigeria.herokuapp.com/api";




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
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getLanguage());
        return country.toLowerCase();
    }


}
