package com.forzipporah.mylove.models;


import android.content.ContentValues;

import com.forzipporah.mylove.database.contracts.ReassureContract;
import com.forzipporah.mylove.helpers.http.JSONParse;

import java.util.ArrayList;

public class Reassure {

    private long   id;
    private String serverId;
    private String reassure;

    public Reassure() {
    }

    public Reassure(String serverId, String reassure) {
        this.serverId = serverId;
        this.reassure = reassure;
    }

    public Reassure(long id, String serverId, String reassure) {
        this.id = id;
        this.serverId = serverId;
        this.reassure = reassure;
    }


    public static ContentValues[] createContentValuesArrayFromJSON(String json) {
        ArrayList<Reassure> reassures = JSONParse.parseJSONToReassurances(json);
        ContentValues[]     values    = new ContentValues[reassures.size()];
        for (int i = 0; i < reassures.size(); i++) {
            values[i] = reassures.get(i).createContentValues();
        }
        return values;
    }

    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(ReassureContract.COL_SERVER_ROW_ID, this.getServerId());
        values.put(ReassureContract.COL_REASSURE, this.getReassure());
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

    public String getReassure() {
        return reassure;
    }

    public void setReassure(String reassure) {
        this.reassure = reassure;
    }
}
