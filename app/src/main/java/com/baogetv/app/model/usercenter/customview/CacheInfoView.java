package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.util.FileUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleFrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class CacheInfoView extends ScaleFrameLayout {

    private static final String TAG = "CacheInfoView";
    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_DOWNLOADED = 2;
    public static final int STATE_PAUSED = 3;
    public static final int STATE_WAITING = 4;
    public static final int STATE_ERROR = 5;

    @IntDef(
            value = {
                    STATE_DOWNLOADING,
                    STATE_DOWNLOADED,
                    STATE_PAUSED,
                    STATE_WAITING,
                    STATE_ERROR
            })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {}
    
    private TextView titleTv;
    private TextView stateTv;
    private TextView curSizeTv;
    private ProgressBar progressBar;
    private String sizeFormat;

    private String stateDownloading;
    private String stateDownloaded;
    private String statePausing;
    private String stateWaiting;
    private String stateError;
    private ForegroundColorSpan colorSpan;

    public CacheInfoView(Context context) {
        this(context, null);
    }

    public CacheInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CacheInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        sizeFormat = getResources().getString(R.string.download_size_format);
        stateDownloaded = getResources().getString(R.string.downloaded);
        stateDownloading = getResources().getString(R.string.downloading);
        statePausing = getResources().getString(R.string.pause_download);
        stateWaiting = getResources().getString(R.string.download_waiting);
        stateError = getResources().getString(R.string.download_error);
        View root = LayoutInflater.from(context).inflate(R.layout.cache_info_layout, this);
        titleTv = root.findViewById(R.id.cache_title);
        stateTv = root.findViewById(R.id.cache_state);
        curSizeTv = root.findViewById(R.id.cache_size);
        progressBar = root.findViewById(R.id.cache_progress);
        colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.reshape_red));
    }

    public void setTitleTv(String title) {
        titleTv.setText(title);
    }

    private long fileSize;
    private long curSize;
    public void setCacheState(@Event int state) {
        switch (state) {
            case STATE_DOWNLOADED:
                progressBar.setVisibility(INVISIBLE);
                curSizeTv.setVisibility(INVISIBLE);
                String size = FileUtil.formatFileSize(fileSize);
                String stateDesc = String.format(sizeFormat, stateDownloaded, size);
                SpannableString content = new SpannableString(stateDesc);
                content.setSpan(
                        colorSpan, 0, stateDownloaded.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                stateTv.setText(content);
                break;
            case STATE_ERROR:
                progressBar.setVisibility(INVISIBLE);
                curSizeTv.setVisibility(INVISIBLE);
                stateTv.setText(stateError);
                break;
            case STATE_DOWNLOADING:
            case STATE_WAITING:
            case STATE_PAUSED:
                if (state == STATE_DOWNLOADING) {
                    stateTv.setText(stateDownloading);
                } else if (state == STATE_WAITING) {
                    stateTv.setText(stateWaiting);
                } else {
                    stateTv.setText(statePausing);
                }
                progressBar.setVisibility(VISIBLE);
                curSizeTv.setVisibility(VISIBLE);
                int progress = 0;
                if (fileSize > 0) {
                    float rate = curSize * 1.0f * progressBar.getMax() / fileSize;
                    progress = Math.min(progressBar.getMax(), (int) rate);
                }
                progressBar.setProgress(progress);
                size = FileUtil.formatFileSize(curSize);
                String fileSizeStr = FileUtil.formatFileSize(fileSize);
                stateDesc = String.format(sizeFormat, size, fileSizeStr);
                content = new SpannableString(stateDesc);
                content.setSpan(
                        colorSpan, 0, size.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                curSizeTv.setText(content);
                break;
        }
    }

    public void update(long progressSize, long sum_size) {
        curSize = progressSize;
        fileSize = sum_size;
    }
}
