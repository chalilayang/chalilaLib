package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class ImageUploadBean {
    /**
     * name : QQ截图20161215170425.png
     * type : image/png
     * size : 21114
     * key : pic
     * ext : png
     * md5 : 2d0ef89a75042f52210be3f111fbbc83
     * sha1 : 957820b04576052f3f69a48424d7326a644a6f8d
     * savename : 585a18e39cf8d.png
     * savepath : 2016-12-21/
     * path : http://localhost/test2/Uploads/Picture/2016-12-21/585a18e39cf8d.png
     * id : 7
     * url": "",
     * status: 1,
     " create_time": "1482299555"
     */

    private String name;
    private String type;
    private int size;
    private String key;
    private String ext;
    private String md5;
    private String sha1;
    private String savename;
    private String savepath;
    private String path;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSavename() {
        return savename;
    }

    public void setSavename(String savename) {
        this.savename = savename;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String url;
    private String status;
    private String create_time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
