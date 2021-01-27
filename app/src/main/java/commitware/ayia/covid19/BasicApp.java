package commitware.ayia.covid19;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import commitware.ayia.covid19.models.Location;
import commitware.ayia.covid19.utils.ThemeChanger;

public class BasicApp extends Application {

    public static final String TAG = BasicApp.class.getSimpleName();

    private static BasicApp mInstance;

    private String id;
    private Location mLocation;

    private boolean isFirstStart;

    private AppExecutors mAppExecutors;

    private Context mContext;
    SharedPreferences getSharedPreferences;
    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;

        mContext = this;

        mAppExecutors = new AppExecutors();

        getSharedPreferences = androidx.preference.PreferenceManager
                .getDefaultSharedPreferences(this);

        isFirstStart =  getSharedPreferences.getBoolean("firstStart", true);

        new ThemeChanger(Objects.requireNonNull(getSharedPreferences.getString("theme",
                "FollowSystem")));

        setId(getSharedPreferences.getString("id", null));



    }

    public static synchronized BasicApp getInstance() {
        return mInstance;
    }

    public Context getContext(){
        return mContext;
    }

    public Boolean isFirstStart() {
        return isFirstStart;
    }

    public void setFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
        SharedPreferences.Editor e = getSharedPreferences.edit();
        e.putBoolean("firstStart", isFirstStart);
        e.apply();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {

        this.mLocation = mLocation;
        SharedPreferences.Editor e = getSharedPreferences.edit();
        e.putString("state", mLocation.getState());
        e.putString("country", mLocation.getCountry());
        e.putString("continent",mLocation.getContinent());
        e.putString("code",mLocation.getCountry());
        e.apply();

    }


}
