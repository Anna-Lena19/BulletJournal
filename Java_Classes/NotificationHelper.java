package com.example.bulletjournal;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    String title, message;

    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "Channel 1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "Channel 2";

    private NotificationManager notificationManager;
    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private NotificationManager mManager;


    public NotificationHelper(Context base, String title, String message) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.title = title;
            this.message = message;
            createChannel();

            //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            createChannels();
            //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            
        }
    }


    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(channel1ID,channel1Name,NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(false);
        channel1.enableVibration(true);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);


        NotificationChannel channel2 = new NotificationChannel(channel2ID,channel2Name,NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(false);
        channel2.enableVibration(true);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotifyManager().createNotificationChannel(channel2);
    }

    public NotificationManager getNotifyManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_add_button);
    }

    public NotificationCompat.Builder getChannel2Notification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_arrow_back);
    }

    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$




    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_add_button);
    }
}