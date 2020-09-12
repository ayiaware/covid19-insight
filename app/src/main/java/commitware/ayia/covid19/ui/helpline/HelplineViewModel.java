package commitware.ayia.covid19.ui.helpline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;




public class HelplineViewModel extends ViewModel {


    private MutableLiveData<String> mText;


    public HelplineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



}