package com.forzipporah.mylove.datasync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Required to use SyncAdapter but not relevant to the App
 */
public class AuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;

    public AuthenticatorService() {
    }

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
