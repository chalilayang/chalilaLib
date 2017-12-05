package com.baogetv.app.model.videodetail.event;

import com.baogetv.app.model.videodetail.entity.CommentData;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class NeedCommentDetailEvent {
    public CommentData commentData;
    public NeedCommentDetailEvent(CommentData p) {
        commentData = p;
    }
}
