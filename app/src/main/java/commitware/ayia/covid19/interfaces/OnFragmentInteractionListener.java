package commitware.ayia.covid19.interfaces;


import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.CasesList;

public interface OnFragmentInteractionListener {
    void listItemClickServer(CasesList casesList);
    void listItemClickSetting(Country country, String location, String listType);
}
