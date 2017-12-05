package com.baogetv.app.model.videodetail.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.model.usercenter.entity.UserData;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class ReplyData implements Parcelable {
    private UserData replyTo;
    private UserData replyer;
    private String content;
    private CommentListBean.DataBean bean;

    public UserData getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(UserData replyTo) {
        this.replyTo = replyTo;
    }

    public UserData getReplyer() {
        return replyer;
    }

    public void setReplyer(UserData replyer) {
        this.replyer = replyer;
    }

    public String getContent() {
        return bean.getContent();
    }

    public void setContent(String content) {
        this.content = content;
    }
    public CommentListBean.DataBean getBean() {
        return bean;
    }

    public void setBean(CommentListBean.DataBean bean) {
        this.bean = bean;
        UserData replyer = new UserData();
        replyer.setNickName(bean.getUsername());
        replyer.setIconUrl(bean.getUser_pic_url());
        this.replyer = replyer;
        UserData replyTo = new UserData();
        replyTo.setNickName(bean.getReply_user_username());
        replyTo.setIconUrl(bean.getReply_user_pic_url());
        this.replyTo = replyTo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.replyTo, flags);
        dest.writeParcelable(this.replyer, flags);
        dest.writeString(this.content);
        dest.writeParcelable(this.bean, flags);
    }

    public ReplyData() {
    }

    protected ReplyData(Parcel in) {
        this.replyTo = in.readParcelable(UserData.class.getClassLoader());
        this.replyer = in.readParcelable(UserData.class.getClassLoader());
        this.content = in.readString();
        this.bean = in.readParcelable(CommentListBean.DataBean.class.getClassLoader());
    }

    public static final Creator<ReplyData> CREATOR = new Creator<ReplyData>() {
        @Override
        public ReplyData createFromParcel(Parcel source) {
            return new ReplyData(source);
        }

        @Override
        public ReplyData[] newArray(int size) {
            return new ReplyData[size];
        }
    };
}
