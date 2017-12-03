package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/12/3.
 */

public class ReportTypeBean {

    /**
     * id : 2
     * name : 暴力言论
     * sort : 0
     * status : 1
     * add_time : 2017-11-11 17:45:09
     */

    private String id;
    private String name;
    private String sort;
    private String status;
    private String add_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
