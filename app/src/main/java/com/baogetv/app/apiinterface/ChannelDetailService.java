package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface ChannelDetailService {

    /** 表单提交要加 @FormUrlEncoded
     * @param id（频道ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/detail")
    Call<ResponseBean<ChannelDetailBean>> getChannelDetail(@Field("id") String id);
}
