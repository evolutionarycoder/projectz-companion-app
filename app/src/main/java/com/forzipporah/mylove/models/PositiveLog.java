package com.forzipporah.mylove.models;


import android.content.ContentValues;

import com.forzipporah.mylove.database.contracts.PositiveLogContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PositiveLog {
    private long   mId;
    private String mPositiveFor;
    private String mDate;

    public PositiveLog(String positiveFor) {
        this.mPositiveFor = positiveFor;
        Date             dNow = new Date();
        SimpleDateFormat ft   = new SimpleDateFormat("d MMMM y");
        mDate = ft.format(dNow);
    }

    public PositiveLog(long id, String positiveFor, String date) {
        this.mId = id;
        this.mPositiveFor = positiveFor;
        this.mDate = date;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getPositiveFor() {
        return mPositiveFor;
    }

    public void setPositiveFor(String positiveFor) {
        this.mPositiveFor = positiveFor;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(PositiveLogContract.COL_POSITIVE_FOR, mPositiveFor);
        values.put(PositiveLogContract.COL_DATE, mDate);
        return values;
    }
}
