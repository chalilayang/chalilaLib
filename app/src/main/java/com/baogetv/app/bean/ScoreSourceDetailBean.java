package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/26.
 */

public class ScoreSourceDetailBean {

    /**
     * id : 1
     * title : 登录
     * score : 10
     * max_score : 10
     * add_time : 2017-10-24 22:45:33
     * status : 1
     */

    private String id;
    private String title;
    private String score;
    private String max_score;
    private String add_time;
    private String status;

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMax_score() {
        return max_score;
    }

    public void setMax_score(String max_score) {
        this.max_score = max_score;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
