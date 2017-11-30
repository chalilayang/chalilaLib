package com.baogetv.app.model.usercenter.event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/30.
 */

public class DateSelectEvent implements Parcelable {
    public final String year;
    public final String month;
    public final String day;
    public DateSelectEvent(String y, String m, String d) {
        year = y;
        month = m;
        day = d;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.year);
        dest.writeString(this.month);
        dest.writeString(this.day);
    }

    protected DateSelectEvent(Parcel in) {
        this.year = in.readString();
        this.month = in.readString();
        this.day = in.readString();
    }

    public static final Creator<DateSelectEvent> CREATOR = new Creator<DateSelectEvent>() {
        @Override
        public DateSelectEvent createFromParcel(Parcel source) {
            return new DateSelectEvent(source);
        }

        @Override
        public DateSelectEvent[] newArray(int size) {
            return new DateSelectEvent[size];
        }
    };
}
