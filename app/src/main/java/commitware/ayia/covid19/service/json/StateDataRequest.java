package commitware.ayia.covid19.service.json;

import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.AppUtilsController;
import commitware.ayia.covid19.models.Summary;

import static com.android.volley.VolleyLog.TAG;
import static commitware.ayia.covid19.AppUtils.NO_INFO;


public class StateDataRequest {

    private String url;


    public StateDataRequest() {

        AppUtilsController appUtilsController = new AppUtilsController();
        url = appUtilsController.getStateUrl();

    }

    public MutableLiveData<Summary> parseJSON() {

        final MutableLiveData<Summary> mutableLiveData = new MutableLiveData<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);

                    try {

                        JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("states");
                        Summary getSummary = new Summary();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            String state = data.getString("state");


                            if(state.toLowerCase().equals(AppController.getInstance().getState().toLowerCase()))
                            {

                                final Summary summary = new Summary();
                                String cases = data.getString("confirmedCases");
                                String deaths = data.getString("death");
                                String recovered = data.getString("discharged");
                                String active = data.getString("casesOnAdmission");


                                summary.setCases(cases);
                                summary.setTodayCases(NO_INFO);
                                summary.setDeaths(deaths);
                                summary.setTodayDeaths(NO_INFO);
                                summary.setRecovered(recovered);
                                summary.setActive(active);
                                summary.setCritical(NO_INFO);
                                summary.setTested(NO_INFO);
                                summary.setUpdated(System.currentTimeMillis());
                                summary.setLocation(state);
                                summary.setGeography("state");

                                getSummary = summary;
                            }

                        }

                        mutableLiveData.setValue(getSummary);

                        // showServerRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "Error: " + e.getMessage());
                    }
                }
            }


        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());

                    }
                }) {

            //cache for 24 if user not connected to internet
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {

                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        return mutableLiveData;

    }




}
