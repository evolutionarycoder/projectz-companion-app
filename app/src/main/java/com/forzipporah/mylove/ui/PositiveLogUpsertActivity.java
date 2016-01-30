package com.forzipporah.mylove.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.fragments.positivelog.PositiveLogEditFragment;
import com.forzipporah.mylove.fragments.positivelog.PositiveLogViewFragment;

public class PositiveLogUpsertActivity extends AppCompatActivity {


    private final String VIEW_FRAGMENT_TAG   = "com.forzipporah.mylove.ui.VIEW";
    private final String EDIT_FRAGMENT_TAG   = "com.forzipporah.mylove.ui.EDIT";
    private final String CREATE_FRAGMENT_TAG = "com.forzipporah.mylove.ui.CREATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_log_upsert);

        Intent                   intent              = getIntent();
        PositiveLogActivity.TYPE actionType          = (PositiveLogActivity.TYPE) intent.getSerializableExtra(PositiveLogActivity.ACTION_TYPE);
        FragmentManager          fragmentManager     = getSupportFragmentManager();
        FragmentTransaction      fragmentTransaction = fragmentManager.beginTransaction();

        PositiveLogEditFragment positiveLogEditFragment;
        switch (actionType) {
            case VIEW:
                PositiveLogViewFragment positiveLogViewFragment = new PositiveLogViewFragment();
                fragmentTransaction.add(R.id.fragment_container, positiveLogViewFragment, VIEW_FRAGMENT_TAG).commit();
                break;

            case EDIT:
                setTitle("Edit");
                positiveLogEditFragment = new PositiveLogEditFragment();
                fragmentTransaction.add(R.id.fragment_container, positiveLogEditFragment, EDIT_FRAGMENT_TAG).commit();
                break;
            case CREATE:
                setTitle("Create");
                positiveLogEditFragment = new PositiveLogEditFragment();
                fragmentTransaction.add(R.id.fragment_container, positiveLogEditFragment, CREATE_FRAGMENT_TAG).commit();
                break;
        }

    }

}
