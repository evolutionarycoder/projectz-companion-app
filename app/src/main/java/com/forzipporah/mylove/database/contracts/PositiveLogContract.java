package com.forzipporah.mylove.database.contracts;


import android.net.Uri;
import android.provider.BaseColumns;

import com.forzipporah.mylove.database.DatabaseProvider;

public class PositiveLogContract implements BaseColumns {

    public static final String BASE_PATH        = "positivelog";
    public static final String TABLE_NAME       = "positivelog";
    public static final String COL_POSITIVE_FOR = "postive_for";
    public static final String COL_DATE         = "pub_date";

    public static final String CREATE_POSITIVE_LOG_TABLE = "CREATE TABLE " + TABLE_NAME +
            "( " +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COL_POSITIVE_FOR + " TEXT NOT NULL, " +
            COL_DATE + " TEXT NOT NULL" +
            " )";

    public static final String DROP_TABLE_POSITIVE_LOG = "DROP TABLE " + CREATE_POSITIVE_LOG_TABLE;

    public static final String[] ALL_COLUMNS = {
            _ID,
            COL_POSITIVE_FOR,
            COL_DATE
    };


    public static Uri buildUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH).build();
    }

    public static Uri buildSingleUri() {
        return DatabaseProvider.CONTENT_URI.buildUpon().appendEncodedPath(BASE_PATH + "/0").build();
    }
}
