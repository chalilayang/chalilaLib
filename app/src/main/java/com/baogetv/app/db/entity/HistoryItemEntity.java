package com.baogetv.app.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by chalilayang on 2017/10/19.
 */
@Entity
public class HistoryItemEntity {
    @Id
    private Long id;
    @Property
    private String historyId;
    @Property
    private String videoId;
    @Property
    private String videoTitle;
    @Property
    private String picUrl;
    @Property
    private String addTime;
    @Generated(hash = 1796552931)
    public HistoryItemEntity(Long id, String historyId, String videoId, String videoTitle, String picUrl,
            String addTime) {
        this.id = id;
        this.historyId = historyId;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.picUrl = picUrl;
        this.addTime = addTime;
    }
    public HistoryItemEntity(String historyId, String videoId, String videoTitle, String picUrl,
                             String addTime) {
        this.historyId = historyId;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.picUrl = picUrl;
        this.addTime = addTime;
    }
    @Generated(hash = 1465563974)
    public HistoryItemEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHistoryId() {
        return this.historyId;
    }
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }
    public String getVideoId() {
        return this.videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getVideoTitle() {
        return this.videoTitle;
    }
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
    public String getPicUrl() {
        return this.picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getAddTime() {
        return this.addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
