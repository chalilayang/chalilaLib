package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.constant.UrlConstance;
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.HistoryManager;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.AddCollectEvent;
import com.baogetv.app.model.videodetail.event.AddHistoryEvent;
import com.baogetv.app.model.videodetail.event.CacheEvent;
import com.baogetv.app.model.videodetail.event.FreshInfoEvent;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyEvent;
import com.baogetv.app.model.videodetail.event.PageSelectEvent;
import com.baogetv.app.model.videodetail.event.ShareEvent;
import com.baogetv.app.model.videodetail.fragment.PlayerFragment;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.baogetv.app.util.CacheUtil;
import com.baogetv.app.util.InputUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;

public class VideoDetailActivity extends BaseActivity implements ShareBoardlistener {

    private static final String TAG = "VideoDetailActivity";
    private VideoDetailFragment videoDetailFragment;
    private PlayerFragment playerFragment;
    private VideoDetailBean videoDetailBean;
    private String videoId;
    private EditText editText;
    private View sendBtn;
    private View editContainer;
    private CustomShareListener customShareListener;
    private ShareContent shareContent;
    private ShareAction shareAction = new ShareAction(VideoDetailActivity.this)
            .setDisplayList(AppConstance.SHARE_LIST)
            .setShareboardclickCallback(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.4f;
        getWindow().setAttributes(params);
        setContentView(R.layout.activity_video_detail);
        videoId = getIntent().getStringExtra(KEY_VIDEO_ID);
        customShareListener = new CustomShareListener(this);
        initView();
        initData();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.comment_edit_text);
        sendBtn = findViewById(R.id.comment_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginManager.hasCommentRight(getApplicationContext())) {
                    if (!LoginManager.hasLogin(getApplicationContext())) {
                        LoginManager.startLogin(VideoDetailActivity.this);
                    } else if (LoginManager.hasMobile(getApplicationContext())) {
                        LoginManager.startChangeMobile(VideoDetailActivity.this);
                    } else {
                        showShortToast(getString(R.string.no_comment_right));
                    }
                } else {
                    String content = editText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        showShortToast("评论不能为空");
                    } else {
                        InputSendEvent event = new InputSendEvent(content);
                        event.commentEvent = commentEvent;
                        event.replyEvent = replyEvent;
                        commentEvent = null;
                        replyEvent = null;
                        editText.setText("");
                        editText.setHint(getResources().getString(R.string.send_edit_hint));
                        EventBus.getDefault().post(event);
                    }
                    editText.clearFocus();
                    InputUtil.HideKeyboard(editText);
                }
            }
        });
        editContainer = findViewById(R.id.comment_edit_container);
        editContainer.setVisibility(View.GONE);
    }

    private void initData() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        String token = null;
        if (LoginManager.hasLogin(getApplicationContext())) {
            token = LoginManager.getUserToken(getApplicationContext());
        }
        Call<ResponseBean<VideoDetailBean>> call = listService.getVideoDetail(videoId, token);
        if (call != null) {
            call.enqueue(new CustomCallBack<VideoDetailBean>() {
                @Override
                public void onSuccess(VideoDetailBean data, String msg, int state) {
                    if (data != null) {
                        videoDetailBean = data;
                        showDetailFragment();
                        showPlayerFragment();
                        addPlayHistory(videoDetailBean);
                    } else {
                        showError();
                    }
                }

                @Override
                public void onFailed(String error, int state) {
                    showError();
                    showShortToast(error);
                }
            });
        }
    }

    private void getVideoDetail() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        String token = null;
        if (LoginManager.hasLogin(getApplicationContext())) {
            token = LoginManager.getUserToken(getApplicationContext());
        }
        Call<ResponseBean<VideoDetailBean>> call = listService.getVideoDetail(videoId, token);
        if (call != null) {
            call.enqueue(new CustomCallBack<VideoDetailBean>() {
                @Override
                public void onSuccess(VideoDetailBean data, String msg, int state) {
                    videoDetailBean = data;
                }
                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    public void showError() {
        View error = findViewById(R.id.error_view);
        if (error != null) {
            error.setVisibility(View.VISIBLE);
        }
    }

    private void showDetailFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (videoDetailFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.simple_introduce), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.comment), 0);
            list.add(pageItemData);
            videoDetailFragment
                    = VideoDetailFragment.newInstance(new VideoDetailData(list, videoDetailBean));
        }
        transaction.replace(R.id.video_detail_fragment_container, videoDetailFragment).commit();
    }

    private void showPlayerFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (playerFragment == null) {
            playerFragment = PlayerFragment.newInstance(videoDetailBean);
        }
        transaction.replace(R.id.video_player_fragment_container, playerFragment).commit();
    }

    private void addPlayHistory(final VideoDetailBean bean) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = null;
        if (LoginManager.hasLogin(getApplicationContext())) {
            token = LoginManager.getUserToken(getApplicationContext());
        }
        Call<ResponseBean<AddItemBean>> call = userApiService.addHistory(token, bean.getId());
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data, String msg, int state) {
                    showShortToast("save history success");
                    if (!LoginManager.hasLogin(getApplicationContext())) {
                        String historyId = data.getId();
                        String vid = bean.getId();
                        String title = bean.getTitle();
                        String pic = bean.getPic_url();
                        HistoryManager.getInstance(getApplicationContext()).saveHistory(historyId,
                                vid, title, pic);
                        Log.i(TAG, "addPlayHistory: " + vid + " " + title + " ");
                        if (HistoryManager.getInstance(getApplicationContext()).isInHistory(vid)) {
                            showShortToast("save history success");
                        } else {
                            showShortToast("save history failed");
                        }
                    }
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private NeedReplyEvent replyEvent;
    private NeedCommentEvent commentEvent;
    @Subscribe
    public void handleCommentEvent(NeedCommentEvent event) {
        Log.i(TAG, "handleCommentEvent: ss ");
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        commentEvent = event;
        editText.requestFocus();
        editText.setHint("@"+event.commentData.getBean().getUsername());
        InputUtil.ShowKeyboard(editText);
    }

    @Subscribe
    public void handleReplyEvent(NeedReplyEvent event) {
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        replyEvent = event;
        Log.i(TAG, "handleReplyEvent: ");
        editText.requestFocus();
        editText.setHint("@"+event.replyData.getReplyer().getNickName());
        InputUtil.ShowKeyboard(editText);
    }

    @Subscribe
    public void handlePageEvent(PageSelectEvent event) {
        Log.i(TAG, "handlePageEvent: " + event.pos);
        if (event.pos == 1) {
            editContainer.setVisibility(View.VISIBLE);
        } else {
            editContainer.setVisibility(View.GONE);
        }
    }
    
    @Subscribe
    public void handleCollectEvent(AddCollectEvent event) {
        Log.i(TAG, "handleCollectEvent: ");
        if (LoginManager.hasLogin(getApplicationContext())) {
            int isCollect = 0;
            try {
                isCollect = Integer.parseInt(videoDetailBean.getIs_collect());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (isCollect == 0) {
                addCollect();
            } else {
                delCollect();
            }
        } else {
            LoginManager.startLogin(this);
        }
    }

    @Subscribe
    public void handleCacheEvent(CacheEvent event) {
        Log.i(TAG, "onCacheClick: ");
        if (!LoginManager.hasCommentRight(getApplicationContext())) {
            if (!LoginManager.hasLogin(getApplicationContext())) {
                LoginManager.startLogin(this);
            } else if (LoginManager.hasMobile(getApplicationContext())) {
                LoginManager.startChangeMobile(this);
            } else {
                showShortToast(getString(R.string.no_comment_right));
            }
        } else {
            DownloadInfo downloadInfo = CacheUtil.getDownloadInfo(this,
                    videoDetailBean.getFile_url());
            if (downloadInfo == null) {
                if (CacheUtil.cacheVideo(getApplicationContext(), videoDetailBean)) {
                    showShortToast("已添加至缓存列表");
                } else {
                    showShortToast("缓存失败");
                }
            } else {
                showShortToast("已在缓存中");
            }
        }
    }

    @Subscribe
    public void handleHistoryEvent(AddHistoryEvent event) {
        if (LoginManager.hasLogin(getApplicationContext())) {
            addPlayHistory(videoDetailBean);
        }
    }

    @Subscribe
    public void handleShareEvent(ShareEvent event) {
        Log.i(TAG, "handleShareEvent: ");
        shareContent = new ShareContent();
        shareContent.mText = videoDetailBean.getTitle();
        UMWeb web = new UMWeb(String.format(UrlConstance.SHARE_BASE_URL, videoDetailBean.getId()));
        web.setTitle(videoDetailBean.getTitle());
        web.setThumb(new UMImage(this, R.mipmap.app_launcher));
        web.setDescription(videoDetailBean.getChannel_name());
        shareContent.mMedia = web;
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        shareAction.open(config);
    }

    private void addCollect() {
        if (!LoginManager.hasLogin(getApplicationContext())) {
            return;
        }
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<AddItemBean>> call = userApiService.addCollect(token, videoId);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data, String msg, int state) {
                    showShortToast("已收藏");
                    getVideoDetail();
                    EventBus.getDefault().post(new FreshInfoEvent());
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private void delCollect() {
        if (!LoginManager.hasLogin(getApplicationContext())) {
            return;
        }
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        if (!TextUtils.isEmpty(videoId)) {
            Call<ResponseBean<List<Object>>> call
                    = userApiService.deleteCollect(token, videoId);
            if (call != null) {
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data, String msg, int state) {
                        Log.i(TAG, "onSuccess: ");
                        getVideoDetail();
                        EventBus.getDefault().post(new FreshInfoEvent());
                    }
                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                    }
                });
            }
        }
    }

    private void addShare() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<AddItemBean>> call = userApiService.addShare(token, videoId);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data, String msg, int state) {
                    getVideoDetail();
                    EventBus.getDefault().post(new FreshInfoEvent());
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<VideoDetailActivity> mActivity;

        private CustomShareListener(VideoDetailActivity activity) {
            mActivity = new WeakReference(activity);
        }
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                if (mActivity != null && mActivity.get() != null) {
                    mActivity.get().showShortToast(platform + " 收藏成功啦");
                    mActivity.get().addShare();
                }
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    if (mActivity != null && mActivity.get() != null) {
                        mActivity.get().showShortToast(platform + " 分享成功啦");
                        mActivity.get().addShare();
                    }
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                if (mActivity != null && mActivity.get() != null) {
                    mActivity.get().showShortToast(platform + " 分享失败啦");
                }
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (mActivity != null && mActivity.get() != null) {
                mActivity.get().showShortToast(platform + " 分享取消了");
            }
        }
    }
    @Override
    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        ShareContent content = shareContent;
        if (content == null) {
            return;
        }
        switch (share_media) {
            case WEIXIN:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
            case WEIXIN_CIRCLE:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
            case SINA:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
            case QZONE:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
            case QQ:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
            default:
                new ShareAction(this).setPlatform(share_media).setCallback(customShareListener)
                        .setShareContent(content)
                        .share();
                break;
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    protected boolean useActionBar() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!NiceVideoPlayerManager.instance().onBackPressd()) {
            super.onBackPressed();
        } else {
            if (!useActionBar() && getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerFragment != null) {
            playerFragment.release();
        }
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        shareAction.close();
    }
}
