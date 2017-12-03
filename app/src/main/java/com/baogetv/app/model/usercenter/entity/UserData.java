package com.baogetv.app.model.usercenter.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class UserData implements Parcelable {
    private int userId;
    private int grage;
    private String iconUrl;
    private String nickName;
    private String desc;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getGrage() {
        return grage;
    }

    public void setGrage(int grage) {
        this.grage = grage;
    }
    public void setGrage(String grage) {
        this.grage = Integer.parseInt(grage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeInt(this.grage);
        dest.writeString(this.iconUrl);
        dest.writeString(this.nickName);
        dest.writeString(this.desc);
    }

    public UserData() {
    }

    protected UserData(Parcel in) {
        this.userId = in.readInt();
        this.grage = in.readInt();
        this.iconUrl = in.readString();
        this.nickName = in.readString();
        this.desc = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
