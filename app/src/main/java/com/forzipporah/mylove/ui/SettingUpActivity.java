package com.forzipporah.mylove.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.datasync.CreateSyncingAccount;
import com.forzipporah.mylove.helpers.SetAlarmsReceivers;
import com.forzipporah.mylove.helpers.http.Http;
import com.forzipporah.mylove.helpers.http.HttpRequestManager;
import com.forzipporah.mylove.models.ILove;
import com.forzipporah.mylove.models.Poem;

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

        @Override
        protected Void doInBackground(Void... params) {
            HttpRequestManager httpRequestPoems      = new HttpRequestManager("poem", "poem.php", "fetch");
            HttpRequestManager httpRequestLoveAbouts = new HttpRequestManager("ilove", "ilove.php", "fetch");
            String             poemResponse          = null;
            String             iloveResponse         = null;
            try {
                poemResponse = httpRequestPoems.connect();
                iloveResponse = httpRequestLoveAbouts.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (poemResponse != null && poemResponse.length() != 0) {
                ContentValues[] values = Poem.createContentValuesArrayFromJSON(poemResponse);
                getBaseContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
            }

            if (iloveResponse != null && iloveResponse.length() != 0) {
                ContentValues[] values = ILove.createContentValuesArrayFromJSON(iloveResponse);
                getBaseContext().getContentResolver().bulkInsert(ILoveContract.buildUri(), values);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startMainActivity();
        }
    }
}
