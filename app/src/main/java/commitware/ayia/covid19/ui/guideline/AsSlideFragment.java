package commitware.ayia.covid19.ui.guideline;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import commitware.ayia.covid19.adapter.PgGuidelineAdapter;

import commitware.ayia.covid19.data.GuidelinesData;
import commitware.ayia.covid19.models.Guideline;
import commitware.ayia.covid19.R;


public class AsSlideFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String request;


    private ViewPager sViewPager;
    private LinearLayout dotsLayout;

    private PgGuidelineAdapter pgGuidelineAdapter;

    private Button btnNext, btnCancel;

    private int slideCount;



    public AsSlideFragment() {
        // Required empty public constructor
    }


    public static AsSlideFragment newInstance(String param1) {
        AsSlideFragment fragment = new AsSlideFragment();
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
       View root = inflater.inflate(R.layout.fragment_as_slide, container, false);

        sViewPager =  root.findViewById(R.id.sViewPager);
        dotsLayout = root.findViewById(R.id.layoutDots);
        btnNext =  root.findViewById(R.id.btnNext);
        btnCancel= root.findViewById(R.id.btnCancel);


        List<Guideline> guidelineList = new GuidelinesData(request).getGuidelines();

        pgGuidelineAdapter = new PgGuidelineAdapter(getActivity(), guidelineList);

        slideCount = pgGuidelineAdapter.getCount();

        // set adapter in ViewPager
        sViewPager.setAdapter(pgGuidelineAdapter);

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
        if (current < pgGuidelineAdapter.getCount()) {
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
                btnNext.setText(R.string.finish);
                btnCancel.setVisibility(View.GONE);
            } else {
                btnNext.setText(R.string.next);
                btnCancel.setVisibility(View.VISIBLE);
                btnCancel.setText(R.string.cancel);
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




    public void onBackPressed() {
        requireActivity().onBackPressed();
    }



}
