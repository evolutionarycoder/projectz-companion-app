package com.forzipporah.mylove.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.database.contracts.ReassureContract;

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
        db.execSQL(MemoryContract.CREATE_MEMORY_TABLE);
        db.execSQL(PromiseContract.CREATE_PROMISE_TABLE);
        db.execSQL(ReassureContract.CREATE_REASSURANCE_TABLE);

        db.execSQL(PositiveLogContract.CREATE_POSITIVE_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PoemContract.DROP_POEM_TABLE);
        db.execSQL(ILoveContract.DROP_LOVE_ABOUT_TABLE);
        db.execSQL(MemoryContract.DROP_MEMORY_TABLE);
        db.execSQL(PromiseContract.DROP_PROMISE_TABLE);
        db.execSQL(ReassureContract.DROP_REASSURE_TABLE);

        db.execSQL(PositiveLogContract.DROP_TABLE_POSITIVE_LOG);
    }
}
