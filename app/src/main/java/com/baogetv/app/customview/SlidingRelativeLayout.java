package com.baogetv.app.customview;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by chalilayang on 2017/6/30.
 */

public class SlidingRelativeLayout extends RelativeLayout
        implements ViewTreeObserver.OnGlobalLayoutListener{
    private static final String TAG = SlidingRelativeLayout.class.getSimpleName();
    private int fixHeight;
    private static final int ANI_DURATION = 300;

    public SlidingRelativeLayout(Context context) {
        this(context, null);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        fixHeight = getHeight();
        Log.i(TAG, "onGlobalLayout: fixHeight " + fixHeight);
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    public void show() {
        Log.i(TAG, "show: " + getTranslationY() + " " + fixHeight);
        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(
                        this, "translationY", fixHeight, 0);
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.setDuration(ANI_DURATION);
//        objectAnimator.setStartDelay(100);
        objectAnimator.start();
    }

    private TimeInterpolator timeInterpolator = new AccelerateDecelerateInterpolator();
    public void hide() {
        Log.i(TAG, "hide: " + getTranslationY() + " " + fixHeight);
        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(
                this, "translationY", 0, fixHeight);
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.setDuration(ANI_DURATION);
        objectAnimator.start();
    }
}
