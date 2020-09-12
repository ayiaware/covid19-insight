package commitware.ayia.covid19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;


public class RecyclerViewAdapterNews extends RecyclerView.Adapter<RecyclerViewAdapterNews.ViewHolder>{

  private List<News> mValues = new ArrayList<>();

    private Context mContext;


    public RecyclerViewAdapterNews(Context context) {

        mContext = context;

    }
    public List<News> getNews() {
        return mValues;
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

         TextView tvTitle;
         TextView tvSource;
         ImageView imageNews;

        News item;

        public ViewHolder(View v) {

            super(v);
            tvTitle = itemView.findViewById(R.id.tvTitleNews);
            tvSource = itemView.findViewById(R.id.tvSourceNews);
            imageNews = itemView.findViewById(R.id.imageNews);


        }

        public void setData(News item) {
            this.item = item;
            Glide.with(mContext)
                    .load(item.getUrlToImage())
                    .into(imageNews);
            tvTitle.setText(item.getTitle());
            tvSource.setText(item.getSource().getName());

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_news, parent, false);

        return new ViewHolder(view);
    }

    public void setNews(List<News> itemModels) {
        if (mValues != null) {
            if (mValues.size() > 0) {
                mValues.clear();
            }
            mValues.addAll(itemModels);
        }
        notifyDataSetChanged();
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