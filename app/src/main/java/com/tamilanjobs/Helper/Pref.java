package com.tamilanjobs.Helper;

import android.content.Context;
import android.content.SharedPreferences;


public class Pref {

    private static final String PRE_AUTH_TOKEN = "authToken";
    private static final String PRE_ACCOUNT_ID = "account_id";
    private static final String PRE_ACCOUNT_DEVICE_ID = "account_deviceid";
    private static final String PRE_ACCOUNT_USERNAME = "account_username";
    private static final String PRE_ACCOUNT_PROFILE_IMAGE = "account_image";
    private static final String PRE_ACCOUNT_PROFILE = "account_profile";
    public static final String AD_COUNT= "ad_count";
    public static final String NOTIFIACTION= "notification";

    private static final String PRE_FIREBASE_TOKEN = "furebase_id";
    private static SharedPreferences preferences = AppController.sharedpreferences;
    private static SharedPreferences.Editor editor;


    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";

    private static Pref mInstance;
    private static Context mCtx;

    public static void setauthtoken(String auth_token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_AUTH_TOKEN, auth_token);
        editor.commit();
    }

    public static String getAuthtoken() {
        return preferences.getString(PRE_AUTH_TOKEN, null);
    }


    public static void setUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_USERNAME, username);
        editor.commit();
    }

    public static void setUserimage(String userimage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_PROFILE_IMAGE, userimage);
        editor.commit();
    }

    public static String getUsername() {
        return preferences.getString(PRE_ACCOUNT_USERNAME, null);
    }

    public static String getUserimage() {
        return preferences.getString(PRE_ACCOUNT_PROFILE_IMAGE, null);
    }

    public static void setProfileimage(String userimage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_PROFILE, userimage);
        editor.commit();
    }

    public static String getProfileimage() {
        return preferences.getString(PRE_ACCOUNT_PROFILE, null);
    }



    public static void setAccountId(int account_id) {
        editor = preferences.edit();
        editor.putInt(PRE_ACCOUNT_ID, account_id);
        editor.commit();
    }

   public static int getAccountId()  {
        return preferences.getInt(PRE_ACCOUNT_ID, 0);
    }

   public static String getPreAccountDeviceId() {
        return preferences.getString(PRE_ACCOUNT_DEVICE_ID, null);
    }

    public static void setPreAccountDeviceId(String usercode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_DEVICE_ID, usercode);
        editor.commit();
    }

    public static void clearpreference() {
        editor = preferences.edit();
        editor.clear();
        editor.commit();

    }


    public static void setdevicetoken(String token) {

        editor = preferences.edit();
        editor.putString(PRE_FIREBASE_TOKEN, token);
        editor.commit();

    }

    static String getfirbasetoken() {

        return preferences.getString(PRE_FIREBASE_TOKEN, null);

    }


    //firebase

    private Pref(Context context) {
        mCtx = context;
    }

    public static synchronized Pref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Pref(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }



    public static void setAdCount(int AdCount) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AD_COUNT, AdCount);
        editor.commit();
    }

    public static boolean setNotification(boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTIFIACTION, b);
        editor.commit();
        return b;
    }

    public static Boolean getNotification() {
        return preferences.getBoolean(NOTIFIACTION, false);
    }


    public static Integer getAdCount() {
        return preferences.getInt(AD_COUNT, 0);
    }

}
