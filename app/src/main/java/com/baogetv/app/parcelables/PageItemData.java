package com.baogetv.app.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/10/21.
 */

public class PageItemData implements Parcelable {
    public static final int TYPE_ALL_VIDEO = 1234;
    public static final int TYPE_RANK_VIDEO = 1235;
    public static final int TYPE_RANK_VIDEO_WEEK = 1236;
    public static final int TYPE_RANK_VIDEO_MONTH = 1237;

    public static final int TYPE_SEARCH_RELATIVE = 1238;
    public static final int TYPE_SEARCH_PLAY_MOST = 1239;
    public static final int TYPE_SEARCH_LATEST_PUBLISH = 1240;

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
