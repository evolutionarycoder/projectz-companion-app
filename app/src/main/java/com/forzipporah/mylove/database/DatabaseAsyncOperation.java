package com.forzipporah.mylove.database;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by prince on 1/24/16.
 */
public class DatabaseAsyncOperation extends AsyncQueryHandler {

    public static final int QUERY_CODE  = 0;
    public static final int INSERT_CODE = 1;
    public static final int UPDATE_CODE = 2;
    public static final int DELETE_CODE = 3;

    private OperationComplete callback;

    public DatabaseAsyncOperation(ContentResolver cr, OperationComplete operationComplete) {
        super(cr);
        callback = operationComplete;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        callback.completed(token, cookie, cursor);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        callback.completed(token, cookie, uri);
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        callback.completed(token, cookie, result);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        callback.completed(token, cookie, result);
    }

    public interface OperationComplete {
        void completed(Object... objects);
    }
}
