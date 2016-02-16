package com.forzipporah.mylove.ui.viewlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.fragments.LoadListDataFragment;

public class ViewListItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_item);
        String title = getIntent().getStringExtra(LoadListDataFragment.TITLE);
        String text  = getIntent().getStringExtra(LoadListDataFragment.TEXT);

        setTitle(title);
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(text);
    }

}
