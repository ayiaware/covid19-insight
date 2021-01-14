package commitware.ayia.covid19.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import commitware.ayia.covid19.adapter.RvAdapter;
import commitware.ayia.covid19.adapter.RvAdapterLocal;
import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.interfaces.OnFragmentInteractionListener;
import commitware.ayia.covid19.interfaces.RVClickListener;
import commitware.ayia.covid19.listeners.RVTouchListener;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.CountryServer;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.repositories.CountriesData;
import commitware.ayia.covid19.repositories.StatesData;

import static commitware.ayia.covid19.AppUtils.LIST_TYPE_LOCAL;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_SERVER;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_SETUP;
import static commitware.ayia.covid19.AppUtils.NO_INFO;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;



    private static final String ARG_GET_LOCATIONS= "location";
    private static final String ARG_GET_LIST= "listType";


    private static final String TAG = ListActivityFragment.class.getSimpleName();


    private String location;
    private String listType;

    private List<CountryServer> serverLocationList;
    private List<Country> localLocationList;

    private String url;

    private OnFragmentInteractionListener mListener;

    boolean isNetworkOk;
    private RvAdapter rvAdapterServer;
    private RvAdapterLocal rvAdapterLocal;

    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    public ListActivityFragment() {

    }

    public static ListActivityFragment newInstance(String param1, String param2) {
        ListActivityFragment fragment = new ListActivityFragment();

        Bundle args = new Bundle();
        args.putString(ARG_GET_LOCATIONS, param1);
        args.putString(ARG_GET_LIST, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = getArguments().getString(ARG_GET_LOCATIONS);
            listType = getArguments().getString(ARG_GET_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        // set has option menu as true because we have menu
        setHasOptionsMenu(true);
        setMenuVisibility(false);
        // isNetworkOk = new NetworkConnectivity(getActivity()).requestDataCallback(true);

        // call view
        recyclerView = root.findViewById(R.id.rvList);
        progressBar = root.findViewById(R.id.progress_circular_country);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //  dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        // call Volley method
        //call list
        errorLayout.setVisibility(View.GONE);

        if(listType.equals(LIST_TYPE_SERVER))
        {
            switch (location) {
                case "state":

                    url = "https://covidnigeria.herokuapp.com/api";
                    parseJSON();
                    break;
                case "continent":
                    url = "https://corona.lmao.ninja/v2/continents";
                    getDataFromServerSortTotalCases();
                    break;
                case "country":
                    url = "https://corona.lmao.ninja/v2/countries";
                    getDataFromServerSortTotalCases();
                    break;
                default:
                    Toast.makeText(getActivity(), "No location", Toast.LENGTH_LONG).show();
                    break;
            }
            if (location.equals("country"))
            {
                getActivity().setTitle( " countries");
            }
            else {
                getActivity().setTitle(location+"s");
            }
        }
        else if(listType.equals(LIST_TYPE_LOCAL))
        {


            getDataFromLocal();
        }
        else if(listType.equals(LIST_TYPE_SETUP))
        {


            getDataFromLocal();
        }






        return root;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    private void showServerRecyclerView() {
        rvAdapterServer = new RvAdapter(getActivity(), serverLocationList);
        if(serverLocationList.isEmpty())
        {

            showErrorMessage(
                    R.drawable.oops,
                    "Oops..",
                    "Check internet");
        }
        else {
            setMenuVisibility(true);
        }
        recyclerView.setAdapter(rvAdapterServer);
        recyclerView.addOnItemTouchListener(new RVTouchListener(getActivity(), recyclerView, new RVClickListener() {
            @Override
            public void onClick(View view, int position) {

                final CountryServer countryServerItem = rvAdapterServer.getmValuesFilteredList().get(position);

                onMenuItemClickServer(countryServerItem);
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));


    }
    private void showLocalRecyclerView() {
        setMenuVisibility(true);
        rvAdapterLocal = new RvAdapterLocal(getActivity(), localLocationList);
        recyclerView.setAdapter(rvAdapterLocal);
        progressBar.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(new RVTouchListener(getActivity(), recyclerView, new RVClickListener() {
            @Override
            public void onClick(View view, int position) {

                final Country country = rvAdapterLocal.getmValuesFilteredList().get(position);

                onMenuItemClickSetting(country,location,listType);

            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

    }

    private void getDataFromServerSortTotalCases() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        serverLocationList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            // Extract JSONObject inside JSONObject


                            switch (location) {
                                case "country":
                                    JSONObject countryInfo = data.getJSONObject("countryInfo");
                                    serverLocationList.add(new CountryServer(
                                            data.getString("country"), data.getInt("cases"),
                                            data.getString("todayCases"), data.getString("deaths"),
                                            data.getString("todayDeaths"), data.getString("recovered"),
                                            data.getString("active"), data.getString("critical"),
                                            data.getString("tests")
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " countries");
                                    break;
                                case "continent":

                                    serverLocationList.add(new CountryServer(
                                            data.getString("continent"), data.getInt("cases"),
                                            data.getString("todayCases"), data.getString("deaths"),
                                            data.getString("todayDeaths"), data.getString("recovered"),
                                            data.getString("active"), data.getString("critical"),
                                            NO_INFO
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " continents");
                                    break;
                                case "state":
                                    //  int cases = Integer.parseInt(data.getString("No_of_cases").replaceAll(",", ""));

                                    int cases = Integer.parseInt(data.getString("confirmedCases"));

                                    serverLocationList.add(new CountryServer(
                                            data.getString("state"), cases,
                                            NO_INFO, data.getString("deaths"),
                                            NO_INFO, data.getString("discharged"),
                                            data.getString("casesOnAdmission"), NO_INFO,
                                            NO_INFO
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " states");
                                    break;
                            }

                        }

                        // sort descending
                        Collections.sort(serverLocationList, new Comparator<CountryServer>() {
                            @Override
                            public int compare(CountryServer o1, CountryServer o2) {
                                if (o1.getConfirmed() > o2.getConfirmed()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

                        // Action Bar Title
                        // getActivity().setTitle(jsonArray.length() + " "+location);

                        showServerRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    showErrorMessage(
                            R.drawable.no_result,
                            "Check your Network",
                            "Try Again!");

                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onResponse: " + error);
                        showErrorMessage(
                                R.drawable.no_result,
                                "Check your Network",
                                "Try Again!");
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void getDataFromServerSortAlphabet() {
        errorLayout.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        serverLocationList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);


                            // Extract JSONObject inside JSONObject
                            switch (location) {
                                case "country":
                                    JSONObject countryInfo = data.getJSONObject("countryInfo");
                                    serverLocationList.add(new CountryServer(
                                            data.getString("country"), data.getInt("cases"),
                                            data.getString("todayCases"), data.getString("deaths"),
                                            data.getString("todayDeaths"), data.getString("recovered"),
                                            data.getString("active"), data.getString("critical"),
                                            data.getString("tests")
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " countries");
                                    break;
                                case "continent":

                                    serverLocationList.add(new CountryServer(
                                            data.getString("continent"), data.getInt("cases"),
                                            data.getString("todayCases"), data.getString("deaths"),
                                            data.getString("todayDeaths"), data.getString("recovered"),
                                            data.getString("active"), data.getString("critical"),
                                            NO_INFO
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " continents");
                                    break;
                                case "state":
                                    //  int cases = Integer.parseInt(data.getString("No_of_cases").replaceAll(",", ""));

                                    int cases = Integer.parseInt(data.getString("confirmedCases"));

                                    serverLocationList.add(new CountryServer(
                                            data.getString("state"), cases,
                                            NO_INFO, data.getString("deaths"),
                                            NO_INFO, data.getString("discharged"),
                                            data.getString("casesOnAdmission"), NO_INFO,
                                            NO_INFO
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " states");
                                    break;
                            }
                        }

                        // Action Bar Title


                        showServerRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: " + error);showErrorMessage(
                                R.drawable.no_result,
                                "Check your Network",
                                "Try Again!\n");

                    }
                });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void parseJSON() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                //  Log.e(TAG, "onResponse: " + response.toString());
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);

                    try {
                        // Parsing json object response
                        // response will be a json object

                        // String cases = response.getString("data");
                        serverLocationList = new ArrayList<>();
                        JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("states");




                        //String critical = response.getString("critical");
                        // String tested = response.getString("tests");
                        //  long updated = response.getLong("updated");


                        //Toast.makeText(getContext(),""+jsonObject.getString("death"),Toast.LENGTH_SHORT).show();

                        //   Toast.makeText(getContext(),""+deaths,Toast.LENGTH_SHORT).show();

                        // Log.e(TAG, "onResponse: " + data.toString());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);


                            int cases = Integer.parseInt(data.getString("confirmedCases"));

                            //JSONObject data = jsonArray.getJSONObject(0);
                            String state = data.getString("state");


                            //  String cases = data.getString("confirmedCases");
                            // String todayCases = response.getString("todayCases");
                            String deaths = data.getString("death");
                            // String todayDeaths = response.getString("todayDeaths");
                            String recovered = data.getString("discharged");
                            String active = data.getString("casesOnAdmission");

                            serverLocationList.add(new CountryServer(
                                    state, cases,
                                    NO_INFO, deaths,
                                    NO_INFO, recovered,
                                    active, NO_INFO,
                                    NO_INFO
                            ));
                            getActivity().setTitle(jsonArray.length() + " states");
                        }

                        // sort descending
                        Collections.sort(serverLocationList, new Comparator<CountryServer>() {
                            @Override
                            public int compare(CountryServer o1, CountryServer o2) {
                                if (o1.getConfirmed() > o2.getConfirmed()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

                        showServerRecyclerView();
                    } catch (JSONException e) {

                        showErrorMessage(
                                R.drawable.no_result,
                                "Error",
                                "Try Again!");
                        e.printStackTrace();
                        Log.d(TAG, "Error: " + e.getMessage());
                    }
                }

                else{

                    showErrorMessage(
                            R.drawable.no_result,
                            "Offline Data Unavailable",
                            "Connect to Internet and Try Again!");

                }

            }


        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        if(serverLocationList.isEmpty())
                        {
                            showErrorMessage(
                                    R.drawable.no_result,
                                    "Check Internet",
                                    "Try Again!");
                        }

                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    private void getDataFromLocal()
    {
        localLocationList = new ArrayList<>();
        switch (location)
        {

            case "state":
                StatesData statesData = new StatesData();
                localLocationList = statesData.getStatesList();
                break;
            case "country":
                CountriesData countriesData = new CountriesData();
                localLocationList = countriesData.getCountryList();
                break;
        }

        getActivity().setTitle("select "+location);
        showLocalRecyclerView();

    }


    private void showErrorMessage(int imageView, String title, String message){
        progressBar.setVisibility(View.GONE);
        setMenuVisibility(false);
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if(listType.equals(LIST_TYPE_SERVER))
                {
                    if (location.equals("state"))
                    {
                        parseJSON();
                    }
                    else {
                        getDataFromServerSortTotalCases();
                    }
                }

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        MenuItem itemCases = menu.findItem(R.id.action_sort_cases);
        MenuItem itemAlpaha = menu.findItem(R.id.action_sort_alpha);
        if(listType.equals(LIST_TYPE_LOCAL)||location.equals("state"))
        {
            itemCases.setVisible(false);
            itemAlpaha.setVisible(false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (rvAdapterServer != null) {
                    rvAdapterServer.getFilter().filter(newText);
                }
                else if (rvAdapterLocal != null)
                {
                    rvAdapterLocal.getFilter().filter(newText);
                }
                return true;
            }
        });

        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_alpha:

                if(listType.equals(LIST_TYPE_SERVER))
                {
                    Toast.makeText(getContext(), "Sort Alphabetically", Toast.LENGTH_SHORT).show();
                    serverLocationList.clear();
                    progressBar.setVisibility(View.VISIBLE);
                    getDataFromServerSortAlphabet();
                }

                return true;

            case R.id.action_sort_cases:
                if(listType.equals(LIST_TYPE_SERVER))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    serverLocationList.clear();
                    Toast.makeText(getContext(), "Sort by Total Cases", Toast.LENGTH_SHORT).show();
                    getDataFromServerSortTotalCases();
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    public void onMenuItemClickServer(CountryServer countryServer) {
        if (mListener != null) {
            getActivity().setTitle(countryServer.getmCovidCountry());
            mListener.listItemClickServer(countryServer);

        }
    }

    public void onMenuItemClickSetting(Country Country, String location, String listType) {
        if (mListener != null) {
            mListener.listItemClickSetting(Country, location, listType);

        }
    }



}
