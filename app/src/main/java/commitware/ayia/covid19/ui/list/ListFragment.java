package commitware.ayia.covid19.ui.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Adapter;
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
import commitware.ayia.covid19.adapter.RvAdapterState;
import commitware.ayia.covid19.interfaces.OnFragmentInteractionListener;
import commitware.ayia.covid19.interfaces.RVClickListener;
import commitware.ayia.covid19.listeners.RVTouchListener;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.CasesList;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.repositories.CountriesData;
import commitware.ayia.covid19.repositories.StatesData;
import commitware.ayia.covid19.services.retrofit.cases.state.ApiResponseState;
import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;
import commitware.ayia.covid19.ui.dashboard.DashboardViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_LOCAL;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_SERVER;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_SETUP;
import static commitware.ayia.covid19.AppUtils.NO_INFO;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;



    private static final String ARG_GET_LOCATIONS= "location";
    private static final String ARG_GET_LIST= "listType";


    private static final String TAG = ListFragment.class.getSimpleName();


    private String location;
    private String listType;

    private List<CasesList> serverLocationList;
    private List<Country> localLocationList;

    private String url;

    private OnFragmentInteractionListener mListener;

    boolean isNetworkOk;
    private RvAdapter rvAdapterServer;
    private RvAdapterLocal rvAdapterLocal;

    private RvAdapterState adapterState;

    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;


    DashboardViewModel dashboardViewModel;

    public ListFragment() {

    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();

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

        // call view
        recyclerView = root.findViewById(R.id.rvList);
        progressBar = root.findViewById(R.id.progress_circular_country);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        // call Volley method
        //call list
        errorLayout.setVisibility(View.GONE);

       // rvAdapterServer = new RvAdapter(getActivity());

        adapterState = new RvAdapterState(getActivity());

        recyclerView.setAdapter(adapterState);


        recyclerView.addOnItemTouchListener(new RVTouchListener(getActivity(), recyclerView, new RVClickListener() {
            @Override
            public void onClick(View view, int position) {

               // final CasesList casesListItem = rvAdapterServer.getmValuesFilteredList().get(position);

                //onMenuItemClickServer(casesListItem);
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));





        return root;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        if(listType.equals(LIST_TYPE_SERVER)) {

            switch (location) {

                case "state":

                    Log.v("SUB", "STATE");

                    subscribeToUi(dashboardViewModel.getCasesStateResponse());

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
        else if(listType.equals(LIST_TYPE_LOCAL)) {


            getDataFromLocal();
        }
        else if(listType.equals(LIST_TYPE_SETUP)) {


            getDataFromLocal();
        }

    }
    private void subscribeToUi(LiveData<ApiResponseState> liveData) {

        errorLayout.setVisibility(View.GONE);
        Log.v("SUB", "SUBCRIBRD");
        liveData.observe(getViewLifecycleOwner(), new Observer<ApiResponseState>() {
            @Override
            public void onChanged(ApiResponseState apiResponse) {

                if (apiResponse == null) {

                    showErrorMessage(
                            R.drawable.oops,
                            "Oops..",
                            "Check internet");
                    //return;
                }
                else if (apiResponse.getError() == null) {
                    setMenuVisibility(true);
                    Log.v("SUB", "NOT ERROr"+apiResponse.getCasesList().size());
                    adapterState.setmValues(apiResponse.getCasesList());

                } else {

                    showErrorMessage(
                            R.drawable.oops,
                            "Oops..",
                            "Something went wrong");
                   // return;
                }

            }


        });

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
                                    serverLocationList.add(new CasesList(
                                            data.getString("country"), data.getInt("cases"),
                                            data.getString("todayCases"), data.getString("deaths"),
                                            data.getString("todayDeaths"), data.getString("recovered"),
                                            data.getString("active"), data.getString("critical"),
                                            data.getString("tests")
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " countries");
                                    break;
                                case "continent":

                                    serverLocationList.add(new CasesList(
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

                                    serverLocationList.add(new CasesList(
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
                        Collections.sort(serverLocationList, new Comparator<CasesList>() {
                            @Override
                            public int compare(CasesList o1, CasesList o2) {
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

                            serverLocationList.add(new CasesList(
                                    state, cases,
                                    NO_INFO, deaths,
                                    NO_INFO, recovered,
                                    active, NO_INFO,
                                    NO_INFO
                            ));
                            getActivity().setTitle(jsonArray.length() + " states");
                        }

                        // sort descending
                        Collections.sort(serverLocationList, new Comparator<CasesList>() {
                            @Override
                            public int compare(CasesList o1, CasesList o2) {
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

        return super.onOptionsItemSelected(item);
    }


    public void onMenuItemClickServer(CasesList casesList) {
        if (mListener != null) {
            getActivity().setTitle(casesList.getmCovidCountry());
            mListener.listItemClickServer(casesList);

        }
    }

    public void onMenuItemClickSetting(Country Country, String location, String listType) {
        if (mListener != null) {
            mListener.listItemClickSetting(Country, location, listType);

        }
    }



}
