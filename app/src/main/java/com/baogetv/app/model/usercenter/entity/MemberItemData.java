package com.baogetv.app.model.usercenter.entity;

import android.os.Parcel;

import com.baogetv.app.parcelables.PageItemData;

/**
 * Created by chalilayang on 2017/11/29.
 */

public class MemberItemData extends PageItemData {
    private String memberId;

    public MemberItemData(String s, int t, String cid) {
        super(s, t);
        memberId = cid;
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.memberId);
    }

    protected MemberItemData(Parcel in) {
        super(in);
        this.memberId = in.readString();
    }

    public static final Creator<MemberItemData> CREATOR = new Creator<MemberItemData>() {
        @Override
        public MemberItemData createFromParcel(Parcel source) {
            return new MemberItemData(source);
        }

        @Override
        public MemberItemData[] newArray(int size) {
            return new MemberItemData[size];
        }
    };
}
