package com.baogetv.app.model.videodetail.event;

import com.baogetv.app.model.videodetail.entity.ReplyData;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class NeedReplyEvent {
    public int commentIndex;
    public ReplyData replyData;
    public NeedReplyEvent(ReplyData p, int cIndex) {
        replyData = p;
        commentIndex = cIndex;
    }
}
