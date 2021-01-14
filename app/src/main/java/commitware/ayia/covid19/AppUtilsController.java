package commitware.ayia.covid19;


import java.util.Calendar;


public class AppUtilsController {

    private String stateUrl;
    private String continentUrl;
    private String countryUrl;
    private String globalUrl;

    public AppUtilsController() {

        Calendar rightNow = Calendar.getInstance();

        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        boolean getYesterday = hour >= 6;

        String yesterday = "true";

        if(!getYesterday) {
            yesterday = "false";
        }


    }

    public String getStateUrl() {
        return stateUrl;
    }

    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }

    public String getContinentUrl() {
        return continentUrl;
    }

    public void setContinentUrl(String continentUrl) {
        this.continentUrl = continentUrl;
    }

    public String getCountryUrl() {
        return countryUrl;
    }

    public void setCountryUrl(String countryUrl) {
        this.countryUrl = countryUrl;
    }


    public String getGlobalUrl() {
        return globalUrl;
    }

    public void setGlobalUrl(String globalUrl) {
        this.globalUrl = globalUrl;
    }
}
