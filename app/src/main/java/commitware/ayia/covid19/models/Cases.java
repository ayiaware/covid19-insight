package commitware.ayia.covid19.models;
import com.google.gson.annotations.SerializedName;
public class Cases {

    @SerializedName("cases")
    private String cases;

    @SerializedName("todayCases")
    private String todayCases;

    @SerializedName("recovered")
    private String recovered;

    @SerializedName("deaths")
    private String deaths;

    @SerializedName("todayDeaths")
    private String todayDeaths;

    @SerializedName("critical")
    private String critical;

    @SerializedName("active")
    private String active;

    @SerializedName("tests")
    private String tested;

    @SerializedName("updated")
    private long updated;

    public Cases() {
    }

    public Cases(String cases, String todayCases, String recovered, String deaths, String todayDeaths,
                 String critical, String active, String tested, long updated) {
        this.cases = cases;
        this.todayCases = todayCases;
        this.recovered = recovered;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.critical = critical;
        this.active = active;
        this.tested = tested;
        this.updated = updated;
    }

    public String getCases() {
        return cases;
    }

    public void setConfirmedCases(String confirmed) {
        this.cases = confirmed;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getTested() {
        return tested;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
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

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
