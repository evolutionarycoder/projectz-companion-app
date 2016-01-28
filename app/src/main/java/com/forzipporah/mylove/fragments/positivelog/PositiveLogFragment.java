package com.forzipporah.mylove.fragments.positivelog;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;


public class PositiveLogFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {
    private static final int INCREMENT_BY                  = 10;
    public static        int LIMIT                         = 10;
    private final        int FETCH_POSITIVE_LOGS_LOADER_ID = 0;
    private int preLast;

    private ListView      positiveListView;
    private CursorAdapter mCursorAdapter;

    public PositiveLogFragment() {
        // Required empty public constructor
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
}
