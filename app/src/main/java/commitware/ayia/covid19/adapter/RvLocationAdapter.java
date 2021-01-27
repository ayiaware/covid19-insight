package commitware.ayia.covid19.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.R;

import commitware.ayia.covid19.models.Location;


public class RvLocationAdapter extends RecyclerView.Adapter<RvLocationAdapter.ViewHolder> implements Filterable {


  private List<Location> mValues;

  private List<Location> mValuesFilteredList;

  private final Context mContext;

  public RvLocationAdapter(Context context) {
        mContext = context;
    }
    
    public void setmValues(List<Location>values){

        if (mValues == null) {

            mValues = values;

            mValuesFilteredList = values;

            notifyItemRangeInserted(0, values.size());
        }
        else {
            //Diff util not necessary since the locations do not change and are not refreshed
            //i will leave this here for now
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mValues.size();
                }

                @Override
                public int getNewListSize() {
                    return values.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mValues.get(oldItemPosition).getCountry()
                            .equals(values.get(newItemPosition).getCountry());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Location newCases = values.get(newItemPosition);
                    Location oldCases = mValues.get(oldItemPosition);

                    return newCases.getCountry().equals(oldCases.getCountry())
                            && TextUtils.equals(newCases.getContinent(), oldCases.getContinent())
                            && newCases.getState().equals(oldCases.getState());
                }
            });
            mValues = values;
            mValuesFilteredList = values;
            result.dispatchUpdatesTo(this);
        }
    }



    //view holder is same with the one in insight adapter hence this view holder can me extracted
    //an called listViewHolder which is the name of parent fragment

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        Location item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(Location item) {
            this.item = item;
            textView.setVisibility(View.INVISIBLE);
            textView2.setText(item.getName());

        }///
    }

    @NonNull
    @Override
    public RvLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValuesFilteredList.get(position));

    }

    @Override
    public int getItemCount() {
        return mValuesFilteredList == null ? 0 : mValuesFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mValuesFilteredList = mValues;
                } else {
                    List<Location> filteredList = new ArrayList<>();

                    for (Location row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase().trim())) {
                            filteredList.add(row);
                        }
                    }

                    mValuesFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mValuesFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mValuesFilteredList = (List<Location>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }


    public List<Location> getValues() {
        return mValuesFilteredList;
    }

}