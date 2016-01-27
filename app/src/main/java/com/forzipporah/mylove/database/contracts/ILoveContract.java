package com.forzipporah.mylove.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.forzipporah.mylove.database.DatabaseProvider;

/**
 * Created by prince on 1/27/16.
 */
public class ILoveContract implements BaseColumns {
    public static final String TABLE_NAME = "loveabout";
    public static final String BASE_PATH  = "loveabout";


    public static final String SERVER_ROW_ID = "server_id";
    public static final String COL_ILOVE     = "ilove";

    public static final String CREATE_LOVE_ABOUT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            SERVER_ROW_ID + " TEXT NOT NULL, " +
            COL_ILOVE + " TEXT NOT NULL " +
            ")";

    public static final String DROP_LOVE_ABOUT_TABLE = "DROP TABLE " + TABLE_NAME;

    public static final String[] ALL_COLUMNS = {
            _ID,
            SERVER_ROW_ID,
            COL_ILOVE
    };

    public static Uri buildUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH).build();
    }

    public static Uri buildSingleUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH + "/0").build();
    }
}
