package commitware.ayia.covid19.ui.list;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import commitware.ayia.covid19.repositories.CasesDataRepository;
import commitware.ayia.covid19.services.retrofit.cases.ApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.ApiResponseState;

public class ListViewModel extends AndroidViewModel {


    CasesDataRepository mRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        mRepository  = new CasesDataRepository(application);
    }

    public LiveData<ApiResponseState> getCasesStateResponse() {
            return mRepository.getStateCases();
    }

    public LiveData<ApiResponse> getCasesResponse(String location) {
        return mRepository.getCases(location);

    }




}