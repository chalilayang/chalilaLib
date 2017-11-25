package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.SearchHotWordBean;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface SearchHotWordService {

    /** 表单提交要加 @FormUrlEncoded
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/hotsearchGet")
    Call<SearchHotWordBean> getHotWord();
}
