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

import commitware.ayia.covid19.models.Slide;
import commitware.ayia.covid19.R;


public class RecyclerViewAdapterSlide extends RecyclerView.Adapter<RecyclerViewAdapterSlide.ViewHolder>{

  private List<Slide> mValues;

    private Context mContext;


    public RecyclerViewAdapterSlide(Context context, List<Slide> values) {

        mValues = values;

        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public ImageView imageView;
        Slide item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.text);
            imageView =  v.findViewById(R.id.imageView);

        }

        public void setData(Slide item) {
            this.item = item;

            textView.setText(item.getSubHeading());
            imageView.setImageResource(item.getImage());

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterSlide.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler, parent, false);

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