package com.baogetv.app.model.videodetail.event;

/**
 * Created by chalilayang on 2017/12/1.
 */

public class CommentCountEvent {
    public final int count;
    public CommentCountEvent(int c) {
        this.count = c;
    }
}
