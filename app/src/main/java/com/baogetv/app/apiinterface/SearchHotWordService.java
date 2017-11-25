package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.SearchHotWordBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface SearchHotWordService {

    /**
     * @return
     */
    @POST("index.php?s=/Video/hotsearchGet")
    Call<ResponseBean<List<String>>> getHotWord();
}
