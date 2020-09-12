package commitware.ayia.covid19.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import commitware.ayia.covid19.Controllers.AppController;

import commitware.ayia.covid19.interfaces.OnFragmentListenerMain;
import commitware.ayia.covid19.models.News;
import commitware.ayia.covid19.R;

import static commitware.ayia.covid19.Controllers.AppUtils.LIST_INTENT;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_REQUEST;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_TYPE;
import static commitware.ayia.covid19.Controllers.AppUtils.LIST_TYPE_SERVER;
import static commitware.ayia.covid19.Controllers.AppUtils.SLIDER_INTENT;

public class MainActivity extends AppCompatActivity implements OnFragmentListenerMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = null;
        if(AppController.getInstance().getAppType().equals("covidNg"))
        {
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_helpline, R.id.navigation_dashboard, R.id.navigation_info, R.id.navigation_news)
                    .build();
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (appBarConfiguration != null) {
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

        });


    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
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
    public void getListIntent(String intent, String arguement) {
        if (intent.equals(LIST_INTENT))
        {
            Intent it = new Intent(MainActivity.this, ListActivity.class);
            it.putExtra(LIST_REQUEST, arguement);
            it.putExtra(LIST_TYPE, LIST_TYPE_SERVER);
            startActivity(it);
            overridePendingTransition(0,0);
        }
        if (intent.equals(SLIDER_INTENT))
        {
            Intent it = new Intent(MainActivity.this, SliderActivity.class);
            it.putExtra("sliderRequest", arguement);
            startActivity(it);
            overridePendingTransition(0,0);
        }


    }

    @Override
    public void getNewsIntent(News news) {
        Intent it = new Intent(MainActivity.this, NewsDetailsActivity.class);
        it.putExtra(NewsDetailsActivity.PARCELABLE_PARSING_DATA, news);
        startActivity(it);
        overridePendingTransition(0,0);
    }

    @Override
    public void getCallHelplineIntent(String helpline, String intent) {
        switch (intent)
        {
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
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


    private void sendViaWhatsApp(String helpline)
    {
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


    private void sentViaSMS(String helpline)
    {
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("smsto:"+helpline));  // This ensures only SMS apps respond
        // intentSMS.setType("vnd.android-dir/mms-sms");
        intentSMS.putExtra("address", helpline);
        intentSMS.putExtra("sms_body", "your message...");
        if (intentSMS.resolveActivity(getPackageManager()) != null) {
            startActivity(intentSMS);
        }
    }


    private void makePhoneCall(String helpline)
    {
        Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + helpline));
        if (intentCall.resolveActivity(getPackageManager()) != null) {
            startActivity(intentCall);
        }
    }

}
