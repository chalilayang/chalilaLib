package com.baogetv.app.model.videodetail.entity;

import com.baogetv.app.model.usercenter.entity.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class CommentData {
    private UserData owner;
    private String content;
    private long time;
    private List<ReplyData> replyList;

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

    public List<ReplyData> getReplyList() {
        List<ReplyData> list = new ArrayList<>();
        if (replyList != null && replyList.size() > 0) {
            list.addAll(replyList);
        }
        return list;
    }

    public void setReplyList(List<ReplyData> list) {
        if (replyList == null) {
            replyList = new ArrayList<>();
        }
        replyList.clear();
        if (list != null && list.size() > 0) {
            replyList.addAll(list);
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
