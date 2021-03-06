package com.baogetv.app.bean;

import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class VideoData implements VideoListAdapter.IVideoData {
    private final String picUrl;
    private final String title;
    private final String publishTime;
    private String playCount;
    private final String videoDuration;
    private final String videoId;
    private boolean isCHN;
    private boolean isPro;
    public VideoData(String pic, String video_title, String publish_time, String count, String duration, String videoID) {
        this.picUrl = pic;
        this.title = video_title;
        this.publishTime = publish_time;
        this.playCount = count;
        this.videoDuration = duration;
        this.videoId = videoID;
    }

    @Override
    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPublishTime() {
        return publishTime;
    }

    @Override
    public String getDuration() {
        return videoDuration;
    }

    @Override
    public String getPlayCount() {
        return playCount;
    }

    @Override
    public String getVideoID() {
        return videoId;
    }

    public void setCHN(boolean CHN) {
        isCHN = CHN;
    }

    public void setPro(boolean pro) {
        isPro = pro;
    }

    @Override
    public boolean isCHN() {
        return isCHN;
    }

    @Override
    public boolean isPro() {
        return isPro;
    }

    @Override
    public void setPlayCount(String count) {
        playCount = count;
    }
}
