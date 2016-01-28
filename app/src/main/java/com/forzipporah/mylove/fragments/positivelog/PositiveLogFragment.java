package com.forzipporah.mylove.fragments.positivelog;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.DatabaseAsyncOperation;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.models.PositiveLog;
import com.forzipporah.mylove.ui.PositiveLogActivity;
import com.forzipporah.mylove.ui.PositiveLogUpsertActivity;


public class PositiveLogFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, DatabaseAsyncOperation.OperationComplete {
    public static final String POSITIVE_LOG_EXTRA = "com.forzipporah.mylove.ui.POSITIVE_LOG_EXTRA";

    private final int INCREMENT_BY                  = 10;
    private final int FETCH_POSITIVE_LOGS_LOADER_ID = 0;
    public        int LIMIT                         = 10;
    FetchTotalPositiveLogs mActivity;
    private int preLast;
    private ListView      positiveListView;
    private CursorAdapter mCursorAdapter;

    public PositiveLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mActivity = (FetchTotalPositiveLogs) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Interface not implemented: FetchTotalPositiveLogs");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_positive_log, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        positiveListView = (ListView) view.findViewById(R.id.positiveListView);
        mCursorAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.positive_item,
                null,
                new String[]{PositiveLogContract.COL_POSITIVE_FOR},
                new int[]{R.id.positiveTextView},
                0);
        positiveListView.setAdapter(mCursorAdapter);
        positiveListView.setOnItemClickListener(this);
        registerForContextMenu(positiveListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_positivelog, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo itemSelected = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int                                pos          = itemSelected.position;
        Cursor                             c;
        switch (item.getItemId()) {
            case R.id.edit_positive_log_context:
                GetPositiveLogFromCursor positiveLogFromCursor = new GetPositiveLogFromCursor(PositiveLogActivity.TYPE.EDIT);
                c = mCursorAdapter.getCursor();
                if (c.moveToPosition(pos)) {
                    positiveLogFromCursor.execute(c);
                }
                return true;

            case R.id.delete_positive_log_context:
                deletePositiveLog(itemSelected.id);
                return true;

            default:
                return false;
        }
    }

    private void startUpsertActivity(PositiveLog positiveLog, PositiveLogActivity.TYPE type) {
        Intent i = new Intent(getContext(), PositiveLogUpsertActivity.class);
        i.putExtra(PositiveLogActivity.ACTION_TYPE, type);
        i.putExtra(POSITIVE_LOG_EXTRA, positiveLog);
        startActivity(i);
    }

    private void deletePositiveLog(long id) {
        DatabaseAsyncOperation db = new DatabaseAsyncOperation(getContext().getContentResolver(), this);
        db.startDelete(DatabaseAsyncOperation.DELETE_CODE,
                null,
                PositiveLogContract.buildSingleUri(),
                null,
                new String[]{
                        id + ""
                });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            if (preLast != lastItem) { //to avoid multiple calls for last item
                LIMIT += INCREMENT_BY;
                getLoaderManager().restartLoader(FETCH_POSITIVE_LOGS_LOADER_ID, null, this);
                preLast = lastItem;
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FETCH_POSITIVE_LOGS_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mActivity.fetchPositiveLogs();
        return new CursorLoader(getContext(), PositiveLogContract.buildUri(),
                null,
                null,
                null,
                PositiveLogContract._ID + " DESC LIMIT " + LIMIT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = mCursorAdapter.getCursor();
        if (c.moveToPosition(position)) {
            GetPositiveLogFromCursor poemFromCursor = new GetPositiveLogFromCursor(PositiveLogActivity.TYPE.VIEW);
            poemFromCursor.execute(c);
        }
    }

    @Override
    public void completed(Object... objects) {
        int token = (int) objects[0];
        switch (token) {
            case DatabaseAsyncOperation.DELETE_CODE:
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public interface FetchTotalPositiveLogs {
        void fetchPositiveLogs();
    }

    private class GetPositiveLogFromCursor extends AsyncTask<Cursor, Void, PositiveLog> {
        private PositiveLogActivity.TYPE mTYPE;

        public GetPositiveLogFromCursor(PositiveLogActivity.TYPE TYPE) {
            mTYPE = TYPE;
        }

        @Override
        protected PositiveLog doInBackground(Cursor... params) {
            Cursor c           = params[0];
            long   id          = c.getLong(c.getColumnIndex(PositiveLogContract._ID));
            String positiveFor = c.getString(c.getColumnIndex(PositiveLogContract.COL_POSITIVE_FOR));
            String date        = c.getString(c.getColumnIndex(PositiveLogContract.COL_DATE));
            return new PositiveLog(id, positiveFor, date);
        }

        @Override
        protected void onPostExecute(PositiveLog positiveLog) {
            startUpsertActivity(positiveLog, mTYPE);
        }
    }

}
