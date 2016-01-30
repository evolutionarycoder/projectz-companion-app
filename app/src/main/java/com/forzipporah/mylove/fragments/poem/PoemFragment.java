package com.forzipporah.mylove.fragments.poem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.models.Poem;
import com.forzipporah.mylove.ui.PoemViewActivity;

public class PoemFragment extends ListFragment {

    public static final String POEM_EXTRA = "com.forzipporah.mylove.fragments.poem.Poem";
    protected CursorAdapter     mCursorAdapter;
    protected RefetchTotalPoems mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainActivity = (RefetchTotalPoems) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor c = mCursorAdapter.getCursor();
        if (c.moveToPosition(position)) {
            GetPoemFromCursor poemFromCursor = new GetPoemFromCursor();
            poemFromCursor.execute(c);
        }
    }

    private void viewPoem(Poem poem) {
        Intent i = new Intent(getContext(), PoemViewActivity.class);
        i.putExtra(POEM_EXTRA, poem);
        startActivity(i);
    }


    public interface RefetchTotalPoems {
        void refetch();
    }

    protected class GetPoemFromCursor extends AsyncTask<Cursor, Void, Poem> {

        @Override
        protected Poem doInBackground(Cursor... params) {
            Cursor c  = params[0];
            long   id = c.getLong(c.getColumnIndex(PoemContract._ID));

            String serverId = c.getString(c.getColumnIndex(PoemContract.COL_SERVER_ROW_ID));
            String author   = c.getString(c.getColumnIndex(PoemContract.COL_AUTHOR));

            String date = c.getString(c.getColumnIndex(PoemContract.COL_DATE));
            String name = c.getString(c.getColumnIndex(PoemContract.COL_POEM_NAME));

            String poem      = c.getString(c.getColumnIndex(PoemContract.COL_POEM));
            String favourite = c.getString(c.getColumnIndex(PoemContract.COL_FAVOURITE));

            return new Poem(serverId, name, poem, favourite, date, author, id);
        }

        @Override
        protected void onPostExecute(Poem poem) {
            viewPoem(poem);
        }
    }

}
