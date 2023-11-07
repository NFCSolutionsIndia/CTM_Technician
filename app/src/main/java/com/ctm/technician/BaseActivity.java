package com.ctm.technician;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.ctm.technician.constants.CommonSharePrefrences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BaseActivity extends RuntimePermissionsActivity {
    private ProgressDialog mProgressDialog;
    private String CONTENT_AUTHORITY;
    private final long SYNC_FREQUENCY = 5;
    private static final int REQUEST_PERMISSIONS = 20;
    private CommonSharePrefrences pref;

    @SuppressWarnings("static-access")
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = CommonSharePrefrences.getInstance(this);

        String language = pref.getlanguage();
        CONTENT_AUTHORITY = getPackageName();

        if (language.equals(""))
            language = "English";
        setLocale(language);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public boolean isValid(String charSequence) {
        charSequence = charSequence.trim();
        if ((charSequence != null) & !(charSequence.equalsIgnoreCase("")))
            return true;
        else
            return false;
    }

    public void showToast(String msg) {
        showDialog(msg);
    }

    public void showDialog(String msg) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage(msg);
        adb.setPositiveButton(R.string.ok, null);
        adb.show();
    }


    public void checkPermissionsMarsh() {
        if (Build.VERSION.SDK_INT >= 23) {

            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                BaseActivity.super.requestAppPermissions(new
                                String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,
                                Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE,}, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);


            }
        }
    }

    public void showDialog(String title, String msg) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(title);
        adb.setMessage(msg);
        adb.setPositiveButton(getResources().getString(R.string.ok), null);
        adb.show();
    }

    public void gotoNext(Class<?> target) {
        startActivity(new Intent(this, target));
    }

    public void gotoNextByFinish(Class<?> target) {
        startActivity(new Intent(this, target));
        finish();
    }

    public String getDateInStandardFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    public String getDateInRequiredFormat(String format) {
        return new SimpleDateFormat(format, Locale.US).format(new Date());
    }


    public void showProgress(String msg) {
        if (mProgressDialog != null)
            mProgressDialog.setMessage(msg);
        else
            mProgressDialog = ProgressDialog.show(this, "", msg);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }


    public void showDialogBox(String title) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(BaseActivity.this);
        adb.setMessage(title);
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                adb.setCancelable(true);
            }
        });

        adb.show();
    }

       public String getNullifiedValue(String value) {
        if (value == null || value.equals(""))
            return "N/A";
        return value.trim().length() > 0 ? value : "N/A";
    }

    public long getDifference(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        Date now = new Date();
        return c.getTimeInMillis() - now.getTime();
    }

    public String localDate(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("dd-MM-yyyy").format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDateInRequiredFormat(String format, Date d) {
        return new SimpleDateFormat(format).format(d);
    }

    public void restartApp() {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }
}
