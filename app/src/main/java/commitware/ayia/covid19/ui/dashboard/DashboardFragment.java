package commitware.ayia.covid19.ui.dashboard;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import commitware.ayia.covid19.models.Location;
import commitware.ayia.covid19.ui.list.ListFragment;
import commitware.ayia.covid19.utils.AppUtils;
import commitware.ayia.covid19.BasicApp;

import commitware.ayia.covid19.interfaces.FragmentInteraction;

import commitware.ayia.covid19.R;

import commitware.ayia.covid19.models.Continent;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.Globe;
import commitware.ayia.covid19.models.State;
import commitware.ayia.covid19.services.retrofit.insight.ResponseContinent;
import commitware.ayia.covid19.services.retrofit.insight.ResponseCountry;
import commitware.ayia.covid19.services.retrofit.insight.ResponseGlobe;
import commitware.ayia.covid19.services.retrofit.insight.state.ResponseState;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private InsightViewModel insightViewModel;
    private static final String TAG = DashboardFragment.class.getSimpleName();
    private FragmentInteraction mListener;

    private TextView tvCases;
    private TextView tvCasesToday;
    private TextView tvRecovered;
    private TextView tvDeaths;
    private TextView tvDeathsToday;
    private TextView tvCritical;
    private TextView tvActive;
    private TextView tvUpdated;
    private TextView tvTested;
    private TextView tvHeading;
    private String location;

    private ExtendedFloatingActionButton fab;
    String fabText;
    private SwipeRefreshLayout swipe;
    RadioGroup rgLocations;

    Location userLocation;

    RadioButton radioState;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        BasicApp app = BasicApp.getInstance();

        userLocation = app.getLocation();

        app.setFirstStart(false);

        rgLocations = root.findViewById(R.id.radioFilter);

        rgLocations.setVisibility(View.VISIBLE);

        swipe= root.findViewById(R.id.swipeView);
        tvCases = root.findViewById(R.id.tvOne);
        tvCasesToday = root.findViewById(R.id.tvCasesToday);
        tvRecovered = root.findViewById(R.id.tvRecovered);
        tvDeaths = root.findViewById(R.id.tvDeaths);
        tvDeathsToday = root.findViewById(R.id.tvDeathsToday);
        tvCritical = root.findViewById(R.id.tvCritical);
        tvActive = root.findViewById(R.id.tvActive);
        tvUpdated = root.findViewById(R.id.tvLastUpdated);
        tvTested = root.findViewById(R.id.tvTested);
        tvHeading = root.findViewById(R.id.tvHeading);

        fab = root.findViewById(R.id.floatingActionButton);

        radioState = root.findViewById(R.id.radioState);

        radioState.setVisibility(View.GONE);

        location = AppUtils.COUNTRY;

        if(userLocation.getCountry().equals("Nigeria")){
            location = AppUtils.STATE;
            radioState.setVisibility(View.VISIBLE);
        }




        swipe.setOnRefreshListener(this);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        insightViewModel = new ViewModelProvider(requireActivity()).get(InsightViewModel.class);

        subscribeToUi();

        rgLocations.check(R.id.radioState);

        setText();

        rgLocations.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = view.findViewById(checkedId);

            location = radioButton.getText().toString();

            setText();

            subscribeToUi();

        });

        fab.setOnClickListener(v -> {

            DashboardFragmentDirections.ActionDashboardToListFragment action =
                    DashboardFragmentDirections.actionDashboardToListFragment();
            action.setLocation(location);
            action.setType(AppUtils.SERVER);
            Navigation.findNavController(view).navigate(action);


        });


    }


    private void subscribeToUi(){

        switch (location) {
            case "state":
                subscribeToUiState(insightViewModel.getObsStates());
                break;
            case "continent":
                subscribeToUiContinent(insightViewModel.getObsContinent());
                break;
            case AppUtils.COUNTRY:
                subscribeToUiCountry(insightViewModel.getObsCountries());
                break;
            case AppUtils.GLOBE:
                subscribeToUiGlobe(insightViewModel.mObsGlobal);
                break;
        }
    }
    private void subscribeToUiState(LiveData<ResponseState> casesLiveData){

        refreshInsight(false);

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState apiResponse) {

                if (apiResponse == null) {

                    removeText();

                }
                else {
                    if (apiResponse.getError() == null) {

                        String cases;
                        String active;
                        String recovered;
                        String deaths;
                        String updated;

                        String casesToday;
                        String deathsToday;
                        String critical;
                        String tested;

                        State insight = apiResponse.getInsight();

                        Log.v(TAG, "CASES COUNT "+apiResponse.getStates().size());


                        cases = insight.getCases();
                        recovered = insight.getRecovered();
                        active = insight.getActive();
                        deaths = insight.getDeaths();
                        casesToday = insight.getTodayCases();
                        deathsToday = insight.getTodayDeaths();
                        critical = insight.getCritical();
                        tested = insight.getTested();


                        if(insight.getUpdated() != Long.parseLong(AppUtils.BLANK)){
                            String  date = "Last Updated"+"\n"+getDate(insight.getUpdated());
                            tvUpdated.setText(date);
                        }
                        else {
                            tvUpdated.setVisibility(View.GONE);
                        }


                        tvCases.setText(cases);
                        tvCasesToday.setText(casesToday);
                        tvRecovered.setText(recovered);
                        tvDeaths.setText(deaths);
                        tvDeathsToday.setText(deathsToday);
                        tvCritical.setText(critical);
                        tvActive.setText(active);
                        tvTested.setText(tested);



                    } else {
                        removeText();
                    }
                }

            }
        });


    }


    private void subscribeToUiCountry(LiveData<ResponseCountry> casesLiveData){

        refreshInsight(false);

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseCountry>() {
            @Override
            public void onChanged(ResponseCountry apiResponse) {

                if (apiResponse == null) {

                    removeText();
                }
                else {
                    if (apiResponse.getError() == null) {

                        String cases;
                        String active;
                        String recovered;
                        String deaths;
                        String updated;

                        String casesToday;
                        String deathsToday;
                        String critical;
                        String tested;


                        Country insight = apiResponse.getCountry();

                        if(insight!=null){
                            cases = insight.getCases();
                            recovered = insight.getRecovered();
                            active = insight.getActive();
                            deaths = insight.getDeaths();
                            casesToday = insight.getTodayCases();
                            deathsToday = insight.getTodayDeaths();
                            critical = insight.getCritical();
                            tested = insight.getTested();

                            updated = "Last Updated"+"\n"+getDate(insight.getUpdated());

                            tvCases.setText(cases);
                            tvCasesToday.setText(casesToday);
                            tvRecovered.setText(recovered);
                            tvDeaths.setText(deaths);
                            tvDeathsToday.setText(deathsToday);
                            tvCritical.setText(critical);
                            tvActive.setText(active);
                            tvTested.setText(tested);


                            tvUpdated.setText(updated);
                        }


                        tvUpdated.setText(getString( R.string.insight_unavailable)+" "+userLocation.getCountry());


                    } else {
                        removeText();
                    }
                }

            }
        });


    }

    private void subscribeToUiContinent(LiveData<ResponseContinent> casesLiveData){

        refreshInsight(false);

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseContinent>() {
            @Override
            public void onChanged(ResponseContinent apiResponse) {

                if (apiResponse == null) {

                    removeText();

                }
                else {
                    if (apiResponse.getError() == null) {

                        String cases;
                        String active;
                        String recovered;
                        String deaths;
                        String updated;

                        String casesToday;
                        String deathsToday;
                        String critical;
                        String tested;


                        Continent insight = apiResponse.getContinent();

                        if(insight!=null){
                            cases = insight.getCases();
                            recovered = insight.getRecovered();
                            active = insight.getActive();
                            deaths = insight.getDeaths();
                            casesToday = insight.getTodayCases();
                            deathsToday = insight.getTodayDeaths();
                            critical = insight.getCritical();
                            tested = insight.getTested();

                            updated = "Last Updated"+"\n"+getDate(insight.getUpdated());

                            tvCases.setText(cases);
                            tvCasesToday.setText(casesToday);
                            tvRecovered.setText(recovered);
                            tvDeaths.setText(deaths);
                            tvDeathsToday.setText(deathsToday);
                            tvCritical.setText(critical);
                            tvActive.setText(active);
                            tvTested.setText(tested);


                            tvUpdated.setText(updated);
                        }


                        tvUpdated.setText(getString( R.string.insight_unavailable)+" "+userLocation.getContinent());

                    } else {
                        removeText();
                    }
                }

            }
        });


    }

    private void subscribeToUiGlobe(LiveData<ResponseGlobe> casesLiveData){

        refreshInsight(false);

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseGlobe>() {
            @Override
            public void onChanged(ResponseGlobe apiResponse) {

                if (apiResponse == null) {

                    removeText();
                }
                else {
                    if (apiResponse.getError() == null) {

                        String cases;
                        String active;
                        String recovered;
                        String deaths;
                        String updated;

                        String casesToday;
                        String deathsToday;
                        String critical;
                        String tested;


                        Globe insight = apiResponse.getGlobe();

                        cases = insight.getCases();
                        recovered = insight.getRecovered();
                        active = insight.getActive();
                        deaths = insight.getDeaths();
                        casesToday = insight.getTodayCases();
                        deathsToday = insight.getTodayDeaths();
                        critical = insight.getCritical();
                        tested = insight.getTested();


                        updated = "Last Updated"+"\n"+getDate(insight.getUpdated());


                        tvCases.setText(cases);
                        tvCasesToday.setText(casesToday);
                        tvRecovered.setText(recovered);
                        tvDeaths.setText(deaths);
                        tvDeathsToday.setText(deathsToday);
                        tvCritical.setText(critical);
                        tvActive.setText(active);
                        tvTested.setText(tested);


                        tvUpdated.setText(updated);


                    } else {
                        removeText();
                    }
                }

            }
        });


    }


    private void removeText() {

        String def = "--";
        tvCases.setText(def);
        tvCasesToday.setText(def);
        tvRecovered.setText(def);
        tvDeaths.setText(def);
        tvDeathsToday.setText(def);
        tvCritical.setText(def);
        tvActive.setText(def);
        tvTested.setText(def);

        String date = "No Offline Data";

        tvUpdated.setText(date);

    }

    private void setText() {
        if (!"globe".equals(location)) {

            fab.setEnabled(true);
            fab.setVisibility(View.VISIBLE);

            fabText = "other " + location + "s";

            if ("country".equals(location)) {

                String heading = userLocation.getCountry().toUpperCase();

                tvHeading.setText(heading);

                fabText = "other " + location.substring(0,6) + "ies";

            }
            else if("state".equals(location)) {
                String heading = userLocation.getState().toUpperCase();
                tvHeading.setText(heading);

            }
            else if("continent".equals(location)) {
                String heading = userLocation.getContinent().toUpperCase();
                tvHeading.setText(heading);
            }

            fab.setText(fabText);
        }

        else {

            tvHeading.setText(AppUtils.GLOBE.toUpperCase());

            fab.setEnabled(false);
            fab.setVisibility(View.INVISIBLE);
        }

    }


    private void refreshInsight(boolean isRefresh) {

        if (isRefresh) {
            swipe.setRefreshing(true);

        } else {
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {

        subscribeToUi();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            mListener = (FragmentInteraction) context;
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

    private String getDate(long milliSecond){
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }




    @Override
    public void onResume() {
        super.onResume();

    }


}