package com.baogetv.app.model.videodetail.event;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class InputSendEvent {
    public String content;
    public NeedCommentEvent commentEvent;
    public NeedReplyEvent replyEvent;
    public InputSendEvent(String p) {
        content = p;
    }
}
