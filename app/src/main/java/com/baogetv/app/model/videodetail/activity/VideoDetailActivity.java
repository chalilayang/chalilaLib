package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.fragment.PlayerFragment;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.parcelables.PageItemData;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends BaseActivity {

    private VideoDetailFragment videoDetailFragment;
    private PlayerFragment playerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_detail);
        init();
        showPlayerFragment();
        showHomeFragment();
    }

    private void init() {

    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (videoDetailFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.simple_introduce), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.comment), 0);
            list.add(pageItemData);
            videoDetailFragment = VideoDetailFragment.newInstance(new VideoDetailData(list));
        }
        transaction.replace(R.id.video_detail_fragment_container, videoDetailFragment).commit();
    }

    private void showPlayerFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (playerFragment == null) {
            playerFragment = PlayerFragment.newInstance();
        }
        transaction.replace(R.id.video_player_fragment_container, playerFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerFragment != null) {
            playerFragment.release();
        }
    }
}
