package commitware.ayia.covid19.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesWrapperState;
import commitware.ayia.covid19.services.retrofit.cases.ApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.ApiResponseState;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import commitware.ayia.covid19.services.retrofit.cases.RetrofitInstance;
import commitware.ayia.covid19.services.retrofit.cases.state.RetrofitInstanceState;
import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasesDataRepository {

    private final Application application;

    MutableLiveData<ApiResponse> ld = new MutableLiveData<>();

    private final MutableLiveData<NewsApiResponse> liveData = new MutableLiveData<>();

    public CasesDataRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<ApiResponse> getCases(String location) {

        RestApiService endpoint = RetrofitInstance.getRetrofitServiceCases();

        Call<Cases> call;

        switch (location){

            case "continent":
                call = endpoint.getCasesContinent(AppController.getInstance().getContinent(), String.valueOf(calTimeDiff()));
                break;
            case "globe":
                call = endpoint.getCasesGlobal( String.valueOf(calTimeDiff()));
                break;
            default:
                call = endpoint.getCasesCountry(AppController.getInstance().getCountry(), String.valueOf(calTimeDiff()));
                break;

        }

        call.enqueue(new Callback<Cases>() {
            @Override
            public void onResponse(@NonNull Call<Cases> call, @NonNull Response<Cases> response) {

                if(response.isSuccessful()) {

                    Cases casesResponse =  response.body();

                    if (casesResponse != null) {

                        ld.setValue(new ApiResponse(casesResponse));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Cases> call, Throwable t) {

                ld.setValue(new ApiResponse(t));

            }
        });

        return ld;
    }

    public MutableLiveData<ApiResponseState> getStateCases() {

        MutableLiveData<ApiResponseState> ldState = new MutableLiveData<>();

        RestApiService endpoint = RetrofitInstanceState.getRetrofitServiceStateCases();

        Call<CasesWrapperState> call =  endpoint.getCasesState();

        call.enqueue(new Callback<CasesWrapperState>() {
            @Override
            public void onResponse(@NonNull Call<CasesWrapperState> call, @NonNull Response<CasesWrapperState> response) {

                if(response.isSuccessful()) {

                    CasesWrapperState casesResponse =  response.body();

                    if (casesResponse != null &&  casesResponse.getCases()!=null) {

                        ldState.postValue(new ApiResponseState(casesResponse.getCases().getCases()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CasesWrapperState> call, Throwable t) {

                ldState.postValue(new ApiResponseState(t));

            }
        });

        return ldState;
    }


    public boolean calTimeDiff() {

        Calendar rightNow = Calendar.getInstance();

        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        return hour >= 6;
    }
}
