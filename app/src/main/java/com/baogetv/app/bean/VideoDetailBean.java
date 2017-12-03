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
     * id : 2
     * title : 标题222
     * intro : 简介222
     * pic : 9
     * file : 26
     * tag_ids : 1,3
     * length : 00:00
     * is_cnword : 1
     * is_commend : 0
     * collects : 0
     * likes : 0
     * shares : 0
     * caches : 0
     * play : 12330
     * comments : 3
     * add_time : 1510991024
     * update_time : 1512226565
     * lastplay_time : 1512226490
     * channel_id : 11
     * channel_name : 频道2
     * channel_pic : 4
     * channel_update_time : 1512181383
     * pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-19/5a1130f3f1c95.jpg
     * file_url :
     * channel_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-19
     * /5a112b8fd0391.jpg
     * tags : [{"id":"3","name":"标签356"},{"id":"1","name":"标签111"}]
     */

    private String id;
    private String title;
    private String intro;
    private String pic;
    private String file;
    private String tag_ids;
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
    private String channel_pic;
    private String channel_update_time;
    private String pic_url;
    private String file_url;
    private String channel_pic_url;
    private List<TagsBean> tags;

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

    public String getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(String tag_ids) {
        this.tag_ids = tag_ids;
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

    public String getChannel_pic() {
        return channel_pic;
    }

    public void setChannel_pic(String channel_pic) {
        this.channel_pic = channel_pic;
    }

    public String getChannel_update_time() {
        return channel_update_time;
    }

    public void setChannel_update_time(String channel_update_time) {
        this.channel_update_time = channel_update_time;
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

    public String getChannel_pic_url() {
        return channel_pic_url;
    }

    public void setChannel_pic_url(String channel_pic_url) {
        this.channel_pic_url = channel_pic_url;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements Parcelable {
        /**
         * id : 3
         * name : 标签356
         */

        private String id;
        private String name;

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
        }

        public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
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
        dest.writeString(this.title);
        dest.writeString(this.intro);
        dest.writeString(this.pic);
        dest.writeString(this.file);
        dest.writeString(this.tag_ids);
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
        dest.writeString(this.channel_pic);
        dest.writeString(this.channel_update_time);
        dest.writeString(this.pic_url);
        dest.writeString(this.file_url);
        dest.writeString(this.channel_pic_url);
        dest.writeList(this.tags);
    }

    public VideoDetailBean() {
    }

    protected VideoDetailBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.intro = in.readString();
        this.pic = in.readString();
        this.file = in.readString();
        this.tag_ids = in.readString();
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
        this.channel_pic = in.readString();
        this.channel_update_time = in.readString();
        this.pic_url = in.readString();
        this.file_url = in.readString();
        this.channel_pic_url = in.readString();
        this.tags = new ArrayList<TagsBean>();
        in.readList(this.tags, TagsBean.class.getClassLoader());
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
