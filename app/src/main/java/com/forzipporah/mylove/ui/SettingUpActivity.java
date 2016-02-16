package com.forzipporah.mylove.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.database.contracts.ReassureContract;
import com.forzipporah.mylove.datasync.CreateSyncingAccount;
import com.forzipporah.mylove.helpers.SetAlarmsReceivers;
import com.forzipporah.mylove.helpers.http.Http;
import com.forzipporah.mylove.helpers.http.HttpRequestManager;
import com.forzipporah.mylove.models.ILove;
import com.forzipporah.mylove.models.Memory;
import com.forzipporah.mylove.models.Poem;
import com.forzipporah.mylove.models.Promise;
import com.forzipporah.mylove.models.Reassure;

import java.io.IOException;

public class SettingUpActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // on apps first launch
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            if (Http.isNetworkAvailable(getBaseContext())) {
                // only continue if internet is available

                // register content provider to be synced
                CreateSyncingAccount.CreateSyncAccount(this);

                // sync data manually for the first time
                SyncData syncData = new SyncData();
                syncData.execute();

                SetAlarmsReceivers.createAlarms(this);

                // mark first time has runned.
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstTime", true);
                editor.apply();
            } else {
                startMainActivity();
            }
        } else {
            startMainActivity();
        }
    }


    private void startMainActivity() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }


    // fetch data and sync it
    private class SyncData extends AsyncTask<Void, Void, Void> {
        // validate response from servers
        private boolean validateResponse(String response) {
            if (response != null) {
                if (!response.equals("false")) {
                    if (response.length() != 0 && response.length() > 12) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpRequestManager httpRequestPoems      = new HttpRequestManager("poem.php", "fetch");
            HttpRequestManager httpRequestLoveAbouts = new HttpRequestManager("ilove.php", "fetch");
            HttpRequestManager httpRequestPromise  = new HttpRequestManager("promise.php", "fetch");
            HttpRequestManager httpRequestMemory   = new HttpRequestManager("memory.php", "fetch");
            HttpRequestManager httpRequestReassure = new HttpRequestManager("reassure.php", "fetch");

            String poemResponse = null,
                    iloveResponse = null,
                    promiseResponse = null,
                    reassureResponse = null,
                    memoryResponse = null;
            try {
                poemResponse = httpRequestPoems.connect();
                iloveResponse = httpRequestLoveAbouts.connect();
                promiseResponse = httpRequestPromise.connect();
                memoryResponse = httpRequestMemory.connect();
                reassureResponse = httpRequestReassure.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (validateResponse(poemResponse)) {
                ContentValues[] values = Poem.createContentValuesArrayFromJSON(poemResponse);
                getBaseContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
            }

            if (validateResponse(iloveResponse)) {
                ContentValues[] values = ILove.createContentValuesArrayFromJSON(iloveResponse);
                getBaseContext().getContentResolver().bulkInsert(ILoveContract.buildUri(), values);
            }

            if (validateResponse(promiseResponse)) {
                ContentValues[] values = Promise.createContentValuesArrayFromJSON(promiseResponse);
                getBaseContext().getContentResolver().bulkInsert(PromiseContract.buildUri(), values);
            }

            if (validateResponse(memoryResponse)) {
                ContentValues[] values = Memory.createContentValuesArrayFromJSON(memoryResponse);
                getBaseContext().getContentResolver().bulkInsert(MemoryContract.buildUri(), values);
            }

            if (validateResponse(reassureResponse)) {
                ContentValues[] values = Reassure.createContentValuesArrayFromJSON(reassureResponse);
                getBaseContext().getContentResolver().bulkInsert(ReassureContract.buildUri(), values);
            }

            Log.i("JSONP", poemResponse);
            Log.i("JSONP", iloveResponse);
            Log.i("JSONP", promiseResponse);
            Log.i("JSONP", memoryResponse);
            Log.i("JSONP", reassureResponse);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startMainActivity();
        }
    }
}
