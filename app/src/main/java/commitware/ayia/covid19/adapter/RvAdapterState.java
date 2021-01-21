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

import commitware.ayia.covid19.R;
import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesState;


public class RvAdapterState extends RecyclerView.Adapter<RvAdapterState.ViewHolder> implements Filterable {


  private List<CasesState> mValues;
  private List<CasesState> mValuesFilteredList = new ArrayList<>();
  private Context mContext;


    public RvAdapterState(Context context) {

       
        mContext = context;

    }
    
    public void setmValues(List<CasesState>values){

        mValues = values;

        mValuesFilteredList = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        CasesState item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(CasesState item) {
            this.item = item;
            textView2.setVisibility(View.VISIBLE);
            textView.setText(item.getCases());
            textView2.setText("location");

        }
    }

    @NonNull
    @Override
    public RvAdapterState.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValuesFilteredList.get(position));

    }

    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
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
                    List<CasesState> filteredList = new ArrayList<>();
                    for (CasesState row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getState().toLowerCase().contains(charString.toLowerCase().trim())) {
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
                mValuesFilteredList = (List<CasesState>) filterResults.values;
                //mValuesFilteredList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public List<CasesState> getmValuesFilteredList() {
        return mValuesFilteredList;
    }
}