package commitware.ayia.covid19.models;

public class Summary {

    private String confirmed;
    private String todayConfirmed;
    private String recovered;
    private String todayRecovered;
    private String deaths;
    private String todayDeaths;
    private String critical;
    private String active;
    private String tested;
    private long  updated;
    private String location;
    private String geography;

//    public Summary(String confirmed, String todayConfirmed, String recovered, String deaths, String todayDeaths, String critical, String active) {
//        this.confirmed = confirmed;
//        this.todayConfirmed = todayConfirmed;
//        this.recovered = recovered;
//        this.deaths = deaths;
//        this.todayDeaths = todayDeaths;
//        this.critical = critical;
//        this.active = active;
//    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setCases(String confirmed) {
        this.confirmed = confirmed;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTested() {
        return tested;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public String getTodayConfirmed() {
        return todayConfirmed;
    }

    public void setTodayCases(String todayConfirmed) {
        this.todayConfirmed = todayConfirmed;
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

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }


    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }
}
