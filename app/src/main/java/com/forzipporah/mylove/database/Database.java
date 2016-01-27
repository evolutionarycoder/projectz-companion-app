package com.forzipporah.mylove.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.PoemContract;

/**
 * Created by prince on 1/24/16.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME    = "mylove.db";
    public static final int    DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PoemContract.CREATE_POEM_TABLE);
        db.execSQL(ILoveContract.CREATE_LOVE_ABOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PoemContract.DROP_POEM_TABLE);
        db.execSQL(ILoveContract.DROP_LOVE_ABOUT_TABLE);
    }
}
