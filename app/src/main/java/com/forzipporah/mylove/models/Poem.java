package com.forzipporah.mylove.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.helpers.http.JSONParse;

import java.util.ArrayList;


public class Poem implements Parcelable {
    public static final String        FALSE   = "false";
    public static final String        TRUE    = "true";
    public static final Creator<Poem> CREATOR = new Creator<Poem>() {
        @Override
        public Poem createFromParcel(Parcel in) {
            return new Poem(in);
        }

        @Override
        public Poem[] newArray(int size) {
            return new Poem[size];
        }
    };
    private long   id;
    private String serverId;
    private String name;
    private String poem;
    private String favourite;
    private String date;
    private String author;

    public Poem(String serverId, String name, String poem, String favourite, String date, String author) {
        this.id = 0;
        this.serverId = serverId;
        this.name = name;
        this.poem = poem;
        this.favourite = favourite;
        this.date = date;
        this.author = author;
    }


    public Poem(String serverId, String name, String poem, String favourite, String date, String author, long rowId) {
        this.serverId = serverId;
        this.name = name;
        this.poem = poem;
        this.favourite = favourite;
        this.date = date;
        this.author = author;
        this.id = rowId;
    }

    protected Poem(Parcel in) {
        id = in.readLong();
        name = in.readString();
        poem = in.readString();
        favourite = in.readString();
        date = in.readString();
        author = in.readString();
        serverId = in.readString();
    }

    public static ContentValues[] createContentValuesArrayFromJSON(String json) {
        ArrayList<Poem> poems  = JSONParse.parseJSONToPoems(json);
        ContentValues[] values = new ContentValues[poems.size()];
        for (int i = 0; i < poems.size(); i++) {
            values[i] = poems.get(i).createContentValues();
        }
        return values;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(poem);
        dest.writeString(favourite);
        dest.writeString(date);
        dest.writeString(author);
        dest.writeString(serverId);
    }

    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(PoemContract.COL_SERVER_ROW_ID, serverId);
        values.put(PoemContract.COL_POEM_NAME, name);
        values.put(PoemContract.COL_POEM, poem);
        values.put(PoemContract.COL_FAVOURITE, favourite);
        values.put(PoemContract.COL_DATE, date);
        values.put(PoemContract.COL_AUTHOR, author);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoem() {
        return poem;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
