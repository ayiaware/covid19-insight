package commitware.ayia.covid19.ui.dashboard;



import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

;
import commitware.ayia.covid19.models.Summary;

import commitware.ayia.covid19.service.json.ContinentDataRequest;
import commitware.ayia.covid19.service.json.CountryDataRequest;
import commitware.ayia.covid19.service.json.GlobeDataRequest;
import commitware.ayia.covid19.service.json.StateDataRequest;

public class  DashboardViewModel extends ViewModel {

    private MutableLiveData<Summary> mutableLiveData;

    public void getDashboardViewModel(String request, Context context) {
        switch (request)
        {
            case "state":
                StateDataRequest stateDataRequest = new StateDataRequest();
                mutableLiveData = stateDataRequest.parseJSON();
                break;
                case "country":
                CountryDataRequest countryDataRequest = new CountryDataRequest(context);
                mutableLiveData = countryDataRequest.parseJSON();
                break;
            case "continent":
                ContinentDataRequest continentDataRequest = new ContinentDataRequest();
                mutableLiveData = continentDataRequest.parseJSON();
                break;
            case "globe":
                GlobeDataRequest globeDataRequest = new GlobeDataRequest();
                mutableLiveData = globeDataRequest.parseJSON();
                break;
        }
    }

    public LiveData<Summary> getStatistics() {

        return mutableLiveData;
    }




}