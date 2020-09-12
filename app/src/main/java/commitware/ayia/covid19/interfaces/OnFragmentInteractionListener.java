package commitware.ayia.covid19.interfaces;


import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.CountryServer;

public interface OnFragmentInteractionListener {
    void listItemClickServer(CountryServer countryServer);
    void listItemClickSetting(Country country, String location, String listType);
}
