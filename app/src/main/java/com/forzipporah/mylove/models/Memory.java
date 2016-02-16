package com.forzipporah.mylove.models;


import android.content.ContentValues;

import com.forzipporah.mylove.database.contracts.MemoryContract;
import com.forzipporah.mylove.helpers.http.JSONParse;

import java.util.ArrayList;

public class Memory {

    private long   id;
    private String serverId;
    private String memory;

    public Memory() {
    }

    public Memory(String serverId, String memory) {
        this.serverId = serverId;
        this.memory = memory;
    }

    public Memory(long id, String serverId, String memory) {
        this.id = id;
        this.serverId = serverId;
        this.memory = memory;
    }


    public static ContentValues[] createContentValuesArrayFromJSON(String json) {
        ArrayList<Memory> memories = JSONParse.parseJSONToMemories(json);
        ContentValues[]   values   = new ContentValues[memories.size()];
        for (int i = 0; i < memories.size(); i++) {
            values[i] = memories.get(i).createContentValues();
        }
        return values;
    }

    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(MemoryContract.COL_SERVER_ROW_ID, this.getServerId());
        values.put(MemoryContract.COL_MEMORY, this.getMemory());
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

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
