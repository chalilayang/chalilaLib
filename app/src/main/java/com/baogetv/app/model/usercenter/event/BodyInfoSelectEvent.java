package com.baogetv.app.model.usercenter.event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/30.
 */

public class BodyInfoSelectEvent implements Parcelable {
    public final int height;
    public final int weight;
    public final int bodyFat;
    public BodyInfoSelectEvent(int h, int w, int b) {
        height = h;
        weight = w;
        bodyFat = b;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeInt(this.weight);
        dest.writeInt(this.bodyFat);
    }

    protected BodyInfoSelectEvent(Parcel in) {
        this.height = in.readInt();
        this.weight = in.readInt();
        this.bodyFat = in.readInt();
    }

    public static final Creator<BodyInfoSelectEvent> CREATOR = new Creator<BodyInfoSelectEvent>() {
        @Override
        public BodyInfoSelectEvent createFromParcel(Parcel source) {
            return new BodyInfoSelectEvent(source);
        }

        @Override
        public BodyInfoSelectEvent[] newArray(int size) {
            return new BodyInfoSelectEvent[size];
        }
    };
}
