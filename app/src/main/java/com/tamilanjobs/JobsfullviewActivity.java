package com.tamilanjobs;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.tamilanjobs.Helper.AppController;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Inventory;
import com.tamilanjobs.Response.Joblistfullviewdetail;
import com.tamilanjobs.Response.Reminder;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;
import com.tamilanjobs.Util.Config;
import com.wang.avi.AVLoadingIndicatorView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tamilanjobs.TamilanjobsAdapter.toCalendar;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class JobsfullviewActivity extends AppCompatActivity {


    private static final String TAG = "JobsfullviewActivity";
    TextView txt_fullposition, txt_fullorganization, txt_fulldate, txt_fullvacancy, txt_fullstartdate, txt_fulllastdate,
            txt_fullworklocation, txt_fullvacancydetail, txt_fullagelimit, txt_fulleducationdetail, txt_fullapplicationfee,
            txt_fullsalary, txt_fullwalkindate, txt_fullapplyinfo, txt_fullapplyaddress, txt_fuldirectinterviewplace, txt_applyinfo;

    ImageView txt_fullapplicationinformation, txt_fullapplicationattach;
    View img_fullfav;

    ArrayList<Joblistfullviewdetail> job_listview;
    String applyurl, attachurl, whatsapp, tamilanofficial,howtoapply,morelink;

    LinearLayout linear_fullwhatsupshare, linear_extrainfo, linear_remindfull, linear_fullfav, linear_startdate,linear_bottomad;
   // FrameLayout linear_fullview;

    DatePickerDialog dpd;

    int dobYear;
    int dobMonth;
    int dobDay;


    int mHour;
    int mMinute;

    ImageView  expiredTag;
    View img_fulllike;

    CardView card_fulladdress, card_applyattach, card_direcwalk, card_walkindate,card_vacancydetail,card_agelimit,card_edudetail,card_applyamount
            ,card_salary,card_applymethod,card_argumentinform,card_startdate,card_enddate;

    String dialogdate, dialogtime, shortitle, organization, fulljobdate, jobvacancy, fulimage, jobid, applylink, lastdate;
    String jobcatatgorey;

    ProgressDialog progressDialog;

    AVLoadingIndicatorView avloadingIndicatorfullView;

    Calendar Mycalendertype;

    String remindermename, remindmedescription;

    View remindercolored;

    Date dateDob = null;
    Date dateDoa = null;
    String dateDoaString = null;

    TextView jobCatgorey;
    TextView txt_fullpositionsubtitle;
    CircleImageView image1;


    AdView adViewbottom,adViewbottomnew;

    private String TAG1 = JobsfullviewActivity.class.getSimpleName();
    InterstitialAd mInterstitialAd;
    LinearLayout linear_progress,linear_fullview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobsfullview);


        UIinitialization();


      //  MobileAds.initialize(this, String.valueOf(R.string.App_id));



    }

    private void showInterstitials() {



        //interstital ad
      // mInterstitialAd = new InterstitialAd(JobsfullviewActivity.this);
      // mInterstitialAd.setAdUnitId(getString(Integer.parseInt(String.valueOf(R.string.Interstitial_id))));
        // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd = new InterstitialAd(JobsfullviewActivity.this);

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

                Log.d(TAG, "onAdLoadedintestital: " + "loaded");
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


    @Override
    protected void onResume() {
       // showInterstitials();
        super.onResume();
    }

    @Override
    protected void onStart() {
        //showInterstitials();
        super.onStart();
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


        txt_fullposition = (TextView) findViewById(R.id.txt_fullposition);
        txt_fullorganization = (TextView) findViewById(R.id.txt_fullorganization);
        txt_fulldate = (TextView) findViewById(R.id.txt_fulldate);
        txt_fullvacancy = (TextView) findViewById(R.id.txt_fullvacancy);
        txt_fullstartdate = (TextView) findViewById(R.id.txt_fullstartdate);
        txt_fulllastdate = (TextView) findViewById(R.id.txt_fulllastdate);
        txt_fullworklocation = (TextView) findViewById(R.id.txt_fullworklocation);
        txt_fullvacancydetail = (TextView) findViewById(R.id.txt_fullvacancydetail);
        txt_fullagelimit = (TextView) findViewById(R.id.txt_fullagelimit);
        txt_fulleducationdetail = (TextView) findViewById(R.id.txt_fulleducationdetail);
        txt_fullapplicationfee = (TextView) findViewById(R.id.txt_fullapplicationfee);
        txt_fullsalary = (TextView) findViewById(R.id.txt_fullsalary);
        txt_fullwalkindate = (TextView) findViewById(R.id.txt_fullwalkindate);
        txt_fullapplyinfo = (TextView) findViewById(R.id.txt_fullapplyinfo);
        txt_fullapplyaddress = (TextView) findViewById(R.id.txt_fullapplyaddress);
        txt_fuldirectinterviewplace = (TextView) findViewById(R.id.txt_fuldirectinterviewplace);
        txt_applyinfo = (TextView) findViewById(R.id.txt_applyinfo);
        txt_fullapplicationinformation = (ImageView) findViewById(R.id.txt_fullapplicationinformation);
        txt_fullapplicationattach = (ImageView) findViewById(R.id.txt_fullapplicationattach);
        linear_fullwhatsupshare = (LinearLayout) findViewById(R.id.linear_fullwhatsupshare);
        linear_extrainfo = (LinearLayout) findViewById(R.id.linear_extrainfo);
        linear_remindfull = (LinearLayout) findViewById(R.id.linear_remindfull);
        card_fulladdress = (CardView) findViewById(R.id.card_fulladdress);
        card_applyattach = (CardView) findViewById(R.id.card_applyattach);
        card_direcwalk = (CardView) findViewById(R.id.card_direcwalk);
        card_walkindate = (CardView) findViewById(R.id.card_walkindate);
        remindercolored = findViewById(R.id.remindercolored);
        linear_bottomad = findViewById(R.id.linear_bottomad);
        card_applymethod = findViewById(R.id.card_applymethod);
        card_edudetail = findViewById(R.id.card_edudetail);
        card_agelimit = findViewById(R.id.card_agelimit);
        card_salary = findViewById(R.id.card_salary);
        card_applyamount = findViewById(R.id.card_applyamount);
        card_startdate = findViewById(R.id.card_startdate);
        card_enddate = findViewById(R.id.card_enddate);
       // image1 = findViewById(R.id.image1);
       // jobCatgorey = findViewById(R.id.jobCatgorey);
       // txt_fullpositionsubtitle = findViewById(R.id.txt_fullpositionsubtitle);

        img_fullfav = findViewById(R.id.img_fullfav);
        img_fulllike = findViewById(R.id.img_fulllike);
        avloadingIndicatorfullView = findViewById(R.id.avloadingIndicatorfullView);
        linear_fullview = findViewById(R.id.linear_fullview);
        linear_progress = findViewById(R.id.linear_progress);
        linear_startdate = findViewById(R.id.linear_startdate);
        expiredTag = findViewById(R.id.expiredTag);


        Mycalendertype = Calendar.getInstance();

        //linear_fullview.setVisibility(View.GONE);
        //progressDialog = new ProgressDialog(JobsfullviewActivity.this);
        //progressDialog.setMessage("Loading");

        jobid = getIntent().getStringExtra("jobid");

        if (getIntent().getStringExtra("reminder_id") != null) {

            if (getIntent().getStringExtra("reminder_id").equals("1")) {
                remindercolored.setVisibility(View.VISIBLE);
                linear_remindfull.setVisibility(View.GONE);

            } else {
                remindercolored.setVisibility(View.GONE);
                linear_remindfull.setVisibility(View.VISIBLE);
            }
        }


        if (getIntent().getStringExtra("favid").equals("1")) {
            img_fulllike.setVisibility(View.VISIBLE);
            img_fullfav.setVisibility(View.GONE);

        } else {
            img_fulllike.setVisibility(View.GONE);
            img_fullfav.setVisibility(View.VISIBLE);
        }



//
//        card_applymethod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(JobsfullviewActivity.this, "appply methid", Toast.LENGTH_SHORT).show();
//            }
//        });






    //adinitialize

        adViewbottom = findViewById(R.id.adViewbottom);
        adViewbottomnew = findViewById(R.id.adViewbottomnew);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewbottom.loadAd(adRequest);
        adViewbottomnew.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdUnitId(getString(R.string.Banner_id));


        adViewbottom.setVisibility(View.GONE);
        adViewbottomnew.setVisibility(View.GONE);

        //onclick adview

        adViewbottom.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: " + "loaded");
                adViewbottom.setVisibility(View.VISIBLE);
              //  Toast.makeText(JobsfullviewActivity.this, "loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");
               // Toast.makeText(JobsfullviewActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "onAdLoaded: " + "onAdOpened");
              //  Toast.makeText(JobsfullviewActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");
               // Toast.makeText(JobsfullviewActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "onAdLoaded: " + "onAdClosed");
               // Toast.makeText(JobsfullviewActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();

            }
        });

        adViewbottomnew.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: " + "loaded");
                adViewbottomnew.setVisibility(View.VISIBLE);
                linear_bottomad.setVisibility(View.VISIBLE);

               // Toast.makeText(JobsfullviewActivity.this, "loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");
               // Toast.makeText(JobsfullviewActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "onAdLoaded: " + "onAdOpened");
               // Toast.makeText(JobsfullviewActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");
               // Toast.makeText(JobsfullviewActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "onAdLoaded: " + "onAdClosed");
               // Toast.makeText(JobsfullviewActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();

            }
        });








        txt_fullapplicationinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (applyurl != null && !applyurl.isEmpty() && !applyurl.equals("null")) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(applyurl));
                    startActivity(intent);
                } else {
                    Toast.makeText(JobsfullviewActivity.this, "No link found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txt_fullapplicationattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (attachurl != null && !attachurl.isEmpty() && !attachurl.equals("null")) {
                    try {
                        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(attachurl));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(JobsfullviewActivity.this, "No link found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(JobsfullviewActivity.this, "No link found", Toast.LENGTH_SHORT).show();
                }

            }
        });


        linear_fullwhatsupshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "*"+ shortitle + "*"+ "\n\n"
                                +"*பணி இடங்கள்:*"+" "+jobvacancy+"\n\n" +
                                "*கடைசி தேதி:*" + " "+ fulljobdate + "\n\n" +
                                "*மேலும் அறிய:*" + " " + tamilanofficial + "\n\n" +
                                "மேலும் இது போன்ற வேலை வாய்ப்பு தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n\n" +
                                "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);



            }
        });


        linear_extrainfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tamilanofficial != null && !tamilanofficial.isEmpty() && !tamilanofficial.equals("null")) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(tamilanofficial));
                    startActivity(intent);
                } else {
                    Toast.makeText(JobsfullviewActivity.this, "No link found", Toast.LENGTH_SHORT).show();
                }

            }
        });


        linear_remindfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dateDob.after(dateDoa)) {
                    Toast.makeText(JobsfullviewActivity.this, "Job expired!, You could not set reminder", Toast.LENGTH_SHORT).show();
                } else {

                    final Dialog dialog = new Dialog(JobsfullviewActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.reminderme_dialog);
                    dialog.show();


                    View img_closedialog = dialog.findViewById(R.id.img_closedialog);
                    LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);
                    final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                    final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                    final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                    final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                    final View linear_dialogreminddate =  dialog.findViewById(R.id.linear_dialogreminddate);


                    edt_remindermename.setText(shortitle);
                    edt_remindmedescription.setText(organization);


                    List<Reminder> reminders = new Select()
                            .from(Reminder.class)
                            .where("reminderpostid = ?", jobid)
                            .execute();

                    Log.d("dsddsssds", "onClick: " + jobid);

                    if (reminders.size() != 0) {

                        edt_remindermename.setText(reminders.get(0).getRemindertitle());
                        edt_remindmedescription.setText(reminders.get(0).getReminderdesc());
                        edt_remindmedate.setText(reminders.get(0).getReminderdate());
                        edt_remindmetime.setText(reminders.get(0).getRemindertime());
                    }


                    img_closedialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    linear_dialogreminddate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            setAppointment(edt_remindmedate, edt_remindmetime);
                        }
                    });


                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            remindermename = edt_remindermename.getText().toString().trim();
                            remindmedescription = edt_remindmedescription.getText().toString().trim();
                            String remindmedate = edt_remindmedate.getText().toString().trim();
                            String remindmetime = edt_remindmetime.getText().toString().trim();


                            if (!remindermename.isEmpty()) {
                                if (!remindmedescription.isEmpty()) {
                                    if (edt_remindmedate.getText().toString().trim() != null &&
                                            !edt_remindmetime.getText().toString().trim().isEmpty()) {

                                        savereminder(jobid, jobvacancy, fulljobdate, remindermename
                                                , remindmedescription, remindmedate, remindmetime, dialog);


                                    } else {
                                        Toast.makeText(JobsfullviewActivity.this, "Please select the date and time", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(JobsfullviewActivity.this, "Please select reminder description", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(JobsfullviewActivity.this, "Please select reminder name", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });


                }
            }
        });

        remindercolored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dateDob.after(dateDoa)) {
                    Toast.makeText(JobsfullviewActivity.this, "Job expired!, You could not set reminder", Toast.LENGTH_SHORT).show();
                } else {

                    final Dialog dialog = new Dialog(JobsfullviewActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.reminderme_dialog);
                    dialog.show();


                    View img_closedialog = dialog.findViewById(R.id.img_closedialog);
                    LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);
                    final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                    final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                    final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                    final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                    final View linear_dialogreminddate = dialog.findViewById(R.id.linear_dialogreminddate);


                    edt_remindermename.setText(shortitle);
                    edt_remindmedescription.setText(organization);


                    List<Reminder> reminders = new Select()
                            .from(Reminder.class)
                            .where("reminderpostid = ?", jobid)
                            .execute();

                    Log.d("dsddsssds", "onClick: " + jobid);

                    if (reminders.size() != 0) {

                        edt_remindermename.setText(reminders.get(0).getRemindertitle());
                        edt_remindmedescription.setText(reminders.get(0).getReminderdesc());
                        edt_remindmedate.setText(reminders.get(0).getReminderdate());
                        edt_remindmetime.setText(reminders.get(0).getRemindertime());
                    }


                    img_closedialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    linear_dialogreminddate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            setAppointment(edt_remindmedate, edt_remindmetime);
                        }
                    });


                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            remindermename = edt_remindermename.getText().toString().trim();
                            remindmedescription = edt_remindmedescription.getText().toString().trim();
                            String remindmedate = edt_remindmedate.getText().toString().trim();
                            String remindmetime = edt_remindmetime.getText().toString().trim();

                            if (!remindermename.isEmpty()) {
                                if (!remindmedescription.isEmpty()) {
                                    if (edt_remindmedate.getText().toString().trim() != null &&
                                            !edt_remindmetime.getText().toString().trim().isEmpty()) {

                                        savereminder(jobid, jobvacancy, fulljobdate,
                                                remindermename, remindmedescription,
                                                remindmedate, remindmetime,dialog);



                                    } else {
                                        Toast.makeText(JobsfullviewActivity.this, "Please select the date and time", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(JobsfullviewActivity.this, "Please select reminder description", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(JobsfullviewActivity.this, "Please select reminder name", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });


                }


            }
        });


        img_fullfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveInventory(jobid, shortitle, jobvacancy, organization, fulljobdate, "image");

                if(DashboardActivity.tamilanjobsAdapter!=null){
                    DashboardActivity.adapterNotify(jobid);


                }else {

                    Log.d(TAG, "onClick: " + "failure");

                }


                img_fulllike.setVisibility(View.VISIBLE);
                img_fullfav.setVisibility(View.GONE);


            }
        });


        img_fulllike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Delete().from(Inventory.class).where("postid = ?", jobid).execute();

                if(DashboardActivity.tamilanjobsAdapter!=null) {

                    DashboardActivity.adapterNotify(jobid);
                }else {

                    Log.d(TAG, "onClick:unfav " + "failure");

                }


                img_fulllike.setVisibility(View.GONE);
                img_fullfav.setVisibility(View.VISIBLE);
                Toast.makeText(JobsfullviewActivity.this, "விருப்பமானவையிலிருந்து நீக்கப்பட்டது", Toast.LENGTH_SHORT).show();


            }
        });




        if(getIntent().getStringExtra("jobid")!=null){
            getjobfullviewlist();
        }else {
            Toast.makeText(this, "job id missing", Toast.LENGTH_SHORT).show();
        }



    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        showInterstitial();
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        showInterstitial();
//    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else {
            Log.d("dsddfdfd", "The interstitial wasn't loaded yet.");
        }
    }

    public Call<BasicResponse> jobfullviewcall;

    public void getjobfullviewlist() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);



        jobfullviewcall = apiService.getjobfullviewlist(Integer.parseInt(getIntent().getStringExtra("jobid")));


        linear_progress.setVisibility(View.VISIBLE);
        linear_fullview.setVisibility(View.GONE);
        jobfullviewcall.enqueue(new Callback<BasicResponse>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    try {
                        job_listview = new ArrayList<>();

                        job_listview.addAll(response.body().getJob_listview());

//                        avloadingIndicatorfullView.setVisibility(View.GONE);
//                        linear_fullview.setVisibility(View.VISIBLE);

                        linear_progress.setVisibility(View.GONE);
                        linear_fullview.setVisibility(View.VISIBLE);


                        //typeid
                        if (response.body().getJob_listview().get(0)
                                .getJpost_type() != null) {
                            if (response.body().getJob_listview().get(0)
                                    .getJpost_type().equals("1")) {
                                card_fulladdress.setVisibility(View.GONE);
                                card_applyattach.setVisibility(View.VISIBLE);
                                card_direcwalk.setVisibility(View.GONE);
                                card_walkindate.setVisibility(View.GONE);
                            } else if (response.body().getJob_listview().get(0)
                                    .getJpost_type().equals("2")) {
                                card_fulladdress.setVisibility(View.VISIBLE);
                                card_applyattach.setVisibility(View.GONE);
                                card_direcwalk.setVisibility(View.GONE);
                                card_walkindate.setVisibility(View.GONE);
                            } else {
                                card_fulladdress.setVisibility(View.GONE);
                                card_applyattach.setVisibility(View.GONE);
                                card_direcwalk.setVisibility(View.VISIBLE);
                                card_walkindate.setVisibility(View.VISIBLE);
                                linear_startdate.setVisibility(View.GONE);
                            }
                        }


//                        card_fulladdress, card_applyattach, card_direcwalk, card_walkindate,card_vacancydetail,
//                          card_agelimit,card_edudetail,card_applyamount
//                                ,card_salary,card_applymethod,card_argumentinform


                        if (response.body().getJob_listview().get(0).getJpost_title() != null &&response.body().getJob_listview().get(0).getJpost_title().length()!=0) {


                            Spanned shorttitle = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_title().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullposition.setText(shorttitle.toString().trim());

                            whatsapp = txt_fullposition.toString();
                        }


//                        if (response.body().getJob_listview().get(0).getJpost_title() != null) {
//
//
//                            Spanned title = Html.fromHtml(response.body().getJob_listview().get(0)
//                                    .getJpost_title().replace("&lt;", "<").
//                                            replace("&gt;", ">").replace("&nbsp;", " "));
//
//
//                            txt_fullpositionsubtitle.setText(title);
//
//                        }



                        if (response.body().getJob_listview().get(0).getJpost_vacancy_details() != null &&response.body().getJob_listview().get(0).getJpost_vacancy_details().length()!=0) {

                            String html = response.body().getJob_listview().get(0).getJpost_vacancy_details();

                            html = html.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
                            html = html.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
                            html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                            html = html.replaceAll("&nbsp;", " ");
                            html = html.replaceAll("&amp;", " ");

                            Spanned sp = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_vacancy_details().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullvacancydetail.setText(sp.toString().trim());

                        }else {
                            card_vacancydetail.setVisibility(View.GONE);
                        }


                        if (response.body().getJob_listview().get(0).getJpost_short_title() != null) {

                            Spanned shorttitle = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_short_title().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));



                            shortitle = shorttitle.toString();

                        }

