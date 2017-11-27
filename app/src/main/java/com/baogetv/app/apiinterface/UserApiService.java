package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.CommentBean;
import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.GradeDetailBean;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.LoginBean;
import com.baogetv.app.bean.RegisterBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ScoreSourceBean;
import com.baogetv.app.bean.ScoreSourceDetailBean;
import com.baogetv.app.bean.UserDetailBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chalilayang on 2017/11/26.
 */

public interface UserApiService {
    /**
     * 登录
     *
     * @param username：（用户名/手机号）
     * @param password：（密码）
     * @param device_token：（设备token）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/login")
    Call<ResponseBean<LoginBean>> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_token") String device_token);

    /**
     * 注册
     *
     * @param mobile：（手机号）
     * @param password：（密码）
     * @param verify_code：（手机验证码）
     * @param username：（手机号）
     * @param device_token：（设备token）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/register")
    Call<ResponseBean<RegisterBean>> register(
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("verify_code") String verify_code,
            @Field("username") String username,
            @Field("device_token") String device_token);

    /**
     * 退出登录
     *
     * @param token：（token）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/logout")
    Call<ResponseBean<List<Object>>> loginOut( @Field("token") String token);

    /**
     * 会员详情
     *
     * @param token：（token）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/detail")
    Call<ResponseBean<UserDetailBean>> getUserDetail(@Field("token") String token);

    /**
     * 发送短信验证码
     * @param mobile：（手机号）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/sendSMS")
    Call<ResponseBean<List<Object>>> sendMobileSMS(@Field("mobile") String mobile);

    /**
     * 找回密码
     *
     * @param mobile：（手机号）
     * @param password：（密码）
     * @param verify_code：（手机验证码）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/findPassword")
    Call<ResponseBean<List<Object>>> reFetchPassword(
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("verify_code") String verify_code);

    /**
     * 更换手机号
     *
     * @param token：（Token，登录凭证）
     * @param mobile：（手机号）
     * @param verify_code：（手机验证码）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/setBinding")
    Call<ResponseBean<List<Object>>> resetMobile(
            @Field("token") String token,
            @Field("mobile") String mobile,
            @Field("verify_code") String verify_code);

    /**
     * 更换密码
     *
     * @param token：（Token，登录凭证）
     * @param password：（旧密码）
     * @param new_password：（新密码）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/editPassword")
    Call<ResponseBean<List<Object>>> editPassword(
            @Field("token") String token,
            @Field("password") String password,
            @Field("new_password") String new_password);

    /**
     * 修改个人资料
     *
     * @param pic：（图片ID）
     * @param username：（用户名）
     * @param sex：（性别：0.保密 1.男 2.女）
     * @param birthday：（生日日期：2011-01-01）
     * @param intro：（个人简介）
     * @param height：（身高：176）
     * @param weight：（体重：60）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/edit")
    Call<ResponseBean<List<Object>>> editUserDetail(
            @Field("pic") String pic,
            @Field("username") String username,
            @Field("sex") int sex,
            @Field("birthday") String birthday,
            @Field("intro") String intro,
            @Field("height") String height,
            @Field("weight") String weight);

    /**
     * 等级列表
     *
     * @param name：
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Level/index")
    Call<ResponseBean<List<GradeBean>>> getGradeList(@Field("name") String name);

    /**
     * 等级详情
     *
     * @param id：等级ID
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Level/detail")
    Call<ResponseBean<GradeDetailBean>> getGradeDetail(@Field("id") int id);

    /**
     * 经验值来源列表
     *
     * @param title：经验值来源标题
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Scoresource/index")
    Call<ResponseBean<List<ScoreSourceBean>>> getScoreSourceList(@Field("title") String title);

    /**
     * 经验值来源详情
     *
     * @param id：经验值来源ID
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Scoresource/detail")
    Call<ResponseBean<ScoreSourceDetailBean>> getScoreSourceDetail(@Field("id") int id);


    /**
     * 添加收藏
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Collects/add")
    Call<ResponseBean<AddItemBean>> addCollect(
            @Field("token") String token, @Field("video_id") int video_id);

    /**
     * 取消收藏
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param id：（收藏ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Collects/del")
    Call<ResponseBean<Object>> deleteCollect(
            @Field("token") String token,
            @Field("video_id") int video_id,
            @Field("id") int id);

    /**
     * 我的收藏
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Collects/my")
    Call<ResponseBean<List<CollectBean>>> getCollectList(
            @Field("token") String token);


    /**
     * 添加播放记录
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/add")
    Call<ResponseBean<AddItemBean>> addHistory(
            @Field("token") String token, @Field("video_id") int video_id);

    /**
     * 取消播放记录
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param id：（收藏ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/del")
    Call<ResponseBean<Object>> deleteHistory(
            @Field("token") String token,
            @Field("video_id") int video_id,
            @Field("id") int id);

    /**
     * 我的播放记录
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/my")
    Call<ResponseBean<List<HistoryBean>>> getHistoryList(
            @Field("token") String token);


    /**
     * 添加评论
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param reply_id：（回复的评论ID）
     * @param reply_user_id：（回复的用户ID）
     * @param content：（评论内容）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/add")
    Call<ResponseBean<AddItemBean>> addComment(
            @Field("token") String token,
            @Field("video_id") int video_id,
            @Field("reply_id") int reply_id,
            @Field("reply_user_id") int reply_user_id,
            @Field("content") String content
    );

    /**
     * 删除评论
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param id：（收藏ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/del")
    Call<ResponseBean<Object>> deleteComment(
            @Field("token") String token,
            @Field("video_id") int video_id,
            @Field("id") int id);

    /**
     * 我的评论
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/my")
    Call<ResponseBean<List<CommentBean>>> getMyCommentList(
            @Field("token") String token,
            @Field("video_id") int video_id,
            @Field("id") int id);

}
