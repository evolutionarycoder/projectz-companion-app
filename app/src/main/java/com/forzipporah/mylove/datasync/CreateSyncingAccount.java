package com.forzipporah.mylove.datasync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;


/**
 * Required to use SyncAdapter but not relevant to the App
 */
public class CreateSyncingAccount {

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY    = "com.forzipporah.mylove.database.DatabaseProvider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.forzipporah.mylove.sync";
    // The account name
    public static final String ACCOUNT      = "Zipporah";

    private static final long SECONDS  = 60L;
    private static final long MINUTES  = 60L;
    private static final long INTERVAL = SECONDS * MINUTES;


    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static void CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            ContentResolver.setIsSyncable(newAccount, CreateSyncingAccount.AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(newAccount, CreateSyncingAccount.AUTHORITY, true);
            ContentResolver.addPeriodicSync(newAccount, CreateSyncingAccount.AUTHORITY, Bundle.EMPTY, 60L);
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
    }


}
