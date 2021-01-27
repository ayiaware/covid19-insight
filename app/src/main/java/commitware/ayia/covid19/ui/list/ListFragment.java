package commitware.ayia.covid19.ui.list;

import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.adapter.RvLocationAdapter;
import commitware.ayia.covid19.data.CountriesData;
import commitware.ayia.covid19.data.StatesData;
import commitware.ayia.covid19.ui.dashboard.DashboardFragmentDirections;
import commitware.ayia.covid19.utils.AppUtils;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.adapter.RvInsightAdapter;
import commitware.ayia.covid19.interfaces.RecyclerViewClickListener;
import commitware.ayia.covid19.models.Insight;
import commitware.ayia.covid19.models.Location;
import commitware.ayia.covid19.services.retrofit.insight.ResponseContinent;
import commitware.ayia.covid19.services.retrofit.insight.ResponseCountry;
import commitware.ayia.covid19.services.retrofit.insight.state.ResponseState;
import commitware.ayia.covid19.ui.dashboard.InsightViewModel;
import commitware.ayia.covid19.utils.RVTouchListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {

    private RecyclerView rv;
    private ProgressBar progressBar;

    private static final String ARG_GET_LOCATIONS= "location";
    private static final String ARG_GET_LIST= "listType";

    private static final String TAG = ListFragment.class.getSimpleName();

    private String location;
    private String listType;

    private List<Insight> serverLocationList;
    private List<Location> localLocationList;

    private String url;



    private RvInsightAdapter adapterI;

    private RvLocationAdapter adapterL;



    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private String tvMenuTitle;


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



    ActionBar  actionBar;
    InsightViewModel insightViewModel;

    ListViewModel listViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        progressBar = root.findViewById(R.id.progress_circular_country);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        errorLayout.setVisibility(View.GONE);
        // set has option menu as true because we have menu
        setHasOptionsMenu(true);

        actionBar  = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle(R.string.loading);

        rv = root.findViewById(R.id.rvList);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                DividerItemDecoration.VERTICAL);

        rv.addItemDecoration(dividerItemDecoration);

        rv.addOnItemTouchListener(new RVTouchListener(getActivity(), rv, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(listType.equals(AppUtils.SERVER)) {

                    final Insight insight = adapterI.getValues().get(position);

                    listViewModel.setInsight(insight);

                    Navigation.findNavController(view).
                            navigate(ListFragmentDirections.actionListFragmentToListDetailFragment());
                }
                else {

                    final Location userLocation = adapterL.getValues().get(position);

                    String msg = getResources().getString(R.string.country)+" "+userLocation.getCountry();

                    if(location.equals(AppUtils.STATE))

                        msg = getResources().getString(R.string.state)+" "+userLocation.getState();

                    new MaterialAlertDialogBuilder(requireActivity())
                            .setMessage(msg.toUpperCase())
                            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    if(!location.equals(AppUtils.STATE) && userLocation.getCountry().equals("Nigeria")){

                                            ListFragmentDirections.ActionListFragmentSelf action =
                                                    ListFragmentDirections.actionListFragmentSelf();
                                            action.setLocation(AppUtils.STATE);
                                            action.setType(AppUtils.LOCAL);
                                            Navigation.findNavController(view).navigate(action);

                                        }
                                        else {

                                            BasicApp.getInstance().setLocation(userLocation);
                                            Navigation.findNavController(view).
                                                    navigate(ListFragmentDirections.actionListFragmentToNavigationDashboard());



                                        }

                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
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

        listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        if (getArguments() != null) {

            listType = ListFragmentArgs.fromBundle(getArguments()).getType();

            location = ListFragmentArgs.fromBundle(getArguments()).getLocation();
        }

        if(listType.equals(AppUtils.SERVER)) {

            insightViewModel = new ViewModelProvider(requireActivity()).get(InsightViewModel.class);

            adapterI = new RvInsightAdapter(getActivity());

            rv.setAdapter(adapterI);
        }
        else {

            adapterL = new RvLocationAdapter(getActivity());

            rv.setAdapter(adapterL);
        }

        if(listType.equals(AppUtils.SERVER)) {

            switch (location) {
                case "state":
                    subscribeToUiState(insightViewModel.getObsStates());
                    break;
                case "continent":
                    subscribeToUiContinent(insightViewModel.getObsContinent());
                    break;
                case "country":
                    subscribeToUiCountry(insightViewModel.getObsCountries());
                    break;
                default:
                    showErrorMessage(getString(R.string.something_went_wrong));
                    break;
            }
        }
        else if(listType.equals(AppUtils.LOCAL)) {
            showList();
        }



    }

    private void subscribeToUiState(LiveData<ResponseState> liveData) {

        liveData.observe(getViewLifecycleOwner(), new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState apiResponse) {
                if (apiResponse == null) {

                    showErrorMessage(getString(R.string.check_internet));
                    //return;
                }
                else if (apiResponse.getError() == null) {
                    adapterI.setmValues(apiResponse.getInsightList());

                    progressBar.setVisibility(View.GONE);

                    if(actionBar!=null)
                        actionBar.setTitle(apiResponse.getStates().size() + " "
                                + getString(R.string.states));

                }else {
                    showErrorMessage(getString(R.string.something_went_wrong));
                    // return;
                }

            }

        });

    }

    private void subscribeToUiCountry(LiveData<ResponseCountry> liveData) {

        liveData.observe(getViewLifecycleOwner(), new Observer<ResponseCountry>() {
            @Override
            public void onChanged(ResponseCountry apiResponse) {


                if (apiResponse == null) {

                    showErrorMessage(
                            "Check internet");
                    //return;
                }
                else if (apiResponse.getError() == null) {


                    adapterI.setmValues(apiResponse.getInsightList());
                    progressBar.setVisibility(View.GONE);




                    if(actionBar!=null)
                        actionBar.setTitle(apiResponse.getCountries().size() + " "
                                + getString(R.string.countries));


                }else {

                    showErrorMessage(
                            "Something went wrong");
                    // return;
                }

            }

        });

    }

    private void subscribeToUiContinent(LiveData<ResponseContinent> liveData) {

        liveData.observe(getViewLifecycleOwner(), new Observer<ResponseContinent>() {
            @Override
            public void onChanged(ResponseContinent apiResponse) {

                //  Log.d("lFFFFFFFFFFFFFFFFFFFist", apiResponse.getCasesList().get(0).getDeaths());

                if (apiResponse == null) {

                    showErrorMessage(getString(R.string.check_internet));
                    //return;
                }
                else if (apiResponse.getError() == null) {


                    adapterI.setmValues(apiResponse.getInsightList());
                    progressBar.setVisibility(View.GONE);


                    if(actionBar!=null)
                        actionBar.setTitle(apiResponse.getContinents().size() + " "
                                + getString(R.string.continents));

                }else {

                    showErrorMessage(getString(R.string.something_went_wrong));
                    // return;
                }

            }

        });

    }


    private void showList() {

        List<Location> list;

        String aBarTitle;

        progressBar.setVisibility(View.GONE);

        switch (location) {
            case "state":
                list = StatesData.getList();
                aBarTitle = "Select State";
                break;
            case "country":
                aBarTitle = "Select Country";
                list = CountriesData.getList();
                break;
            default:
                showErrorMessage(getString(R.string.something_went_wrong));
                aBarTitle = "";
                list = new ArrayList<>();
                break;
        }

        if(actionBar!=null)
            actionBar.setTitle(aBarTitle);

        adapterL.setmValues(list);

    }




    @Override
    public void onDestroyView() {
        adapterI = null;
        adapterL =null;
        super.onDestroyView();
    }

    private void showErrorMessage(String message){
        progressBar.setVisibility(View.GONE);

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(R.drawable.oops);
        errorTitle.setText(R.string.oops);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);


            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(requireActivity());
        searchView.setQueryHint(getString(R.string.search));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterI != null) {
                    adapterI.getFilter().filter(newText);
                }
                if (adapterL != null) {
                    adapterL.getFilter().filter(newText);
                }

                return true;
            }
        });

        searchItem.setActionView(searchView);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // MenuItem item = menu.findItem(R.id.settings);
        // item.setVisible(false);

    }

    public void updateOptionsMenu() {

        requireActivity().invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }





}
