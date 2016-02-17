package com.forzipporah.mylove.models;

/**
 * Created by evolutionarycoder on 2/17/16.
 */
public class Notification {
    private String id, serverId, text, synced;

    public Notification() {
    }

    public Notification(String serverId, String text, String synced) {
        this.serverId = serverId;
        this.text = text;
        this.synced = synced;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }
}
