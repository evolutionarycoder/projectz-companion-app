package com.forzipporah.mylove.ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.contracts.ILoveContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class ILoveActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {
    private static int LIMIT           = 10;
    private static int INCREMENT_BY    = 10;
    private final  int ILOVE_LOADER_ID = 0;
    private ListView            iloveLV;
    private SimpleCursorAdapter mCursorAdapter;
    private int                 preLast;

    public ILoveActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ilove, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iloveLV = (ListView) view.findViewById(R.id.iloveLV);
        iloveLV.setOnScrollListener(this);
        String from[] = {
                ILoveContract.COL_ILOVE
        };
        int[] to = {
                R.id.iloveTextView
        };
        mCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.ilove_item, null, from, to, 0);
        iloveLV.setAdapter(mCursorAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ILOVE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getBaseContext(),
                ILoveContract.buildUri(),
                null,
                null,
                null,
                ILoveContract.SERVER_ROW_ID + " DESC LIMIT " + LIMIT
        );
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            if (preLast != lastItem) { //to avoid multiple calls for last item
                LIMIT += INCREMENT_BY;
                getLoaderManager().restartLoader(ILOVE_LOADER_ID, null, this);
                preLast = lastItem;
            }
        }
    }
}
