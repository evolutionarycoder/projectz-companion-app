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
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.database.contracts.PositiveLogContract;


public class DatabaseProvider extends ContentProvider {
    public static final  UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String     AUTHORITY    = "com.forzipporah.mylove.database.DatabaseProvider";
    private static final String     BASE_PATH    = "table";
    public static final  Uri        CONTENT_URI  = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // recognized URI's
    // all poems
    private static final int POEMS   = 100;
    private static final int POEM_ID = 101;

    private static final int LOVEABOUTS   = 201;
    private static final int LOVEABOUT_ID = 202;

    private static final int POSITIVELOGS   = 301;
    private static final int POSITIVELOG_ID = 302;

    static {
        // for poems table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PoemContract.BASE_PATH, POEMS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PoemContract.BASE_PATH + "/#", POEM_ID);

        // for I love about table
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ILoveContract.BASE_PATH, LOVEABOUTS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + ILoveContract.BASE_PATH + "/#", LOVEABOUT_ID);

        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PositiveLogContract.BASE_PATH, POSITIVELOGS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/" + PositiveLogContract.BASE_PATH + "/#", POSITIVELOG_ID);
    }

    private Database mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = new Database(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor         c        = null;
        SQLiteDatabase database = mDatabase.getReadableDatabase();
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
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        Uri            insertUri;
        long           id;
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
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        insertUri = Uri.parse(PoemContract.BASE_PATH + "/" + id);
        getContext().getContentResolver().notifyChange(uri, null);
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        int            affected = 0;
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

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDatabase.getWritableDatabase();
        int            affected = 0;
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

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int            numInserted = 0;
        SQLiteDatabase sqlDB;
        int            uriType     = sURI_MATCHER.match(uri);
        switch (uriType) {
            case POEMS:
                sqlDB = new Database(getContext()).getWritableDatabase();
                sqlDB.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = sqlDB.insertOrThrow(PoemContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    sqlDB.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    sqlDB.endTransaction();
                }
                break;

            case LOVEABOUTS:
                sqlDB = new Database(getContext()).getWritableDatabase();
                sqlDB.beginTransaction();
                try {
                    for (ContentValues cv : values) {
                        long newID = sqlDB.insertOrThrow(ILoveContract.TABLE_NAME, null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    sqlDB.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    sqlDB.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("UNKNOWN URI " + uri);
        }
        return numInserted;
    }
}
