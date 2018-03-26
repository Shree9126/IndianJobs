package com.tamilanjobs;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tamilanjobs.Helper.AppController;
import com.tamilanjobs.Helper.Pref;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    public boolean isNotifi = false;

   // Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Pref.setAdCount(0);

        if(getIntent().getStringExtra("notify_id")!=null) {

            if (getIntent().getStringExtra("notify_ntype_id").equals("1")) {
                Pref.setAdCount(0);
                Pref.setNotification(true);
                isNotifi = true;
                Intent notify = new Intent(SplashScreenActivity.this, NotificationViewActivity.class);
                notify.putExtra("notify_id", getIntent().getStringExtra("notify_id"));
                notify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(notify);
                finish();
            } else {
                Pref.setAdCount(0);
                isNotifi = true;
                Pref.setNotification(true);
                Intent notify = new Intent(SplashScreenActivity.this, JobsfullviewActivity.class);
                notify.putExtra("jobid", getIntent().getStringExtra("notify_jpost_id"));
                notify.putExtra("reminder_id", "0");
                notify.putExtra("favid", "0");
                notify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(notify);
               finish();
            }

        }







//        handler.postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//
//
////                if(getIntent().getStringExtra("notify_id")!=null){
////
////
//////
//////                    getIntent().getStringExtra("notify_ntype_id");
//////                    getIntent().getStringExtra("notify_jpost_id");
////
////
////
////
////                }else {
//                    Intent dashboard = new Intent(SplashScreenActivity.this, DashboardActivity.class);
//                    dashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(dashboard);
//                    finish();
//               // }
//
//
//            }
//        }, SPLASH_TIME_OUT);

        if(!isNotifi){

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent dashboard = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    dashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }


    }

}
