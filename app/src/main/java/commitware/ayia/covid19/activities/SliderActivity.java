package commitware.ayia.covid19.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import commitware.ayia.covid19.interfaces.OnFragmentListenerSlider;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.fragments.InfoAsListFragment;
import commitware.ayia.covid19.fragments.InfoAsSliderFragment;

public class SliderActivity extends AppCompatActivity implements OnFragmentListenerSlider {
    private String sliderRequest;

    String tag;
    FloatingActionButton floatingActionButton;

    private void beginSliderTransaction(String sliderRequest)
    {
        tag = "slider";
        InfoAsSliderFragment fragment = InfoAsSliderFragment.newInstance(sliderRequest);
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
        InfoAsListFragment fragment = InfoAsListFragment.newInstance(sliderRequest);
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
        setContentView(R.layout.activity_slider);
        sliderRequest = getIntent().getStringExtra("sliderRequest");

        beginSliderTransaction(sliderRequest);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
           // actionBar.setTitle("");
            actionBar.hide();
        }

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setEnabled(true);
        floatingActionButton.setClickable(true);
        floatingActionButton.setOnClickListener(v -> {
            InfoAsSliderFragment fragment =(InfoAsSliderFragment)getSupportFragmentManager().findFragmentByTag("slide");
            if(fragment!= null && fragment.isVisible())
            {
                beginRecyclerTransaction(sliderRequest);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_carousel_white_24dp));
            }
            InfoAsListFragment fragmentR =(InfoAsListFragment)getSupportFragmentManager().findFragmentByTag("recycler");
            if(fragmentR!= null && fragmentR.isVisible()) {

                beginSliderTransaction(sliderRequest);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_list_white_24dp));
            }

        });

    }



    @Override
    public void doOnBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_switch_view) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
