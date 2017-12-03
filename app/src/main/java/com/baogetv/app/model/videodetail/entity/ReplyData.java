package com.baogetv.app.model.videodetail.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baogetv.app.model.usercenter.entity.UserData;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class ReplyData implements Parcelable {
    private UserData replyTo;
    private UserData replyer;
    private String content;

    public UserData getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(UserData replyTo) {
        this.replyTo = replyTo;
    }

    public UserData getReplyer() {
        return replyer;
    }

    public void setReplyer(UserData replyer) {
        this.replyer = replyer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.replyTo, flags);
        dest.writeParcelable(this.replyer, flags);
        dest.writeString(this.content);
    }

    public ReplyData() {
    }

    protected ReplyData(Parcel in) {
        this.replyTo = in.readParcelable(UserData.class.getClassLoader());
        this.replyer = in.readParcelable(UserData.class.getClassLoader());
        this.content = in.readString();
    }

    public static final Creator<ReplyData> CREATOR = new Creator<ReplyData>() {
        @Override
        public ReplyData createFromParcel(Parcel source) {
            return new ReplyData(source);
        }

        @Override
        public ReplyData[] newArray(int size) {
            return new ReplyData[size];
        }
    };
}
