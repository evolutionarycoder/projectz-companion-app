package com.forzipporah.mylove.datasync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;

import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.helpers.Notification;
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

/**
 * Performs the Sync in the background right database update logic here
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {


    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

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
        Uri.Builder builder = new Uri.Builder();
        // create UR(I)L
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
        StringBuilder sb = null;
        try {
            URL syncURl = new URL(builder.toString());
            HttpURLConnection connection = (HttpURLConnection) syncURl.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // fetch data
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
            if (poems.size() > 0) {
                ContentValues[] values = JSONParse.createContentValuesArrayFromPoemsList(poems);
                getContext().getContentResolver().bulkInsert(PoemContract.buildUri(), values);
                Notification.showSyncNotification(getContext());
            }
        }
    }
}
