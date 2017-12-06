package com.baogetv.app.model.search;

import android.os.Parcel;

import com.baogetv.app.parcelables.PageItemData;

/**
 * Created by chalilayang on 2017/11/29.
 */

public class SearchItemData extends PageItemData {
    private String searchKey;

    public SearchItemData(String s, int t, String cid) {
        super(s, t);
        searchKey = cid;
    }

    public String getSearchKey() {
        return searchKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.searchKey);
    }

    protected SearchItemData(Parcel in) {
        super(in);
        this.searchKey = in.readString();
    }

    public static final Creator<SearchItemData> CREATOR = new Creator<SearchItemData>() {
        @Override
        public SearchItemData createFromParcel(Parcel source) {
            return new SearchItemData(source);
        }

        @Override
        public SearchItemData[] newArray(int size) {
            return new SearchItemData[size];
        }
    };
}
