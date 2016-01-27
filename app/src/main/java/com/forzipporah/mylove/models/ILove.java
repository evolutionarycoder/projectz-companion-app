package com.forzipporah.mylove.models;


import android.content.ContentValues;

import com.forzipporah.mylove.database.contracts.ILoveContract;

public class ILove {
    private long   id;
    private String serverId;
    private String ilove;

    public ILove() {
    }


    public ILove(String serverId, String ilove) {
        this.serverId = serverId;
        this.ilove = ilove;
    }

    public ILove(long id, String serverId, String ilove) {
        this.id = id;
        this.serverId = serverId;
        this.ilove = ilove;
    }

    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(ILoveContract.SERVER_ROW_ID, this.getServerId());
        values.put(ILoveContract.COL_ILOVE, this.getIlove());
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

    public String getIlove() {
        return ilove;
    }

    public void setIlove(String ilove) {
        this.ilove = ilove;
    }
}
