package com.tamilanjobs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Notificationdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity" ;
    RecyclerView recycle_notitifacation;
    NotificationAdapter notificationAdapter;
    LinearLayout lay_nodata_found;

    ProgressDialog progressDialog;
    ArrayList<Notificationdetail> notification_list;

    AVLoadingIndicatorView avloadingIndicatornotification;

    TextView delete_allnotify;

    AdView adViewbottom;

    LinearLayout linear_avloader,linear_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        UIinitialization();

    }

    private void UIinitialization() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.toolbar_backarrowicon);


        toolbar.setTitleTextColor(Color.WHITE);


      //  progressDialog = new ProgressDialog(NotificationActivity.this);


        lay_nodata_found = (LinearLayout) findViewById(R.id.lay_nodata_found);
        recycle_notitifacation = (RecyclerView) findViewById(R.id.recycle_notitifacation);
        avloadingIndicatornotification =  findViewById(R.id.avloadingIndicatornotification);
        delete_allnotify =  findViewById(R.id.delete_allnotify);
        linear_avloader =  findViewById(R.id.linear_avloader);
        linear_notification =  findViewById(R.id.linear_notification);

        recycle_notitifacation.setLayoutManager(new LinearLayoutManager(this));

        notification_list = new ArrayList<>();



        //onclick
        delete_allnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(notification_list.size()!=0){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NotificationActivity.this);

                    // Setting Dialog Title
                   // alertDialog.setTitle("Confirm Delete...");
                    alertDialog.setTitle("இந்த அறிவிப்பை எல்லாம் நீக்க விரும்புகிறீர்களா");

                    // Setting Dialog Message
                    //alertDialog.setMessage("Are you sure you want delete this?");

                    // Setting Icon to Dialog

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            // Write your code here to invoke YES event

                            deleteall_notify();

                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                           // Toast.makeText(NotificationActivity.this, "No notification deleted", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                }else {
                    Toast.makeText(NotificationActivity.this, "No notification available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //adview


        adViewbottom = findViewById(R.id.adViewbottom);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewbottom.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdUnitId(String.valueOf(R.string.Banner_id));


        adViewbottom.setVisibility(View.GONE);


        //onclick adview

        adViewbottom.setAdListener(new AdListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: " + "loaded");
                adViewbottom.setVisibility(View.VISIBLE);
              //  Toast.makeText(NotificationActivity.this, "loaded", Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");
               // Toast.makeText(NotificationActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "onAdLoaded: " + "onAdOpened");
              //  Toast.makeText(NotificationActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");
              //  Toast.makeText(NotificationActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "onAdLoaded: " + "onAdClosed");
               // Toast.makeText(NotificationActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();

            }
        });




        jobnotification_detail();

    }


    //joblist

    Call<BasicResponse> notificationcall;

    private void jobnotification_detail() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        //progressDialog.show();

        Log.d(TAG, "jobnotification_detail: "  + Pref.getPreAccountDeviceId());

        //avloadingIndicatornotification.setVisibility(View.VISIBLE);
        notificationcall = apiService.getnotificationlist(Pref.getPreAccountDeviceId());

        linear_avloader.setVisibility(View.VISIBLE);
        linear_notification.setVisibility(View.GONE);


        notificationcall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    notification_list.addAll(response.body().getNotification_list());

                    Log.d("sdsdsds", "onResponse: " + notification_list.size());

                    if (notification_list.size() != 0) {


                        lay_nodata_found.setVisibility(View.GONE);

                        linear_avloader.setVisibility(View.GONE);
                        linear_notification.setVisibility(View.VISIBLE);

                       // progressDialog.dismiss();
                        notificationAdapter = new NotificationAdapter(NotificationActivity.this,notification_list);
                        recycle_notitifacation.setAdapter(notificationAdapter);
                    }else {
                        //avloadingIndicatornotification.setVisibility(View.GONE);
                        lay_nodata_found.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
               // progressDialog.dismiss();
                linear_avloader.setVisibility(View.GONE);
                linear_notification.setVisibility(View.GONE);

            }
        });

    }


    Call<BasicResponse> deleteall_notifycall;

    private void deleteall_notify() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        deleteall_notifycall = apiService.deleteallnotify(Pref.getPreAccountDeviceId());

        deleteall_notifycall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {


                    Intent intent = new Intent(NotificationActivity.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    Toast.makeText(NotificationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(NotificationActivity.this, "Already deleted ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Toast.makeText(NotificationActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent intent = new Intent(NotificationActivity.this,DashboardActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }


    //onbackpress

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
