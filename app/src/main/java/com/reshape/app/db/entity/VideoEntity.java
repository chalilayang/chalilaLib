package com.reshape.app.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by chalilayang on 2017/10/19.
 */
@Entity
public class VideoEntity {
    @Id
    private Long id;
    @Property(nameInDb = "VIDEONAME")
    private String videoName;
    @Property(nameInDb = "COVER_LOCALE")
    private String cover;
    @Generated(hash = 268961616)
    public VideoEntity(Long id, String videoName, String cover) {
        this.id = id;
        this.videoName = videoName;
        this.cover = cover;
    }
    @Generated(hash = 1984976152)
    public VideoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideoName() {
        return this.videoName;
    }
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
}
