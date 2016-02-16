package com.forzipporah.mylove.models;

import android.content.ContentValues;

import com.forzipporah.mylove.database.contracts.PromiseContract;
import com.forzipporah.mylove.helpers.http.JSONParse;

import java.util.ArrayList;

/**
 * Created by evolutionarycoder on 2/16/16.
 */
public class Promise {
    private long   id;
    private String serverId;
    private String promise;

    public Promise() {
    }

    public Promise(String serverId, String promise) {
        this.serverId = serverId;
        this.promise = promise;
    }


    public Promise(long id, String serverId, String promise) {
        this.id = id;
        this.serverId = serverId;
        this.promise = promise;
    }

    public static ContentValues[] createContentValuesArrayFromJSON(String json) {
        ArrayList<Promise> promises = JSONParse.parseJSONToPromises(json);
        ContentValues[]    values   = new ContentValues[promises.size()];
        for (int i = 0; i < promises.size(); i++) {
            values[i] = promises.get(i).createContentValues();
        }
        return values;
    }


    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(PromiseContract.SERVER_ROW_ID, this.getServerId());
        values.put(PromiseContract.COL_PROMISE, this.getPromise());
        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPromise() {
        return promise;
    }

    public void setPromise(String promise) {
        this.promise = promise;
    }
}
