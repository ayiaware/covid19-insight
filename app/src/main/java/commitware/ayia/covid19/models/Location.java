package commitware.ayia.covid19.models;

import commitware.ayia.covid19.utils.AppUtils;

public class Location {

    private final String country;
    private final String state;
    private final String continent;
    private final String code;
    private String name ;

    public Location(String state, String country, String continent, String code) {

        this.country = country;
        this.state = state;
        this.continent = continent;
        this.code = code;

        if (country!=null)
            if(state.equals(""))
            name = country;
        else
            name = state;

    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
