package com.reshape.app.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by chalilayang on 2017/10/19.
 */
@Entity
public class SearchItemEntity {
    @Property
    private String content;
    @Property
    private Long time;
    @Generated(hash = 1239644255)
    public SearchItemEntity(String content, Long time) {
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 757630992)
    public SearchItemEntity() {
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
