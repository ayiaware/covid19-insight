package commitware.ayia.covid19.ui.list;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import commitware.ayia.covid19.models.Insight;

public class ListViewModel extends ViewModel {
    private static final String TAG = ListViewModel.class.getSimpleName();
    // gonna use this to send the insight to listdetailfragment
    // it is not advisable to send objects through the Safe Args

    private final MutableLiveData<Insight> mInsight = new MutableLiveData<>();

    public void setInsight(Insight insight) {
        mInsight.setValue(insight); //
    }

    public LiveData<Insight> getInsight() {
      //  Log.v(TAG, "GET INSIGHT: NAME "+mInsight.getValue().getName());
        return mInsight;
    }




}
