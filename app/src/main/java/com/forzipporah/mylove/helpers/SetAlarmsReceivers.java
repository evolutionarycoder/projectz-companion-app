package com.forzipporah.mylove.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.forzipporah.mylove.receivers.NotificationReceiver;

import java.util.Calendar;

public class SetAlarmsReceivers {

    public static void createAlarms(Context context) {
        SetAlarmsReceivers.createNotificationAlarm(context);
    }


    public static void createNotificationAlarm(Context context) {
        // register alarm
        Intent        i            = new Intent(context, NotificationReceiver.class);
        PendingIntent pIntent      = PendingIntent.getBroadcast(context, 0, i, 0);
        AlarmManager  alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar      calendar     = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
    }

}
