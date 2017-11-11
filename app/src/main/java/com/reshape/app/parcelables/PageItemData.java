package com.reshape.app.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/10/21.
 */

public class PageItemData implements Parcelable {
    private final String title;
    private final int type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.type);
    }

    public PageItemData(String s, int t) {
        this.title = s;
        this.type = t;
    }


    protected PageItemData(Parcel in) {
        this.title = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<PageItemData> CREATOR = new Creator<PageItemData>() {
        @Override
        public PageItemData createFromParcel(Parcel source) {
            return new PageItemData(source);
        }

        @Override
        public PageItemData[] newArray(int size) {
            return new PageItemData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }
}
