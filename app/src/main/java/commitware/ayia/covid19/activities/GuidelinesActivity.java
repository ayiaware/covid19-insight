package commitware.ayia.covid19.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import commitware.ayia.covid19.R;
import commitware.ayia.covid19.ui.guideline.AsListFragment;
import commitware.ayia.covid19.ui.guideline.AsSlideFragment;

public class GuidelinesActivity extends AppCompatActivity {
    private String sliderRequest;

    String tag;
    FloatingActionButton fab;

    private void beginSliderTransaction(String sliderRequest) {
        tag = "slider";
        AsSlideFragment fragment = AsSlideFragment.newInstance(sliderRequest);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.fragment, fragment,"slide")
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }

    }
    private void beginRecyclerTransaction(String sliderRequest)
    {tag = "recycler";
        AsListFragment fragment = AsListFragment.newInstance(sliderRequest);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.fragment, fragment, "recycler")
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(sliderRequest);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);
        sliderRequest = getIntent().getStringExtra("sliderRequest");

        beginSliderTransaction(sliderRequest);


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }

        fab = findViewById(R.id.floatingActionButton);
        fab.setEnabled(true);
        fab.setClickable(true);
        fab.setOnClickListener(v -> {
            AsSlideFragment fragment =(AsSlideFragment)getSupportFragmentManager().findFragmentByTag("slide");
            if(fragment!= null && fragment.isVisible()) {
                beginRecyclerTransaction(sliderRequest);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_carousel_white_24dp));
            }
            AsListFragment fragmentR =(AsListFragment)getSupportFragmentManager().findFragmentByTag("recycler");
            if(fragmentR!= null && fragmentR.isVisible()) {

                beginSliderTransaction(sliderRequest);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_list_white_24dp));
            }

        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
