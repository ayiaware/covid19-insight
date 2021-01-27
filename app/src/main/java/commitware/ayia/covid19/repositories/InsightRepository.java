package commitware.ayia.covid19.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Calendar;
import java.util.List;

import commitware.ayia.covid19.models.StatesWrapper;
import commitware.ayia.covid19.models.Continent;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.Globe;
import commitware.ayia.covid19.services.retrofit.insight.ResponseContinent;
import commitware.ayia.covid19.services.retrofit.insight.ResponseCountry;
import commitware.ayia.covid19.services.retrofit.insight.ResponseGlobe;
import commitware.ayia.covid19.services.retrofit.insight.state.ResponseState;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import commitware.ayia.covid19.services.retrofit.insight.RetrofitInstance;
import commitware.ayia.covid19.services.retrofit.insight.state.RetrofitInstanceState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightRepository {

    private final Application application;

    private static final String TAG = InsightRepository.class.getSimpleName();

    //observable get states data immediately cases data repository is created

    final MediatorLiveData<ResponseState> mObsStates;

    final MediatorLiveData<ResponseCountry> mObsCountries;

    final MediatorLiveData<ResponseContinent> mObsContinents;

    final MediatorLiveData<ResponseGlobe> mObsGlobal;

    public InsightRepository(Application application) {

        this.application = application;

        mObsStates = new MediatorLiveData<>();

        mObsCountries = new MediatorLiveData<>();

        mObsContinents = new MediatorLiveData<>();

        mObsGlobal = new MediatorLiveData<>();

        mObsStates.addSource(getStatesResponse(), new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState response) {

                mObsStates.postValue(response);

            }
        });

        mObsCountries.addSource(getCountriesResponse(), new Observer<ResponseCountry>() {
            @Override
            public void onChanged(ResponseCountry response) {
                mObsCountries.postValue(response);
            }
        });

        mObsContinents.addSource(getContinentResponse(), new Observer<ResponseContinent>() {
            @Override
            public void onChanged(ResponseContinent response) {
                mObsContinents.postValue(response);
            }
        });

        mObsGlobal.addSource(getGlobalResponse(), new Observer<ResponseGlobe>() {
            @Override
            public void onChanged(ResponseGlobe responseGlobe) {
                mObsGlobal.postValue(responseGlobe);
            }
        });


    }

    public LiveData<ResponseState> getStates(){
        return mObsStates;
    }

    public LiveData<ResponseCountry> getCountries(){
        return mObsCountries;
    }

    public LiveData<ResponseContinent> getContinents(){
        return mObsContinents;
    }

    public LiveData<ResponseGlobe> getGlobal(){
        return mObsGlobal;
    }



    public LiveData<ResponseGlobe> getGlobalResponse() {

        MutableLiveData<ResponseGlobe> liveData = new MutableLiveData<>();

        RestApiService endpoint = RetrofitInstance.getRetrofitServiceInsight();

        Call<Globe>call = endpoint.getGlobal( String.valueOf(calTimeDiff()));

        call.enqueue(new Callback<Globe>() {
            @Override
            public void onResponse(@NonNull Call<Globe> call, @NonNull Response<Globe> response) {

                if(response.isSuccessful()) {

                    Globe insightResponse =  response.body();

                    if (insightResponse != null) {

                        liveData.setValue(new ResponseGlobe(insightResponse));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Globe> call, Throwable t) {

                liveData.setValue(new ResponseGlobe(t));

            }
        });

        return liveData;
    }

    private LiveData<ResponseState> getStatesResponse() {

        RestApiService endpoint = RetrofitInstanceState.getRetrofitServiceStateCases();

        MutableLiveData<ResponseState> liveData = new MutableLiveData<>();

        Call<StatesWrapper> call =  endpoint.getStates();

        call.enqueue(new Callback<StatesWrapper>() {
            @Override
            public void onResponse(@NonNull Call<StatesWrapper> call, @NonNull Response<StatesWrapper> response) {

                Log.v(TAG, "IS GET STATES RESPONSE SUCCESSFUL" + response.isSuccessful());

                if(response.isSuccessful()) {

                    StatesWrapper casesResponse =  response.body();

                    if (casesResponse != null &&  casesResponse.getStates()!= null) {

                        liveData.postValue(new ResponseState(casesResponse.getStates().getList()));

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<StatesWrapper> call, Throwable t) {

                liveData.postValue(new ResponseState(t));

            }
        });

        return liveData;
    }

    private LiveData<ResponseCountry> getCountriesResponse() {

        RestApiService endpoint = RetrofitInstance.getRetrofitServiceInsight();

        MutableLiveData<ResponseCountry> liveData = new MutableLiveData<>();

        Call<List<Country>> call =  endpoint.getCountries(String.valueOf(calTimeDiff()));

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(@NonNull Call<List<Country>> call, @NonNull Response<List<Country>> response) {

                Log.v(TAG, "IS GET COUNTRIES RESPONSE SUCCESSFUL"+response.isSuccessful());

                if(response.isSuccessful()) {

                    List<Country> apiResponse =  response.body();

                    if (apiResponse != null) {

                        liveData.postValue(new ResponseCountry(apiResponse));

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Country>> call, Throwable t) {

                liveData.postValue(new ResponseCountry(t));

            }
        });

        return liveData;
    }

    private LiveData<ResponseContinent> getContinentResponse() {

        RestApiService endpoint = RetrofitInstance.getRetrofitServiceInsight();

        MutableLiveData<ResponseContinent> liveData = new MutableLiveData<>();

        Call<List<Continent>> call =  endpoint.getContinents(String.valueOf(calTimeDiff()));

        call.enqueue(new Callback<List<Continent>>() {
            @Override
            public void onResponse(@NonNull Call<List<Continent>> call, @NonNull Response<List<Continent>> response) {

                Log.v(TAG, "IS GET CONTINENT RESPONSE SUCCESSFUL"+response.isSuccessful());

                if(response.isSuccessful()) {

                    List<Continent> apiResponse =  response.body();

                    if (apiResponse != null) {

                        liveData.postValue(new ResponseContinent(apiResponse));

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Continent>> call, Throwable t) {

                liveData.postValue(new ResponseContinent(t));

            }
        });

        return liveData;
    }


    public boolean calTimeDiff() {

        //this calculation is done based on the time difference between nigeria and the server
        // location in usa nigeria is ahead 6hrs

        Calendar rightNow = Calendar.getInstance();

        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        return hour >= 6;
    }





}
