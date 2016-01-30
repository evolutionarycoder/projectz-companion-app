package com.forzipporah.mylove.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.forzipporah.mylove.helpers.Notification;

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.showGreetingNotification(context);
    }


}
