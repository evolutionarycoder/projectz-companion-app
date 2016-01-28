package com.forzipporah.mylove.ui;

import android.app.Activity;
import android.os.Bundle;

import com.forzipporah.mylove.R;

public class PositiveLogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_log);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
