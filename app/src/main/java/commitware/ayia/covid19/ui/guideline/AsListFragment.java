package commitware.ayia.covid19.ui.guideline;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.adapter.RvGuidelineAdapter;
import commitware.ayia.covid19.data.GuidelinesData;
import commitware.ayia.covid19.models.Guideline;
import commitware.ayia.covid19.R;


public class AsListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String request;


    public AsListFragment() {
        // Required empty public constructor
    }


    public static AsListFragment newInstance(String param1) {
        AsListFragment fragment = new AsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_as_list, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        List<Guideline> guidelineList = new GuidelinesData(request).getGuidelines();

        RvGuidelineAdapter rvGuidelineAdapter = new RvGuidelineAdapter(getActivity(), guidelineList);
        recyclerView.setAdapter(rvGuidelineAdapter);

        return root;
    }


    public void onBackPressed() {
       requireActivity().onBackPressed();
    }



    // i will move this to a web server later

    public List<Guideline> howSpread() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                getResources().getString(R.string.infection1_heading),
                getResources().getString(R.string.infection1),
                R.drawable.waterdrop));
        guidelineList.add(new Guideline(getResources().getString(R.string.infection2_heading),
                getResources().getString(R.string.infection2),R.drawable.closecontact));
        guidelineList.add(new Guideline(getResources().getString(R.string.infection3_heading),
                getResources().getString(R.string.infection3),R.drawable.surface));
        return guidelineList;
    }

    public List<Guideline> quarantine() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                getResources().getString(R.string.quarantine1_heading),
                getResources().getString(R.string.quarantine1_body),
                R.drawable.quarantinedays));
        guidelineList.add(new Guideline(getResources().getString(R.string.quarantine2_heading),
                getResources().getString(R.string.quarantine2_body),
                R.drawable.stayhome));
        guidelineList.add(new Guideline(getResources().getString(R.string.quarantine3_heading),
                getResources().getString(R.string.quarantine3_body),
                R.drawable.spread));
        guidelineList.add(new Guideline(getResources().getString(R.string.quarantine4_heading),
                getResources().getString(R.string.quarantine4_body),
                R.drawable.illness));

        return guidelineList;
    }

    public List<Guideline> prevention() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                getResources().getString(R.string.prevention1_heading),
                getResources().getString(R.string.prevention1),
                R.drawable.hand));
        guidelineList.add(new Guideline(getResources().getString(R.string.prevention2_heading),
                getResources().getString(R.string.prevention2),
                R.drawable.shake));
        guidelineList.add(new Guideline(getResources().getString(R.string.prevention3_heading),
                getResources().getString(R.string.prevention3),
                R.drawable.sanitizer));
        guidelineList.add(new Guideline(getResources().getString(R.string.prevention4_heading),
                getResources().getString(R.string.prevention4),
                R.drawable.surface));

        return guidelineList;
    }

    public List<Guideline> signs() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                getResources().getString(R.string.symptom1_heading),
                getResources().getString(R.string.symptom1),
                R.drawable.temperature));
        guidelineList.add(new Guideline(getResources().getString(R.string.symptom2_heading),
                getResources().getString(R.string.symptom2),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(getResources().getString(R.string.symptom3_heading),
                getResources().getString(R.string.symptom3),
                R.drawable.cough));
        guidelineList.add(new Guideline(getResources().getString(R.string.symptom4_heading),
                getResources().getString(R.string.symptom4),
                R.drawable.headache));
        guidelineList.add(new Guideline(getResources().getString(R.string.symptom5_heading),
                getResources().getString(R.string.symptom5),
                R.drawable.breathing));
        return guidelineList;
    }
    public List<Guideline> reduce() {
        List<Guideline> guidelineList = new ArrayList<>();
        guidelineList.add(new Guideline(
                getResources().getString(R.string.reduce1_heading),
                getResources().getString(R.string.reduce1),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(getResources().getString(R.string.reduce2_heading),
                getResources().getString(R.string.reduce2),
                R.drawable.stayhome2));
        guidelineList.add(new Guideline(getResources().getString(R.string.reduce3_heading),
                getResources().getString(R.string.reduce3),
                R.drawable.sneeze));
        guidelineList.add(new Guideline(getResources().getString(R.string.reduced4_heading),
                getResources().getString(R.string.reduce4),
                R.drawable.facemask));
        guidelineList.add(new Guideline(getResources().getString(R.string.reduce5_heading),
                getResources().getString(R.string.reduce5),
                R.drawable.stayhome));
        return guidelineList;
    }

}
