package com.baogetv.app.model.videodetail.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.model.usercenter.entity.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class CommentData implements Parcelable {
    private UserData owner;
    private String content;
    private long time;
    private List<ReplyData> replyList;
    private CommentListBean bean;

    public UserData getOwner() {
        return owner;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<ReplyData> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyData> replyList) {
        this.replyList = replyList;
    }

    public CommentListBean getBean() {
        return bean;
    }

    public void setBean(CommentListBean bean) {
        this.bean = bean;
        UserData replyer = new UserData();
        replyer.setNickName(bean.getUsername());
        replyer.setDesc(bean.getIntro());
        replyer.setGrage(bean.getGrade());
        replyer.setIconUrl(bean.getUser_pic_url());
        setOwner(replyer);
        setContent(bean.getContent());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.content);
        dest.writeLong(this.time);
        dest.writeTypedList(this.replyList);
        dest.writeParcelable(this.bean, flags);
    }

    public CommentData() {
    }

    protected CommentData(Parcel in) {
        this.owner = in.readParcelable(UserData.class.getClassLoader());
        this.content = in.readString();
        this.time = in.readLong();
        this.replyList = in.createTypedArrayList(ReplyData.CREATOR);
        this.bean = in.readParcelable(CommentListBean.class.getClassLoader());
    }

    public static final Creator<CommentData> CREATOR = new Creator<CommentData>() {
        @Override
        public CommentData createFromParcel(Parcel source) {
            return new CommentData(source);
        }

        @Override
        public CommentData[] newArray(int size) {
            return new CommentData[size];
        }
    };
}
