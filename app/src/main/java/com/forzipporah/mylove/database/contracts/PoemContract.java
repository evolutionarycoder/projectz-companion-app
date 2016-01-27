package com.forzipporah.mylove.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.forzipporah.mylove.database.DatabaseProvider;

/**
 * Created by prince on 1/24/16.
 */
public class PoemContract implements BaseColumns {

    public static final String BASE_PATH = "poems";


    public static final String TABLE_NAME = "poems";

    public static final String COL_SERVER_ROW_ID = "server_row_id";
    public static final String COL_POEM_NAME     = "name";
    public static final String COL_POEM          = "poem";
    public static final String COL_FAVOURITE     = "favourite";
    public static final String COL_DATE          = "pub_date";
    public static final String COL_AUTHOR        = "author";

    public static final String CREATE_POEM_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COL_SERVER_ROW_ID + " TEXT NOT NULL, " +
            COL_POEM_NAME + " TEXT NOT NULL, " +
            COL_POEM + " TEXT NOT NULL, " +
            COL_FAVOURITE + " TEXT NOT NULL, " +
            COL_DATE + " TEXT NOT NULL, " +
            COL_AUTHOR + " TEXT NOT NULL)";

    public static final String DROP_POEM_TABLE = "DROP TABLE " + TABLE_NAME;

    public static final String[] ALL_COLUMNS = {
            _ID,
            COL_SERVER_ROW_ID,
            COL_POEM_NAME,
            COL_POEM,
            COL_FAVOURITE,
            COL_DATE,
            COL_AUTHOR
    };


    public static Uri buildUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH).build();
    }

    public static Uri buildSingleUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH + "/0").build();
    }
}
