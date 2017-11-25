package com.baogetv.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/11/14.
 */

public class LogoImageView extends AppCompatImageView {
    private Drawable proLogo;
    private boolean proLogoVisible;

    private Drawable chnLogo;
    private boolean chnLogoVisible;

    private Drawable mengceng;
    private boolean mengcengVisible;

    private int marginForCHNLogo;

    public LogoImageView(Context context) {
        this(context, null);
    }

    public LogoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setProLogoVisible(boolean flag) {
        if (flag != proLogoVisible) {
            proLogoVisible = flag;
            invalidate();
        }
    }

    public void setChnLogoVisible(boolean flag) {
        if (flag != chnLogoVisible) {
            chnLogoVisible = flag;
            invalidate();
        }
    }

    public void setMengCengVisible(boolean flag) {
        if (flag != mengcengVisible) {
            mengcengVisible = flag;
            invalidate();
        }
    }

    private void init(Context context) {
        marginForCHNLogo = ScaleCalculator.getInstance(context).scaleWidth(30);
        proLogo = getResources().getDrawable(R.mipmap.pro_logo);
        chnLogo = getResources().getDrawable(R.mipmap.chinese_logo);
        mengceng = getResources().getDrawable(R.mipmap.mengceng);
        mengcengVisible = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (mengcengVisible && width > 0 && height > 0) {
            int left = 0;
            int top = 0;
            int right = width;
            int bottom = height;
            mengceng.setBounds(left, top, right, bottom);
            mengceng.draw(canvas);
        }
        if (proLogoVisible && width > 0 && height > 0) {
            int left = width - proLogo.getIntrinsicWidth();
            int top = 0;
            int right = width;
            int bottom = proLogo.getIntrinsicHeight();
            proLogo.setBounds(left, top, right, bottom);
            proLogo.draw(canvas);
        }

        if (chnLogoVisible && width > 0 && height > 0) {
            int left = width - chnLogo.getIntrinsicWidth() - marginForCHNLogo;
            int top = 0;
            int right = width - marginForCHNLogo;
            int bottom = chnLogo.getIntrinsicHeight();
            chnLogo.setBounds(left, top, right, bottom);
            chnLogo.draw(canvas);
        }
    }
}
