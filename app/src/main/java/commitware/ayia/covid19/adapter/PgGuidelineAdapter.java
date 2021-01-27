package commitware.ayia.covid19.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import commitware.ayia.covid19.models.Guideline;
import commitware.ayia.covid19.R;

public class PgGuidelineAdapter extends PagerAdapter {



    private Context context;

    private List<Guideline> guidelineItems;



    public PgGuidelineAdapter(Context context, List<Guideline> guidelineItems) {

        this.context = context;
        this.guidelineItems = guidelineItems;

    }
    @Override
    public int getCount() {

        return guidelineItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_guideline, container,false);
        container.addView(view);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView slideHeading = view.findViewById(R.id.tvHeading);
        TextView slideDescription = view.findViewById(R.id.tvSubHeading);




        imageView.setImageResource(guidelineItems.get(position).getImage());

        slideHeading.setText(guidelineItems.get(position).getHeading());
        slideDescription.setText(guidelineItems.get(position).getSubHeading());



        return view;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }

}


