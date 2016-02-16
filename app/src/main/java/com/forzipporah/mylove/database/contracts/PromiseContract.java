package com.forzipporah.mylove.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.forzipporah.mylove.database.DatabaseProvider;

/**
 * Created by evolutionarycoder on 2/16/16.
 */
public class PromiseContract implements BaseColumns {
    public static final String TABLE_NAME = "promise";
    public static final String BASE_PATH  = "promise";


    public static final String COL_SERVER_ROW_ID = "server_id";
    public static final String COL_PROMISE       = "promise";

    public static final String CREATE_PROMISE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COL_SERVER_ROW_ID + " INTEGER NOT NULL, " +
            COL_PROMISE + " TEXT NOT NULL " +
            ")";

    public static final String DROP_PROMISE_TABLE = "DROP TABLE " + TABLE_NAME;

    public static final String[] ALL_COLUMNS = {
            _ID,
            COL_SERVER_ROW_ID,
            COL_PROMISE
    };

    public static Uri buildUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH).build();
    }

    public static Uri buildSingleUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH + "/0").build();
    }
}
