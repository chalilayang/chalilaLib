package com.baogetv.app.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/12/19.
 */

public class PressImageView extends AppCompatImageView {
    private static final String TAG = "PressImageView";
    public PressImageView(Context context) {
        this(context, null);
    }

    public PressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PressImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRef == null || mRef.get() == null) {
            return super.onTouchEvent(event);
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setColorFilter(Color.parseColor("#77000000")); // 设置滤镜效果
                    break;
                case MotionEvent.ACTION_UP:
                    mRef.get().onClick(this);
                case MotionEvent.ACTION_CANCEL:
                    clearColorFilter();
                    break;
            }
            return true;
        }
    }

    private SoftReference<OnClickBack> mRef;
    public void setOnClickBack(OnClickBack back) {
        Log.i(TAG, "setOnClickBack: ");
        mRef = new SoftReference<OnClickBack>(back);
    }
    public interface OnClickBack {
        void onClick(View view);
    }
}
