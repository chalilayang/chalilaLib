package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.AdvertisingListBean;
import com.baogetv.app.bean.AdviceBean;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.CommentBean;
import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.GradeDetailBean;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.NewCountBean;
import com.baogetv.app.bean.ReportTypeBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.bean.ScoreSourceBean;
import com.baogetv.app.bean.ScoreSourceDetailBean;
import com.baogetv.app.bean.SystemInfoBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.bean.ZanMeBean;

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
     * 三方登录
     *
     * @param type：（登录方式：weixin/qq/weibo(默认为：weixin)）
     * @param authentication：（登录凭证字符串）
     * @param username：（昵称）
     * @param pic_url：（图片URL）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/otherlogin")
    Call<ResponseBean<UserDetailBean>> loginPartner(
            @Field("type") String type,
            @Field("authentication") String authentication,
            @Field("username") String username,
            @Field("pic_url") String pic_url);

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
    Call<ResponseBean<UserDetailBean>> login(
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
    Call<ResponseBean<UserDetailBean>> register(
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
     * @param user_id：（会员ID，不传就看token是哪个用户）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/detail")
    Call<ResponseBean<UserDetailBean>> getUserDetail(
            @Field("token") String token,
            @Field("user_id") String user_id
    );

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
     * @param token：（Token，登录凭证）
     * @param bfr：（体脂率：20）
     * @param is_push_comments：（是否接收评论推送）
     * @param is_push_likes：（是否接收点赞推送）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Member/edit")
    Call<ResponseBean<List<Object>>> editUserDetail(
            @Field("pic") String pic,
            @Field("username") String username,
            @Field("sex") String sex,
            @Field("birthday") String birthday,
            @Field("intro") String intro,
            @Field("height") String height,
            @Field("weight") String weight,
            @Field("bfr") String bfr,
            @Field("is_push_comments") String is_push_comments,
            @Field("is_push_likes") String is_push_likes,
            @Field("token") String token);

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
            @Field("token") String token, @Field("video_id") String video_id);

    /**
     * 取消收藏
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（多个ID用逗号隔开）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Collects/del")
    Call<ResponseBean<List<Object>>> deleteCollect(
            @Field("token") String token,
            @Field("video_id") String video_id);

    /**
     * 我的收藏
     *
     * @param token：（Token，登录凭证）
     * @param user_id：（会员ID，不传就看token是哪个用户）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Collects/my")
    Call<ResponseBean<List<CollectBean>>> getCollectList(
            @Field("token") String token,
            @Field("user_id") String user_id
    );

    /**
     * 添加缓存
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Videocaches/add")
    Call<ResponseBean<AddItemBean>> addCache(
            @Field("token") String token, @Field("video_id") String video_id);

    /**
     * 添加分享
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Shares/add")
    Call<ResponseBean<AddItemBean>> addShare(
            @Field("token") String token, @Field("video_id") String video_id);

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
            @Field("token") String token, @Field("video_id") String video_id);

    /**
     * 取消播放记录
     *
     * @param token：（Token，登录凭证）
     * @param id：（收藏ID，多个ID用逗号隔开）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/del")
    Call<ResponseBean<List<Object>>> deleteHistory(
            @Field("token") String token,
            @Field("id") String id);

    /**
     * 关联播放记录
     *
     * @param token：（Token，登录凭证）
     * @param id：（收藏ID，多个ID用逗号隔开）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/edit")
    Call<ResponseBean<List<Object>>> editHistory(
            @Field("token") String token,
            @Field("id") String id);

    /**
     * 根据播放记录ID获取数据
     *
     * @param id：（播放记录ID，多个用逗号隔开）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Play/databyid")
    Call<ResponseBean<List<HistoryBean>>> getHistoryListById(
            @Field("id") String id);


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
     * 评论列表
     *
     * @param video_id：（视频ID）
     * @param token：（Token，登录凭证）
     * @param p：（分页，第几页）
     * @param r：（分页，每页显示多少条数据）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/index")
    Call<ResponseBean<List<CommentListBean>>> getCommentList(
            @Field("video_id") String video_id,
            @Field("token") String token,
            @Field("p") String p,
            @Field("r") String r
    );

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
    Call<ResponseBean<CommentListBean>> addComment(
            @Field("token") String token,
            @Field("video_id") String video_id,
            @Field("reply_id") String reply_id,
            @Field("reply_user_id") String reply_user_id,
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
    Call<ResponseBean<List<Object>>> deleteComment(
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


    /**
     * 点赞
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param comments_id：（评论ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Likes/add")
    Call<ResponseBean<AddItemBean>> addZan(
            @Field("token") String token,
            @Field("video_id") String video_id,
            @Field("comments_id") String comments_id
    );

    /**
     * 取消赞
     *
     * @param token：（Token，登录凭证）
     * @param video_id：（视频ID）
     * @param comments_id：（评论ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Likes/del")
    Call<ResponseBean<List<Object>>> delZan(
            @Field("token") String token,
            @Field("video_id") String video_id,
            @Field("comments_id") String comments_id
    );

    /**
     * 未读点赞数
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Likes/fromMeNewcount")
    Call<ResponseBean<NewCountBean>> getNewZanCount(@Field("token") String token);

    /**
     * 赞我的
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Likes/fromMe")
    Call<ResponseBean<List<ZanMeBean>>> getZanMeList(@Field("token") String token);

    /**
     * 回复我的
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/atMe")
    Call<ResponseBean<List<ResponseMeBean>>> getResponseMeList(@Field("token") String token);

    /**
     * 未读回复
     *
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Comments/atMeNewcount")
    Call<ResponseBean<NewCountBean>> getNewResponseMeCount(@Field("token") String token);

    /**
     * 广告列表
     *
     * @param title：（广告标题）
     * @param type_id：（广告类型ID：1.引导页 2.启动广告 3.文字广告）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Adv/index")
    Call<ResponseBean<List<AdvertisingListBean>>> getAdvertisingList(
            @Field("title") String title,
            @Field("type_id") int type_id);

    /**
     * 反馈意见
     *
     * @param token：（Token，登录凭证）
     * @param type_id：（类型ID：1.反馈意见 2.功能bug）
     * @param content：（举报内容）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Feedback/add")
    Call<ResponseBean<AdviceBean>> uploadAdvice(
            @Field("token") String token,
            @Field("type_id") String type_id,
            @Field("content") String content);

    /**
     * 举报
     *
     * @param token：（Token，登录凭证）
     * @param type_id：（举报类型：1.广告 2.暴力言论）
     * @param content：（举报内容）
     * @param video_id：（举报视频ID）
     * @param be_user_id：（被举报用户ID）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Accusation/add")
    Call<ResponseBean<AddItemBean>> uploadReport(
            @Field("token") String token,
            @Field("type_id") String type_id,
            @Field("content") String content,
            @Field("video_id") String video_id,
            @Field("be_user_id") String be_user_id);

    /**
     * 举报类型
     *
     * @return
     */
    @POST("index.php?s=/Accusation/type")
    Call<ResponseBean<List<ReportTypeBean>>> getReportTypeList();

    /**
     * 系统信息列表
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Message/index")
    Call<ResponseBean<List<SystemInfoBean>>> getSystemInfoList(@Field("token") String token);

    /**
     * 删除信息
     * @param id：（系统信息ID）
     * @param token：（Token，登录凭证）
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?s=/Message/setIsRead")
    Call<ResponseBean<List<Object>>> delelteInfo(@Field("id") String id, @Field("token") String token);
}
