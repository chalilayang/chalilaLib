package com.baogetv.app.bean;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class CommentListBean {

    /**
     * id : 1
     * video_id : 1
     * reply_id : 0
     * user_id : 1
     * reply_user_id : 0
     * content : xxs
     * likes : 0
     * status : 1
     * username : aa
     * user_pic_url :
     * reply_user_username :
     * reply_user_pic_url :
     * add_time : 2017-11-12 09:13:05
     * user_pic : 0
     * reply_user_pic : 0
     * is_like : 0
     * child : []
     */

    private String id;
    private String video_id;
    private String reply_id;
    private String user_id;
    private String reply_user_id;
    private String content;
    private String likes;
    private String status;
    private String username;
    private String user_pic_url;
    private String reply_user_username;
    private String reply_user_pic_url;
    private String add_time;
    private String user_pic;
    private int reply_user_pic;
    private String is_like;
    private List<CommentListBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReply_user_id() {
        return reply_user_id;
    }

    public void setReply_user_id(String reply_user_id) {
        this.reply_user_id = reply_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_pic_url() {
        return user_pic_url;
    }

    public void setUser_pic_url(String user_pic_url) {
        this.user_pic_url = user_pic_url;
    }

    public String getReply_user_username() {
        return reply_user_username;
    }

    public void setReply_user_username(String reply_user_username) {
        this.reply_user_username = reply_user_username;
    }

    public String getReply_user_pic_url() {
        return reply_user_pic_url;
    }

    public void setReply_user_pic_url(String reply_user_pic_url) {
        this.reply_user_pic_url = reply_user_pic_url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public int getReply_user_pic() {
        return reply_user_pic;
    }

    public void setReply_user_pic(int reply_user_pic) {
        this.reply_user_pic = reply_user_pic;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public List<CommentListBean> getChild() {
        return child;
    }

    public void setChild(List<CommentListBean> child) {
        this.child = child;
    }
}
