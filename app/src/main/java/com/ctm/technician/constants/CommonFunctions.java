package com.ctm.technician.constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Plannedevents.Prioritymodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Techniciansitelistmodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Ticketproritymodel;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.models.Checklist.Checklistsubmit;
import com.ctm.technician.models.Checklist.PMAssetControl;
import com.ctm.technician.models.Checklist.PMAssetControlValueLists;
import com.ctm.technician.models.Checklist.PMAssetsubmitdata;
import com.ctm.technician.models.Tickets.PMTicketData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommonFunctions {
    Context context;

    public static String fontName_Regular = "TitilliumWeb-Light.ttf";
    public static String type;
    MyProgressDialog Loading;




    public static List<PMAssetControlValueLists>controllist = new ArrayList<>();
    public static List<PMTicketData>ticketdata = new ArrayList<>();
    public static List<SupPMTechnicianData>supticketdata = new ArrayList<>();

    public static List<PMAssetControl>assetcontrollist = new ArrayList<>();
    public static ArrayList<PMAssetsubmitdata>datalist = new ArrayList<>();

    public static List<Techniciansitelistmodel> TechniciansitesList = new ArrayList<>();
    public static List<Ticketproritymodel> PriorityList = new ArrayList<>();


    public CommonFunctions(Context context) {
        this.context = context;
    }
    public void Toast_Short(String toastMessage) {
        if (toastMessage.length() > 0) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void Toast_Long(String toastMessage) {
        if (toastMessage.length() > 0) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
        }
    }
    public void showProgressDialogue() {
        Loading = new MyProgressDialog(context);

        try {
            Loading.show();
            Loading.setCancelable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialogue() {
        if (Loading.isShowing()) {
            try {
                Loading.dismiss();
                Loading.setCancelable(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Dialog showinternetdialog() {
        final Dialog dialog = new Dialog(context);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert);
            dialog.getWindow().setLayout(getWidth() - 40, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            LinearLayout linearLayoutOK = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_OK);
            TextView txtCustomAlert_Heading = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Heading);
            TextView txtCustomAlert_Message = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Message);
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);

            txtCustomAlert_Heading.setText("No internet connection!");
            txtCustomAlert_Message.setText(R.string.nointernet);


            linearLayoutOK.setVisibility(View.VISIBLE);
            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dialog;
    }


    public void gotoNextActivity(Intent intent, boolean finish) {
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
    }


    public void backtoPreviousActivity(boolean finish) {
        if (finish) {
            ((Activity) context).finish();
        }
    }


    public void backtoAnyActivity(Intent intent, boolean finish) {
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
    }

    public void setFontLargeNormal(View view, String ColorCode) {
        TextView tv = (TextView) view;
        tv.setTextAppearance(context, android.R.style.TextAppearance_Large);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontName_Regular);
        tv.setTypeface(tf);
        tv.setTextColor(Color.parseColor(ColorCode));
    }

    public void setFontMediumNormal(View view, String ColorCode) {
        TextView tv = (TextView) view;
        tv.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontName_Regular);
        tv.setTypeface(tf);
        tv.setTextColor(Color.parseColor(ColorCode));
    }

    public String getString(int resource) {
        String string = context.getResources().getString(resource);
        if (!string.contains("#") || !string.contains("RS")) {
            System.out.println("String From String.xml :- " + string);
        }
        return string;
    }

    public String getTextFromView(View view) {
        String str = "";
        try {
            str = ((TextView) view).getText().toString().trim();
        } catch (Exception e) {
            str = ((EditText) view).getText().toString().trim();
        }
        System.out.println("String from TextView/EditText : " + str);
        return str;
    }


    public int getWidth() {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        return screen_width;
    }

    @SuppressLint("NewApi")
    public int getHeight() {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_height = size.y;
        return screen_height;
    }


    public void setKeyBoardHidden() {
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void HidingSoftKeyBoard(View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isAvailable() &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public void showExitAlert() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert);
        dialog.getWindow().setLayout(getWidth() - 40, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout linearLayoutYesNo = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_YesNo);
        LinearLayout linearLayoutOK = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_OK);
        TextView txtCustomAlert_Heading = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Heading);
        TextView txtCustomAlert_Message = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Message);
        TextView txtCustomAlert_Yes = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Yes);
        TextView txtCustomAlert_No = (TextView) dialog.findViewById(R.id.txt_CustomAlert_No);
        TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);

        txtCustomAlert_Heading.setText(getString(R.string.app_name) + " " + getString(R.string.strAlertTitle));
        txtCustomAlert_Message.setText(getString(R.string.strAlertMessage_Exit));


        linearLayoutYesNo.setVisibility(View.VISIBLE);
        linearLayoutOK.setVisibility(View.GONE);

        txtCustomAlert_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                backtoPreviousActivity(true);
            }
        });

        txtCustomAlert_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public Dialog showAlertDialogOK(String message) {
        final Dialog dialog = new Dialog(context);
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




}
