package commitware.ayia.covid19.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.adapter.SliderAdapter;
import commitware.ayia.covid19.interfaces.OnFragmentListenerSlider;
import commitware.ayia.covid19.models.Slide;
import commitware.ayia.covid19.R;


public class InfoAsSliderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String sliderRequest;


    private ViewPager sViewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;

    private Button btnNext, btnCancel;

    private int slideCount;

    private OnFragmentListenerSlider mListener;

    public InfoAsSliderFragment() {
        // Required empty public constructor
    }


    public static InfoAsSliderFragment newInstance(String param1) {
        InfoAsSliderFragment fragment = new InfoAsSliderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sliderRequest = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_slider, container, false);

       // getActivity().getActionBar().setDisplayShowHomeEnabled(false);

        sViewPager =  root.findViewById(R.id.sViewPager);
        dotsLayout = root.findViewById(R.id.layoutDots);
        btnNext =  root.findViewById(R.id.btnNext);
        btnCancel= root.findViewById(R.id.btnCancel);


        List<Slide> slideList = new ArrayList<>();

        if (sliderRequest != null) {
            switch (sliderRequest)
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


        sliderAdapter = new SliderAdapter(getActivity(), slideList);

        slideCount = sliderAdapter.getCount();

        // set adapter in ViewPager
        sViewPager.setAdapter(sliderAdapter);

        // set PageChangeListener
        sViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        addBottomDots(0);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNextClick(v);

            }
        });


       return root;
    }



    private int getItem(int i) {
        return sViewPager.getCurrentItem() + i;
    }

    //btnNextClick
    public void btnNextClick(View v) {
        // checking for last page
        // if last page home screen will be launched
        int current = getItem(1);
        if (current < sliderAdapter.getCount()) {
            // move to next screen
            sViewPager.setCurrentItem(current);
        } else {
            onBackPressed();
        }
    }

    // viewPagerPage ChangeListener according to Dots-Points
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
//            sliderAdapter.checkReadyBox();

            if (position == slideCount - 1) {
                btnNext.setText("finish");
                btnCancel.setVisibility(View.GONE);
            } else {
                btnNext.setText("next");
                btnCancel.setVisibility(View.VISIBLE);
                btnCancel.setText("cancel");
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    };


    // set of Dots points
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[slideCount];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.textPrimary));  // dot_inactive
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorAccent)); // dot_active
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
                R.drawable.stayhome2));
        return slideList;
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


}
