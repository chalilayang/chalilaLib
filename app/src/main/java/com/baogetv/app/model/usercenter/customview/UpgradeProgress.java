package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/11/12.
 */

public class UpgradeProgress extends View {
    public int percent = 0;
    private Paint outlinePaint;
    private Paint innerPaint;
    private RectF outRect;
    private RectF inRect;
    public UpgradeProgress(Context context) {
        this(context, null);
    }

    public UpgradeProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpgradeProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(context.getResources().getColor(R.color.upgrade_progress_color));
        outlinePaint.setStyle(Paint.Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
        outlinePaint.setPathEffect(effects);
        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setColor(context.getResources().getColor(R.color.upgrade_progress_color));
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outRect = new RectF();
        inRect = new RectF();
    }

    public void setUpGradeProgress(int p) {
        if (p >= 0 && p <= 100) {
            percent = p;
            invalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        outRect.left = 0;
        outRect.top = 0;
        outRect.right = canvas.getWidth();
        outRect.bottom = canvas.getHeight();
        canvas.drawRoundRect(outRect, canvas.getHeight()/2, canvas.getHeight()/2, outlinePaint);
        inRect.left = 0;
        inRect.top = 0;
        inRect.right = canvas.getWidth() * percent * 1.0f / 100;
        inRect.bottom = canvas.getHeight();
        canvas.drawRoundRect(inRect, canvas.getHeight()/2, canvas.getHeight()/2, innerPaint);
    }
}
