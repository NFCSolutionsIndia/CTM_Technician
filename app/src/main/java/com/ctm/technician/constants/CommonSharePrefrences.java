package com.ctm.technician.constants;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonSharePrefrences {
    private static CommonSharePrefrences mInstance;
    private static Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String PREFERENCE_FILE_NAME = "CTM";
    public static final String PREFERENCE_GCM = "gcm";
    public static final String PREFERENCE_USER_mobile = "mobile";
    public static final String PREFERENCE_TOKEN = "token";
    public static final String PREFERENCE_USERNAME= "name";
    public static final String PREFERENCE_DESIGNATION= "designation";
    public static final String PREFERENCE_TECHNICIANID= "technicianid";
    public static final String PREFERENCE_USERID= "id";


    public static final String PREFERENCE_LANGUAGE = "language";

    private CommonSharePrefrences(Context context) {
        this.context = context;
    }

    public static synchronized CommonSharePrefrences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CommonSharePrefrences(context);
        }
        return mInstance;
    }

    public void initializeSharedPrefs() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME
                    , Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }


    public void setlanguage(String language) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_LANGUAGE, language);
        editor.commit();
    }

    public String getlanguage() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_LANGUAGE, "");
    }


    public void setGcm(String token) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_GCM, token);
        editor.commit();
    }

    public String getGcm() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_GCM, "");
    }


    public void setusermobile(String mobile) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_USER_mobile, mobile);
        editor.commit();
    }

    public String getUsermobile() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_USER_mobile, "");
    }

    public void settoken(String token) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_TOKEN, token);
        editor.commit();
    }

    public String gettoken() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_TOKEN, "");
    }

    public void setusername(String username) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_USERNAME, username);
        editor.commit();
    }

    public String getusername() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_USERNAME, "");
    }


    public void setdesignation(String designation) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_DESIGNATION, designation);
        editor.commit();
    }

    public String getdesignation() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_DESIGNATION, "");
    }

    public void setuserid(String userid) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_USERID, userid);
        editor.commit();
    }

    public String getuserid() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_USERID, "");
    }

    public void settechnicianid(String technicianid) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_TECHNICIANID, technicianid);
        editor.commit();
    }

    public String gettechnicianid() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_TECHNICIANID, "");
    }


    public void settechname(String username) {
        initializeSharedPrefs();
        editor.putString(PREFERENCE_USERNAME, username);
        editor.commit();
    }

    public String gettechname() {
        initializeSharedPrefs();
        return sharedPreferences.getString(PREFERENCE_USERNAME, "");
    }





}
