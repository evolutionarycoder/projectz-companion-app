package com.forzipporah.mylove.ui.positivelog;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.Database;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.fragments.positivelog.PositiveLogFragment;

public class PositiveLogActivity extends AppCompatActivity implements PositiveLogFragment.FetchTotalPositiveLogs {
    public static final String ACTION_TYPE = "com.forzipporah.mylove.ui.ACTION_TYPE";
    public TextView total;

    @Override
    public void fetchPositiveLogs() {
        TotalPositiveRecords total = new TotalPositiveRecords();
        total.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_log);
        total = (TextView) findViewById(R.id.totalTV);
    }

    private void setNumThankfulFor(String s) {
        String text = s + " ITEMS";
        total.setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_positvelog, menu);
        MenuItem update = menu.findItem(R.id.action_update);
        MenuItem save   = menu.findItem(R.id.action_save);
        update.setVisible(false);
        save.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create:
                Intent i = new Intent(getBaseContext(), PositiveLogUpsertActivity.class);
                i.putExtra(PositiveLogActivity.ACTION_TYPE, TYPE.CREATE);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public enum TYPE {
        VIEW,
        EDIT,
        CREATE
    }

    private class TotalPositiveRecords extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Database       database   = new Database(getBaseContext());
            SQLiteDatabase db         = database.getReadableDatabase();
            long           numEntries = DatabaseUtils.queryNumEntries(db, PositiveLogContract.TABLE_NAME);
            return numEntries + "";
        }

        @Override
        protected void onPostExecute(String s) {
            setNumThankfulFor(s);
        }
    }

}
