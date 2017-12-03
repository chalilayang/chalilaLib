package com.baogetv.app.bean;

import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.model.channel.entity.ChannelData;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class BeanConvert {
    private static final String TAG = "BeanConvert";
    public static VideoListAdapter.IVideoData getIVideoData(VideoRankListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getTitle();
        String publishTime = data.getAdd_time();
        String playCount = data.getPlay_count();
        String duration = data.getLength();
        String vid = data.getVideo_id();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, duration, vid);
        return result;
    }

    public static VideoListAdapter.IVideoData getIVideoData(VideoListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getTitle();
        String publishTime = data.getAdd_time();
        String playCount = data.getPlay();
        String duration = data.getLength();
        String vid = data.getId();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, duration, vid);
        return result;
    }

    public static ChannelData getChannelData(ChannelListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getName();
        String publishTime = data.getAdd_time();
        int playCount = 0;
        if (!TextUtils.isEmpty(data.getCount())) {
            try {
                playCount = Integer.parseInt(data.getCount());
            } catch (NumberFormatException e) {
                Log.i(TAG, "getIVideoData: " + e);;
            }
        }
        String desc = data.getIntro();
        String cid = data.getId();
        ChannelData result = new ChannelData(cid, picUrl, title, playCount, publishTime, desc);
        return result;
    }

    public static  VideoListAdapter.IVideoData getIVideoData(CollectBean data) {
        String picUrl = data.getPic_url();
        String title = data.getType_name();
        String publishTime = data.getAdd_time();
        String playCount = data.getPlay();
//        String duration = data.getLength();
        String vid = data.getId();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, "", vid);
        return result;
    }
}
