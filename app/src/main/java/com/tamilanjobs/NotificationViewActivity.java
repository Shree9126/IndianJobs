package com.tamilanjobs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Notificatiomlist;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewActivity extends AppCompatActivity {


    private static final String TAG = "NotificationViewActiv";
    TextView notify_fulltitle,notify_fulljobheader,notify_fulldescription;
    ImageView notify_fullclose;

    AdView adViewbottom;

    InterstitialAd mInterstitialAd;

    ArrayList<Notificatiomlist> motification_general_list;
    ImageView img_notifywhatsshare;

    String notifytitle,notifycontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

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


        notify_fulltitle = (TextView)findViewById(R.id.notify_fulltitle);
        notify_fulljobheader = (TextView)findViewById(R.id.notify_fulljobheader);
        notify_fulldescription = (TextView)findViewById(R.id.notify_fulldescription);
        notify_fullclose = (ImageView) findViewById(R.id.notify_fullclose);
        img_notifywhatsshare =  findViewById(R.id.img_notifywhatsshare);


        motification_general_list = new ArrayList<>();


        //getintent

        if(getIntent()!=null){
            //notify_fulltitle.setText(getIntent().getStringExtra("notify_id"));
            loadnotificationmessage(getIntent().getStringExtra("notify_id"));
        }


        img_notifywhatsshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "*"+ notifytitle + "*"+ "\n\n" +" "+notifycontent+"\n\n" +
                                "மேலும் இது போன்ற வேலை வாய்ப்பு தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n\n" +
                                "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);


            }
        });



        adViewbottom = findViewById(R.id.adViewbottom);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewbottom.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdUnitId(String.valueOf(R.string.Banner_id));





        //onclick adview

        adViewbottom.setAdListener(new AdListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: " + "loaded");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "onAdLoaded: " + "onAdOpened");


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "onAdLoaded: " + "onAdClosed");

            }
        });


        notify_fullclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                onBackPressed();

               // finish();

