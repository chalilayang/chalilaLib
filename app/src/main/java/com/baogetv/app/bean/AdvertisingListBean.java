package com.baogetv.app.bean;

import java.util.List;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class AdvertisingListBean {

    /**
     * id : 1
     * type_id : 3
     * title : 文字广告1
     * pic_url : /test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * url : www.baidu.com
     * sort : 0
     * status : 1
     * add_time : 2017-11-07 22:33:27
     * status_text : 正常
     * type_id_text : 文字广告
     * pic : 1
     */

    private String id;
    private String type_id;
    private String title;
    private String pic_url;
    private String url;
    private String sort;
    private String status;
    private String add_time;
    private String status_text;
    private String type_id_text;
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getType_id_text() {
        return type_id_text;
    }

    public void setType_id_text(String type_id_text) {
        this.type_id_text = type_id_text;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
