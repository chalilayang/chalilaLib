package com.baogetv.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleCalculator;

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
    private Drawable oneYellow;
    private Drawable twoYellow;
    private Drawable threeYellow;
    private Drawable fourYellow;
    private Drawable fiveYellow;

    private Paint circlePaint;
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
        oneYellow = context.getResources().getDrawable(R.mipmap.user_grade_one_yellow_icon);
        twoYellow = context.getResources().getDrawable(R.mipmap.user_grade_two_yellow_icon);
        threeYellow = context.getResources().getDrawable(R.mipmap.user_grade_three_yellow_icon);
        fourYellow = context.getResources().getDrawable(R.mipmap.user_grade_four_yellow_icon);
        fiveYellow = context.getResources().getDrawable(R.mipmap.user_grade_five_yellow_icon);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(getResources().getColor(R.color.reshape_red));
        int paintWidth = ScaleCalculator.getInstance(context).scaleWidth(4);
        circlePaint.setStrokeWidth(paintWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    public void setLogo(int grade, int level) {
        switch (level) {
            case 1:
                proLogo = oneYellow;
                if (grade == 3) {
                    proLogo = one;
                }
                break;
            case 2:
                proLogo = twoYellow;
                if (grade == 3) {
                    proLogo = two;
                }
                break;
            case 3:
                proLogo = threeYellow;
                if (grade == 3) {
                    proLogo = three;
                }
                break;
            case 4:
                proLogo = fourYellow;
                if (grade == 3) {
                    proLogo = four;
                }
                break;
            case 5:
                proLogo = fiveYellow;
                if (grade == 3) {
                    proLogo = five;
                }
                break;
            default:
                proLogo = null;
                break;

        }
        if (grade == 3) {
            circlePaint.setColor(getResources().getColor(R.color.reshape_red));
        } else {
            circlePaint.setColor(getResources().getColor(R.color.transparent));
        }
        invalidate();
    }

    public void setLogo(String gradeStr, String levelStr) {
        int grade = Integer.parseInt(gradeStr);
        int level = Integer.parseInt(levelStr);
        setLogo(grade, level);
    }

    public void setLogo(String levelStr) {
        int grade = 0;
        int level = Integer.parseInt(levelStr);
        setLogo(grade, level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (proLogo != null && width > 0 && height > 0) {
            float cx = width / 2.0f;
            float cy = height / 2.0f;
            canvas.drawCircle(cx, cy, cx - circlePaint.getStrokeWidth()/2, circlePaint);
            int left = width - proLogo.getIntrinsicWidth();
            int top = height - proLogo.getIntrinsicHeight();
            int right = width;
            int bottom = height;
            proLogo.setBounds(left, top, right, bottom);
            proLogo.draw(canvas);
        }
    }
}
