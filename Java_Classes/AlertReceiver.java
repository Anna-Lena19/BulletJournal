package com.example.bulletjournal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {

    String title,message;

    //Chanels Notification tutorialspoint ########################################
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    //Chanels Notification tutorialspoint ########################################


    @Override
    public void onReceive(Context context, Intent intent) {

        //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
       if(intent.hasExtra("title") && intent.hasExtra("message")) {
            title = intent.getStringExtra("title");
            message = intent.getStringExtra("message");
           NotificationHelper notificationHelper = new NotificationHelper(context,title,message);
           NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
           notificationHelper.getManager().notify(1, nb.build());

        }

       /*
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setContentTitle("dfl")//intent.getStringExtra("title")
                .setContentText("ldfl")//intent.getStringExtra("message")
                .setSmallIcon(R.drawable.ic_note);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
        //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$





        //Chanels Notification tutorialspoint ########################################

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;

         */
        //Chanels Notification tutorialspoint ########################################







        //Normal Alarmmanager
        NotificationHelper notificationHelper = new NotificationHelper(context,title,message);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}

