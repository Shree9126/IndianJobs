package com.tamilanjobs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.tamilanjobs.Helper.AppController;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Joblistdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;
import com.tamilanjobs.Service.ConnectivityReceiver;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = "DashboardActivity";
    DrawerLayout drawer;

    LinearLayout linear_arivipugal, linear_virupanamanavaigal, linear_karthukal, linear_engalaipatri, linear_vithimuraigal,
            linear_thaniurimaikolgai, linear_veliyeru, linear_ninaivutal, linear_movetofilter, linear_mugapu;

    RecyclerView recycle_tamilbjoblist;
    static TamilanjobsAdapter tamilanjobsAdapter;

    ArrayList<Joblistdetail> job_list;

    ProgressDialog progressDialog;

    AVLoadingIndicatorView avloadingIndicatorView;

    ImageView notifydashicon;

    SwipeRefreshLayout swipeRefreshLayout;

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    private TelephonyManager mTelephonyManager;
    String deviceid;

    private AdView adViewbottom;

    LinearLayout lay_nodata_found;

    TextView txt_notification_count, txt_notificationtop_count;

    //sqlite

    //loadmore

    int offset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);


        UIinitialiation();





        checkConnection();
    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {

            linear_movetofilter.setVisibility(View.VISIBLE);
            // Toast.makeText(this, "Good! Connected to Internet", Toast.LENGTH_SHORT).show();

        } else {

            linear_movetofilter.setVisibility(View.GONE);


            Toast.makeText(this, "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("வெளியேறு");

            // Setting Dialog Message
            alertDialog.setMessage("இணைய இணைப்பு இல்லை?");
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog

            // Setting Positive "Yes" Button

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    // Toast.makeText(DashboardActivity.this, "No notification deleted", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    finishAffinity();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
    }


    private void UIinitialiation() {

        //toolbar init

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarimage);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        recycle_tamilbjoblist = (RecyclerView) findViewById(R.id.recycle_tamilbjoblist);
        linear_arivipugal = (LinearLayout) findViewById(R.id.linear_arivipugal);
        linear_virupanamanavaigal = (LinearLayout) findViewById(R.id.linear_virupanamanavaigal);
        linear_ninaivutal = (LinearLayout) findViewById(R.id.linear_ninaivutal);
        linear_karthukal = (LinearLayout) findViewById(R.id.linear_karthukal);
        linear_engalaipatri = (LinearLayout) findViewById(R.id.linear_engalaipatri);
        linear_vithimuraigal = (LinearLayout) findViewById(R.id.linear_vithimuraigal);
        linear_thaniurimaikolgai = (LinearLayout) findViewById(R.id.linear_thaniurimaikolgai);
        linear_veliyeru = (LinearLayout) findViewById(R.id.linear_veliyeru);
        linear_movetofilter = (LinearLayout) findViewById(R.id.linear_movetofilter);
        avloadingIndicatorView = findViewById(R.id.avloadingIndicatorView);
        notifydashicon = findViewById(R.id.notifydashicon);
        linear_mugapu = findViewById(R.id.linear_mugapu);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lay_nodata_found = findViewById(R.id.lay_nodata_found);
        txt_notification_count = findViewById(R.id.txt_notification_count);
        txt_notificationtop_count = findViewById(R.id.txt_notificationtop_count);



        //adinitialize

//        adViewbottom = findViewById(R.id.adViewbottom);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adViewbottom.loadAd(adRequest);
//
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(String.valueOf(R.string.Banner_id));
//
//        adViewbottom.setVisibility(View.GONE);
//
//        //onclick adview
//
//        adViewbottom.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                Log.d(TAG, "onAdLoaded: " + "loaded");
//                adViewbottom.setVisibility(View.VISIBLE);
//               // Toast.makeText(DashboardActivity.this, "loaded", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");
//              //  Toast.makeText(DashboardActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//                Log.d(TAG, "onAdLoaded: " + "onAdOpened");
//               // Toast.makeText(DashboardActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");
//              //  Toast.makeText(DashboardActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the user is about to return
//                // to the app after tapping on an ad.
//                Log.d(TAG, "onAdLoaded: " + "onAdClosed");
//               // Toast.makeText(DashboardActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        recycle_tamilbjoblist.setLayoutManager(new LinearLayoutManager(this));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.post(new Runnable() {

                    @Override
                    public void run() {
                        // job_list.clear();

                        swipeRefreshLayout.setRefreshing(true);

                        joblist_detail(deviceid);


                    }
                });


            }
        });


        //text


        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //onlcik
        linear_mugapu.setOnClickListener(this);
        linear_arivipugal.setOnClickListener(this);
        linear_virupanamanavaigal.setOnClickListener(this);
        linear_karthukal.setOnClickListener(this);
        linear_engalaipatri.setOnClickListener(this);
        linear_vithimuraigal.setOnClickListener(this);
        linear_thaniurimaikolgai.setOnClickListener(this);
        linear_veliyeru.setOnClickListener(this);
        linear_ninaivutal.setOnClickListener(this);
        linear_movetofilter.setOnClickListener(this);

        progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setMessage("Loading");

        job_list = new ArrayList<>();


        Log.d("sdsdsds", "UIinitialiation: " + deviceid);


        //listload


        notifydashicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent notifydashicon = new Intent(DashboardActivity.this, NotificationActivity.class);
                notifydashicon.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(notifydashicon);


                // loginupdate();
            }
        });


        //device id

        checkper_function();

    }


    private void checkper_function() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }

        } else {

            getLowerVersionId();

        }
    }

    @SuppressLint("HardwareIds")
    private void getLowerVersionId() {

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
/*
* getDeviceId() returns the unique device ID.
* For example,the IMEI for GSM and the MEID or ESN for CDMA phones.
*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        assert telephonyManager != null;
        deviceid = telephonyManager.getDeviceId();
        Pref.setPreAccountDeviceId(deviceid);

        loginupdate();
        joblist_detail(deviceid);

        Log.d(TAG, "getLowerVersionId: " + deviceid);



/*
* getSubscriberId() returns the unique subscriber ID,
* For example, the IMSI for a GSM phone.
*/
    }


    public void showHideProgressDialog(boolean status) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(DashboardActivity.this);
            progressDialog.setTitle("Title");
            progressDialog.setMessage("Your Message");
        }

        if (status) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }else {
            finish();
        }
    }

    @SuppressLint("HardwareIds")
    private void getDeviceImei() {

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        deviceid = mTelephonyManager.getDeviceId();
//        Log.d("msg", "DeviceImei " + deviceid);
//        Pref.setPreAccountDeviceId(deviceid);
//        joblist_detail(deviceid);

        assert telephonyManager != null;
        deviceid = telephonyManager.getDeviceId();

        Log.d(TAG, "getDeviceImei: " + deviceid);

        Pref.setPreAccountDeviceId(deviceid);
        loginupdate();
        joblist_detail(deviceid);


    }


    private void settext() {

        txt_notification_count.setText("0");
    }


    //logn update


    Call<BasicResponse> logincall;

    private void loginupdate() {


        Log.d(TAG, "loginupdate: " + deviceid);
        Log.d(TAG, "loginupdate: " + Pref.getAuthtoken());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        logincall = apiService.login(deviceid, deviceid, Pref.getAuthtoken());
        logincall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().getSuccess() == 1) {


                    //  Toast.makeText(DashboardActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    //   Toast.makeText(DashboardActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                //  showHideProgressDialog(true);
                //  progressDialog.dismiss();

                Toast.makeText(DashboardActivity.this, "failure" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppController.getInstance().setConnectivityListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {


            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    //onclick on navigation drawer fields

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.linear_arivipugal) {
            settext();
            Intent linear_arivipugal = new Intent(DashboardActivity.this, NotificationActivity.class);
            linear_arivipugal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(linear_arivipugal);
        } else if (id == R.id.linear_virupanamanavaigal) {
            Intent linear_virupanamanavaigal = new Intent(DashboardActivity.this, FavoritesActivity.class);
            startActivity(linear_virupanamanavaigal);
        } else if (id == R.id.linear_karthukal) {
            Intent linear_karthukal = new Intent(DashboardActivity.this, FeedbackActivity.class);
            linear_karthukal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(linear_karthukal);
        } else if (id == R.id.linear_engalaipatri) {
            Intent linear_engalaipatri = new Intent(DashboardActivity.this, AboutActivity.class);
            linear_engalaipatri.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(linear_engalaipatri);
        } else if (id == R.id.linear_vithimuraigal) {
            Intent linear_vithimuraigal = new Intent(DashboardActivity.this, TermsandConditionActivity.class);
            linear_vithimuraigal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(linear_vithimuraigal);
        } else if (id == R.id.linear_thaniurimaikolgai) {
            Intent linear_thaniurimaikolgai = new Intent(DashboardActivity.this, PrivacyPolicyActivity.class);
            linear_thaniurimaikolgai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(linear_thaniurimaikolgai);
        } else if (id == R.id.linear_ninaivutal) {
            Intent intent = new Intent(this, ReminderActivity.class);
            startActivityForResult(intent, 1);

        } else if (id == R.id.linear_movetofilter) {

            Intent intent = new Intent(DashboardActivity.this, FilterActivity.class);
            startActivityForResult(intent, 2);// Activity is started with requestCode 2


        } else if (id == R.id.linear_veliyeru) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("வெளியேறு");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to exit?");
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    Pref.clearpreference();
                    finishAffinity();

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    // Toast.makeText(DashboardActivity.this, "No notification deleted", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();


        } else if (id == R.id.linear_mugapu) {
            //drawer.closeDrawer(GravityCompat.START);

            Intent linear_mugapu = new Intent(DashboardActivity.this, DashboardActivity.class);
            startActivity(linear_mugapu);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }


    //joblist

    Call<BasicResponse> joblistcall;

    private void joblist_detail(String deviceid) {


        job_list = new ArrayList<>();

        swipeRefreshLayout.setRefreshing(true);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        avloadingIndicatorView.setVisibility(View.VISIBLE);
        //progressDialog.show();
        //showHideProgressDialog(true);

        joblistcall = apiService.getJobList(deviceid);

        joblistcall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    job_list.addAll(response.body().getJob_list());

                    Log.d("sdsdsds", "onResponse: " + job_list.size());

                    if (job_list.size() != 0) {

                        avloadingIndicatorView.setVisibility(View.GONE);
                        lay_nodata_found.setVisibility(View.GONE);
                        recycle_tamilbjoblist.setVisibility(View.VISIBLE);
                        // progressDialog.dismiss();
                        //showHideProgressDialog(false);

                        Log.d(TAG, "onResponse:cout " + response.body().getNotificationcount());

                        if (response.body().getNotificationcount().equals("0")) {
                            txt_notification_count.setVisibility(View.GONE);
                            txt_notificationtop_count.setVisibility(View.GONE);
                        } else {
                            txt_notification_count.setVisibility(View.VISIBLE);
                            txt_notificationtop_count.setVisibility(View.VISIBLE);
                            txt_notification_count.setText(response.body().getNotificationcount());
                            txt_notificationtop_count.setText(response.body().getNotificationcount());
                        }


                        //onloadmore

//                        tamilanjobsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//                            @Override
//                            public void onLoadMore() {
//                                if (job_list.size() <= 20) {
//                                    job_list.add(null);
//                                    tamilanjobsAdapter.notifyItemInserted(job_list.size() - 1);
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            job_list.remove(job_list.size() - 1);
//                                            tamilanjobsAdapter.notifyItemRemoved(job_list.size());
//
//                                            //Generating more data
//                                            int index = job_list.size();
//                                            int end = index + 10;
//                                            for (int i = index; i < end; i++) {
//                                                Joblistdetail joblistdetail = new Joblistdetail();
//                                                joblistdetail.setJpost_title(job_list.get(i).getJpost_title());
//                                                joblistdetail.setJpost_organization(job_list.get(i).getJpost_organization());
//                                                joblistdetail.setJpost_total_vacancy(job_list.get(i).getJpost_total_vacancy());
//                                                joblistdetail.setIsFav(job_list.get(i).getIsFav());
//                                                joblistdetail.setIsRemind(job_list.get(i).getIsRemind());
//                                                job_list.add(joblistdetail);
//                                            }
//                                            tamilanjobsAdapter.notifyDataSetChanged();
//                                            tamilanjobsAdapter.setLoaded();
//                                        }
//                                    }, 5000);
//                                } else {
//                                    Toast.makeText(DashboardActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });



                        tamilanjobsAdapter = new TamilanjobsAdapter(DashboardActivity.this, job_list);
                        recycle_tamilbjoblist.setAdapter(tamilanjobsAdapter);
                    }

                } else {
                    //showHideProgressDialog(true);
                    //progressDialog.dismiss();
                    lay_nodata_found.setVisibility(View.VISIBLE);
                    recycle_tamilbjoblist.setVisibility(View.GONE);
                    avloadingIndicatorView.setVisibility(View.GONE);

                }
                swipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                //  showHideProgressDialog(true);
                //  progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                avloadingIndicatorView.setVisibility(View.GONE);

            }
        });

    }


    public static void adapterNotify(String jobid) {

        tamilanjobsAdapter.notifyDataSetChanged();
    }


    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {


            // get String data from Intent
            tamilanjobsAdapter = new TamilanjobsAdapter(DashboardActivity.this, job_list);
            recycle_tamilbjoblist.setAdapter(tamilanjobsAdapter);
            tamilanjobsAdapter.notifyDataSetChanged();


        }
        if (requestCode == 2) {

            try {

                String marketingstocklist = data.getStringExtra("marketingstocklist");
                String marketingfilter = data.getStringExtra("marketingfilter");

                submitData(marketingstocklist, marketingfilter);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    Call<BasicResponse> call1;


    private void submitData(String marketingstocklist, String marketingfilter) {

        job_list = new ArrayList<>();

        swipeRefreshLayout.setRefreshing(true);

        avloadingIndicatorView.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call1 = apiService.searchjobpost(marketingstocklist, marketingfilter);
        call1.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    job_list.addAll(response.body().getJob_list());

                    avloadingIndicatorView.setVisibility(View.GONE);
                    tamilanjobsAdapter = new TamilanjobsAdapter(DashboardActivity.this, job_list);
                    recycle_tamilbjoblist.setAdapter(tamilanjobsAdapter);
                    tamilanjobsAdapter.notifyDataSetChanged();

                    lay_nodata_found.setVisibility(View.VISIBLE);

                    if (job_list.size() != 0) {

                        tamilanjobsAdapter = new TamilanjobsAdapter(DashboardActivity.this, job_list);
                        recycle_tamilbjoblist.setAdapter(tamilanjobsAdapter);
                        tamilanjobsAdapter.notifyDataSetChanged();

                        lay_nodata_found.setVisibility(View.GONE);
                    }

                } else {
                    //showHideProgressDialog(true);
                    //progressDialog.dismiss();
                    avloadingIndicatorView.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                t.printStackTrace();

                swipeRefreshLayout.setRefreshing(false);
                avloadingIndicatorView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {

        joblist_detail(deviceid);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
