package com.forzipporah.mylove.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.ui.MainActivity;

import java.util.Random;

/**
 * Created by prince on 1/26/16.
 */
public class Notification {
    public static final int GREETING_NOTIF_ID = 0;
    public static final int SYNC_NOTIF_ID     = 1;

    public static final int GREETING_INTENT_ID = 100;
    public static final int SYNC_INTENT_ID     = 101;


    public static void showGreetingNotification(Context context) {
        Random rand = new Random();
        String[] strings = {
                "Enjoy your day princess",
                "I love you",
                "You're my queen",
                "Daniel was right, you're beautiful",
                "I really miss you :(",
                "Your flaws are perfection to me",
                "Some day I'll marry you",
                "My heart is and always will be yours",
                "Every love story is beautiful, but ours is my favorite."

        };
        int pos = rand.nextInt(strings.length);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.fav);
        mBuilder.setContentTitle("My Love");
        mBuilder.setContentText(strings[pos]);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(strings[pos]));
        mBuilder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(GREETING_NOTIF_ID, mBuilder.build());
    }

    public static void showSyncNotification(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, SYNC_INTENT_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.stat_notify_sync);
        mBuilder.setContentTitle("My Love");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentText("New Poems Available!");
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Notification.SYNC_NOTIF_ID, mBuilder.build());
    }
}
