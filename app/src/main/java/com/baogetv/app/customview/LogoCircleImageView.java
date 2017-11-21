package com.baogetv.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.baogetv.app.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class LogoCircleImageView extends CircleImageView {
    private Drawable proLogo;
    private Drawable one;
    private Drawable two;
    private Drawable three;
    private Drawable four;
    private Drawable five;
    public LogoCircleImageView(Context context) {
        this(context, null);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        one = context.getResources().getDrawable(R.mipmap.user_grade_one_icon);
        two = context.getResources().getDrawable(R.mipmap.user_grade_two_icon);
        three = context.getResources().getDrawable(R.mipmap.user_grade_three_icon);
        four = context.getResources().getDrawable(R.mipmap.user_grade_four_icon);
        five = context.getResources().getDrawable(R.mipmap.user_grade_five_icon);
    }

    public void setLogo(int level) {
        switch (level) {
            case 1:
                proLogo = one;
                break;
            case 2:
                proLogo = two;
                break;
            case 3:
                proLogo = three;
                break;
            case 4:
                proLogo = four;
                break;
            case 5:
                proLogo = five;
                break;
            default:
                proLogo = null;
                break;

        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (proLogo != null && width > 0 && height > 0) {
            int left = width - proLogo.getIntrinsicWidth();
            int top = height - proLogo.getIntrinsicHeight();
            int right = width;
            int bottom = height;
            proLogo.setBounds(left, top, right, bottom);
            proLogo.draw(canvas);
        }
    }
}
