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

    public static UserDetailBean getUserDetailBean(RegisterBean bean) {
        UserDetailBean userDetailBean = new UserDetailBean();
        userDetailBean.setUser_id(bean.getUser_id());
        userDetailBean.setOpenid(bean.getOpenid());
        userDetailBean.setUsername(bean.getUsername());
        userDetailBean.setMobile(bean.getMobile());
        userDetailBean.setSex(bean.getSex());
        userDetailBean.setBirthday(bean.getBirthday());
        userDetailBean.setIntro(bean.getIntro());
        userDetailBean.setHeight(bean.getHeight());
        userDetailBean.setWeight(bean.getWeight());
        userDetailBean.setBfr(bean.getBfr());
        userDetailBean.setPic_url(bean.getPic_url());
        userDetailBean.setScore(bean.getScore());
        userDetailBean.setLevel_id(bean.getLevel_id());
        userDetailBean.setPic_url(bean.getPic_url());
        userDetailBean.setLevel_time(bean.getLevel_time());
        userDetailBean.setIs_sure(bean.getIs_sure());
        userDetailBean.setDumb_time(bean.getDumb_time());
        userDetailBean.setLogin(bean.getLogin());
        userDetailBean.setReg_ip(bean.getReg_ip());
        userDetailBean.setReg_time(bean.getReg_time());
        userDetailBean.setLast_login_time(bean.getLast_login_time());
        userDetailBean.setLast_login_ip(bean.getLast_login_ip());
        userDetailBean.setStatus(bean.getStatus());
        return userDetailBean;
    }

    public static UserDetailBean getUserDetailBean(LoginBean bean) {
        UserDetailBean userDetailBean = new UserDetailBean();
        userDetailBean.setUser_id(bean.getUser_id());
        userDetailBean.setOpenid(bean.getOpenid());
        userDetailBean.setUsername(bean.getUsername());
        userDetailBean.setMobile(bean.getMobile());
        userDetailBean.setSex(bean.getSex());
        userDetailBean.setBirthday(bean.getBirthday());
        userDetailBean.setIntro(bean.getIntro());
        userDetailBean.setHeight(bean.getHeight());
        userDetailBean.setWeight(bean.getWeight());
        userDetailBean.setBfr(bean.getBfr());
        userDetailBean.setPic_url(bean.getPic_url());
        userDetailBean.setScore(bean.getScore());
        userDetailBean.setLevel_id(bean.getLevel_id());
        userDetailBean.setPic_url(bean.getPic_url());
        userDetailBean.setLevel_time(bean.getLevel_time());
        userDetailBean.setIs_sure(bean.getIs_sure());
        userDetailBean.setDumb_time(bean.getDumb_time());
        userDetailBean.setLogin(bean.getLogin());
        userDetailBean.setReg_ip(bean.getReg_ip());
        userDetailBean.setReg_time(bean.getReg_time());
        userDetailBean.setLast_login_time(bean.getLast_login_time());
        userDetailBean.setLast_login_ip(bean.getLast_login_ip());
        userDetailBean.setStatus(bean.getStatus());
        return userDetailBean;
    }
}
