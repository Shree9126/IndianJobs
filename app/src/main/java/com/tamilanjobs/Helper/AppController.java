package com.tamilanjobs.Helper;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.ads.MobileAds;
import com.tamilanjobs.R;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;
import com.tamilanjobs.Service.ConnectivityReceiver;


public class AppController extends Application {

    private static AppController instance;
    public static SharedPreferences sharedpreferences;
    public static ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public static boolean profileUpdated = false;

    public static AppController getInstance(){
        return instance;
    }

    public static ApiInterface getClient() {
        return apiInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        MobileAds.initialize(this, getString(R.string.App_id));

        instance=this;
        sharedpreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);



    }



    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
