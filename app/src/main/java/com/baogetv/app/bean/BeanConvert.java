package com.baogetv.app.bean;

import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.db.entity.UserItemEntity;
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
            result.setPro(isPro != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static VideoListAdapter.IVideoData getIVideoData(VideoListBean data) {
        return getIVideoData(data, false);
    }
    public static VideoListAdapter.IVideoData getIVideoData(VideoListBean data, boolean smallPic) {
        String picUrl = smallPic ? data.getThumb_pic_url() : data.getPic_url();
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
            result.setPro(isPro != 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ChannelData getChannelData(ChannelListBean data) {
        String picUrl = data.getPic_url();
        String title = data.getName();
        String publishTime = data.getUpdate_time();
        int playCount = 0;
        if (!TextUtils.isEmpty(data.getCount())) {
            try {
                playCount = Integer.parseInt(data.getCount());
            } catch (NumberFormatException e) {
                Log.i(TAG, "getIVideoData: " + e);
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
        String vid = data.getVideo_id();
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
        commentData.setLevel_medal(data.getLevel_medal());
        commentData.setIs_like(data.getIs_like());
        return commentData;
    }

    public static UserItemEntity getUserItemEntity(UserDetailBean bean) {
        /**
         * user_id : 9
         * openid :
         * username : 山南
         * mobile : 13821049089
         * device_token : AgNPWWcJ965E_IAmOLh4V2dIJsLMmWDcQE_8a609vg2c
         * sex : 1
         * birthday : 1991-01-01
         * intro :
         * height : 0
         * weight : 0
         * bfr : 0
         * pic : 0
         * score : 36
         * level_id : 1
         * level_time : 2017-12-03 00:23:51
         * grade : 2
         * is_sure : 0
         * dumb_time : 1970-01-01 08:00:00
         * is_push_comments : 1
         * is_push_likes : 1
         * read_message_ids :
         * del_message_ids :
         * login : 17
         * reg_ip : 2099501355
         * reg_time : 2017-12-01 12:10:19
         * last_login_ip : 42.80.233.3
         * last_login_time : 2017-12-03 13:24:16
         * status : 1
         * token : ad748196435a35e5d9fa89a120af158d
         * pic_url :
         * level_name : LV1
         * medal : lv1
         * level_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-19/5a114121a67ac.jpg
         * next_level_score : 666
         * age : 26
         */
        UserItemEntity entity = new UserItemEntity(
                bean.getUser_id(),
                bean.getOpenid(),
                bean.getUsername(),
                bean.getMobile(),
                bean.getDevice_token(),
                bean.getSex(),
                bean.getBirthday(),
                bean.getIntro(),
                bean.getHeight(),
                bean.getWeight(),
                bean.getBfr(),
                bean.getPic_url(),
                bean.getScore(),
                bean.getLevel_id(),
                bean.getLevel_time(),
                bean.getGrade(),
                bean.getIs_sure(),
                bean.getDumb_time(),
                bean.getIs_push_comments(),
                bean.getIs_push_likes(),
                bean.getIs_push_comments(),
                bean.getDel_message_ids(),
                bean.getLogin(),
                bean.getReg_ip(),
                bean.getReg_time(),
                bean.getLast_login_ip(),
                bean.getLast_login_time(),
                bean.getStatus(),
                bean.getToken(),
                bean.getPic_url(),
                bean.getLevel_name(),
                bean.getMedal(),
                bean.getLevel_pic_url(),
                bean.getNext_level_score(),
                bean.getAge()
        );
        return entity;
    }

    public static UserDetailBean getUserDetailBean(UserItemEntity bean) {
        /**
         * user_id : 9
         * openid :
         * username : 山南
         * mobile : 13821049089
         * device_token : AgNPWWcJ965E_IAmOLh4V2dIJsLMmWDcQE_8a609vg2c
         * sex : 1
         * birthday : 1991-01-01
         * intro :
         * height : 0
         * weight : 0
         * bfr : 0
         * pic : 0
         * score : 36
         * level_id : 1
         * level_time : 2017-12-03 00:23:51
         * grade : 2
         * is_sure : 0
         * dumb_time : 1970-01-01 08:00:00
         * is_push_comments : 1
         * is_push_likes : 1
         * read_message_ids :
         * del_message_ids :
         * login : 17
         * reg_ip : 2099501355
         * reg_time : 2017-12-01 12:10:19
         * last_login_ip : 42.80.233.3
         * last_login_time : 2017-12-03 13:24:16
         * status : 1
         * token : ad748196435a35e5d9fa89a120af158d
         * pic_url :
         * level_name : LV1
         * medal : lv1
         * level_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-19/5a114121a67ac.jpg
         * next_level_score : 666
         * age : 26
         */
        UserDetailBean entity = new UserDetailBean(
                bean.getUser_id(),
                bean.getOpenid(),
                bean.getUsername(),
                bean.getMobile(),
                bean.getDevice_token(),
                bean.getSex(),
                bean.getBirthday(),
                bean.getIntro(),
                bean.getHeight(),
                bean.getWeight(),
                bean.getBfr(),
                bean.getPic_url(),
                bean.getScore(),
                bean.getLevel_id(),
                bean.getLevel_time(),
                bean.getGrade(),
                bean.getIs_sure(),
                bean.getDumb_time(),
                bean.getIs_push_comments(),
                bean.getIs_push_likes(),
                bean.getIs_push_comments(),
                bean.getDel_message_ids(),
                bean.getLogin(),
                bean.getReg_ip(),
                bean.getReg_time(),
                bean.getLast_login_ip(),
                bean.getLast_login_time(),
                bean.getStatus(),
                bean.getToken(),
                bean.getPic_url(),
                bean.getLevel_name(),
                bean.getMedal(),
                bean.getLevel_pic_url(),
                bean.getNext_level_score(),
                bean.getAge()
        );
        return entity;
    }

    public static CommentListBean.DataBean getCommentListDataBean(CommentListBean bean) {
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
        CommentListBean.DataBean dataBean = new CommentListBean.DataBean();
        dataBean.setId(bean.getId());
        dataBean.setVideo_id(bean.getVideo_id());
        dataBean.setReply_id(bean.getReply_id());
        dataBean.setUser_id(bean.getUser_id());
        dataBean.setReply_user_id(bean.getReply_user_id());
        dataBean.setContent(bean.getContent());
        dataBean.setLikes(bean.getLikes());
        dataBean.setAdd_time(bean.getAdd_time());
        dataBean.setStatus(bean.getStatus());

        dataBean.setUsername(bean.getUsername());
        dataBean.setUser_pic(bean.getUser_pic());
        dataBean.setReply_user_username(bean.getReply_user_username());
        dataBean.setReply_user_pic(bean.getReply_user_pic());

        dataBean.setLevel_id(bean.getLevel_id());
        dataBean.setLevel_name(bean.getLevel_name());
        dataBean.setGrade(bean.getGrade());
        dataBean.setIntro(bean.getIntro());
        dataBean.setLevel_medal(bean.getLevel_medal());
        dataBean.setUser_pic_url(bean.getUser_pic_url());
        dataBean.setReply_user_picid(bean.getReply_user_picid());
        dataBean.setIs_like(bean.getIs_like());

        return dataBean;
    }
}
