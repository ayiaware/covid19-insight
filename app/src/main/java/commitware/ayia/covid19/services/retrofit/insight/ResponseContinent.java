package commitware.ayia.covid19.services.retrofit.insight;



import java.util.ArrayList;
import java.util.List;


import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.models.Insight;
import commitware.ayia.covid19.models.Continent;



public class ResponseContinent {

    private Continent continent;

    private List<Continent>continents;

    private List<Insight> insightList;

    private Throwable error;

    public ResponseContinent(List<Continent>continents) {

        this.continents = continents;

        insightList = new ArrayList<>();

        for (Continent continent:continents) {

            if(continent.getName().equals(BasicApp.getInstance().getLocation().getContinent()))
                this.continent = continent;

            insightList.add( new Insight(continent.getName(), Integer.parseInt(continent.getCases()),
                    continent.getTodayCases(), continent.getDeaths(), continent.getTodayDeaths(),
                    continent.getRecovered(), continent.getActive(), continent.getCritical(),
                    "", continent.getUpdated(),continent.getTested()));


        }

        this.error = null;
    }

    public ResponseContinent(Throwable error) {
        this.error = error;
        this.continent = null;
        this.insightList = null;
    }


    public List<Continent> getContinents() {
        return continents;
    }

    public Throwable getError() {
        return error;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public List<Insight> getInsightList() {
        return insightList;
    }
}
