package com.baogetv.app.model.channel.entity;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class ChannelData {
    public final String iConUrl;
    public final String channelTitle;
    public final int videoCount;
    public final String updateTime;
    public final String description;
    public String channelId;
    public ChannelData(
            String id, String url, String title, int count, String time, String desc) {
        this.channelId = id;
        this.iConUrl = url;
        this.channelTitle = title;
        this.videoCount = count;
        this.updateTime = time;
        this.description = desc;
    }
}