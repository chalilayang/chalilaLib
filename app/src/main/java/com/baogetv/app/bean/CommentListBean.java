package com.baogetv.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class CommentListBean implements Parcelable {


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


    public static class DataBean implements Parcelable {

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
        private String level_id;
        private String level_name;
        private String grade;
        private String intro;

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.video_id);
            dest.writeString(this.reply_id);
            dest.writeString(this.user_id);
            dest.writeString(this.reply_user_id);
            dest.writeString(this.content);
            dest.writeString(this.likes);
            dest.writeString(this.add_time);
            dest.writeString(this.status);
            dest.writeString(this.username);
            dest.writeString(this.user_pic);
            dest.writeString(this.reply_user_username);
            dest.writeString(this.reply_user_pic);
            dest.writeString(this.user_pic_url);
            dest.writeString(this.reply_user_pic_url);
            dest.writeInt(this.reply_user_picid);
            dest.writeString(this.is_like);
            dest.writeString(this.level_id);
            dest.writeString(this.level_name);
            dest.writeString(this.grade);
            dest.writeString(this.intro);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.video_id = in.readString();
            this.reply_id = in.readString();
            this.user_id = in.readString();
            this.reply_user_id = in.readString();
            this.content = in.readString();
            this.likes = in.readString();
            this.add_time = in.readString();
            this.status = in.readString();
            this.username = in.readString();
            this.user_pic = in.readString();
            this.reply_user_username = in.readString();
            this.reply_user_pic = in.readString();
            this.user_pic_url = in.readString();
            this.reply_user_pic_url = in.readString();
            this.reply_user_picid = in.readInt();
            this.is_like = in.readString();
            this.level_id = in.readString();
            this.level_name = in.readString();
            this.grade = in.readString();
            this.intro = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.video_id);
        dest.writeString(this.reply_id);
        dest.writeString(this.user_id);
        dest.writeString(this.reply_user_id);
        dest.writeString(this.content);
        dest.writeString(this.likes);
        dest.writeString(this.add_time);
        dest.writeString(this.status);
        dest.writeString(this.username);
        dest.writeString(this.user_pic);
        dest.writeString(this.reply_user_username);
        dest.writeString(this.reply_user_pic);
        dest.writeString(this.level_id);
        dest.writeString(this.level_name);
        dest.writeString(this.grade);
        dest.writeString(this.intro);
        dest.writeString(this.user_pic_url);
        dest.writeInt(this.reply_user_picid);
        dest.writeString(this.is_like);
        dest.writeTypedList(this.child);
    }

    public CommentListBean() {
    }

    protected CommentListBean(Parcel in) {
        this.id = in.readString();
        this.video_id = in.readString();
        this.reply_id = in.readString();
        this.user_id = in.readString();
        this.reply_user_id = in.readString();
        this.content = in.readString();
        this.likes = in.readString();
        this.add_time = in.readString();
        this.status = in.readString();
        this.username = in.readString();
        this.user_pic = in.readString();
        this.reply_user_username = in.readString();
        this.reply_user_pic = in.readString();
        this.level_id = in.readString();
        this.level_name = in.readString();
        this.grade = in.readString();
        this.intro = in.readString();
        this.user_pic_url = in.readString();
        this.reply_user_picid = in.readInt();
        this.is_like = in.readString();
        this.child = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<CommentListBean> CREATOR = new Creator<CommentListBean>() {
        @Override
        public CommentListBean createFromParcel(Parcel source) {
            return new CommentListBean(source);
        }

        @Override
        public CommentListBean[] newArray(int size) {
            return new CommentListBean[size];
        }
    };
}
