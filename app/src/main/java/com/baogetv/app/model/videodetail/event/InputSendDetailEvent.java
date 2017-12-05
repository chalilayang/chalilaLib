package com.baogetv.app.model.videodetail.event;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class InputSendDetailEvent {
    public String content;
    public NeedCommentDetailEvent commentEvent;
    public NeedReplyDetailEvent replyEvent;
    public InputSendDetailEvent(String p) {
        content = p;
    }
}
