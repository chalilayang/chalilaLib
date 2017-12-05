package com.baogetv.app.model.videodetail.event;

import com.baogetv.app.model.videodetail.entity.ReplyData;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class NeedReplyDetailEvent {
    public ReplyData replyData;
    public NeedReplyDetailEvent(ReplyData p) {
        replyData = p;
    }
}
