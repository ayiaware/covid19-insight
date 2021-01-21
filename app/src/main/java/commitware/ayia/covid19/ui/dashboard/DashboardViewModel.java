package commitware.ayia.covid19.ui.dashboard;



import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

;

import java.util.List;

import commitware.ayia.covid19.models.CasesState;
import commitware.ayia.covid19.repositories.CasesDataRepository;
import commitware.ayia.covid19.services.retrofit.cases.ApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.ApiResponseState;
import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;

public class  DashboardViewModel extends AndroidViewModel {


    CasesDataRepository mRepository;

    MediatorLiveData<ApiResponseState> mObsStates;


    public DashboardViewModel(@NonNull Application application) {

        super(application);

        mRepository  = new CasesDataRepository(application);

        mObsStates = new MediatorLiveData<>();

        mObsStates.addSource(mRepository.getStateCases(), new Observer<ApiResponseState>() {
            @Override
            public void onChanged(ApiResponseState apiResponse) {

                mObsStates.setValue(apiResponse);

            }

        });

    }

    public LiveData<ApiResponseState> getCasesStateResponse() {
        Log.v("DashboardViewModel", "getCasesStateResponse()");
        return mObsStates;
    }

    public LiveData<ApiResponse> getCasesResponse(String location) {
        return mRepository.getCases(location);
    }





}