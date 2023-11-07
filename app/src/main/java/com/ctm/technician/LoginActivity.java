package com.ctm.technician;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.SupervisorActivity.DashboardActivity;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Login.LoginPojoResponse;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    EditText userNameEt, passwordEt;
    Button loginbtn;
    String make, model,deviceId, mImeiNumber;
    String fDate;
    private CommonFunctions com;
    private String strUserName = "", strPassword = "";
    private static final int REQUEST_PERMISSIONS = 20;
    AlertDialog.Builder builder;
    private CommonSharePrefrences pref;
    TextInputLayout evpassword;
    View view;
    ScrollView scroll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        make = android.os.Build.MANUFACTURER;
        com = new CommonFunctions(this);
        pref = CommonSharePrefrences.getInstance(this);
        builder = new AlertDialog.Builder(this);

        model = android.os.Build.MODEL;
        userNameEt = (EditText) findViewById(R.id.et_username);

        evpassword = (TextInputLayout) findViewById(R.id.ev_password);
        evpassword.setHint("");
        passwordEt = (EditText) findViewById(R.id.et_password);
        scroll = (ScrollView) findViewById(R.id.scroll);
        view = (View) findViewById(R.id.view);

        loginbtn = (Button) findViewById(R.id.btnSubmit);
        Calendar c = Calendar.getInstance(Locale.US);


        fDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(c.getTime());
        initializeOnClickListeners();
        checkPermissionsMarsh();






        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen && userNameEt.hasFocus()) {
                    View lastChild = scroll.getChildAt(scroll.getChildCount() - 1);
                    int bottom = lastChild.getBottom() + scroll.getPaddingBottom();
                    int sy = scroll.getScrollY();
                    int sh = scroll.getHeight();
                    int delta = bottom - (sy + sh);
                    scroll.smoothScrollBy(0, delta);
                }
            }
        });

    }

    private void setLanguage() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.language_title);
        String[] languages = getResources().getStringArray(R.array.languages);
        adb.setItems(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] codes = new String[]{"en", "hi", "mr"};
                String language = codes[which];
                pref.setlanguage(language);
                restartApp();
            }
        });
        adb.show();
    }

    public void restartApp() {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void initializeOnClickListeners() {
        loginbtn.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        com.HidingSoftKeyBoard(view);
        switch (view.getId()) {
            case R.id.btnSubmit:
                checkValidations();
                break;
        }
    }

    private void checkValidations() {
        strUserName = com.getTextFromView(userNameEt);
        strPassword = com.getTextFromView(passwordEt);
        if (strUserName.isEmpty()) {
            userNameEt.requestFocus();
            userNameEt.setError(com.getString(R.string.strLogin_Empty_Number));
            return;
        } else if (strUserName.length() != 10) {
            userNameEt.requestFocus();
            userNameEt.setError(com.getString(R.string.strLogin_Invalid_Mobile));
            return;
        } else if (strPassword.isEmpty()) {
            passwordEt.requestFocus();
            passwordEt.setError(com.getString(R.string.strLogin_Empty_Password));
            return;
        } else if (strPassword.length() < 6) {
            passwordEt.requestFocus();
            passwordEt.setError(com.getString(R.string.strLogin_Invalid_Password));
            return;
        } else if (!strUserName.isEmpty() && strUserName.length() == 10
                && !strPassword.isEmpty() && strPassword.length() >= 6) {
            sendLoginToServer();
        }
    }


    private void sendLoginToServer() {
        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            LoginPojoResponse response = new LoginPojoResponse();
            response.setContactNumber(strUserName);
            response.setPassword(strPassword);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginPojoResponse> call = apiService.getLoginDetails(response);
            call.enqueue(new Callback<LoginPojoResponse>() {
                @Override
                public void onResponse(Call<LoginPojoResponse> call, Response<LoginPojoResponse> response) {
                    com.dismissProgressDialogue();
                    if (response.code() == 200) {
                        parseLoginResponse(response.body());
                    } else {
                        com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                    }
                }
                @Override
                public void onFailure(Call<LoginPojoResponse> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private void parseLoginResponse(LoginPojoResponse body) {

        try {
            if (body.getLogindetails().getStatusCode() == 200) {

                pref.setusermobile(body.getLogininfo().getContactNumber());
                pref.settoken("Bearer " + body.getLogininfo().getToken());
                pref.setusername(body.getLogininfo().getUserName());
                pref.setuserid("" + body.getLogininfo().getUserId());
                pref.setdesignation(body.getLogininfo().getDesignation());

                if (body.getLogininfo().getDesignation().equals("Technician")) {

                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity((i));
                } else if (body.getLogininfo().getDesignation().equals("Supervisor")) {
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity((i));

                } else {
                   showAlertDialogOK("Invalid credentials");

                }

            } else {
               showAlertDialogOK(body.getLogindetails().getStatusMessage());
            }
        } catch (NullPointerException e) {
            com.showAlertDialogOK(com.getString(R.string.something));
        }
    }

    private void showInternetDialogue() {
        builder.setTitle("No internet connection!");
        builder.setMessage("We're having trouble reaching the network. Check your connection or try again.");
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }
    public int getWidth() {
        Display display = ((Activity)this).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        return screen_width;
    }

    public Dialog showAlertDialogOK(String message) {
        final Dialog dialog = new Dialog(this);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert);
            dialog.getWindow().setLayout(getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            LinearLayout linearLayoutYesNo = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_YesNo);
            LinearLayout linearLayoutOK = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_OK);
            TextView txtCustomAlert_Heading = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Heading);
            TextView txtCustomAlert_Message = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Message);
            TextView txtCustomAlert_Yes = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Yes);
            TextView txtCustomAlert_No = (TextView) dialog.findViewById(R.id.txt_CustomAlert_No);
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);

            txtCustomAlert_Heading.setText(getString(R.string.app_name) + " " + getString(R.string.strAlertTitle));
            txtCustomAlert_Message.setText(message);

            txtCustomAlert_OK.setBackgroundColor(getResources().getColor(R.color.red));
            linearLayoutYesNo.setVisibility(View.GONE);
            linearLayoutOK.setVisibility(View.VISIBLE);
            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dialog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }


    public void checkPermissionsMarsh() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                LoginActivity.super.requestAppPermissions(new
                                String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,
                                Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_SMS}, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);
            }
        }
    }
}