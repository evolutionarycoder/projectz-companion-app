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
import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.database.contracts.ReassureContract;
import com.forzipporah.mylove.helpers.Notification;
import com.forzipporah.mylove.helpers.http.HttpRequestManager;
import com.forzipporah.mylove.models.ILove;
import com.forzipporah.mylove.models.Memory;
import com.forzipporah.mylove.models.Poem;
import com.forzipporah.mylove.models.Promise;
import com.forzipporah.mylove.models.Reassure;

import java.io.IOException;

/**
 * Performs the Sync in the background right database update logic here
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    private boolean dataRetrieved;

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
        dataRetrieved = false;

        // define request objects with there destination to retrieve data
        HttpRequestManager httpRequestPoems      = new HttpRequestManager("poem.php", "fetch");
        HttpRequestManager httpRequestLoveAbouts = new HttpRequestManager("ilove.php", "fetch");
        HttpRequestManager httpRequestPromise  = new HttpRequestManager("promise.php", "fetch");
        HttpRequestManager httpRequestMemory   = new HttpRequestManager("memory.php", "fetch");
        HttpRequestManager httpRequestReassure = new HttpRequestManager("reassure.php", "fetch");

        // variables to hold data
        String poemResponse = null,
                iloveResponse = null,
                promiseResponse = null,
                reassureResponse = null,
                memoryResponse = null;
        // retrieve data
        try {
            poemResponse = httpRequestPoems.connect();
            iloveResponse = httpRequestLoveAbouts.connect();
            promiseResponse = httpRequestPromise.connect();
            memoryResponse = httpRequestMemory.connect();
            reassureResponse = httpRequestReassure.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // validate data and insert them

        // poems
        if (validateResponse(poemResponse)) {
            ContentValues[] values = Poem.createContentValuesArrayFromJSON(poemResponse);
            getContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
        }

        // i loves
        if (validateResponse(iloveResponse)) {
            ContentValues[] values = ILove.createContentValuesArrayFromJSON(iloveResponse);
            getContext().getContentResolver().bulkInsert(ILoveContract.buildUri(), values);
        }

        // promises
        if (validateResponse(promiseResponse)) {
            ContentValues[] values = Promise.createContentValuesArrayFromJSON(promiseResponse);
            getContext().getContentResolver().bulkInsert(PromiseContract.buildUri(), values);
        }

        // memories
        if (validateResponse(memoryResponse)) {
            ContentValues[] values = Memory.createContentValuesArrayFromJSON(memoryResponse);
            getContext().getContentResolver().bulkInsert(MemoryContract.buildUri(), values);
        }

        // reassurance
        if (validateResponse(reassureResponse)) {
            ContentValues[] values = Reassure.createContentValuesArrayFromJSON(reassureResponse);
            getContext().getContentResolver().bulkInsert(ReassureContract.buildUri(), values);
        }

        // if data is received show notification
        if (dataRetrieved) {
            Notification.showSyncNotification(getContext());
        }
    }

    // validate response from servers
    private boolean validateResponse(String response) {
        if (response != null) {
            if (!response.equals("false")) {
                if (response.length() != 0 && response.length() > 12) {
                    dataRetrieved = true;
                    return true;
                }
            }
        }
        return false;
    }
}
