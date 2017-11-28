package com.baogetv.app.model.channel.entity;

import android.os.Parcel;

import com.baogetv.app.parcelables.PageItemData;

/**
 * Created by chalilayang on 2017/11/29.
 */

public class ChannelItemData extends PageItemData {
    private String channelId;

    public ChannelItemData(String s, int t, String cid) {
        super(s, t);
        channelId = cid;
    }

    public String getChannelId() {
        return channelId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.channelId);
    }

    protected ChannelItemData(Parcel in) {
        super(in);
        this.channelId = in.readString();
    }

    public static final Creator<ChannelItemData> CREATOR = new Creator<ChannelItemData>() {
        @Override
        public ChannelItemData createFromParcel(Parcel source) {
            return new ChannelItemData(source);
        }

        @Override
        public ChannelItemData[] newArray(int size) {
            return new ChannelItemData[size];
        }
    };
}
