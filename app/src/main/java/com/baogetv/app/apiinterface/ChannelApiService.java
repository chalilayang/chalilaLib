package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ChannelListBean;
import com.baogetv.app.bean.ResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface ChannelApiService {

    /**
     * 频道详情
     * @param id（频道ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/detail")
    Call<ResponseBean<ChannelDetailBean>> getChannelDetail(@Field("id") String id);

    /**
     * 频道列表
     * @param name：（频道名称）
     * @param intro：（频道简介）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/index")
    Call<ResponseBean<List<ChannelListBean>>> getChannelList(
            @Field("name") String name, @Field("intro") String intro);
}