//
//                mInterstitialAd = new InterstitialAd(NotificationViewActivity.this);
//                mInterstitialAd.setAdUnitId(String.valueOf(R.string.Interstitial_id));
//               // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//                // Load ads into Interstitial Ads
//
//
////                if (mInterstitialAd.isLoaded()) {
////                    mInterstitialAd.show();
////                } else {
////                    Log.d("TAG", "The interstitial wasn't loaded yet.");
////                }
//
//
//
//
//
//
//
//
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onAdLoaded() {
//                        // Code to be executed when an ad finishes loading.
//                        showInterstitial();
//                        Log.d(TAG, "onAdLoaded: " + "loaded");
//                    }
//
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        // Code to be executed when an ad request fails.
//                        Log.d(TAG, "onAdLoaded: " + "failedtoload");
//                        Log.d(TAG, "onAdLoaded:faled" + errorCode);
//                    }
//
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onAdOpened() {
//                        // Code to be executed when the ad is displayed.
//                        Log.d(TAG, "onAdLoaded: " + "adopened");
//
//                    }
//
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onAdLeftApplication() {
//                        // Code to be executed when the user has left the app.
//                        Log.d(TAG, "onAdLoaded: " + "leftapplication");
//
//                    }
//
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onAdClosed() {
//                        // Code to be executed when when the interstitial ad is closed.
//                        Log.d(TAG, "onAdLoaded: " + "adclosed");
//
//                    }
//                });
            }
        });


    }

    Call<BasicResponse> notificationcall;

    private void loadnotificationmessage(String notify_id) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        notificationcall = apiService.loadnotificationmessage(notify_id);

        notificationcall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {



                    motification_general_list.addAll(response.body().getMotification_general_list());


                    if(motification_general_list.size()!=0){


                        Log.v("html",response.body().getMotification_general_list().get(0)
                                .getNotify_message());

                      //  String c="<p><font color=\"#0000ff\" xss=removed><b>சென்னை SERC- யில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: ITI<br>கடைசிதேதி: 13.03.2018<br><font color=\"#397b21\"><b>Apply:</b></font> <a href=\"https://goo.gl/4r4SES\">https://goo.gl/4r4SES</a><br><br></p><p><font color=\"#0000ff\" xss=removed><b>(TNSDMA) தமிழ் நாடு பேரிடர் மீட்புக் குழுவில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> <a href=\"s://goo.gl\">https://goo.gl/BGjkP4</a></p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>அணு சக்தி மையப் பள்ளி (AECS கல்பாக்கம்) வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Diploma, Degree, B. Ed<br>கடைசிதேதி: 05.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/Ptr1Uq</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>பெங்களூர் மெட்ரோ ரயில் ஆட்சேர்ப்பு 2018<br></b></font>தகுதி: Degree, MBA<br>கடைசிதேதி: 28.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/5Zi94m</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>தமிழ்நாடு கால்நடை மற்றும் விலங்கு அறிவியல் பல்கலைக்கழகம் (TANUVAS)ஆட்சேர்ப்பு <br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/3sC5wh</p><p><br></p><p><font color=\"#0000ff\"><b>மதுரை காமராஜ் பல்கலைக்கழகத்தில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: M.Phil, PH. D<br>கடைசிதேதி: 15.03.2018<br><b><font color=\"#397b21\">Apply: </font></b>http<a href=\"s://goo.gl\">s://goo.gl</a>/xHtzMx</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>வங்கி பணியாளர் தேர்வு நிறுவனத்தில் (IBPS) வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 12.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/pWtCpW</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>இந்திய அறிவியல் நிறுவனம் (IISc) ஆட்சேர்ப்பு 2018<br></b></font>தகுதி: B.E/ B. Tech<br>கடைசிதேதி: 13.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/vPPz3T</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>IIFT- யில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/1EGn2m</p><p><br></p><p><b><font color=\"#0000ff\">10th படித்தவருக்கான வேலைவாய்ப்புகள்</font></b></p><p><b><font color=\"#397b21\">உடனே பார்க்க:</font></b> https://goo.gl/ywZwbk</p>";
                     //   String d="<p><font color=\"#0000ff\" xss=removed><b>சென்னை SERC- யில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: ITI<br>கடைசிதேதி: 13.03.2018<br><font color=\"#397b21\"><b>Apply:</b></font> <a href=\"https://goo.gl/4r4SES\">https://goo.gl/4r4SES</a><br><br></p><p><font color=\"#0000ff\" xss=removed><b>(TNSDMA) தமிழ் நாடு பேரிடர் மீட்புக் குழுவில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> <a href=\"s://goo.gl\">https://goo.gl/BGjkP4</a></p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>அணு சக்தி மையப் பள்ளி (AECS கல்பாக்கம்) வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Diploma, Degree, B. Ed<br>கடைசிதேதி: 05.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/Ptr1Uq</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>பெங்களூர் மெட்ரோ ரயில் ஆட்சேர்ப்பு 2018<br></b></font>தகுதி: Degree, MBA<br>கடைசிதேதி: 28.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/5Zi94m</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>தமிழ்நாடு கால்நடை மற்றும் விலங்கு அறிவியல் பல்கலைக்கழகம் (TANUVAS)ஆட்சேர்ப்பு <br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/3sC5wh</p><p><br></p><p><font color=\"#0000ff\"><b>மதுரை காமராஜ் பல்கலைக்கழகத்தில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: M.Phil, PH. D<br>கடைசிதேதி: 15.03.2018<br><b><font color=\"#397b21\">Apply: </font></b>http<a href=\"s://goo.gl\">s://goo.gl</a>/xHtzMx</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>வங்கி பணியாளர் தேர்வு நிறுவனத்தில் (IBPS) வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 12.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/pWtCpW</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>இந்திய அறிவியல் நிறுவனம் (IISc) ஆட்சேர்ப்பு 2018<br></b></font>தகுதி: B.E/ B. Tech<br>கடைசிதேதி: 13.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/vPPz3T</p><p><br></p><p><font color=\"#0000ff\" xss=removed><b>IIFT- யில் வேலை வாய்ப்பு 2018<br></b></font>தகுதி: Degree<br>கடைசிதேதி: 14.03.2018<br><b><font color=\"#397b21\">Apply:</font></b> http<a href=\"s://goo.gl\">s://goo.gl</a>/1EGn2m</p><p><br></p><p><b><font color=\"#0000ff\">10th படித்தவருக்கான வேலைவாய்ப்புகள்</font></b></p><p><b><font color=\"#397b21\">உடனே பார்க்க:</font></b> https://goo.gl/ywZwbk</p>";

                        if(response.body().getMotification_general_list().get(0)
                                .getNotify_title()!=null){
                            Spanned title = Html.fromHtml(response.body().getMotification_general_list().get(0)
                                    .getNotify_title().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));
// Spanned title = Html.fromHtml(c);

                            notify_fulltitle.setText(title);
                            notify_fulljobheader.setText(title);


                            notifytitle = title.toString();

                        }


                        if(response.body().getMotification_general_list().get(0)
                                .getNotify_message()!=null){
//                            Spanned message = Html.fromHtml(response.body().getMotification_general_list().get(0)
//                                    .getNotify_message());

                            Spanned message = Html.fromHtml(response.body().getMotification_general_list().get(0)
                                    .getNotify_message().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                            notify_fulldescription.setText(message);

                            notifycontent = message.toString();
                        }



                    }



                }else {
                    Toast.makeText(NotificationViewActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                // progressDialog.dismiss();
            }
        });

    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else {
            Log.d("dsddfdfd", "The interstitial wasn't loaded yet.");
            Toast.makeText(this, "not yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        try{
            if(Pref.getAdCount()==0){
                showInterstitials();
                Pref.setAdCount(Pref.getAdCount()+1);

            }else{
                Pref.setAdCount(Pref.getAdCount()+1);
                if(Pref.getAdCount()>4){
                    Pref.setAdCount(0);
                }
            }
        }catch (Exception e){

        }

        if(Pref.getNotification()){

            Pref.setNotification(false);
            Intent intent = new Intent(NotificationViewActivity.this,DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }else{
            super.onBackPressed();
        }

//
//        Intent intent = new Intent(NotificationViewActivity.this,NotificationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

              onBackPressed();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void showInterstitials() {



        //interstital ad
        // mInterstitialAd = new InterstitialAd(JobsfullviewActivity.this);
        // mInterstitialAd.setAdUnitId(getString(Integer.parseInt(String.valueOf(R.string.Interstitial_id))));
        // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd = new InterstitialAd(NotificationViewActivity.this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_id));

        AdRequest adRequest = new AdRequest.Builder()
                //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
//                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        mInterstitialAd.loadAd(adRequest);




        // Load ads into Interstitial Ads



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                Log.d(TAG, "onAdLoaded: " + "loaded");
                showInterstitial();

                //  mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdLoadedintestital: " + "failedtoload");
                Log.d(TAG, "onAdLoaded:faled" + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d(TAG, "onAdLoadedintestital: " + "adopened");

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLoadedintestital: " + "leftapplication");

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.d(TAG, "onAdLoadedintestital: " + "adclosed");
                // mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });
    }

}
