package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
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
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.AddCollectEvent;
import com.baogetv.app.model.videodetail.event.AddHistoryEvent;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.PageSelectEvent;
import com.baogetv.app.model.videodetail.fragment.PlayerFragment;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class VideoDetailActivity extends BaseActivity {

    private static final String TAG = "VideoDetailActivity";
    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    public static final String KEY_VIDEO_DETAIL = "KEY_VIDEO_DETAIL";
    private VideoDetailFragment videoDetailFragment;
    private PlayerFragment playerFragment;
    private VideoDetailBean videoDetailBean;
    private String videoId;
    private EditText editText;
    private View sendBtn;
    private View editContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_detail);
        videoId = getIntent().getStringExtra(KEY_VIDEO_ID);
        initView();
        initData();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.comment_edit_text);
        sendBtn = findViewById(R.id.comment_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showShortToast("评论不能为空");
                } else {
                    EventBus.getDefault().post(new InputSendEvent(content));
                }
            }
        });
        editContainer = findViewById(R.id.comment_edit_container);
        editContainer.setVisibility(View.GONE);
    }

    private void initData() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<VideoDetailBean>> call = listService.getVideoDetail(videoId);
        if (call != null) {
            call.enqueue(new CustomCallBack<VideoDetailBean>() {
                @Override
                public void onSuccess(VideoDetailBean data) {
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
                public void onFailed(String error) {
                    showError();
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

    private void addPlayHistory(VideoDetailBean bean) {
        if (!LoginManager.hasLogin(getApplicationContext())) {
            return;
        }
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<AddItemBean>> call = userApiService.addHistory(token, bean.getId());
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data) {
                    showShortToast("save history success");
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
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
        addCollect(videoDetailBean);
    }

    @Subscribe
    public void handleHistoryEvent(AddHistoryEvent event) {
        addPlayHistory(videoDetailBean);
    }

    private void addCollect(VideoDetailBean bean) {
        if (!LoginManager.hasLogin(getApplicationContext())) {
            return;
        }
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<AddItemBean>> call = userApiService.addCollect(token, bean.getId());
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data) {
                    showShortToast("已收藏");
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
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
    protected void onDestroy() {
        super.onDestroy();
        if (playerFragment != null) {
            playerFragment.release();
        }
    }
}
