package com.baogetv.app.customview;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by chalilayang on 2017/9/7.
 */

public class VideoPlayerView extends SurfaceView
        implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener {
    private static final String TAG = VideoPlayerView.class.getSimpleName();
    private MediaPlayer videoPlayer;
    private MediaPlayer musicPlayer;
    private boolean surfaceReady;
    private SurfaceHolder surfaceHolder;

    public VideoPlayerView(Context context) {
        super(context);
        init();
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        videoPlayer = new MediaPlayer();
        surfaceReady = false;
        videoPlayer = new MediaPlayer();
        videoPlayer.setOnPreparedListener(this);
    }

    private void play() {
        if (surfaceReady) {
            try {
                videoPlayer.reset();
                videoPlayer.setSurface(surfaceHolder.getSurface());
                AssetManager assetManager = this.getContext().getAssets();
                AssetFileDescriptor fileDescriptor = assetManager.openFd("video.mp4");
                videoPlayer.setDataSource(
                        fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                        fileDescriptor.getStartOffset());
                videoPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer iMediaPlayer) {
        if (videoPlayer != null) {
            videoPlayer.start();
            videoPlayer.setLooping(true);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: ddddd ");
        surfaceHolder = holder;
        surfaceReady = true;
        play();
    }

    private void pause() {
        if (videoPlayer != null) {
            videoPlayer.pause();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceHolder = null;
        surfaceReady = false;
        pause();
    }

    public void release() {
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }
}