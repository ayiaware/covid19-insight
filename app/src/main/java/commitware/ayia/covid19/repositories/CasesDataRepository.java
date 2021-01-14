package commitware.ayia.covid19.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesWrapper;
import commitware.ayia.covid19.services.retrofit.cases.CasesApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.CasesStateApiResponse;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import commitware.ayia.covid19.services.retrofit.cases.RetrofitInstanceCases;
import commitware.ayia.covid19.services.retrofit.cases.state.RetrofitInstanceCasesState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasesDataRepository {

    private final Application application;

    MutableLiveData<CasesApiResponse> ld = new MutableLiveData<>();

    public CasesDataRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<CasesApiResponse> getCases(String location) {

        RestApiService endpoint = RetrofitInstanceCases.getRetrofitServiceCases();

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

                        ld.setValue(new CasesApiResponse(casesResponse));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Cases> call, Throwable t) {

                ld.setValue(new CasesApiResponse(t));

            }
        });

        return ld;
    }

    public MutableLiveData<CasesStateApiResponse> getStateCases() {

        MutableLiveData<CasesStateApiResponse> ldState = new MutableLiveData<>();

        RestApiService endpoint = RetrofitInstanceCasesState.getRetrofitServiceStateCases();

        Call<CasesWrapper> call =  endpoint.getCasesState();

        call.enqueue(new Callback<CasesWrapper>() {
            @Override
            public void onResponse(@NonNull Call<CasesWrapper> call, @NonNull Response<CasesWrapper> response) {

                if(response.isSuccessful()) {

                    CasesWrapper casesResponse =  response.body();

                    if (casesResponse != null &&  casesResponse.getCases()!=null) {

                        ldState.setValue(new CasesStateApiResponse(casesResponse.getCases().getCases()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CasesWrapper> call, Throwable t) {

                ldState.setValue(new CasesStateApiResponse(t));

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
