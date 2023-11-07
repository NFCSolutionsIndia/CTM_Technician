package com.ctm.technician;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.ctm.technician.SupervisorActivity.DashboardActivity;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    AlertDialog.Builder builder;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private CommonSharePrefrences pref;
    GoogleCloudMessaging gcm;
    String regid;

    public static final String PROJECT_NUMBER = "140833883742";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splashscreen);
        pref = CommonSharePrefrences.getInstance(this);

        builder = new AlertDialog.Builder(this);


        getRegId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPlayServicesAvailable()) {
                    gotoNextScreen();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void gotoNextScreen() {

        String language = pref.getlanguage();

        if (language.equals("")) {
            Intent i = new Intent(SplashActivity.this, LanguageActivity.class);
            startActivity(i);
            finish();
        } else {
            if (pref.gettoken().equals("")) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                if (pref.getdesignation().equals("Technician")) {

                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity((i));
                    finish();

                } else {
                    Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity((i));
                    finish();


                }


            }
        }
    }

    private boolean isPlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    public void getRegId() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    pref.setGcm(regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {


            }
        }.execute(null, null, null);
    }

}
