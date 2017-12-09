package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.AdvBean;
import com.baogetv.app.bean.AdvListBean;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ChannelListBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.bean.VideoRankListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/25.
 */

public interface VideoListService {

    /**
     * 表单提交要加 @FormUrlEncoded
     *
     * @param channel_id（频道ID）
     * @param tag_id：（标签ID）
     * @param keyword：（关键字（标题/简介模糊搜索））
     * @param orderby：（排序依据：0.默认排序     1.按播放量排序 2.按发布时间排序）
     * @param ordertype：（排序类型：0.降序     1.升序）
     * @param p：（分页，第几页）
     * @param r：（分页，每页显示多少条数据）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/index")
    Call<ResponseBean<List<VideoListBean>>>
    getVideoList(
            @Field("channel_id") String channel_id,
            @Field("tag_id") String tag_id,
            @Field("keyword") String keyword,
            @Field("orderby") String orderby,
            @Field("ordertype") String ordertype,
            @Field("p") String p,
            @Field("r") String r);

    /**
     * 排行榜
     *
     * @param type             排行榜类型：0.总榜 1.周榜 2.月榜
     * @param p：（分页，第几页）
     * @param r：（分页，每页显示多少条数据）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/ranking")
    Call<ResponseBean<List<VideoRankListBean>>> getRankVideoList(
            @Field("type") int type,
            @Field("p") String p,
            @Field("r") String r);

    /**
     * 视频详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Video/detail")
    Call<ResponseBean<VideoDetailBean>> getVideoDetail(@Field("id") String id);

    /**
     * @return
     */
    @POST("index.php?s=/Video/hotsearchGet")
    Call<ResponseBean<List<String>>> getHotWord();

    /**
     * 频道详情
     *
     * @param id（频道ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/detail")
    Call<ResponseBean<ChannelDetailBean>> getChannelDetail(@Field("id") String id);

    /**
     * 频道列表
     *
     * @param name：（频道名称）
     * @param intro：（频道简介）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Channel/index")
    Call<ResponseBean<List<ChannelListBean>>> getChannelList(
            @Field("name") String name, @Field("intro") String intro);

    /**
     * 广告列表
     *
     * @param title：（广告标题）
     * @param type_id：（广告类型ID：1.引导页 2.启动广告 3.文字广告）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Adv/index")
    Call<ResponseBean<List<AdvListBean>>> getAdvList(
            @Field("title") String title, @Field("type_id") String type_id);

    /**
     * 广告详情
     *
     * @param id：（广告ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Adv/detail")
    Call<ResponseBean<AdvBean>> getAdvDetail(
            @Field("id") String id);


    /**
     * 广告点击成功调用
     *
     * @param token：（Token，登录凭证）
     * @param id：（广告ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Adv/advclick")
    Call<ResponseBean<List<Object>>> AdvClick(
            @Field("token") String token,
            @Field("id") String id);

}
