package com.baogetv.app.model.videodetail.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.baogetv.app.R;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.videodetail.customview.CustomSeekBar;
import com.baogetv.app.model.videodetail.event.AddCollectEvent;
import com.baogetv.app.model.videodetail.event.ShareEvent;
import com.baogetv.app.util.FileUtils;
import com.baogetv.app.util.StorageManager;
import com.chalilayang.scaleview.ScaleTextView;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayerController extends NiceVideoPlayerController
        implements View.OnClickListener, CustomSeekBar.OnSeekUpdateListener {

    private static final String TAG = "PlayerController";

    private ScaleTextView videoTitleSmall;
    private ScaleTextView videoTitle;
    private ImageView shareBtn;
    private ImageView heartBtn;
    private ImageView shootBtn;

    private ImageView playBtn;
    private ImageView lockBtn;

    private View topGroup;

    private View smallScreenController;
    private CustomSeekBar playerSeekBarSmall;
    private ScaleTextView timeTvSmall;
    private ScaleTextView durationTvSmall;
    private ImageView fullScreenBtnSmall;

    private String timeFormat;

    public PlayerController(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        timeFormat = context.getResources().getString(R.string.player_time_format);
        LayoutInflater.from(context).inflate(
                R.layout.player_controller, this, true);
        findViewById(R.id.full_screen_back).setOnClickListener(this);
        playBtn = (ImageView) findViewById(R.id.pause_btn);
        videoTitleSmall = (ScaleTextView) findViewById(R.id.player_title_small);
        videoTitle = (ScaleTextView) findViewById(R.id.player_title_fullscreen);

        shareBtn = (ImageView) findViewById(R.id.player_share);
        heartBtn = (ImageView) findViewById(R.id.player_add_collect);
        shootBtn = (ImageView) findViewById(R.id.player_shoot);
        shootBtn.setVisibility(GONE);
        shootBtn.setOnClickListener(this);
        shareBtn.setVisibility(GONE);
        shareBtn.setOnClickListener(this);
        heartBtn.setVisibility(GONE);
        heartBtn.setOnClickListener(this);

        lockBtn = (ImageView) findViewById(R.id.player_lock);
        screenLocked = false;
        lockBtn.setImageResource(R.mipmap.player_unlock);
        lockBtn.setVisibility(GONE);

        topGroup = findViewById(R.id.top_group);

        smallScreenController = findViewById(R.id.small_screen_controller);
        smallScreenController.setVisibility(VISIBLE);
        playerSeekBarSmall = (CustomSeekBar) findViewById(R.id.player_seek_bar_small);
        playerSeekBarSmall.setOnSeekUpdateListener(this);
        timeTvSmall = (ScaleTextView) findViewById(R.id.player_time_small);
        durationTvSmall = (ScaleTextView) findViewById(R.id.player_duration_small);
        fullScreenBtnSmall = (ImageView) findViewById(R.id.small_screen_btn);
        fullScreenBtnSmall.setOnClickListener(this);

        setOnClickListener(this);
        playBtn.setOnClickListener(this);
        lockBtn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pause_btn) {
            if (mNiceVideoPlayer.isPlaying() || mNiceVideoPlayer.isBufferingPlaying()) {
                mNiceVideoPlayer.pause();
            } else if (mNiceVideoPlayer.isPaused() || mNiceVideoPlayer.isBufferingPaused()) {
                mNiceVideoPlayer.restart();
            } else if (mNiceVideoPlayer.isIdle()) {
                mNiceVideoPlayer.start();
            } else if (mNiceVideoPlayer.isCompleted()) {
                mNiceVideoPlayer.restart();
            }
        } else if (v.getId() == R.id.small_screen_btn) {
            if (mNiceVideoPlayer.isNormal() || mNiceVideoPlayer.isTinyWindow()) {
                mNiceVideoPlayer.enterFullScreen();
            } else if (mNiceVideoPlayer.isFullScreen()) {
                mNiceVideoPlayer.exitFullScreen();
            }
        } else if (v.getId() == R.id.full_screen_back) {
            if (mNiceVideoPlayer.isFullScreen()) {
                mNiceVideoPlayer.exitFullScreen();
            } else {
                NiceUtil.scanForActivity(mContext).onBackPressed();
            }
        } else if (v.getId() == R.id.player_lock) {
            if (mNiceVideoPlayer.isFullScreen()) {
                if (screenLocked) {
                    lockBtn.setImageResource(R.mipmap.player_unlock);
                    screenLocked = false;
                } else {
                    lockBtn.setImageResource(R.mipmap.player_lock);
                    screenLocked = true;
                }
                setControllerVisibility(true);
            }
        } else if (v.getId() == R.id.player_shoot) {
            if (!isTryingShoot) {
                String path = StorageManager.getSavePath()
                        + File.separator + StorageManager.generateFileName() + ".png";
                isTryingShoot = mNiceVideoPlayer.tryToShoot(path);
            }
        } else if (v.getId() == R.id.player_add_collect) {
            EventBus.getDefault().post(new AddCollectEvent());
        } else if (v.getId() == R.id.player_share) {
            EventBus.getDefault().post(new ShareEvent());
        } else if (v == this) {
            if (mNiceVideoPlayer.isPlaying()
                    || mNiceVideoPlayer.isPaused()
                    || mNiceVideoPlayer.isBufferingPlaying()
                    || mNiceVideoPlayer.isBufferingPaused()
                    || mNiceVideoPlayer.isCompleted()) {
                setControllerVisibility(!controllerVisible);
            }
        }
    }

    private boolean isTryingShoot;

    /**
     * 非主线程
     * @param filePath
     */
    @Override
    public void onShootGot(String filePath) {
        super.onShootGot(filePath);
        final String mPath = filePath;
        Log.i(TAG, "onShootGot: " + filePath);
        isTryingShoot = false;
        post(new Runnable() {
            @Override
            public void run() {
                CustomToastUtil.makeShort(getContext(), "已保存至相册：" + mPath);
                FileUtils.scanFile(getContext(), mPath);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (screenLocked) {
            lockBtn.setVisibility(VISIBLE);
            return true;
        } else {
            return super.onTouch(v, event);
        }
    }

    @Override
    public void setTitle(String title) {
        videoTitleSmall.setText(title);
        videoTitle.setText(title);
    }

    @Override
    public void setImage(@DrawableRes int resId) {

    }

    @Override
    public ImageView imageView() {
        return null;
    }

    @Override
    public void setVideoDuration(long length) {
        timeTvSmall.setText(NiceUtil.formatTime(length));
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                playBtn.setImageResource(R.mipmap.play_start);
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                playBtn.setImageResource(R.mipmap.play_pause);
                break;
            case NiceVideoPlayer.STATE_PREPARED:
                playBtn.setImageResource(R.mipmap.play_pause);
                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                playBtn.setImageResource(R.mipmap.play_pause);
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_PAUSED:
                playBtn.setImageResource(R.mipmap.play_start);
                cancelDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                playBtn.setImageResource(R.mipmap.play_pause);
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                playBtn.setImageResource(R.mipmap.play_pause);
                break;
            case NiceVideoPlayer.STATE_ERROR:
                playBtn.setImageResource(R.mipmap.play_start);
                cancelUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                playBtn.setImageResource(R.mipmap.play_start);
                cancelUpdateProgressTimer();
                break;
        }
    }

    @Override
    protected void onPlayModeChanged(int playMode) {
        switch (playMode) {
            case NiceVideoPlayer.MODE_FULL_SCREEN:
                fullScreenBtnSmall.setImageResource(R.mipmap.player_small_screen);
                lockBtn.setVisibility(VISIBLE);
                heartBtn.setVisibility(VISIBLE);
                shareBtn.setVisibility(VISIBLE);
                shootBtn.setVisibility(VISIBLE);
                videoTitle.setVisibility(VISIBLE);
                videoTitleSmall.setVisibility(GONE);
                break;
            case NiceVideoPlayer.MODE_NORMAL:
            case NiceVideoPlayer.MODE_TINY_WINDOW:
                fullScreenBtnSmall.setImageResource(R.mipmap.full_screen_icon);
                screenLocked = false;
                lockBtn.setVisibility(GONE);
                heartBtn.setVisibility(GONE);
                shareBtn.setVisibility(GONE);
                shootBtn.setVisibility(GONE);
                videoTitle.setVisibility(GONE);
                videoTitleSmall.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    protected void reset() {
        cancelUpdateProgressTimer();
    }

    @Override
    protected void updateProgress() {
        long position = mNiceVideoPlayer.getCurrentPosition();
        long duration = mNiceVideoPlayer.getDuration();
        int bufferPercentage = mNiceVideoPlayer.getBufferPercentage();
        playerSeekBarSmall.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        playerSeekBarSmall.setProgress(progress);
        String pos = NiceUtil.formatTime(position);
        String dur = NiceUtil.formatTime(duration);
        timeTvSmall.setText(pos);
        durationTvSmall.setText(dur);
    }

    @Override
    public void onSeekUpdate(int value, boolean fromUser) {
        if (fromUser && mNiceVideoPlayer != null) {
            long pos = value * mNiceVideoPlayer.getDuration() / playerSeekBarSmall.getMax();
            mNiceVideoPlayer.seekTo(pos);
        }
        playerSeekBarSmall.setProgress(value);
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {

    }

    @Override
    protected void hideChangePosition() {

    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {

    }

    @Override
    protected void hideChangeVolume() {

    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {

    }

    @Override
    protected void hideChangeBrightness() {

    }

    /**
     * 设置top、bottom的显示和隐藏
     *
     * @param visible true显示，false隐藏.
     */
    private void setControllerVisibility(boolean visible) {
        controllerVisible = visible;
        if (mNiceVideoPlayer.isFullScreen()) {
            lockBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else {
            lockBtn.setVisibility(View.GONE);
        }
        if (!screenLocked) {
            topGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
            smallScreenController.setVisibility(visible ? View.VISIBLE : View.GONE);
            playBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else {
            topGroup.setVisibility(View.GONE);
            smallScreenController.setVisibility(View.GONE);
            playBtn.setVisibility(View.GONE);
        }
        if (visible) {
            if (!mNiceVideoPlayer.isPaused() && !mNiceVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }
    private boolean controllerVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;
    /**
     * 开启top、bottom自动消失的timer
     */
    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(6000, 6000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setControllerVisibility(false);
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }

    /**
     * 取消top、bottom自动消失的timer
     */
    private void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }
}
