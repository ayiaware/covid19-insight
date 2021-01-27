package commitware.ayia.covid19.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.interfaces.FragmentInteraction;
import commitware.ayia.covid19.models.Location;
import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.utils.CSVReader;

import static commitware.ayia.covid19.utils.AppUtils.SLIDER_INTENT;

public class MainActivity extends AppCompatActivity implements FragmentInteraction {
    AppBarConfiguration appBarConfiguration;
    boolean isShow = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_helpline, R.id.navigation_dashboard, R.id.navigation_info,
                    R.id.navigation_news).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);

//        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager
//                .getDefaultSharedPreferences(getApplicationContext());
//
//        boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

        boolean  isFirstStart = BasicApp.getInstance().isFirstStart();

        if (isFirstStart) {
            graph.setStartDestination(R.id.listFragment);
        } else {
            graph.setStartDestination(R.id.navigation_dashboard);
        }

        navController.setGraph(graph);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.listFragment ||destination.getId() == R.id.listDetailFragment) {
                    navView.setVisibility(View.GONE);
                    isShow = false;
                } else {
                    navView.setVisibility(View.VISIBLE);
                    isShow = true;
                }
                updateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(isShow);

        return true;
    }
    public void updateOptionsMenu() {
//        isShow = !isShow;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent it = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(it);
            overridePendingTransition(0,0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void getListIntent(String intent, String argument) {

        if (intent.equals(SLIDER_INTENT)) {
            Intent it = new Intent(MainActivity.this, GuidelinesActivity.class);
            it.putExtra("sliderRequest", argument);
            startActivity(it);
            overridePendingTransition(0,0);
        }

    }

    @Override
    public void getNewsIntent(News news) {
        Intent it = new Intent(MainActivity.this, NewsActivity.class);
        it.putExtra(NewsActivity.PARCELABLE_PARSING_DATA, news);
        startActivity(it);
        overridePendingTransition(0,0);
    }

    @Override
    public void getCallHelplineIntent(String helpline, String intent) {
        switch (intent) {
            case "Whatsapp":
                sendViaWhatsApp(helpline);
                break;
            case "SMS":
                sentViaSMS(helpline);
                break;
            case "Phone":
                makePhoneCall(helpline);
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void sendViaWhatsApp(String helpline) {
        String url = "https://api.whatsapp.com/send?phone=" + "+234"+ helpline.substring(1);
        try {
            PackageManager pm = MainActivity.this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent intentWhatsapp = new Intent();
            intentWhatsapp.setAction(Intent.ACTION_SEND);
            intentWhatsapp.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            intentWhatsapp.setType("text/plain");
            intentWhatsapp.setPackage("com.whatsapp");
            if (intentWhatsapp.resolveActivity(getPackageManager()) != null) {
                startActivity(intentWhatsapp);
            }
            if (intentWhatsapp.resolveActivity(getPackageManager()) != null) {
                startActivity(intentWhatsapp);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void sentViaSMS(String helpline) {
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("smsto:"+helpline));  // This ensures only SMS apps respond
        // intentSMS.setType("vnd.android-dir/mms-sms");
        intentSMS.putExtra("address", helpline);
        intentSMS.putExtra("sms_body", "your message...");
        if (intentSMS.resolveActivity(getPackageManager()) != null) {
            startActivity(intentSMS);
        }
    }

    private void makePhoneCall(String helpline) {
        Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + helpline));
        if (intentCall.resolveActivity(getPackageManager()) != null) {
            startActivity(intentCall);
        }
    }

}
