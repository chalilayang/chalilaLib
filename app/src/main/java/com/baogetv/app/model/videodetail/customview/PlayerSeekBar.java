package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayerSeekBar extends View {
    public PlayerSeekBar(Context context) {
        this(context, null);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.play_seekbar_bg);
    }
}
