package com.forzipporah.mylove.datasync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.helpers.Notification;
import com.forzipporah.mylove.helpers.http.HttpRequestManager;
import com.forzipporah.mylove.models.ILove;
import com.forzipporah.mylove.models.Poem;

import java.io.IOException;

/**
 * Performs the Sync in the background right database update logic here
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    private boolean dataRetrieved = false;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        HttpRequestManager httpRequestPoems      = new HttpRequestManager("poem", "poem.php", "fetch");
        HttpRequestManager httpRequestLoveAbouts = new HttpRequestManager("ilove", "ilove.php", "fetch");


        String poemResponse  = null;
        String iloveResponse = null;
        try {
            poemResponse = httpRequestPoems.connect();
            iloveResponse = httpRequestLoveAbouts.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (validateResponse(iloveResponse)) {
            ContentValues[] values = ILove.createContentValuesArrayFromJSON(iloveResponse);
            getContext().getContentResolver().bulkInsert(ILoveContract.buildUri(), values);
        }

        if (validateResponse(poemResponse)) {
            ContentValues[] values = Poem.createContentValuesArrayFromJSON(poemResponse);
            getContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
        }

        if (dataRetrieved) {
            Notification.showSyncNotification(getContext());
        }
    }

    private boolean validateResponse(String response) {
        if (response != null && response.length() != 0) {
            dataRetrieved = true;
            return true;
        }
        return false;
    }
}
