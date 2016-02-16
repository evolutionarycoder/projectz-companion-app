package com.forzipporah.mylove.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.forzipporah.mylove.database.DatabaseProvider;


public class ReassureContract implements BaseColumns {
    public static final String TABLE_NAME = "reassurance";
    public static final String BASE_PATH  = "reassurance";


    public static final String SERVER_ROW_ID = "server_id";
    public static final String COL_REASSURE  = "reassure";

    public static final String CREATE_REASSURANCE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            SERVER_ROW_ID + " INTEGER NOT NULL, " +
            COL_REASSURE + " TEXT NOT NULL " +
            ")";

    public static final String DROP_REASSURE_TABLE = "DROP TABLE " + TABLE_NAME;

    public static final String[] ALL_COLUMNS = {
            _ID,
            SERVER_ROW_ID,
            COL_REASSURE
    };

    public static Uri buildUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH).build();
    }

    public static Uri buildSingleUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH + "/0").build();
    }
}
