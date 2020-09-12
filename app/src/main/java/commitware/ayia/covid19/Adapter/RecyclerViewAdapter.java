package commitware.ayia.covid19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.CountryServer;
import commitware.ayia.covid19.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

  private List<CountryServer> mValues;
  private List<CountryServer> mValuesFilteredList;
    private Context mContext;


    public RecyclerViewAdapter(Context context, List<CountryServer> values) {

        mValues = values;
        mValuesFilteredList = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        CountryServer item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(CountryServer item) {
            this.item = item;
            textView2.setVisibility(View.VISIBLE);
            textView.setText(Integer.toString(item.getConfirmed()));
            textView2.setText(item.getmCovidCountry());

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValuesFilteredList.get(position));

    }

    @Override
    public int getItemCount() {

        return mValuesFilteredList.size();
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
                    List<CountryServer> filteredList = new ArrayList<>();
                    for (CountryServer row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getmCovidCountry().toLowerCase().contains(charString.toLowerCase().trim())) {
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
                mValuesFilteredList = (List<CountryServer>) filterResults.values;
                //mValuesFilteredList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public List<CountryServer> getmValuesFilteredList(
    )
    {
        return mValuesFilteredList;
    }
}