//                        if (response.body().getJob_listview().get(0).getJobcategory() != null) {
//
//                            Spanned jobcategory = Html.fromHtml(response.body().getJob_listview().get(0)
//                                    .getJobcategory().replace("&lt;", "<").
//                                            replace("&gt;", ">").replace("&nbsp;", " "));
//
//
//
//                            jobcatatgorey = jobcategory.toString().trim();
//                            jobCatgorey.setText(jobcatatgorey);
//
//                        }

                        if (response.body().getJob_listview().get(0).getJpost_organization() != null&&response.body().getJob_listview().get(0).getJpost_organization().length()!=0) {

                            Spanned organizationtext = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_organization().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullorganization.setText(organizationtext.toString().trim());

                            organization = organizationtext.toString();


                        }

                              if (response.body().getJob_listview().get(0).getJpost_application_send_address() != null&&response.body().getJob_listview().get(0).getJpost_application_send_address().length()!=0) {

                            Spanned sendaddress = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_application_send_address().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                                  txt_fullapplyaddress.setText(sendaddress.toString().trim());

                        }else {
                                  card_fulladdress.setVisibility(View.GONE);
                              }


                        if (response.body().getJob_listview().get(0).getJpost_type().equals("1")) {
                            if (response.body().getJob_listview().get(0).getJpost_end_date() != null) {
                                txt_fulldate.setText(response.body().getJob_listview().get(0).getJpost_end_date().trim());

                                fulljobdate = response.body().getJob_listview().get(0).getJpost_end_date();

                                Log.d("sadasdsa", "onResponse: " + response.body().getJob_listview().get(0).getJpost_created_on());
                            }
                        } else if (response.body().getJob_listview().get(0).getJpost_type().equals("2")) {

                            txt_fulldate.setText(response.body().getJob_listview().get(0).getJpost_end_date());

                            fulljobdate = response.body().getJob_listview().get(0).getJpost_end_date();

                            Log.d("sadasdsa", "onResponse: " + response.body().getJob_listview().get(0).getJpost_created_on());
                        } else {

                            txt_fulldate.setText(response.body().getJob_listview().get(0).getJpost_walkin_date());

                            fulljobdate = response.body().getJob_listview().get(0).getJpost_walkin_date();

                            Log.d("sadasdsa", "onResponse: " + response.body().getJob_listview().get(0).getJpost_created_on());
                        }


                        if (response.body().getJob_listview().get(0).getJpost_total_vacancy() != null&&response.body().getJob_listview().get(0).getJpost_total_vacancy().length()!=0) {

                            Spanned jobspanvacancy = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_total_vacancy().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullvacancy.setText(jobspanvacancy.toString().trim());

                            jobvacancy = jobspanvacancy.toString();
                        }else {
                            card_vacancydetail.setVisibility(View.GONE);
                        }


                        if (response.body().getJob_listview().get(0).getJpost_start_date() != null&&response.body().getJob_listview().get(0).getJpost_start_date().length()!=0) {
                            txt_fullstartdate.setText(response.body().getJob_listview().get(0).getJpost_start_date().trim());
                        }else {
                            card_startdate.setVisibility(View.GONE);
                        }


                        if (response.body().getJob_listview().get(0).getJpost_end_date() != null&&response.body().getJob_listview().get(0).getJpost_end_date().length()!=0) {
                            txt_fulllastdate.setText(response.body().getJob_listview().get(0).getJpost_end_date().trim());
                        }else {
                            card_enddate.setVisibility(View.GONE);
                        }

                        if (response.body().getJob_listview().get(0).getJpost_place() != null&&response.body().getJob_listview().get(0).getJpost_place().length()!=0) {

                            Spanned place = Html.fromHtml(response.body().getJob_listview().get(0)
                                    .getJpost_place().replace("&lt;", "<").
                                            replace("&gt;", ">").replace("&nbsp;", " "));



                            txt_fullworklocation.setText(place.toString().trim());
                        }
                        if (response.body().getJob_listview().get(0).getJpost_interview_location() != null&&response.body().getJob_listview().get(0).getJpost_interview_location().length()!=0) {

                            Spanned directwalk = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_interview_location().
                                    replace("&lt;", "<").replace("&gt;", ">")
                                    .replace("&npsp;", " "));


                            txt_fuldirectinterviewplace.setText(directwalk.toString().trim());
                        }else {
                            card_direcwalk.setVisibility(View.GONE);
                        }

                        if (response.body().getJob_listview().get(0).getJpost_walkin_date() != null) {
                            txt_fullwalkindate.setText(response.body().getJob_listview().get(0).getJpost_walkin_date().trim());
                        }

                        if (response.body().getJob_listview().get(0).getJpost_age_limit() != null&&response.body().getJob_listview().get(0).getJpost_age_limit().length()!=0) {



                            Spanned sp = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_age_limit().
                                    replace("&lt;", "<").replace("&gt;", ">")
                                    .replace("\u00a0", " "));

                            txt_fullagelimit.setText(sp.toString().trim());

                            Log.d("asdasdsa", "onResponse: " + sp.toString().trim());
                        }else {
                            card_agelimit.setVisibility(View.GONE);
                        }

                        if (response.body().getJob_listview().get(0).getJpost_educational_qualification() != null&&response.body().getJob_listview().get(0).getJpost_educational_qualification().length()!=0) {


                            Spanned sp = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_educational_qualification().
                                    replace("&lt;", "<").replace("&gt;", ">")
                                    .replace("&npsp;", " "));

                            txt_fulleducationdetail.setText(sp.toString().replace("&npsp;","").trim());
                        }else {
                            card_edudetail.setVisibility(View.GONE);
                        }

                        if (response.body().getJob_listview().get(0).getJpost_application_fees() != null&&response.body().getJob_listview().get(0).getJpost_application_fees().length()!=0&&!response.body().getJob_listview().get(0).getJpost_application_fees().equals("")) {

                            Spanned appfees = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_application_fees().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullapplicationfee.setText(appfees.toString().trim());

                            Log.d("applicationfee", "onResponse: " + appfees.toString().trim());
                            Log.d("applicationfee", "onResponse: " + response.body().getJob_listview().get(0).getJpost_application_fees());

                        }else {
                            card_applyamount.setVisibility(View.GONE);
                        }

                        if (response.body().getJob_listview().get(0).getJpost_salary() != null&&response.body().getJob_listview().get(0).getJpost_salary().length()!=0) {

                            Spanned appsalary = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_salary().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));


                            txt_fullsalary.setText(appsalary.toString().trim());
                        }else {
                            card_salary.setVisibility(View.GONE);
                        }


                        if (response.body().getJob_listview().get(0).getJpost_official_notification_link() != null) {

                            applyurl = response.body().getJob_listview().get(0).getJpost_official_notification_link();

                        }

                        if (response.body().getJob_listview().get(0).getJpost_apply_link() != null) {

                            attachurl = response.body().getJob_listview().get(0).getJpost_apply_link();


                        }

                        if (response.body().getJob_listview().get(0).getJpost_how_to_apply() != null&&response.body().getJob_listview().get(0).getJpost_how_to_apply().length()!=0) {


                            String html = response.body().getJob_listview().get(0).getJpost_how_to_apply();

                            html = html.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
                            html = html.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
                            html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                            html = html.replaceAll("\u00a0", " ");
                            html = html.replaceAll("&amp;", " ");

                            Spanned sp = Html.fromHtml(response.body().getJob_listview().get(0).getJpost_how_to_apply()
                                    .replace("&lt;", "<").replace("&gt;", ">")
                                    .replace("&nbsp;", " "));

                            txt_fullapplyinfo.setText(sp.toString().trim());

                            howtoapply = sp.toString();
                            Log.d(TAG, "onResponse:if " + response.body().getJob_listview().get(0).getJpost_how_to_apply().length());

                        }else {

                            Log.d(TAG, "onResponse:else " + response.body().getJob_listview().get(0).getJpost_how_to_apply().length());
                            card_applymethod.setVisibility(View.GONE);
                        }


                        if (response.body().getJob_listview().get(0).getJpost_more_detail_link() != null&&response.body().getJob_listview().get(0).getJpost_more_detail_link().length()!=0) {

                            tamilanofficial = response.body().getJob_listview().get(0).getJpost_more_detail_link();

                        }

