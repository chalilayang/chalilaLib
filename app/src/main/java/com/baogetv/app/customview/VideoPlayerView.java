package com.baogetv.app.customview;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.baogetv.app.R;

import java.io.IOException;

/**
 * Created by chalilayang on 2017/9/7.
 */

public class VideoPlayerView extends SurfaceView
        implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener {
    private static final String TAG = VideoPlayerView.class.getSimpleName();
    private MediaPlayer videoPlayer;
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
    }

    private void play() {
        try {
            videoPlayer = new MediaPlayer();
            videoPlayer.setOnPreparedListener(this);
            videoPlayer.setSurface(surfaceHolder.getSurface());
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.video);
            videoPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            videoPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
        Log.i(TAG, "surfaceCreated:");
        surfaceHolder = holder;
        play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceHolder = null;
        release();
    }

    public void release() {
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
    }
}