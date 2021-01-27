package commitware.ayia.covid19.models;
import android.os.Parcel;
import android.os.Parcelable;

public class Insight{

    String mName, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mActive, mCritical, mFlags, mTested;

    long mUpdated;

    int mCases;

    public Insight(String name, int mCases, String mTodayCases, String mDeaths, String mTodayDeaths,
                   String mRecovered, String mActive, String mCritical, String mFlags, long mUpdated, String mTested) {
        this.mName = name;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mActive = mActive;
        this.mCritical = mCritical;
        this.mFlags = mFlags;
        this.mUpdated = mUpdated;
        this.mTested =mTested;
    }

    public String getName() {
        return mName;
    }

    public int getCases() {
        return mCases;
    }

    public String getTodayCases() {
        return mTodayCases;
    }

    public String getDeaths() {
        return mDeaths;
    }

    public String getTodayDeaths() {
        return mTodayDeaths;
    }

    public String getRecovered() {
        return mRecovered;
    }

    public String getActive() {
        return mActive;
    }

    public String getCritical() {
        return mCritical;
    }

    public String getFlags() {
        return mFlags;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public String getTested() {
        return mTested;
    }


}

