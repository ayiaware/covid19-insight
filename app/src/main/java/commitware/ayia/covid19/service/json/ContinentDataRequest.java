package commitware.ayia.covid19.service.json;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.AppUtilsController;
import commitware.ayia.covid19.models.Summary;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ContinentDataRequest {


    private String url;

    public ContinentDataRequest() {
        AppUtilsController appUtilsController = new AppUtilsController();
        url = appUtilsController.getContinentUrl();
    }

    public MutableLiveData<Summary> parseJSON() {

        final MutableLiveData<Summary> mutableLiveData = new MutableLiveData<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    String cases = response.getString("cases");
                    String todayCases = response.getString("todayCases");
                    String deaths = response.getString("deaths");
                    String todayDeaths = response.getString("todayDeaths");
                    String recovered = response.getString("recovered");
                    String active = response.getString("active");
                    String critical = response.getString("critical");
//                    String tested = response.getString("tests");
                    Long updated = response.getLong("updated");
                    String continent = response.getString("continent");

                    final Summary summary = new Summary();

                    summary.setCases(cases);
                    summary.setTodayCases(todayCases);
                    summary.setDeaths(deaths);
                    summary.setTodayDeaths(todayDeaths);
                    summary.setRecovered(recovered);
                    summary.setActive(active);
                    summary.setCritical(critical);
                    summary.setTested("N/A");
                    summary.setUpdated(updated);
                    summary.setLocation(continent);
                    summary.setGeography("continent");
                    mutableLiveData.setValue(summary);



                } catch (JSONException e) {
                    e.printStackTrace();
                    VolleyLog.d(TAG, "Error: " + e.getMessage());
                }

            }


        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

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
