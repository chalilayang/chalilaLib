package com.chalilayang.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by chalilayang on 2017/7/8.
 */

public class CircleImageView extends AppCompatImageView {
    private Paint mPaintCircle;
    private Paint mPaintBackgroud;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;
    private int mWidth;
    private int mHeight;
    private int mRadius;

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintBackgroud = new Paint();
        mPaintBackgroud.setAntiAlias(true);
        mPaintBackgroud.setStyle(Paint.Style.FILL);
    }

    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int bitmapSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
        float scale = mWidth * 1.0f / bitmapSize;
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaintCircle.setShader(mBitmapShader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        int mCircleSize = Math.min(mHeight, mWidth);
        mRadius = mCircleSize / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() != null) {
            setBitmapShader();
            canvas.drawRect(0, 0, mWidth, mHeight, mPaintBackgroud);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintCircle);
        } else {
            super.onDraw(canvas);
        }
    }
}
