package commitware.ayia.covid19.services.retrofit.insight;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;

import commitware.ayia.covid19.models.Insight;

import commitware.ayia.covid19.models.Country;


public class ResponseCountry {

    private Country country;

    private List<Country>mCountries;

    private List<Insight> insightList;

    private Throwable error;

    public ResponseCountry(List<Country>countries) {
        mCountries = countries;

        insightList = new ArrayList<>();

        for (Country country:countries) {
            if(country.getName().equals(BasicApp.getInstance().getLocation().getCountry()))
                this.country = country;

            insightList.add( new Insight(country.getName(), Integer.parseInt(country.getCases()),
                    country.getTodayCases(), country.getDeaths(), country.getTodayDeaths(),
                    country.getRecovered(), country.getActive(), country.getCritical(), "",
                    country.getUpdated(), country.getTested()));


        }

        this.error = null;
    }

    public ResponseCountry(Throwable error) {
        this.error = error;
        this.country = null;
        this.insightList = null;
    }

    public Country getCountry() {
        return country;
    }

    public List<Country> getCountries() {
        return mCountries;
    }

    public Throwable getError() {
        return error;
    }

    public List<Insight> getInsightList() {
        return insightList;
    }
}
