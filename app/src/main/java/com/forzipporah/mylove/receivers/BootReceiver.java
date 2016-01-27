package com.forzipporah.mylove.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.forzipporah.mylove.helpers.SetAlarmsReceivers;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SetAlarmsReceivers.createAlarms(context);
        }
    }
}
