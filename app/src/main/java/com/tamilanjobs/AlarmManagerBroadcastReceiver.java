package com.tamilanjobs;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.tamilanjobs.Response.Reminder;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mahei on 2/14/2018.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final static int ALARM_ID = 1;// use unique id while creating multiple
    // alarms
    MediaPlayer mediaPlayer;
    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        // we are starting an service here


        Log.d(TAG, "onReceive: onReceive");

        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();


        mediaPlayer=MediaPlayer.create(context,R.raw.long_expected);
        mediaPlayer.start();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.tamilan_roundicon);



        Intent notificationIntent = new Intent(context, DashboardActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        String jobid = intent.getStringExtra("jobid");


        new Delete().from(Reminder.class).where("reminderpostid = ?", jobid).execute();


        Log.d(TAG, "onReceive:jobid " + intent.getStringExtra("jobid"));

        Log.d("AlarmBroadcastReceiver", "setAlarm:name " + intent.getStringExtra("jobtitle"));
        Log.d("AlarBroadcastReceiver", "setAlarm:description " + intent.getStringExtra("joborganization"));



        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(mPendingIntent);
      //  builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        builder.setContentTitle(intent.getStringExtra("jobtitle"));
        builder.setContentText(intent.getStringExtra("joborganization"));

        builder.setAutoCancel(true);




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());

        // builder.setSubText("Tap to view the website.");




//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

    }


//    public void setAlarm(Context context, Calendar calendar, int ID) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Put Reminder ID in Intent Extra
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        // Calculate notification time
//        Calendar c = Calendar.getInstance();
//        long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;
//
//        // Start alarm using notification time
//        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + diffTime,
//                mPendingIntent);
//
//        // Restart alarm if device is rebooted
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//    }
//
//    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Put Reminder ID in Intent Extra
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        // Calculate notification timein
//        Calendar c = Calendar.getInstance();
//        long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;
//
//        // Start alarm using initial notification time and repeat interval time
//        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + diffTime,
//                RepeatTime , mPendingIntent);
//
//        // Restart alarm if device is rebooted
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//    }

}
