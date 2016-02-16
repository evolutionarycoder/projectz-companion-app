package com.forzipporah.mylove.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.forzipporah.mylove.database.contracts.ILoveContract;
import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;
import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.database.contracts.ReassureContract;


public class DatabaseProvider extends ContentProvider {
    public static final  UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String     AUTHORITY    = "com.forzipporah.mylove.database.DatabaseProvider";
    private static final String     BASE_PATH    = "table";
    public static final  Uri        CONTENT_URI  = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    // recognized URI's
    // all poems
    private static final int        POEMS        = 100;
    private static final int        POEM_ID      = 101;

    private static final int LOVEABOUTS   = 201;
    private static final int LOVEABOUT_ID = 202;

    private static final int POSITIVELOGS   = 301;
    private static final int POSITIVELOG_ID = 302;

    private static final int MEMORIES  = 401;
    private static final int MEMORY_ID = 402;

    private static final int PROMISES   = 501;
    private static final int PROMISE_ID = 502;

    private static final int REASSURANCES = 601;
    private static final int REASSURE_ID  = 602;

    static {
        // for poems table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PoemContract.BASE_PATH, POEMS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PoemContract.BASE_PATH + "/#", POEM_ID);

        // for I love about table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ILoveContract.BASE_PATH, LOVEABOUTS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ILoveContract.BASE_PATH + "/#", LOVEABOUT_ID);

        // positive log table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PositiveLogContract.BASE_PATH, POSITIVELOGS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PositiveLogContract.BASE_PATH + "/#", POSITIVELOG_ID);

        // memory table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + MemoryContract.BASE_PATH, MEMORIES);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + MemoryContract.BASE_PATH + "/#", MEMORY_ID);

        // promise table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PromiseContract.BASE_PATH, PROMISES);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PromiseContract.BASE_PATH + "/#", PROMISE_ID);

        // reassure table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ReassureContract.BASE_PATH, REASSURANCES);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ReassureContract.BASE_PATH + "/#", REASSURE_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        Database Database = new Database(getContext());
        database = Database.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        // match URI's and return proper data
        switch (sURI_MATCHER.match(uri)) {
            case POEMS:
                c = database.query(PoemContract.TABLE_NAME,
                        PoemContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case POEM_ID:
                c = database.query(PoemContract.TABLE_NAME,
                        PoemContract.ALL_COLUMNS,
                        PoemContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;

            case LOVEABOUTS:
                c = database.query(ILoveContract.TABLE_NAME,
                        ILoveContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case LOVEABOUT_ID:
                c = database.query(ILoveContract.TABLE_NAME,
                        ILoveContract.ALL_COLUMNS,
                        ILoveContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;

            case POSITIVELOGS:
                c = database.query(PositiveLogContract.TABLE_NAME,
                        PositiveLogContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case POSITIVELOG_ID:
                c = database.query(PositiveLogContract.TABLE_NAME,
                        PositiveLogContract.ALL_COLUMNS,
                        PositiveLogContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;

            case PROMISES:
                c = database.query(PromiseContract.TABLE_NAME,
                        PromiseContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case PROMISE_ID:
                c = database.query(PromiseContract.TABLE_NAME,
                        PromiseContract.ALL_COLUMNS,
                        PromiseContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;

            case MEMORIES:
                c = database.query(MemoryContract.TABLE_NAME,
                        MemoryContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case MEMORY_ID:
                c = database.query(MemoryContract.TABLE_NAME,
                        MemoryContract.ALL_COLUMNS,
                        MemoryContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;

            case REASSURANCES:
                c = database.query(ReassureContract.TABLE_NAME,
                        ReassureContract.ALL_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case REASSURE_ID:
                c = database.query(ReassureContract.TABLE_NAME,
                        ReassureContract.ALL_COLUMNS,
                        ReassureContract._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // listen for changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri  insertUri;
        long id;
        switch (sURI_MATCHER.match(uri)) {
            case POEMS:
                id = database.insertOrThrow(PoemContract.TABLE_NAME, null, values);
                break;

            case LOVEABOUTS:
                id = database.insertOrThrow(ILoveContract.TABLE_NAME, null, values);
                break;

            case POSITIVELOGS:
                id = database.insertOrThrow(PositiveLogContract.TABLE_NAME, null, values);
                break;

            case PROMISES:
                id = database.insertOrThrow(PromiseContract.TABLE_NAME, null, values);
                break;

            case MEMORIES:
                id = database.insertOrThrow(MemoryContract.TABLE_NAME, null, values);
                break;

            case REASSURANCES:
                id = database.insertOrThrow(ReassureContract.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        insertUri = Uri.parse(PoemContract.BASE_PATH + "/" + id);
        getContext().getContentResolver().notifyChange(uri, null);
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int affected = 0;
        switch (sURI_MATCHER.match(uri)) {
            case POEM_ID:
                affected = database.delete(PoemContract.TABLE_NAME,
                        PoemContract._ID + " = ?",
                        selectionArgs);
                break;
            case LOVEABOUT_ID:
                affected = database.delete(ILoveContract.TABLE_NAME,
                        ILoveContract._ID + " = ?",
                        selectionArgs);
                break;
            case POSITIVELOG_ID:
                affected = database.delete(PositiveLogContract.TABLE_NAME,
                        PositiveLogContract._ID + " = ?",
                        selectionArgs);
                break;
            case PROMISE_ID:
                affected = database.delete(PromiseContract.TABLE_NAME,
                        PromiseContract._ID + " = ?",
                        selectionArgs);
                break;
            case MEMORY_ID:
                affected = database.delete(MemoryContract.TABLE_NAME,
                        MemoryContract._ID + " = ?",
                        selectionArgs);
                break;
            case REASSURE_ID:
                affected = database.delete(ReassureContract.TABLE_NAME,
                        ReassureContract._ID + " = ?",
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int affected = 0;
        switch (sURI_MATCHER.match(uri)) {
            case POEM_ID:
                affected = database.update(
                        PoemContract.TABLE_NAME,
                        values,
                        PoemContract._ID + " = ?",
                        selectionArgs
                );
                break;
            case LOVEABOUT_ID:
                affected = database.update(
                        ILoveContract.TABLE_NAME,
                        values,
                        ILoveContract._ID + " = ?",
                        selectionArgs
                );
                break;

            case POSITIVELOG_ID:
                affected = database.update(
                        PositiveLogContract.TABLE_NAME,
                        values,
                        PositiveLogContract._ID + " = ?",
                        selectionArgs
                );
                break;

            case PROMISES:
                affected = database.update(
                        PromiseContract.TABLE_NAME,
                        values,
                        PoemContract._ID + " = ?",
                        selectionArgs
                );
                break;
            case MEMORIES:
                affected = database.update(
                        MemoryContract.TABLE_NAME,
                        values,
                        ILoveContract._ID + " = ?",
                        selectionArgs
                );
                break;

            case REASSURANCES:
                affected = database.update(
                        ReassureContract.TABLE_NAME,
                        values,
                        PositiveLogContract._ID + " = ?",
                        selectionArgs
                );
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        int uriType     = sURI_MATCHER.match(uri);
        switch (uriType) {
            case POEMS:
                database.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = database.insertOrThrow(PoemContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;

            case LOVEABOUTS:
                database.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = database.insertOrThrow(ILoveContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;

            case MEMORIES:
                database.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = database.insertOrThrow(MemoryContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;

            case PROMISES:
                database.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = database.insertOrThrow(PromiseContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;

            case REASSURANCES:
                database.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = database.insertOrThrow(ReassureContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("UNKNOWN URI " + uri);
        }
        return numInserted;
    }
}
