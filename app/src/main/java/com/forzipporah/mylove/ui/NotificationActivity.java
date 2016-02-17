package com.forzipporah.mylove.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.helpers.http.Http;
import com.forzipporah.mylove.helpers.http.HttpRequestManager;
import com.forzipporah.mylove.helpers.http.JSONParse;
import com.forzipporah.mylove.models.Notification;

import java.io.IOException;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        lv = (ListView) findViewById(R.id.notificationList);
        FetchNotifications fetch = new FetchNotifications();
        fetch.execute();
    }


    private class FetchNotifications extends AsyncTask<Void, Void, ArrayList<Notification>> {

        @Override
        protected ArrayList<Notification> doInBackground(Void... params) {
            if (Http.isNetworkAvailable(NotificationActivity.this)) {
                HttpRequestManager fetchNotifs = new HttpRequestManager("notification.php", "fetch");
                String response;
                try {
                    response = fetchNotifs.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }

                if (response != null && response.length() > 12) {
                    return JSONParse.parseJSONToNotifications(response);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Notification> notifications) {
            super.onPostExecute(notifications);
            String[] strings;
            if (notifications != null) {
                strings = new String[notifications.size()];
                for (int i = 0; i < notifications.size(); i++) {
                    strings[i] = notifications.get(i).getText();
                }
            } else {
                strings = new String[]{
                        "No New Data"
                };
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, strings);
            lv.setAdapter(adapter);
        }
    }

}
