package commitware.ayia.covid19.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.utils.AppUtils;


public class NewsFragment extends Fragment {

    News news;
    News getNews;

    public NewsFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(News news) {
        NewsFragment fragment = new NewsFragment();
        fragment.setNewDetail(news);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNews = news;
    }

    private void setNewDetail(News news) {
        this.news = news;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        TextView tvTitle = root.findViewById(R.id.tvNewsTitleDetail);
        TextView tvSource = root.findViewById(R.id.tvNewsSourceDetail);
        TextView tvAuthor = root.findViewById(R.id.tvNewsAuthorDetail);
        TextView tvPublished = root.findViewById(R.id.tvNewsPublishedDetail);
        TextView tvDesc = root.findViewById(R.id.tvNewsDescDetail);
        TextView tvContent = root.findViewById(R.id.tvNewsContentDetail);

        ImageView imageView = root.findViewById(R.id.imageDetailNews);

        Button btnMore = root.findViewById(R.id.btnMore);



        tvTitle.setText(getNews.getTitle());
        tvSource.setText(String.format("%s %s", getResources().getString(R.string.sourceNews), getNews.getSource().getName()));
        if (getNews.getAuthor() == null) {
            tvAuthor.setText(R.string.editor);
        } else {
            tvAuthor.setText(String.format("%s %s", getResources().getString(R.string.authorNews), getNews.getAuthor()));
        }
        tvPublished.setText(String.format("%s %s", getResources().getString(R.string.dateNews),  AppUtils.DateFormat(getNews.getPublishedAt())));
        tvDesc.setText(getNews.getDescription());

        if (getNews.getContent() == null) {
            tvContent.setText(getResources().getString(R.string.description_unavailable));
        } else {
            tvContent.setText(getNews.getContent());
        }
        Glide.with(this)
                .load(getNews.getUrlToImage())
                .into(imageView);

        btnMore.setOnClickListener(view -> {
            String URL = getNews.getUrl();
            Uri urlUri = Uri.parse(URL);
            Intent toUrl = new Intent(Intent.ACTION_VIEW, urlUri);
            startActivity(toUrl);
            Toast.makeText(getContext(), getResources().getString(R.string.redirect), Toast.LENGTH_SHORT).show();
        });

        return root;
    }


}
