package com.baogetv.app.model.videodetail.customview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.baogetv.app.model.videodetail.entity.PlayData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/9/7.
 */

public class VideoPlayerView extends TextureView
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener {
    private static final String TAG = VideoPlayerView.class.getSimpleName();
    private MediaPlayer videoPlayer;
    private Surface playSurface;

    private PlayData playData;

    private boolean playerPrepared;

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
        setSurfaceTextureListener(surfaceTextureListener);
        //电源键监听
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        getContext().registerReceiver(mPowerReceiver, filter);
        //home键监听
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        getContext().registerReceiver(mHomePressReceiver, homeFilter);
        videoPlayer = new MediaPlayer();
        videoPlayer.setOnInfoListener(this);
        videoPlayer.setOnPreparedListener(this);
        videoPlayer.setOnErrorListener(this);
        videoPlayer.setOnCompletionListener(this);
        videoPlayer.setOnVideoSizeChangedListener(this);
        videoPlayer.setOnBufferingUpdateListener(this);
    }

    public void setData(PlayData data) {
        this.playData = data;
    }

    public void seek(int time) {
        if (videoPlayer != null) {
            videoPlayer.seekTo(time);
        }
    }

    public void pause() {
        if (videoPlayer != null) {
            videoPlayer.pause();
        }
    }

    public void resume() {
        if (videoPlayer != null) {
            if (playerPrepared) {
                videoPlayer.start();
            } else {
                startPlay();
            }
        }
    }

    public boolean isPlaying() {
        if (videoPlayer != null && playerPrepared) {
            return videoPlayer.isPlaying();
        } else {
            return false;
        }
    }

    public void startPlay() {
        if (playData != null && !videoPlayer.isPlaying()) {
            if (playSurface != null) {
                videoPlayer.reset();
                playerPrepared = false;
                videoPlayer.setSurface(playSurface);
                if (playData.type == PlayData.LOCALE_FILE) {
                    File file = new File(playData.videoPath);
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                        videoPlayer.setDataSource(fis.getFD());
                        videoPlayer.prepareAsync();
                    } catch (IOException e) {
                        Log.i(TAG, "startPlay: " + e);
                    } finally {
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                Log.i(TAG, "startPlay: " + e);
                            }
                        }
                    }
                } else if (playData.type == PlayData.NET) {
                    try {
                        videoPlayer.setDataSource(getContext(), Uri.parse(playData.videoPath));
                        videoPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (videoPlayer != null) {
            playerPrepared = true;
            videoPlayer.start();
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    private TextureView.SurfaceTextureListener surfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            playSurface = new Surface(surface);
            Log.i(TAG, "onSurfaceTextureAvailable: ");
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Log.i(TAG,"onSurfaceTextureSizeChanged");
            Log.i(TAG, "onSurfaceTextureSizeChanged: ");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            playSurface = null;
            Log.i(TAG, "onSurfaceTextureDestroyed: ");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            // Log.i(TAG,"onSurfaceTextureUpdated");
            Log.i(TAG, "onSurfaceTextureUpdated: ");
        }
    };

    private BroadcastReceiver mPowerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if(Intent.ACTION_SCREEN_OFF.equals(action)) {
//                internalPause();
            }
        }
    };
    private BroadcastReceiver mHomePressReceiver = new BroadcastReceiver() {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action != null && action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if(reason != null && reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                    internalPause();
                }
            }
        }
    };

    public void release() {
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
        if(mPowerReceiver != null) {
            try {
                getContext().unregisterReceiver(mPowerReceiver);
            } catch(Exception e) {
                Log.d(TAG,e.getMessage());
            }
            mPowerReceiver = null;
        }
        if(mHomePressReceiver != null) {
            try {
                getContext().unregisterReceiver(mHomePressReceiver);
            } catch(Exception e) {
                Log.d(TAG,e.getMessage());
            }
            mHomePressReceiver = null;
        }
    }

    private SoftReference<OnPlayCallBack> softReference;
    public void setPlayCallBack(OnPlayCallBack callBack) {
        if (callBack != null) {
            softReference = new SoftReference<OnPlayCallBack>(callBack);
        }
    }
    public interface OnPlayCallBack {
        void onPrepared();
        void onPlayUpdate(int pos);
        void onPause();
        void onResume();
    }
}