package com.baogetv.app.bean;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class CommentListBean {


    /**
     * id : 3
     * video_id : 2
     * reply_id : 0
     * user_id : 3
     * reply_user_id : 0
     * content : xsw
     * likes : 0
     * add_time : 1510449380
     * status : 1
     * username : 15913196454
     * user_pic : 1
     * reply_user_username :
     * reply_user_pic :
     * level_id : 1
     * level_name : LV1
     * grade : 3
     * intro :
     * user_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-20
     * /5a127d675b918.jpg
     * reply_user_picid : 0
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
    private String add_time;
    private String status;
    private String username;
    private String user_pic;
    private String reply_user_username;
    private String reply_user_pic;
    private String level_id;
    private String level_name;
    private String grade;
    private String intro;
    private String user_pic_url;
    private int reply_user_picid;
    private String is_like;
    private List<DataBean> child;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getReply_user_username() {
        return reply_user_username;
    }

    public void setReply_user_username(String reply_user_username) {
        this.reply_user_username = reply_user_username;
    }

    public String getReply_user_pic() {
        return reply_user_pic;
    }

    public void setReply_user_pic(String reply_user_pic) {
        this.reply_user_pic = reply_user_pic;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUser_pic_url() {
        return user_pic_url;
    }

    public void setUser_pic_url(String user_pic_url) {
        this.user_pic_url = user_pic_url;
    }

    public int getReply_user_picid() {
        return reply_user_picid;
    }

    public void setReply_user_picid(int reply_user_picid) {
        this.reply_user_picid = reply_user_picid;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public List<DataBean> getChild() {
        return child;
    }

    public void setChild(List<DataBean> child) {
        this.child = child;
    }

    public static class DataBean {

        /**
         * id : 2
         * video_id : 2
         * reply_id : 1
         * user_id : 3
         * reply_user_id : 1
         * content : 内容
         * likes : 1
         * add_time : 1510449380
         * status : 1
         * username : 15913196454
         * user_pic : 1
         * reply_user_username : aa
         * reply_user_pic : 1
         * user_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-20
         * /5a127d675b918.jpg
         * reply_user_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-20
         * /5a127d675b918.jpg
         * reply_user_picid : 0
         * is_like : 0
         */

        private String id;
        private String video_id;
        private String reply_id;
        private String user_id;
        private String reply_user_id;
        private String content;
        private String likes;
        private String add_time;
        private String status;
        private String username;
        private String user_pic;
        private String reply_user_username;
        private String reply_user_pic;
        private String user_pic_url;
        private String reply_user_pic_url;
        private int reply_user_picid;
        private String is_like;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getReply_user_username() {
            return reply_user_username;
        }

        public void setReply_user_username(String reply_user_username) {
            this.reply_user_username = reply_user_username;
        }

        public String getReply_user_pic() {
            return reply_user_pic;
        }

        public void setReply_user_pic(String reply_user_pic) {
            this.reply_user_pic = reply_user_pic;
        }

        public String getUser_pic_url() {
            return user_pic_url;
        }

        public void setUser_pic_url(String user_pic_url) {
            this.user_pic_url = user_pic_url;
        }

        public String getReply_user_pic_url() {
            return reply_user_pic_url;
        }

        public void setReply_user_pic_url(String reply_user_pic_url) {
            this.reply_user_pic_url = reply_user_pic_url;
        }

        public int getReply_user_picid() {
            return reply_user_picid;
        }

        public void setReply_user_picid(int reply_user_picid) {
            this.reply_user_picid = reply_user_picid;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }
    }
}
