package com.baogetv.app.customview;

import android.content.Context;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class LogoCircleImageView extends CircleImageView {
    public LogoCircleImageView(Context context) {
        this(context, null);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
    }

    public void setLogo(int level) {

    }
}
