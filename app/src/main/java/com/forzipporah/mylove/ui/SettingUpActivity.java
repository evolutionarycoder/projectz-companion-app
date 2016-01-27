package com.forzipporah.mylove.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.datasync.CreateSyncingAccount;
import com.forzipporah.mylove.helpers.SetAlarmsReceivers;
import com.forzipporah.mylove.helpers.http.Http;
import com.forzipporah.mylove.helpers.http.JSONParse;
import com.forzipporah.mylove.models.Poem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
    private class SyncData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder sb      = null;
            Uri.Builder   builder = new Uri.Builder();
            builder.scheme(Http.SCHEME)
                    .authority(Http.DOMAIN)
                    .appendPath("versatile")
                    .appendPath("projectz")
                    .appendPath("cms")
                    .appendPath("Backend")
                    .appendPath("api")
                    .appendPath("poem")
                    .appendPath("poem.php")
                    .appendQueryParameter("fetch", "");
            try {
                URL syncUrl = new URL(builder.toString());
                HttpURLConnection connection = (HttpURLConnection) syncUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                connection.connect();

                // means connection successful
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream response = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                    sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    response.close();
                    reader.close();
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sb != null && sb.toString().length() != 0) {
                ArrayList<Poem> poems = JSONParse.parseJSONToPoems(sb.toString());
                ContentValues[] values = JSONParse.createContentValuesArrayFromPoemsList(poems);
                getBaseContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
                return "true";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && s.equals("true")) {
                startMainActivity();
            } else {
                Toast.makeText(getBaseContext(), "Sorry an error occurred", Toast.LENGTH_LONG).show();
                startMainActivity();
            }
        }
    }
}
