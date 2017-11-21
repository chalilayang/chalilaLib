package com.baogetv.app.model.videodetail.entity;

import com.baogetv.app.model.usercenter.entity.UserData;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class ReplyData {
    private UserData replyTo;
    private UserData replyer;
    private String content;

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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
