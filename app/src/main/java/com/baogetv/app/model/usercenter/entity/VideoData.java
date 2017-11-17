package com.baogetv.app.model.usercenter.entity;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class VideoData {
    public final String iConUrl;
    public final String videoTitle;
    public final long updateTime;
    public VideoData(String url, String title, long time) {
        this.iConUrl = url;
        this.videoTitle = title;
        this.updateTime = time;
    }
}