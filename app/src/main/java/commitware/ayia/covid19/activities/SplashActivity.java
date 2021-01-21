package commitware.ayia.covid19.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import commitware.ayia.covid19.AppController;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.AppUtils;

import static commitware.ayia.covid19.AppUtils.LIST_REQUEST;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE;
import static commitware.ayia.covid19.AppUtils.LIST_TYPE_SETUP;
import static commitware.ayia.covid19.AppUtils.LOCATION_STATE;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);
                if(isFirstStart) {

                    String state = AppController.getInstance().getState();
                    Toast.makeText(SplashActivity.this,""+ AppUtils.getCountry()+" "+AppUtils.getLanguage(),Toast.LENGTH_LONG).show();

                    if(state==null||state.equals("")) {

                        Intent it = new Intent(SplashActivity.this, ListActivity.class);
                        it.putExtra(LIST_REQUEST, LOCATION_STATE);
                        it.putExtra(LIST_TYPE, LIST_TYPE_SETUP);
                        startActivity(it);
                    }

                    else {
                        Intent it = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(it);
                        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    }

                }
                else {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(0,0);
                }

                SplashActivity.this.finish();

            }
        }, 2000);
    }
}
