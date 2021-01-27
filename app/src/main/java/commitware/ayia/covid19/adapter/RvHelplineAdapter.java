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

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.models.Helpline;
import commitware.ayia.covid19.R;


public class RvHelplineAdapter extends RecyclerView.Adapter<RvHelplineAdapter.ViewHolder> {

    private List<Helpline> mValues;
    private Context mContext;


    public RvHelplineAdapter(Context context, List<Helpline> values) {

        mValues = values;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        ImageView imageView;
        Helpline item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvHelplineLabel);
            textView2 =  v.findViewById(R.id.tvHelpline);
            imageView = v.findViewById(R.id.tvHelplineType);
        }

        public void setData(Helpline item) {
            this.item = item;
            textView2.setText(item.getFIELD1());

            textView.setText(BasicApp.getInstance().getLocation().getState());

            if (item.getFIELD3().equals("Whatsapp"))
            {
                imageView.setImageResource(R.drawable.whatsapp);
            }
            else if (item.getFIELD3().equals("SMS"))
            {
                imageView.setImageResource(R.drawable.ic_sms_black_24dp);
            }
            else if (item.getFIELD3().equals("Phone"))
            {
                imageView.setImageResource(R.drawable.ic_call_black_24dp);
            }
        }
    }

    @NonNull
    @Override
    public RvHelplineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_helpline, parent, false);

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
    public List<Helpline> getmValuesFilteredList(
    )
    {
        return mValues;
    }
}