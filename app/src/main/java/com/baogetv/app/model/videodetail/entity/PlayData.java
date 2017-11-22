package com.baogetv.app.model.videodetail.entity;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayData {
    public static final int LOCALE_FILE = 0;
    public static final int NET = 1;
    public final int type;
    public final String videoPath;
    public PlayData(int t, String p) {
        type = t;
        videoPath = p;
    }
}
