package com.baogetv.app.model.videodetail.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoDetailData extends PageData {
    public int videoId;
    public VideoDetailBean videoDetailBean;

    public VideoDetailData(List<PageItemData> list, VideoDetailBean bean) {
        super(list);
        videoDetailBean = bean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.videoId);
        dest.writeParcelable(this.videoDetailBean, flags);
    }

    protected VideoDetailData(Parcel in) {
        super(in);
        this.videoId = in.readInt();
        this.videoDetailBean = in.readParcelable(VideoDetailBean.class.getClassLoader());
    }

    public static final Creator<VideoDetailData> CREATOR = new Creator<VideoDetailData>() {
        @Override
        public VideoDetailData createFromParcel(Parcel source) {
            return new VideoDetailData(source);
        }

        @Override
        public VideoDetailData[] newArray(int size) {
            return new VideoDetailData[size];
        }
    };
}
