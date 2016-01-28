package com.forzipporah.mylove.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.DatabaseAsyncOperation;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.models.PositiveLog;

public class PositiveLogActivity extends AppCompatActivity implements DatabaseAsyncOperation.OperationComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_log);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_positvelog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create:
                PositiveLog positiveLog = new PositiveLog("life");
                DatabaseAsyncOperation db = new DatabaseAsyncOperation(getContentResolver(), this);
                db.startInsert(DatabaseAsyncOperation.INSERT_CODE, null, PositiveLogContract.buildUri(), positiveLog.createContentValues());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void completed(Object... objects) {

    }
}
