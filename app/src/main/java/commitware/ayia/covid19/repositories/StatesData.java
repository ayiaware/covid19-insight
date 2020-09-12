package commitware.ayia.covid19.repositories;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.Country;

public class StatesData {


    private String[] states = {"Lagos","Kano","Abuja", "Katsina", "Bauchi", "Borno", "Jigawa", "Ogun",
            "Gombe", "Kaduna", "Sokoto", "Edo", "Zamfara", "Oyo", "Kwara", "Osun", "Rivers", "Kebbi",
            "Nasarawa", "Delta", "Adamawa", "Yobe", "Plateau", "Ondo", "Taraba", "Akwa Ibom", "Ekiti",
            "Enugu", "Niger", "Ebonyi", "Bayelsa", "Benue", "Imo", "Anambra", "Abia", "Kogi", "Cross River"};


    private List<Country> statesList = new ArrayList<>();

    public List<Country> getStatesList() {

        for (int i= 0; i < states.length; i++ )
        {
            Country country = new Country();
            country.setName(states[i]);
            country.setContinent(String.valueOf(i));
            country.setCode("");
            statesList.add(country);
        }
        return statesList;
    }
}
