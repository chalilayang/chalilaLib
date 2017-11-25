package com.baogetv.app.bean;

import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class BeanConvert {
    public static VideoListAdapter.IVideoData getIVideoData(VideoRankListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getTitle();
        String publishTime = data.getAdd_time();
        String playCount = data.getPlay_count();
        String duration = data.getLength();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, duration);
        return result;
    }

    public static VideoListAdapter.IVideoData getIVideoData(VideoListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getTitle();
        String publishTime = data.getAdd_time();
        String playCount = data.getPlay();
        String duration = data.getLength();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, duration);
        return result;
    }
}
