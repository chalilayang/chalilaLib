package com.chalilayang.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by chalilayang on 2017/10/19.
 */
@Entity
public class SearchItemEntity {
    @Id
    private Long id;
    @Property(nameInDb = "content")
    private String content;
    @Property(nameInDb = "time")
    private Long time;
    @Generated(hash = 162082132)
    public SearchItemEntity(Long id, String content, Long time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 757630992)
    public SearchItemEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
