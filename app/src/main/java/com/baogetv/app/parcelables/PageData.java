package com.baogetv.app.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/10/21.
 */

public class PageData implements Parcelable {
    private List<PageItemData> itemDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.itemDataList);
    }

    public PageData(List<PageItemData> list) {
        if (list != null && list.size() > 0) {
            itemDataList = new ArrayList<>();
            itemDataList.addAll(list);
        }
    }

    public List<PageItemData> getItemDataList() {
        if (itemDataList != null) {
            List<PageItemData> list = new ArrayList<>();
            list.addAll(itemDataList);
            return list;
        } else {
            return null;
        }
    }

    public int getCount() {
        if (itemDataList != null) {
            return itemDataList.size();
        } else {
            return 0;
        }
    }
    protected PageData(Parcel in) {
        this.itemDataList = in.createTypedArrayList(PageItemData.CREATOR);
    }

    public static final Creator<PageData> CREATOR = new Creator<PageData>() {
        @Override
        public PageData createFromParcel(Parcel source) {
            return new PageData(source);
        }

        @Override
        public PageData[] newArray(int size) {
            return new PageData[size];
        }
    };
}
