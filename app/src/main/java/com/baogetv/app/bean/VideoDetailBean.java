package com.baogetv.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class VideoDetailBean implements Parcelable {

    /**
     * id : 1
     * title : 测试
     * intro : 我
     * pic_url : http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * file_url : http://localhost/test2/Uploads/Download/2017-11-19/5a112381bea7c.mp4
     * length : 00:00
     * is_cnword : 0
     * is_commend : 0
     * collects : 1
     * likes : 0
     * shares : 0
     * caches : 0
     * play : 0
     * comments : 0
     * add_time : 24天前
     * update_time : 24天前
     * lastplay_time :
     * channel_id : 1
     * channel_name : 频道1
     * channel_pic_url :
     * channel_update_time :
     * pic : 1
     * file : 1
     * channel_pic : 0
     * tags : []
     */

    private String id;
    private String title;
    private String intro;
    private String pic_url;
    private String file_url;
    private String length;
    private String is_cnword;
    private String is_commend;
    private String collects;
    private String likes;
    private String shares;
    private String caches;
    private String play;
    private String comments;
    private String add_time;
    private String update_time;
    private String lastplay_time;
    private String channel_id;
    private String channel_name;
    private String channel_pic_url;
    private String channel_update_time;
    private String pic;
    private String file;
    private String channel_pic;
    private List<String> tags;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getIs_cnword() {
        return is_cnword;
    }

    public void setIs_cnword(String is_cnword) {
        this.is_cnword = is_cnword;
    }

    public String getIs_commend() {
        return is_commend;
    }

    public void setIs_commend(String is_commend) {
        this.is_commend = is_commend;
    }

    public String getCollects() {
        return collects;
    }

    public void setCollects(String collects) {
        this.collects = collects;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getCaches() {
        return caches;
    }

    public void setCaches(String caches) {
        this.caches = caches;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getLastplay_time() {
        return lastplay_time;
    }

    public void setLastplay_time(String lastplay_time) {
        this.lastplay_time = lastplay_time;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_pic_url() {
        return channel_pic_url;
    }

    public void setChannel_pic_url(String channel_pic_url) {
        this.channel_pic_url = channel_pic_url;
    }

    public String getChannel_update_time() {
        return channel_update_time;
    }

    public void setChannel_update_time(String channel_update_time) {
        this.channel_update_time = channel_update_time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getChannel_pic() {
        return channel_pic;
    }

    public void setChannel_pic(String channel_pic) {
        this.channel_pic = channel_pic;
    }

    public List<String> getTags() {
        List<String> result = new ArrayList<>();
        if (tags != null) {
            result.addAll(tags);
        }
        return result;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.intro);
        dest.writeString(this.pic_url);
        dest.writeString(this.file_url);
        dest.writeString(this.length);
        dest.writeString(this.is_cnword);
        dest.writeString(this.is_commend);
        dest.writeString(this.collects);
        dest.writeString(this.likes);
        dest.writeString(this.shares);
        dest.writeString(this.caches);
        dest.writeString(this.play);
        dest.writeString(this.comments);
        dest.writeString(this.add_time);
        dest.writeString(this.update_time);
        dest.writeString(this.lastplay_time);
        dest.writeString(this.channel_id);
        dest.writeString(this.channel_name);
        dest.writeString(this.channel_pic_url);
        dest.writeString(this.channel_update_time);
        dest.writeString(this.pic);
        dest.writeString(this.file);
        dest.writeString(this.channel_pic);
        dest.writeStringList(this.tags);
    }

    public VideoDetailBean() {
    }

    protected VideoDetailBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.intro = in.readString();
        this.pic_url = in.readString();
        this.file_url = in.readString();
        this.length = in.readString();
        this.is_cnword = in.readString();
        this.is_commend = in.readString();
        this.collects = in.readString();
        this.likes = in.readString();
        this.shares = in.readString();
        this.caches = in.readString();
        this.play = in.readString();
        this.comments = in.readString();
        this.add_time = in.readString();
        this.update_time = in.readString();
        this.lastplay_time = in.readString();
        this.channel_id = in.readString();
        this.channel_name = in.readString();
        this.channel_pic_url = in.readString();
        this.channel_update_time = in.readString();
        this.pic = in.readString();
        this.file = in.readString();
        this.channel_pic = in.readString();
        this.tags = in.createStringArrayList();
    }

    public static final Creator<VideoDetailBean> CREATOR = new Creator<VideoDetailBean>() {
        @Override
        public VideoDetailBean createFromParcel(Parcel source) {
            return new VideoDetailBean(source);
        }

        @Override
        public VideoDetailBean[] newArray(int size) {
            return new VideoDetailBean[size];
        }
    };
}
