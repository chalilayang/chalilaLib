package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.player.PlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

public class VideoPlayerActivity extends BaseActivity {
    private static final String TAG = "VideoPlayerActivity";
    public static final String KEY_VIDEO_PATH = "KEY_VIDEO_PATH";
    private NiceVideoPlayer mNiceVideoPlayer;
    private String videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoPath = getIntent().getStringExtra(KEY_VIDEO_PATH);
    }

    public void init(View root) {
        mNiceVideoPlayer = (NiceVideoPlayer) root.findViewById(R.id.player_surface);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        if (!TextUtils.isEmpty(videoPath)) {
            mNiceVideoPlayer.setUp(videoPath, null);
            Log.i(TAG, "init: " + videoPath);
            NiceVideoPlayerController controller = new PlayerController(this);
            mNiceVideoPlayer.setController(controller);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}
