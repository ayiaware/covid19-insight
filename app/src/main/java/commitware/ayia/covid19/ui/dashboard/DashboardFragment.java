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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import commitware.ayia.covid19.AppController;

import commitware.ayia.covid19.interfaces.OnFragmentListenerMain;

import commitware.ayia.covid19.R;
import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesState;
import commitware.ayia.covid19.services.retrofit.cases.ApiResponse;
import commitware.ayia.covid19.services.retrofit.cases.state.ApiResponseState;

import static commitware.ayia.covid19.AppUtils.LIST_INTENT;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private DashboardViewModel dashboardViewModel;

    private OnFragmentListenerMain mListener;

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
    private TextView tvNetwork;
    private String locationDataRequest;

    private ExtendedFloatingActionButton fab;
    String fabText;
    private SwipeRefreshLayout swipe;
    RadioGroup radioFilter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        radioFilter = root.findViewById(R.id.radioFilter);

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
        tvNetwork = root.findViewById(R.id.tvNetwork);

        fab = root.findViewById(R.id.floatingActionButton);

            final RadioButton radioState = root.findViewById(R.id.radioState);
            radioState.setVisibility(View.VISIBLE);
            locationDataRequest = "state";
            radioFilter.check(R.id.radioState);

        fab.setOnClickListener(v -> startIntent(locationDataRequest));
        swipe.setOnRefreshListener(this);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);


      dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

      setText();

        if(locationDataRequest.equals("state"))
            subscribeToUiS(dashboardViewModel.getCasesStateResponse());
        else
            subscribeToUi(dashboardViewModel.getCasesResponse(locationDataRequest));

        radioFilter.setOnCheckedChangeListener((group, checkedId) -> {

           RadioButton radioButton = view.findViewById(checkedId);

            locationDataRequest = radioButton.getText().toString();

            setText();

              if(locationDataRequest.equals("state"))
                  subscribeToUiS(dashboardViewModel.getCasesStateResponse());
              else
                  subscribeToUi(dashboardViewModel.getCasesResponse(locationDataRequest));


          });


    }

//    private void subscribeToUi(LiveData<CaseApiResponse> casesLiveData){
//
//        casesLiveData.observe(getViewLifecycleOwner(), new Observer<CaseApiResponse>() {
//            @Override
//            public void onChanged(CaseApiResponse apiResponse) {
//
//                if (apiResponse == null) {
//
//                    removeText();
//
//                }
//                else {
//                    if (apiResponse.getError() == null) {
//
//                        String cases;
//                        String active;
//                        String recovered;
//                        String deaths;
//                        String updated;
//
//                        String casesToday;
//                        String deathsToday;
//                        String critical;
//                        String tested;
//
//                        if(!locationDataRequest.equals("state")){
//
//                            Case aCase = apiResponse.getaCase();
//
//                            cases = aCase.getCases();
//                            recovered = aCase.getRecovered();
//                            active = aCase.getActive();
//                            deaths = aCase.getDeaths();
//                            casesToday = aCase.getTodayCases();
//                            deathsToday = aCase.getTodayDeaths();
//                            critical = aCase.getCritical();
//                            tested = aCase.getTested();
//
//
//                            updated = "Last Updated"+"\n"+getDate(aCase.getUpdated());
//
//                        }
//
//                        else {
//                            CaseState aCase = apiResponse.getCaseState();
//
//                            cases = aCase.getCases();
//                            recovered = aCase.getRecovered();
//                            active = aCase.getActive();
//                            deaths = aCase.getDeaths();
//
//                            casesToday = "--";
//                            deathsToday = "--";
//                            critical = "--";
//                            tested = "--";
//
//                            updated = "";
//
//
//                        }
//
//                        tvNetwork.setVisibility(View.GONE);
//
//                        tvCases.setText(cases);
//                        tvCasesToday.setText(casesToday);
//                        tvRecovered.setText(recovered);
//                        tvDeaths.setText(deaths);
//                        tvDeathsToday.setText(deathsToday);
//                        tvCritical.setText(critical);
//                        tvActive.setText(active);
//                        tvTested.setText(tested);
//
//
//                        tvUpdated.setText(updated);
//
////                        String heading = cases.getLocation()+" Cases";
////
////                        if(heading != null)
////                        tvHeading.setText(heading);
//
//                    } else {
//                        removeText();
//                    }
//                }
//
//            }
//        });
//
//
//    }
    private void subscribeToUi(LiveData<ApiResponse> casesLiveData){

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

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


                            Cases aCases = apiResponse.getaCases();

                            cases = aCases.getCases();
                            recovered = aCases.getRecovered();
                            active = aCases.getActive();
                            deaths = aCases.getDeaths();
                            casesToday = aCases.getTodayCases();
                            deathsToday = aCases.getTodayDeaths();
                            critical = aCases.getCritical();
                            tested = aCases.getTested();


                            updated = "Last Updated"+"\n"+getDate(aCases.getUpdated());





                        tvNetwork.setVisibility(View.GONE);

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

    private void subscribeToUiS(LiveData<ApiResponseState> casesLiveData){

        casesLiveData.observe(getViewLifecycleOwner(), new Observer<ApiResponseState>() {
            @Override
            public void onChanged(ApiResponseState apiResponse) {

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

                            CasesState aCase = apiResponse.getCases();
                            Log.v("subscribeToUiS", "cases List count)"+apiResponse.getCasesList().size());
                            cases = aCase.getCases();
                            recovered = aCase.getRecovered();
                            active = aCase.getActive();
                            deaths = aCase.getDeaths();

                            casesToday = "--";
                            deathsToday = "--";
                            critical = "--";
                            tested = "--";

                            updated = "";


                        tvNetwork.setVisibility(View.GONE);

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
        if (!"globe".equals(locationDataRequest)) {

            fab.setEnabled(true);
            fab.setVisibility(View.VISIBLE);

            fabText = "other " + locationDataRequest + "s";

            if ("country".equals(locationDataRequest)) {

                String heading = AppController.getInstance().getCountry()+" Cases";

                tvHeading.setText(heading);

                fabText = "other " + locationDataRequest.substring(0,6) + "ies";

            }
            else if("state".equals(locationDataRequest)) {
                String heading = AppController.getInstance().getState()+" Cases";
                tvHeading.setText(heading);

            }
            else if("continent".equals(locationDataRequest)) {
                String heading = AppController.getInstance().getContinent()+" Cases";
                tvHeading.setText(heading);
            }
            
            fab.setText(fabText);
        }

       else {

            String heading = "Global Cases";
            tvHeading.setText(heading);

            fab.setEnabled(false);
            fab.setVisibility(View.INVISIBLE);
        }

    }


    private void refreshStats(boolean isRefresh) {
        if (isRefresh) {
            swipe.setRefreshing(true);

        } else {
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {

        if(locationDataRequest.equals("state"))
            subscribeToUiS(dashboardViewModel.getCasesStateResponse());
        else
            subscribeToUi(dashboardViewModel.getCasesResponse(locationDataRequest));

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListenerMain) {
            mListener = (OnFragmentListenerMain) context;
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
    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(String arguement) {
        if (mListener != null) {

                mListener.getListIntent(LIST_INTENT,arguement);
        }
    }



    @Override
    public void onResume() {
        super.onResume();

    }


}