package commitware.ayia.covid19.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import commitware.ayia.covid19.models.CasesList;
import commitware.ayia.covid19.R;

public class ListDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private CasesList casesList;

    private CasesList stats;

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

    public static ListDetailFragment newInstance(CasesList casesList) {
        ListDetailFragment fragment = new ListDetailFragment();
        fragment.setCasesList(casesList);
        return fragment;
    }

    private void setCasesList(CasesList casesList) {
        this.casesList = casesList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats = casesList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_list_detail, container, false);

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


        tvCases.setText(String.valueOf(stats.getConfirmed()));
        tvCasesToday.setText(stats.getTodayConfirmed());
        tvRecovered.setText(stats.getRecovered());
        tvDeaths.setText(stats.getDeaths());
        tvDeathsToday.setText(stats.getTodayDeaths());
        tvCritical.setText(stats.getCritical());
        tvActive.setText(stats.getActive());
        tvTested.setText(stats.getmFlags());

        //String date = "Last Updated"+"\n"+getDate(stats.getUpdated());
       // tvUpdated.setText(date);
        String heading = stats.getmCovidCountry()+" Cases";
        tvHeading.setText(heading);


        return root;
    }






    private String getDate(long milliSecond){
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }




}