package com.baogetv.app.model.videodetail.entity;

import com.baogetv.app.model.usercenter.entity.UserData;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class CommentData {
    private UserData owner;
    private String content;
    private List<ReplyData> replyList;
}
