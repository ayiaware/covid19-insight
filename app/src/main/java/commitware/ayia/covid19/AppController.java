package commitware.ayia.covid19;

import android.app.Application;
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

import commitware.ayia.covid19.utils.ThemeChanger;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;


    private String id;
    private String state;
    private String code;
    private String continent;
    private String country;
    private String appType;
    private boolean isConnected;

    private boolean isFirstStart;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        DatabaseReference connection = FirebaseDatabase.getInstance().getReference(".info/isConnected");
        connection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                setConnected(snapshot.getValue(Boolean.class));

                if (isConnected()) {
                    Log.d(TAG, "isConnected");

                } else {
                    Log.d(TAG, "not isConnected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        setFirstStart(getSharedPreferences.getBoolean("firstStart", true));

        setAppType(getSharedPreferences.getString("appType", "covidGlobal"));

        new ThemeChanger(getSharedPreferences.getString("theme", "FollowSystem"));




        if (isFirstStart())
        {
            SharedPreferences.Editor e = getSharedPreferences.edit();
            e.putString("country", "Nigeria");
            e.putString("code", "ng");
            e.putString("continent","Africa");

            e.apply();
        }

        setState(getSharedPreferences.getString("state",null));
        setId(getSharedPreferences.getString("id", null));

        setContinent(getSharedPreferences.getString("continent", null));

        setCountry(getSharedPreferences.getString("country", null));

        setCode(getSharedPreferences.getString("code", null));



    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Boolean isFirstStart() {
        return isFirstStart;
    }

    public void setFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
