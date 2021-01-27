package commitware.ayia.covid19.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import commitware.ayia.covid19.ui.news.NewsFragment;
import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;

public class NewsActivity extends AppCompatActivity {
    public static final String PARCELABLE_PARSING_DATA = "parcelable_parsing_data" ;

    private void beginSliderTransaction(News news) {

        NewsFragment fragment = NewsFragment.newInstance(news);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        News model = getIntent().getParcelableExtra(PARCELABLE_PARSING_DATA);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        beginSliderTransaction(model);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
