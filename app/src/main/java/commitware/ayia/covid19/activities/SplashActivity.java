package commitware.ayia.covid19.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.R;
import commitware.ayia.covid19.utils.AppUtils;

import static commitware.ayia.covid19.utils.AppUtils.LIST_REQUEST;
import static commitware.ayia.covid19.utils.AppUtils.LIST_TYPE;
import static commitware.ayia.covid19.utils.AppUtils.SETUP;
import static commitware.ayia.covid19.utils.AppUtils.STATE;

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

                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                SplashActivity.this.finish();

            }
        }, 2000);
    }
}
