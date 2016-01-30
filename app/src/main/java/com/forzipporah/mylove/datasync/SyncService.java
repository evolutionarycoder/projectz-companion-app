package com.forzipporah.mylove.datasync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Required to use SyncAdapter but not relevant to the App
 */
public class SyncService extends Service {

    // Object to use as a thread-safe lock
    private static final Object      sSyncAdapterLock = new Object();
    // Storage for an instance of the sync adapter
    private static       SyncAdapter sSyncAdapter     = null;

    public SyncService() {
    }

    @Override
    public void onCreate() {
         /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }


}
