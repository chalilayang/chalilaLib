package com.baogetv.app.model.videodetail.event;

import com.baogetv.app.model.videodetail.entity.CommentData;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class NeedCommentEvent {
    public CommentData commentData;
    public NeedCommentEvent(CommentData p) {
        commentData = p;
    }
}
