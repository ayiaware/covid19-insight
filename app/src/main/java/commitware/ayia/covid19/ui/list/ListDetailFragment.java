package commitware.ayia.covid19.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import commitware.ayia.covid19.models.Insight;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.ui.dashboard.DashboardFragment;
import commitware.ayia.covid19.utils.AppUtils;

public class ListDetailFragment extends Fragment {

    private static final String TAG = ListDetailFragment.class.getSimpleName();
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

    public ListDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

       RadioGroup rgFilter = root.findViewById(R.id.radioFilter);
       rgFilter.setVisibility(View.GONE);


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

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListViewModel listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        listViewModel.getInsight().observe(getViewLifecycleOwner(), new Observer<Insight>() {
            @Override
            public void onChanged(@Nullable Insight insight) {

                Log.v(TAG, "INSIGHT: NAME "+insight.getName());

                tvCases.setText(String.valueOf(insight.getCases()));
                tvCasesToday.setText(insight.getTodayCases());
                tvRecovered.setText(insight.getRecovered());
                tvDeaths.setText(insight.getDeaths());
                tvDeathsToday.setText(insight.getTodayDeaths());
                tvCritical.setText(insight.getCritical());
                tvActive.setText(insight.getActive());
                tvTested.setText(insight.getTested());


                if(insight.getUpdated()!=Long.parseLong(AppUtils.BLANK)){
                    String  date = "Last Updated"+"\n"+getDate(insight.getUpdated());
                    tvUpdated.setText(date);
                }
                else {
                    tvUpdated.setVisibility(View.GONE);
                }

                String heading = insight.getName().toUpperCase();
                tvHeading.setText(heading);
            }
        });
    }




    private String getDate(long milliSecond){
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }




}