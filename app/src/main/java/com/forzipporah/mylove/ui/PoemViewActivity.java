package com.forzipporah.mylove.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.database.DatabaseAsyncOperation;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.fragments.poem.PoemFragment;
import com.forzipporah.mylove.models.Poem;

public class PoemViewActivity extends AppCompatActivity implements DatabaseAsyncOperation.OperationComplete {

    private boolean isFavourited;
    private Poem    poemObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_view);

        Intent intent = getIntent();
        if (intent.hasExtra(PoemFragment.POEM_EXTRA)) {
            poemObj = intent.getParcelableExtra(PoemFragment.POEM_EXTRA);
            TextView name = (TextView) findViewById(R.id.text);
            TextView author = (TextView) findViewById(R.id.tvAuthor);
            TextView date = (TextView) findViewById(R.id.tvDate);
            TextView poem = (TextView) findViewById(R.id.tvPoem);

            name.setText(poemObj.getName());
            author.setText(poemObj.getAuthor());
            date.setText(poemObj.getDate());
            poem.setText(poemObj.getPoem());
            isFavourited = Boolean.parseBoolean(poemObj.getFavourite());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_poem, menu);
        if (isFavourited) {
            MenuItem item = menu.findItem(R.id.action_favourite);
            item.setIcon(R.drawable.fav);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite:
                // update the status of whether a poem is favourited in the database
                if (isFavourited) {
                    poemObj.setFavourite(Poem.FALSE);
                    isFavourited = false;
                    item.setIcon(R.drawable.unfav);
                } else {
                    poemObj.setFavourite(Poem.TRUE);
                    isFavourited = true;
                    item.setIcon(R.drawable.fav);
                }
                updateFavouriteStatus();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateFavouriteStatus() {
        DatabaseAsyncOperation databaseAsyncOperation = new DatabaseAsyncOperation(getContentResolver(), this);
        databaseAsyncOperation.startUpdate(DatabaseAsyncOperation.UPDATE_CODE, null,
                PoemContract.buildSingleUri(),
                poemObj.createContentValues(),
                PoemContract._ID + " = ?",
                new String[]{
                        poemObj.getId() + ""
                }
        );
    }

    @Override
    public void completed(Object... objects) {
        int token = (int) objects[0];
        switch (token) {
            case DatabaseAsyncOperation.UPDATE_CODE:
                int result = (int) objects[2];
                if (result == 1) {
                    if (isFavourited) {
                        Toast.makeText(PoemViewActivity.this, "Favourited", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PoemViewActivity.this, "Un-Favourited", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
