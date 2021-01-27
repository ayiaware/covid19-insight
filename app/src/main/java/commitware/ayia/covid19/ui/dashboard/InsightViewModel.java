package commitware.ayia.covid19.ui.dashboard;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

;

import commitware.ayia.covid19.repositories.InsightRepository;
import commitware.ayia.covid19.services.retrofit.insight.ResponseContinent;
import commitware.ayia.covid19.services.retrofit.insight.ResponseCountry;
import commitware.ayia.covid19.services.retrofit.insight.ResponseGlobe;
import commitware.ayia.covid19.services.retrofit.insight.state.ResponseState;

public class InsightViewModel extends AndroidViewModel {


    InsightRepository mRepository;
    //observable states
    LiveData<ResponseState> mObsStates;
    LiveData<ResponseCountry> mObsCountries;
    LiveData<ResponseContinent> mObsContinent;
    LiveData<ResponseGlobe>  mObsGlobal;

    public InsightViewModel(@NonNull Application application) {

        super(application);

        mRepository  = new InsightRepository(application);

        mObsStates = mRepository.getStates();

        mObsCountries  = mRepository.getCountries();

        mObsContinent = mRepository.getContinents();

        mObsGlobal = mRepository.getGlobal();


    }

    public LiveData<ResponseState> getObsStates() {
        return mObsStates;
    }
    public LiveData<ResponseGlobe> getObsGlobal() {
        return mObsGlobal;
    }
    public LiveData<ResponseCountry> getObsCountries() {
        return mObsCountries;
    }

    public LiveData<ResponseContinent> getObsContinent() {
        return mObsContinent;
    }



}