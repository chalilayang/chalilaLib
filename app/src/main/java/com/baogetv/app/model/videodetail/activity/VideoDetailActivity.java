package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.fragment.PlayerFragment;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class VideoDetailActivity extends BaseActivity {

    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    public static final String KEY_VIDEO_DETAIL = "KEY_VIDEO_DETAIL";
    private VideoDetailFragment videoDetailFragment;
    private PlayerFragment playerFragment;
    private VideoDetailBean videoDetailBean;
    private String videoId;
    private EditText editText;
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

    @Override
    public void onBackPressed() {
        if (!NiceVideoPlayerManager.instance().onBackPressd()) {
            super.onBackPressed();
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
