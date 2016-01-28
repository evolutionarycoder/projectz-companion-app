package com.forzipporah.mylove.models;


import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.forzipporah.mylove.database.contracts.PositiveLogContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PositiveLog implements Parcelable {
    public static final Creator<PositiveLog> CREATOR = new Creator<PositiveLog>() {
        @Override
        public PositiveLog createFromParcel(Parcel in) {
            return new PositiveLog(in);
        }

        @Override
        public PositiveLog[] newArray(int size) {
            return new PositiveLog[size];
        }
    };
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

    protected PositiveLog(Parcel in) {
        mId = in.readLong();
        mPositiveFor = in.readString();
        mDate = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mPositiveFor);
        dest.writeString(mDate);
    }
}
