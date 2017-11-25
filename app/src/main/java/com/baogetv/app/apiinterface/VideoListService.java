package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.VideoListBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface VideoListService {

    /** 表单提交要加 @FormUrlEncoded
     * @param channel_id（频道ID）
     * @param tag_id：（标签ID）
     * @param keyword：（关键字（标题/简介模糊搜索））
     * @param orderby：（排序依据：0.默认排序 1.按播放量排序 2.按发布时间排序）
     * @param ordertype：（排序类型：0.降序 1.升序）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/index")
    Call<VideoListBean> getVideoList(@Field("channel_id") String channel_id,
                              @Field("tag_id") String tag_id,
                              @Field("keyword") String keyword,
                              @Field("orderby") String orderby,
                              @Field("ordertype") String ordertype);
}
