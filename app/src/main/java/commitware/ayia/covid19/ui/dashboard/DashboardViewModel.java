package commitware.ayia.covid19.ui.dashboard;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

;

import commitware.ayia.covid19.repositories.CasesDataRepository;
import commitware.ayia.covid19.services.retrofit.cases.CasesApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.CasesStateApiResponse;

public class  DashboardViewModel extends AndroidViewModel {


    CasesDataRepository mRepository;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        mRepository  = new CasesDataRepository(application);
    }

    public LiveData<CasesStateApiResponse> getCasesStateResponse() {
            return mRepository.getStateCases();
    }

    public LiveData<CasesApiResponse> getCasesResponse(String location) {
        return mRepository.getCases(location);

    }




}