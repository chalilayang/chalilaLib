package com.baogetv.app.bean;

import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.model.channel.entity.ChannelData;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;

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
        try {
            int isChn = Integer.parseInt(data.getIs_cnword());
            result.setCHN(isChn != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            int isPro = Integer.parseInt(data.getIs_commend());
            result.setCHN(isPro != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
        try {
            int isChn = Integer.parseInt(data.getIs_cnword());
            result.setCHN(isChn != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            int isPro = Integer.parseInt(data.getIs_commend());
            result.setCHN(isPro != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
        String duration = data.getLength();
        String vid = data.getId();
        VideoData result = new VideoData(picUrl, title, publishTime, playCount, duration, vid);
        try {
            int isChn = Integer.parseInt(data.getIs_cnword());
            result.setCHN(isChn != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static CommentListBean getCommentListBean(CommentListBean.DataBean data) {
        /**
         * id : 3
         * video_id : 2
         * reply_id : 0
         * user_id : 3
         * reply_user_id : 0
         * content : xsw
         * likes : 0
         * add_time : 1510449380
         * status : 1
         * username : 15913196454
         * user_pic : 1
         * reply_user_username :
         * reply_user_pic :
         * level_id : 1
         * level_name : LV1
         * grade : 3
         * intro :
         * user_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-20
         * /5a127d675b918.jpg
         * reply_user_picid : 0
         * is_like : 0
         * child : []
         */
        CommentListBean commentData = new CommentListBean();
        commentData.setId(data.getId());
        commentData.setVideo_id(data.getVideo_id());
        commentData.setReply_id(data.getReply_id());
        commentData.setUser_id(data.getUser_id());
        commentData.setReply_user_id(data.getUser_id());
        commentData.setContent(data.getContent());
        commentData.setLikes(data.getLikes());
        commentData.setAdd_time(data.getAdd_time());
        commentData.setStatus(data.getStatus());
        commentData.setUsername(data.getUsername());
        commentData.setUser_pic(data.getUser_pic());
        commentData.setReply_user_username(data.getReply_user_username());
        commentData.setReply_user_pic(data.getReply_user_pic());
        commentData.setLevel_id(data.getLevel_id());
        commentData.setLevel_name(data.getLevel_name());
        commentData.setGrade(data.getGrade());
        commentData.setIntro(data.getIntro());
        commentData.setUser_pic_url(data.getUser_pic_url());
        commentData.setReply_user_picid(data.getReply_user_picid());
        commentData.setIs_like(data.getIs_like());
        return commentData;
    }
}
