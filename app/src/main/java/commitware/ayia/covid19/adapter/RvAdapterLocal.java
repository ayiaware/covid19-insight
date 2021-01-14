package commitware.ayia.covid19.adapter;

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

import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.R;


public class RvAdapterLocal extends RecyclerView.Adapter<RvAdapterLocal.ViewHolder> implements Filterable {

  private List<Country> mValues;
  private List<Country> mValuesFilteredList;
    private Context mContext;


    public RvAdapterLocal(Context context, List<Country> values) {

        mValues = values;
        mValuesFilteredList = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        Country item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);


        }

        public void setData(Country item) {
            this.item = item;

            textView.setText(item.getName());

        }
    }

    @NonNull
    @Override
    public RvAdapterLocal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                    List<Country> filteredList = new ArrayList<>();
                    for (Country row : mValues) {

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
                mValuesFilteredList = (List<Country>) filterResults.values;
                //mValuesFilteredList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public List<Country> getmValuesFilteredList(
    )
    {
        return mValuesFilteredList;
    }
}