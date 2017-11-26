package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/26.
 */

public class ScoreSourceBean {

    /**
     * id : 2
     * title : 评论
     * score : 3
     * max_score : 12
     * status : 1
     * add_time : 2017-10-25 00:08:28
     */

    private String id;
    private String title;
    private String score;
    private String max_score;
    private String status;
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
