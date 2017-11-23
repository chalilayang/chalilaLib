package com.baogetv.app.model.videodetail.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.customview.PlayerSeekBar;
import com.chalilayang.scaleview.ScaleTextView;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayerController extends NiceVideoPlayerController implements View.OnClickListener {

    private ImageView playBtn;
    private ScaleTextView timeTv;
    private ScaleTextView videoTitle;
    private PlayerSeekBar playerSeekBar;
    private ImageView fullScreenBtn;
    private ImageView shareBtn;
    private ImageView heartBtn;
    private ImageView shootBtn;
    private ImageView lockBtn;
    private View topGroup;
    private View bottomGroup;

    private String timeFormat;
    public PlayerController(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        timeFormat = context.getResources().getString(R.string.player_time_format);
        LayoutInflater.from(context).inflate(
                R.layout.player_controller, this, true);
        playBtn = (ImageView) findViewById(R.id.pause_btn);
        videoTitle = (ScaleTextView) findViewById(R.id.player_title);
        timeTv = (ScaleTextView) findViewById(R.id.player_time);
        fullScreenBtn = (ImageView) findViewById(R.id.full_screen);
        shareBtn = (ImageView) findViewById(R.id.player_share);
        heartBtn = (ImageView) findViewById(R.id.player_thumb_up);
        shootBtn = (ImageView) findViewById(R.id.player_shoot);
        lockBtn = (ImageView) findViewById(R.id.pause_btn);
        playerSeekBar = (PlayerSeekBar) findViewById(R.id.player_seek_bar);
        topGroup = findViewById(R.id.top_group);
        bottomGroup = findViewById(R.id.bottom_group);

        playBtn.setOnClickListener(this);
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
            }
        }
    }

    @Override
    public void setTitle(String title) {
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
        timeTv.setText(NiceUtil.formatTime(length));
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                break;
            case NiceVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                playBtn.setImageResource(R.mipmap.play_pause);
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_PAUSED:
                cancelDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                break;
            case NiceVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                break;
        }
    }

    @Override
    protected void onPlayModeChanged(int playMode) {
        switch (playMode) {
            case NiceVideoPlayer.MODE_NORMAL:
                break;
            case NiceVideoPlayer.MODE_FULL_SCREEN:
                break;
            case NiceVideoPlayer.MODE_TINY_WINDOW:
                break;
        }
    }

    /**
     * 电池状态即电量变化广播接收器
     */
    private BroadcastReceiver mBatterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
//            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
//                // 充电中
//                mBattery.setImageResource(R.drawable.battery_charging);
//            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
//                // 充电完成
//                mBattery.setImageResource(R.drawable.battery_full);
//            } else {
//                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
//                int percentage = (int) (((float) level / scale) * 100);
//                if (percentage <= 10) {
//                    mBattery.setImageResource(R.drawable.battery_10);
//                } else if (percentage <= 20) {
//                    mBattery.setImageResource(R.drawable.battery_20);
//                } else if (percentage <= 50) {
//                    mBattery.setImageResource(R.drawable.battery_50);
//                } else if (percentage <= 80) {
//                    mBattery.setImageResource(R.drawable.battery_80);
//                } else if (percentage <= 100) {
//                    mBattery.setImageResource(R.drawable.battery_100);
//                }
//            }
        }
    };

    @Override
    protected void reset() {
        cancelUpdateProgressTimer();
    }

    @Override
    protected void updateProgress() {
        long position = mNiceVideoPlayer.getCurrentPosition();
        long duration = mNiceVideoPlayer.getDuration();
        int bufferPercentage = mNiceVideoPlayer.getBufferPercentage();
        playerSeekBar.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        playerSeekBar.setProgress(progress);
        String pos = NiceUtil.formatTime(position);
        String dur = NiceUtil.formatTime(duration);
        timeTv.setText(String.format(timeFormat, pos, dur));
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
    private void setTopBottomVisible(boolean visible) {
        topGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
        bottomGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
        topBottomVisible = visible;
        if (visible) {
            if (!mNiceVideoPlayer.isPaused() && !mNiceVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }
    private boolean topBottomVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;
    /**
     * 开启top、bottom自动消失的timer
     */
    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(8000, 8000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setTopBottomVisible(false);
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
