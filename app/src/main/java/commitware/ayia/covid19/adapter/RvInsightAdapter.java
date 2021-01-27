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

import commitware.ayia.covid19.models.Insight;


public class RvInsightAdapter extends RecyclerView.Adapter<RvInsightAdapter.ViewHolder> implements Filterable {


  private List<Insight> mValues;

  private List<Insight> mValuesFilteredList;

  private final Context mContext;

  public RvInsightAdapter(Context context) {
        mContext = context;
    }
    
    public void setmValues(List<Insight>values){

        if (mValues == null) {

            mValues = values;

            mValuesFilteredList = values;

            notifyItemRangeInserted(0, values.size());
        }
        else {
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
                    return mValues.get(oldItemPosition).getName().equals(values.get(newItemPosition).getName());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Insight newCases = values.get(newItemPosition);
                    Insight oldCases = mValues.get(oldItemPosition);

                    return newCases.getName().equals(oldCases.getName())
                            && TextUtils.equals(newCases.getActive(), oldCases.getActive())
                            && newCases.getCases() == oldCases.getCases();
                }
            });
            mValues = values;
            mValuesFilteredList = values;
            result.dispatchUpdatesTo(this);
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        Insight item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(Insight item) {
            this.item = item;
            textView.setVisibility(View.VISIBLE);
            String cases = String.valueOf(item.getCases());
            textView.setText( cases);
            textView2.setText(item.getName());

        }
    }

    @NonNull
    @Override
    public RvInsightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                    List<Insight> filteredList = new ArrayList<>();

                    for (Insight row : mValues) {

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

                mValuesFilteredList = (List<Insight>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }


    public List<Insight> getValues() {
        return mValuesFilteredList;
    }

}