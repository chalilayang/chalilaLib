package com.baogetv.app.model.usercenter.event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/30.
 */

public class SexSelectEvent implements Parcelable{
    public final int sex;
    public SexSelectEvent(int s) {
        sex = s;
    }
    public SexSelectEvent(String s) {
        int t = 0;
        try {
            t = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        sex = t;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sex);
    }

    protected SexSelectEvent(Parcel in) {
        this.sex = in.readInt();
    }

    public static final Creator<SexSelectEvent> CREATOR = new Creator<SexSelectEvent>() {
        @Override
        public SexSelectEvent createFromParcel(Parcel source) {
            return new SexSelectEvent(source);
        }

        @Override
        public SexSelectEvent[] newArray(int size) {
            return new SexSelectEvent[size];
        }
    };
}
