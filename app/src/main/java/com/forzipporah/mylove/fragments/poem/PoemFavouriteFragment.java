package com.forzipporah.mylove.fragments.poem;


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

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.adapters.ListCursorAdapter;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.models.Poem;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoemFavouriteFragment extends PoemFragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {

    private static final int FETCH_FAVOURITE_POEMS_LOADER = 0;

    public PoemFavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poem_favourite_fragment, container, false);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.poem_list_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCursorAdapter = new ListCursorAdapter(getContext(), null, 0, PoemContract.COL_POEM_NAME);
        setListAdapter(mCursorAdapter);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FETCH_FAVOURITE_POEMS_LOADER, null, this);
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
                getLoaderManager().restartLoader(FETCH_FAVOURITE_POEMS_LOADER, null, this);
                preLast = lastItem;
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                PoemContract.buildUri(), // build uri
                null, // ALL_COLUMNS
                PoemContract.COL_FAVOURITE + " = ?", // select where
                new String[]{
                        Poem.TRUE
                }, // select where ARGS
                PoemContract.COL_SERVER_ROW_ID + " DESC LIMIT " + LIMIT);
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
