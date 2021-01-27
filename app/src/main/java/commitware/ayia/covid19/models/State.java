package commitware.ayia.covid19.models;
import com.google.gson.annotations.SerializedName;

import commitware.ayia.covid19.utils.AppUtils;

public class State {
    
    @SerializedName("confirmedCases")
    private String cases;

    private String todayCases = AppUtils.NO_INFO;

    @SerializedName("discharged")
    private String recovered;

    @SerializedName("death")
    private String deaths;

    private String todayDeaths = AppUtils.NO_INFO;

    private String critical= AppUtils.NO_INFO;

    @SerializedName("casesOnAdmission")
    private String active;

    private String tested = AppUtils.NO_INFO;

    @SerializedName("state")
    private String name;

    private long updated = Long.parseLong(AppUtils.BLANK);

    public String getCases() {
        return cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getCritical() {
        return critical;
    }

    public String getActive() {
        return active;
    }

    public String getTested() {
        return tested;
    }

    public String getName() {
        return name;
    }

    public long getUpdated() {
        return updated;
    }

}
