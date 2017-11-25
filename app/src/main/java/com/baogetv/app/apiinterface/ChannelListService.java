package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.ChannelListBean;
import com.baogetv.app.bean.VideoListBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface ChannelListService {

    /** 表单提交要加 @FormUrlEncoded
     * @param name：（频道名称）
     * @param intro：（频道简介）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/index")
    Call<ChannelListBean> getChannelList(
            @Field("name") String name, @Field("intro") String intro);
}
