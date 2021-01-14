package commitware.ayia.covid19.models;
import com.google.gson.annotations.SerializedName;

public class CasesState {

    @SerializedName("confirmedCases")
    private String cases;

    @SerializedName("discharged")
    private String recovered;

    @SerializedName("death")
    private String deaths;

    @SerializedName("casesOnAdmission")
    private String active;


    public String getCases() {
        return cases;
    }

    public void setConfirmedCases(String confirmed) {
        this.cases = confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
