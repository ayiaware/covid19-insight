package commitware.ayia.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import commitware.ayia.covid19.models.Guideline;
import commitware.ayia.covid19.R;


public class RvGuidelineAdapter extends RecyclerView.Adapter<RvGuidelineAdapter.ViewHolder>{

  private List<Guideline> mValues;

    private Context mContext;


    public RvGuidelineAdapter(Context context, List<Guideline> values) {

        mValues = values;

        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public ImageView imageView;
        Guideline item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.text);
            imageView =  v.findViewById(R.id.imageView);

        }

        public void setData(Guideline item) {
            this.item = item;

            textView.setText(item.getSubHeading());
            imageView.setImageResource(item.getImage());

        }
    }

    @NonNull
    @Override
    public RvGuidelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_guideline, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

}