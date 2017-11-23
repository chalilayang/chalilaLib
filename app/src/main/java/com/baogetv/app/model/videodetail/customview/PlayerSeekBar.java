package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/11/22.
 */

public class PlayerSeekBar extends View {
    private Paint seekPaint;
    private LinearGradient gradient;
    private int startColor;
    private int endColor;

    private float progress;
    public PlayerSeekBar(Context context) {
        this(context, null);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        seekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        startColor = getResources().getColor(R.color.play_seek_bar_start);
        endColor =  getResources().getColor(R.color.play_seek_bar_end);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gradient == null) {
            gradient = new LinearGradient(
                    0, 0, canvas.getWidth(), 0,
                    startColor, endColor, Shader.TileMode.CLAMP);
            seekPaint.setShader(gradient);
        }
        int right = (int) (canvas.getWidth() * progress / 100.0f);
        canvas.drawRect(0, 0, right, canvas.getHeight(), seekPaint);
    }

    public void setProgress(int p) {
        progress = p;
        invalidate();
    }

    public void setSecondaryProgress(float p) {
//        progress = p;
//        invalidate();
    }
}