//                        if (response.body().getJob_listview().get(0).getJpost_image() != null) {
//
//
//                            Picasso.with(JobsfullviewActivity.this).load(ApiClient.URL_ACCOUNT_PHOTO + response.body().getJob_listview().get(0).getJpost_image()).
//                                    placeholder(R.drawable.sample_image)// Place holder image from drawable folder
//                                    .error(R.drawable.sample_image)
//                                    .into(image1);
//
//
//                        } else {
//                            image1.setImageResource(R.drawable.jobsicon);
//                        }


                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String getCurrentDateTime = sdf.format(c.getTime());


                        try {

                            if (response.body().getJob_listview().get(0).getJpost_type().equals("1")) {
                                dateDob = sdf.parse(getCurrentDateTime);

                                SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = null;
                                date = form.parse(response.body().getJob_listview().get(0).getJpost_end_date());
                                dateDoa = date;
                                dateDoaString = response.body().getJob_listview().get(0).getJpost_end_date();

                            } else if (response.body().getJob_listview().get(0).getJpost_type().equals("2")) {
                                dateDob = sdf.parse(getCurrentDateTime);

                                SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = null;
                                date = form.parse(response.body().getJob_listview().get(0).getJpost_end_date());
                                dateDoa = date;
                                dateDoaString = response.body().getJob_listview().get(0).getJpost_end_date();

                            } else {
                                dateDob = sdf.parse(getCurrentDateTime);

                                dateDob = sdf.parse(getCurrentDateTime);

                                SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = null;
                                date = form.parse(response.body().getJob_listview().get(0).getJpost_walkin_date());
                                dateDoa = date;
                                dateDoaString = response.body().getJob_listview().get(0).getJpost_walkin_date();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (dateDob.after(dateDoa)) {
                            expiredTag.setVisibility(View.VISIBLE);
                        } else {
                            expiredTag.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
//                avloadingIndicatorfullView.setVisibility(View.GONE);
//                linear_fullview.setVisibility(View.GONE);

                linear_progress.setVisibility(View.GONE);
                linear_fullview.setVisibility(View.GONE);
            }
        });


    }


    //calenderinti

    private void calendarinitialization(final EditText date1) {

        final Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                        String month = months[monthOfYear];

                        Toast.makeText(JobsfullviewActivity.this, "" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        if (date1 != null) {

                            date1.setText(year + "-" + month + "-" + dayOfMonth);

                        }

                        dobYear = year;
                        dobMonth = monthOfYear;
                        dobDay = dayOfMonth;
                    }
                },
                now.get(YEAR),
                now.get(MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    //cae

    public void setAppointment(final EditText edt_remindmedate, final EditText edt_remindmetime) {


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                                                    String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                                    String month = months[monthOfYear];

                                                                    //  Toast.makeText(JobsfullviewActivity.this, "" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                                                                    Mycalendertype.set(Calendar.YEAR, year);
                                                                    Mycalendertype.set(Calendar.MONTH, monthOfYear);
                                                                    Mycalendertype.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                                                                    edt_remindmedate.setText(dayOfMonth + "-" + month + "-" + year);


                                                                    tiemPicker(edt_remindmetime);

                                                                    dobYear = year;
                                                                    dobMonth = monthOfYear;
                                                                    dobDay = dayOfMonth;
                                                                }
                                                            },
                now.get(YEAR),
                now.get(MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        dpd.setMinDate(now);

        Date maximumdate = null;

        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = form.parse(dateDoaString);
            maximumdate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dpd.setMaxDate(toCalendar(maximumdate));


        dpd.show(getFragmentManager(), "Datepickerdialog");


    }

    private void tiemPicker(final EditText edt_remindmetime) {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;

                        Mycalendertype.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        Mycalendertype.set(Calendar.MINUTE, minute);

                        boolean isPM = (hourOfDay >= 12);
                        edt_remindmetime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));


                        // edt_remindmetime.setText(hourOfDay + ":" + minute);
                    }

                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void saveInventory(String jpost_id, String short_title, String jpost_total_vacancy, String jpost_organization, String jpost_createddate, String jpost_image) {
        //Getting name from editText
        //name = editTextInventoryName.getText().toString().trim();

        //Checking if name is blank
//        if (name.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }

        //If name is not blank creating a new Inventory object

        new Delete().from(Inventory.class).where("postid = ?", jpost_id).execute();

        Inventory inventory = new Inventory();
        //Adding the given name to inventory name
        inventory.postid = jpost_id;
        inventory.name = short_title;
        inventory.vacancy = jpost_total_vacancy;
        inventory.organization = jpost_organization;
        inventory.favdate = jpost_createddate;
        inventory.favimage = jpost_image;
        //Saving name to sqlite database
        inventory.save();
        //  inventoryItems.add(jpost_id);


        inventory.postid = String.valueOf(new Select()
                .from(Inventory.class)
                .where("postid = ?", inventory.getPostid())
                .execute());

        Toast.makeText(JobsfullviewActivity.this, "விருப்பமானவையில் சேர்க்கப்பட்டது", Toast.LENGTH_LONG).show();
    }


    //remindersave

    private void savereminder(String jpostId,
                              String vacancy,
                              String datetime,
                              String remindermename,
                              String remindmedescription,
                              String remindmedate,
                              String remindmetime, Dialog dialog) {
        //Getting name from editText
        //name = editTextInventoryName.getText().toString().trim();

        //Checking if name is blank
//        if (name.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }

        //If name is not blank creating a new Inventory object

        new Delete().from(Reminder.class).where("reminderpostid = ?", jpostId).execute();

        Reminder reminder = new Reminder();
        //Adding the given name to inventory name
        reminder.reminderpostid = jpostId;
        reminder.remindertitle = remindermename;
        reminder.vacancy = vacancy;
        reminder.reminderdesc = remindmedescription;
        reminder.reminderdatetime = datetime;
        reminder.reminderdate = remindmedate;
        reminder.remindertime = remindmetime;
        //Saving name to sqlite database
        reminder.save();
        //  inventoryItems.add(jpost_id);

        setAlarm(jpostId);

        dialog.cancel();
        // getAll();

        Toast.makeText(JobsfullviewActivity.this, "Reminder job successfully", Toast.LENGTH_LONG).show();
    }


    private void setAlarm(String jpostId) {


        if (Mycalendertype != null) {
            Intent intent = new Intent(JobsfullviewActivity.this, AlarmManagerBroadcastReceiver.class);
            intent.putExtra("jobid",jpostId);
            intent.putExtra("jobtitle", remindermename);
            intent.putExtra("joborganization", remindmedescription);
            // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_1, intent, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(JobsfullviewActivity.this, Integer.parseInt(jpostId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) JobsfullviewActivity.this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, Mycalendertype.getTimeInMillis(), pendingIntent);
        } else {
            Toast.makeText(JobsfullviewActivity.this, "No alarm available", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(JobsfullviewActivity.this,DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }else{
            super.onBackPressed();
        }
    }



    //onbackpress

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


}
