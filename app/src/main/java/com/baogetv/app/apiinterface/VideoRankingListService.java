package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.VideoRankListBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface VideoRankingListService {

    /** 表单提交要加 @FormUrlEncoded
     * 登录
     * @param type 排行榜类型：0.总榜 1.周榜 2.月榜
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/ranking")
    Call<VideoRankListBean> getVideoList(@Field("type") int type);
}
