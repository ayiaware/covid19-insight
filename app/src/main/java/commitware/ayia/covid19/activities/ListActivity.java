package commitware.ayia.covid19.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;


import commitware.ayia.covid19.Controllers.AppController;
import commitware.ayia.covid19.fragments.ListActivityFragment;
import commitware.ayia.covid19.fragments.ListDetailFragment;
import commitware.ayia.covid19.interfaces.OnFragmentInteractionListener;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.CountryServer;
import commitware.ayia.covid19.R;


import static commitware.ayia.covid19.Controllers.AppUtils.LIST_REQUEST;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_TYPE;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_TYPE_LOCAL;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_TYPE_SETUP;
import static commitware.ayia.covid19.Controllers.AppUtils.LOCATION_COUNTRY;
import static commitware.ayia.covid19.Controllers.AppUtils.LOCATION_STATE;

public class ListActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    String location;
    private void beginListTransaction(String data, String listType)
    {
        ListActivityFragment fragment = ListActivityFragment.newInstance(data, listType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag())
                .commit();
    }
    private void beginDetailsTransaction(CountryServer countryServer){
        ListDetailFragment fragment = ListDetailFragment.newInstance(countryServer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag()).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String data = getIntent().getStringExtra(LIST_REQUEST);
        String listType = getIntent().getStringExtra(LIST_TYPE);
        location = listType;
        beginListTransaction(data, listType);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

                            onBackPressed();

                        }
                    }
                });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }



    }

    @Override
    public void listItemClickServer(CountryServer countryServer) {
        beginDetailsTransaction(countryServer);

    }

    @Override
    public void listItemClickSetting(Country country, String location, String listType) {

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = getSharedPreferences.edit();

        String countryName;
        String continent;
        String state;
        String id ;
        String code;

        switch (location){
            case LOCATION_STATE:
                state = country.getName();
                id = country.getContinent();
                e.putString("state", state);
                e.putString("id", id);
                AppController.getInstance().setId(id);
                AppController.getInstance().setState(state);
                break;
            case LOCATION_COUNTRY:
                countryName =  country.getName();
                continent = country.getContinent();
                code = country.getCode();
                e.putString("country", countryName);
                e.putString("continent",continent);
                e.putString("code",code);
                AppController.getInstance().setCountry(countryName);
                AppController.getInstance().setContinent(continent);
                AppController.getInstance().setCode(code);
                break;
        }

        if(listType.equals(LIST_TYPE_SETUP))
        {  e.putBoolean("firstStart",false);
            e.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            finish();
        }
        else if(listType.equals(LIST_TYPE_LOCAL)) {
            e.apply();
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

}
