package com.baogetv.app.model.videodetail.event;

import com.baogetv.app.model.videodetail.entity.CommentData;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class NeedCommentEvent {
    public CommentData commentData;
    public int commentIndex;
    public NeedCommentEvent(CommentData p, int cIndex) {
        commentData = p;
        commentIndex = cIndex;
    }
}
