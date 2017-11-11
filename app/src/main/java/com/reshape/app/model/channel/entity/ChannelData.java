package com.reshape.app.model.channel.entity;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class ChannelData {
    public final String iConUrl;
    public final String channelTitle;
    public final int videoCount;
    public final long updateTime;
    public final String description;
    public ChannelData(String url, String title, int count, long time, String desc) {
        this.iConUrl = url;
        this.channelTitle = title;
        this.videoCount = count;
        this.updateTime = time;
        this.description = desc;
    }
}