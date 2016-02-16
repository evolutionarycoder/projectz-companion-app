package com.forzipporah.mylove.fragments.positivelog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.DatabaseAsyncOperation;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.models.PositiveLog;
import com.forzipporah.mylove.ui.positivelog.PositiveLogActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PositiveLogEditFragment extends Fragment implements DatabaseAsyncOperation.OperationComplete {

    private PositiveLogActivity.TYPE mTYPE = PositiveLogActivity.TYPE.CREATE;
    private PositiveLog positiveLog;
    private EditText    etPositive;


    public PositiveLogEditFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        positiveLog = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_positive_log_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent i = getActivity().getIntent();

        etPositive = (EditText) view.findViewById(R.id.positiveET);
        // means they want to edit a positive log
        if (i.getSerializableExtra(PositiveLogActivity.ACTION_TYPE) == PositiveLogActivity.TYPE.EDIT) {
            positiveLog = i.getParcelableExtra(PositiveLogFragment.POSITIVE_LOG_EXTRA);
            etPositive.setText(positiveLog.getPositiveFor());
            mTYPE = PositiveLogActivity.TYPE.EDIT;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_positvelog, menu);
        MenuItem create = menu.findItem(R.id.action_create);
        MenuItem update = menu.findItem(R.id.action_update);
        MenuItem save   = menu.findItem(R.id.action_save);
        create.setVisible(false);

        if (mTYPE == PositiveLogActivity.TYPE.EDIT) {
            save.setVisible(false);
        } else {
            update.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int                    id   = item.getItemId();
        DatabaseAsyncOperation db   = new DatabaseAsyncOperation(getContext().getContentResolver(), this);
        String                 text = etPositive.getText().toString();
        switch (id) {
            case R.id.action_save:
                if (positiveLog == null) {
                    positiveLog = new PositiveLog(text);
                } else {
                    positiveLog.setPositiveFor(text);
                }
                db.startInsert(DatabaseAsyncOperation.INSERT_CODE,
                        null,
                        PositiveLogContract.buildUri(),
                        positiveLog.createContentValues());
                return true;
            case R.id.action_update:
                positiveLog.setPositiveFor(text);
                db.startUpdate(DatabaseAsyncOperation.UPDATE_CODE,
                        null,
                        PositiveLogContract.buildSingleUri(),
                        positiveLog.createContentValues(),
                        null,
                        new String[]{
                                positiveLog.getId() + ""
                        });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void notifyAndFinish(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void completed(Object... objects) {
        int token = (int) objects[0];
        switch (token) {
            case DatabaseAsyncOperation.INSERT_CODE:
                notifyAndFinish("Created");
                break;
            case DatabaseAsyncOperation.UPDATE_CODE:
                notifyAndFinish("Updated");
                break;
        }
    }
}
