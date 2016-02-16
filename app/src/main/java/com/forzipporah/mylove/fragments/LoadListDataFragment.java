package com.forzipporah.mylove.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.adapters.ListCursorAdapter;
import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.database.contracts.ReassureContract;
import com.forzipporah.mylove.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadListDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {
    private static int LOADER_ID = 0;

    private static int LIMIT        = 10;
    private static int INCREMENT_BY = 10;
    private int               preLast;
    private DATABASE_TABLES   type;
    private ListCursorAdapter mAdapter;
    private ListView          listView;

    public LoadListDataFragment() {
        // Required empty public constructor
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
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                preLast = lastItem;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        type = (DATABASE_TABLES) getActivity().getIntent().getSerializableExtra(MainActivity.DATABASE_TABLE);
        switch (type) {
            case MEMORY:
                getActivity().setTitle("Memory");
                break;
            case PROMISE:

                getActivity().setTitle("Promise");
                break;
            case REASSURE:
                getActivity().setTitle("Reassurance");
                break;
            default:
                getActivity().setTitle("Memory");
                break;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_list_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);

        String column;
        switch (type) {
            case MEMORY:
                column = MemoryContract.COL_MEMORY;

                break;
            case PROMISE:
                column = PromiseContract.COL_PROMISE;

                break;
            case REASSURE:
                column = ReassureContract.COL_REASSURE;

                break;
            default:
                column = MemoryContract.COL_MEMORY;
                break;
        }

        mAdapter = new ListCursorAdapter(getContext(), null, 0, column);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri    uri;
        String sortOrder;
        switch (type) {
            case MEMORY:
                uri = MemoryContract.buildUri();
                sortOrder = MemoryContract.COL_SERVER_ROW_ID;
                break;
            case PROMISE:
                uri = PromiseContract.buildUri();
                sortOrder = PromiseContract.COL_SERVER_ROW_ID;
                break;
            case REASSURE:
                uri = ReassureContract.buildUri();
                sortOrder = ReassureContract.COL_SERVER_ROW_ID;
                break;
            default:
                uri = MemoryContract.buildUri();
                sortOrder = MemoryContract.COL_SERVER_ROW_ID;
                break;
        }
        return new CursorLoader(getContext(),
                uri, // build uri
                null, // ALL_COLUMNS
                null, // select where
                null, // select where ARGS
                sortOrder + " DESC LIMIT " + LIMIT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public enum DATABASE_TABLES {
        MEMORY,
        PROMISE,
        REASSURE
    }

}
