package com.tamilanjobs.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tamilanjobs.DashboardActivity;
import com.tamilanjobs.NotificationActivity;
import com.tamilanjobs.NotificationViewActivity;
import com.tamilanjobs.R;
import com.tamilanjobs.SplashScreenActivity;
import com.tamilanjobs.Util.Config;
import com.tamilanjobs.Util.NotificationUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import static android.content.ContentValues.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    Bitmap bitmap;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


      //  NotificationUtils.isAppIsInBackground(getApplicationContext());


//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle(remoteMessage.getData().get("title"))
//                .setContentText(remoteMessage.getData().get("body"))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .build();
//        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//        manager.notify(123, notification);


/*

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getFrom());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getMessageId());
            Log.d(TAG, "Message Notification type: " + remoteMessage.getMessageType());
        }
        Log.e(TAG, "Notification Body: " + remoteMessage.getFrom());

*/
        Log.e(TAG, "Notification Body: " + remoteMessage.getFrom());

        Log.d(TAG, "onMessageReceived:remoteMessage.getData().size() " + remoteMessage.getData().size());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload:sdsds " + remoteMessage.getData().toString());

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("body");

        Log.d(TAG, "onMessageReceived: "+remoteMessage.getData().get("body"));
        //imageUri will contain URL of the image to be displayed with Notification
      //  String imageUri = remoteMessage.getData().get("image");
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");

        //To get a Bitmap image from the URL received
       // bitmap = getBitmapfromUrl(imageUri);

      //  sendNotification(title, message);
    }


    private void sendNotification(String messageBody, String TrueOrFalse) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageBody)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

           // JSONObject json = null;

//            if(remoteMessage.getData().size()!=0){
//
//                try {
//                    json = new JSONObject(remoteMessage.getData().toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                handleDataMessage(json);
//            }else {
//                Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//                handleDataMessage(json);
//            }
//
//        }
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                handleDataMessage(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        } else {
//            Log.e(TAG, "Data Payload:sadfasdfasdfasdfds " + remoteMessage.getData().toString());
//
//        }


  //  }

//    private void handleNotification(String message) {
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        }else{
//            // If the app is in background, firebase itself handles the notification
//        }
//    }

    private void handleDataMessage(JSONObject json) {
   /*     Log.e(TAG, "push json: " + json.toString());*/



        try {

            Log.d(TAG, "handleDataMessage: " + NotificationUtils.isAppIsInBackground(getApplicationContext()));
/*
            JSONObject jsona = (JSONObject) JSONSerializer.toJSON(data);
            JSONObject data = json.getJSONObject(json);
*/

            String notify_ntype_id = json.getString("notify_ntype_id");



            if (notify_ntype_id.equals("2")) {

                String notify_jpost_id = json.getString("notify_jpost_id");

                String notify_id = json.getString("notify_id");

                Log.d(TAG, "handleDataMessage: notify_jpost_id "+notify_jpost_id);
                Log.d(TAG, "handleDataMessage: notify_id "+notify_id);



            } else {

                String notify_id = json.getString("notify_id");
                Log.d(TAG, "handleDataMessage: notify_id "+notify_id);

            }

            /*String notify_title = data.getString("notify_title");
            String notify_short_desc = data.getString("notify_short_desc");


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                //  pushNotification.putExtra("message", URLDecoder.decode(message));
                //  pushNotification.putExtra("receiver_id", receiver_id);
                pushNotification.putExtra("job_notification_type", job_notification_type);
                pushNotification.putExtra("notify_id", notify_id);
                pushNotification.putExtra("notify_title", notify_title);
                pushNotification.putExtra("notify_short_desc", notify_short_desc);
                sendBroadcast(pushNotification);
                // LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray

                if (job_notification_type.equals("1")) {
                    Intent resultIntent = new Intent(getApplicationContext(), NotificationViewActivity.class);
                    resultIntent.putExtra("job_notification_type", job_notification_type);
                    resultIntent.putExtra("notify_id", notify_id);
                    resultIntent.putExtra("notify_title", notify_title);
                    resultIntent.putExtra("notify_short_desc", notify_short_desc);

                    long time = System.currentTimeMillis();

                    showNotificationMessage(getApplicationContext(),
                            notify_title,
                            notify_short_desc, String.valueOf(time), resultIntent);


                } else {
                    Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                    resultIntent.putExtra("job_notification_type", job_notification_type);
                    resultIntent.putExtra("notify_id", notify_id);
                    resultIntent.putExtra("notify_title", notify_title);
                    resultIntent.putExtra("notify_short_desc", notify_short_desc);

                    long time = System.currentTimeMillis();

                    showNotificationMessage(getApplicationContext(),
                            notify_title,
                            notify_short_desc, String.valueOf(time), resultIntent);
                }

            }*/

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context,
                                         String title,
                                         String message,
                                         String timeStamp,
                                         Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}