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
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.model.usercenter.HistoryManager;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.AddCollectEvent;
import com.baogetv.app.model.videodetail.event.AddHistoryEvent;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyEvent;
import com.baogetv.app.model.videodetail.event.PageSelectEvent;
import com.baogetv.app.model.videodetail.fragment.PlayerFragment;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.baogetv.app.util.InputUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;

public class VideoDetailActivity extends BaseActivity {

    private static final String TAG = "VideoDetailActivity";
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
            String vid = bean.getId();
            String title = bean.getTitle();
            String pic = bean.getPic_url();
            HistoryManager.getInstance(getApplicationContext()).saveHistory(
                    vid, title, pic);
            Log.i(TAG, "addPlayHistory: " + vid + " " + title + " ");
            if (HistoryManager.getInstance(getApplicationContext()).isInHistory(vid)) {
                showShortToast("save history success");
            } else {
                showShortToast("save history failed");
            }
        } else {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(getApplicationContext());
            Call<ResponseBean<AddItemBean>> call = userApiService.addHistory(token, bean.getId());
            if (call != null) {
                call.enqueue(new CustomCallBack<AddItemBean>() {
                    @Override
                    public void onSuccess(AddItemBean data, String msg, int state) {
                        showShortToast("save history success");
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                    }
                });
            }
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
                public void onSuccess(AddItemBean data, String msg, int state) {
                    showShortToast("已收藏");
                }

                @Override
                public void onFailed(String error, int state) {
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
