package com.chalilayang.customview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by chalilayang on 2017/7/8.
 */

public class CircleImageView extends de.hdodenhof.circleimageview.CircleImageView {

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
