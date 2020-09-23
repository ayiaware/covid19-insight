package commitware.ayia.covid19.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.adapter.RecyclerViewAdapterSlide;
import commitware.ayia.covid19.interfaces.OnFragmentListenerSlider;
import commitware.ayia.covid19.models.Slide;
import commitware.ayia.covid19.R;


public class InfoAsListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";



    private String recyclerRequest;


    private OnFragmentListenerSlider mListener;

    public InfoAsListFragment() {
        // Required empty public constructor
    }


    public static InfoAsListFragment newInstance(String param1) {
        InfoAsListFragment fragment = new InfoAsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recyclerRequest = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recycler, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        List<Slide> slideList = new ArrayList<>();

        if (recyclerRequest != null) {
            switch (recyclerRequest)
            {
                case "spread":
                    slideList = howSpread();
                    break;
                case "quarantine":
                    slideList = quarantine();
                    break;
                case "prevention":
                    slideList = prevention();
                    break;
                case "signs":
                    slideList = signs();
                    break;
                case "reduce":
                    slideList = reduce();
                    break;
            }
        }

        RecyclerViewAdapterSlide recyclerViewAdapterSlide = new RecyclerViewAdapterSlide(getActivity(),slideList);
        recyclerView.setAdapter(recyclerViewAdapterSlide);

        return root;
    }


    public void onBackPressed() {
        if (mListener != null) {
            mListener.doOnBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListenerSlider) {
            mListener = (OnFragmentListenerSlider) context;
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



    public List<Slide> howSpread()
    {
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(
                getResources().getString(R.string.infection1_heading),
                getResources().getString(R.string.infection1),
                R.drawable.waterdrop));
        slideList.add(new Slide(getResources().getString(R.string.infection2_heading),
                getResources().getString(R.string.infection2),R.drawable.closecontact));
        slideList.add(new Slide(getResources().getString(R.string.infection3_heading),
                getResources().getString(R.string.infection3),R.drawable.surface));
        return slideList;
    }

    public List<Slide> quarantine()
    {
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(
                getResources().getString(R.string.quarantine1_heading),
                getResources().getString(R.string.quarantine1_body),
                R.drawable.quarantinedays));
        slideList.add(new Slide(getResources().getString(R.string.quarantine2_heading),
                getResources().getString(R.string.quarantine2_body),
                R.drawable.stayhome));
        slideList.add(new Slide(getResources().getString(R.string.quarantine3_heading),
                getResources().getString(R.string.quarantine3_body),
                R.drawable.spread));
        slideList.add(new Slide(getResources().getString(R.string.quarantine4_heading),
                getResources().getString(R.string.quarantine4_body),
                R.drawable.illness));

        return slideList;
    }

    public List<Slide> prevention()
    {
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(
                getResources().getString(R.string.prevention1_heading),
                getResources().getString(R.string.prevention1),
                R.drawable.hand));
        slideList.add(new Slide(getResources().getString(R.string.prevention2_heading),
                getResources().getString(R.string.prevention2),
                R.drawable.shake));
        slideList.add(new Slide(getResources().getString(R.string.prevention3_heading),
                getResources().getString(R.string.prevention3),
                R.drawable.sanitizer));
        slideList.add(new Slide(getResources().getString(R.string.prevention4_heading),
                getResources().getString(R.string.prevention4),
                R.drawable.surface));

        return slideList;
    }

    public List<Slide> signs()
    {
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(
                getResources().getString(R.string.symptom1_heading),
                getResources().getString(R.string.symptom1),
                R.drawable.temperature));
        slideList.add(new Slide(getResources().getString(R.string.symptom2_heading),
                getResources().getString(R.string.symptom2),
                R.drawable.sneeze));
        slideList.add(new Slide(getResources().getString(R.string.symptom3_heading),
                getResources().getString(R.string.symptom3),
                R.drawable.cough));
        slideList.add(new Slide(getResources().getString(R.string.symptom4_heading),
                getResources().getString(R.string.symptom4),
                R.drawable.headache));
        slideList.add(new Slide(getResources().getString(R.string.symptom5_heading),
                getResources().getString(R.string.symptom5),
                R.drawable.breathing));
        return slideList;
    }
    public List<Slide> reduce()
    {
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(
                getResources().getString(R.string.reduce1_heading),
                getResources().getString(R.string.reduce1),
                R.drawable.sneeze));
        slideList.add(new Slide(getResources().getString(R.string.reduce2_heading),
                getResources().getString(R.string.reduce2),
                R.drawable.stayhome2));
        slideList.add(new Slide(getResources().getString(R.string.reduce3_heading),
                getResources().getString(R.string.reduce3),
                R.drawable.sneeze));
        slideList.add(new Slide(getResources().getString(R.string.reduced4_heading),
                getResources().getString(R.string.reduce4),
                R.drawable.facemask));
        slideList.add(new Slide(getResources().getString(R.string.reduce5_heading),
                getResources().getString(R.string.reduce5),
                R.drawable.stayhome));
        return slideList;
    }

}
