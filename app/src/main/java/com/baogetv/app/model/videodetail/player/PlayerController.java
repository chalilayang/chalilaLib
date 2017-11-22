package com.baogetv.app.model.videodetail.player;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.customview.PlayerSeekBar;
import com.chalilayang.scaleview.ScaleTextView;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayerController extends NiceVideoPlayerController {

    private ImageView playBtn;
    private ScaleTextView timeTv;
    private PlayerSeekBar playerSeekBar;
    private ImageView fullScreenBtn;

    public PlayerController(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.player_controller, this, true);
    }

    @Override
    public void setTitle(String title) {

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

    }

    @Override
    protected void onPlayStateChanged(int playState) {

    }

    @Override
    protected void onPlayModeChanged(int playMode) {

    }

    @Override
    protected void reset() {

    }

    @Override
    protected void updateProgress() {

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
}
