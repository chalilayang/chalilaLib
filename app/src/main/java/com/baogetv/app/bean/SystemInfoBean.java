package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/12/5.
 */

public class SystemInfoBean {

    /**
     * id : 1
     * title :
     * content : aa
     * is_unread : 1
     * add_time : 2017-11-11 17:56:15
     */

    private String id;
    private String title;
    private String content;
    private String is_unread;
    private String add_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_unread() {
        return is_unread;
    }

    public void setIs_unread(String is_unread) {
        this.is_unread = is_unread;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
